package com.frankhaver.snackbar.controllers;

import com.frankhaver.snackbar.gateways.SnackbarGateway;
import com.frankhaver.snackermandomain.data.SnackGenerator;
import com.frankhaver.snackermandomain.model.Snack;
import com.frankhaver.snackermandomain.model.Snackbar;
import com.frankhaver.snackermaninterfaces.utils.ConnectionUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import org.json.simple.parser.JSONParser;

public class SnackbarController extends AnchorPane {

    private final SnackbarGateway snackbarGateway;

    private final JSONParser parser;

    private final String snackbarName;
    
    private final Snackbar snackbar;

    @FXML
    private Label lblPriceList;
    
    @FXML
    private ListView lvPriceList;

    public SnackbarController(String snackbarName) throws IOException, TimeoutException {
        this.snackbarName = snackbarName;
        
        this.setUpFXML();

        System.out.println("snackbar started with name: " + snackbarName);
        this.snackbar = new Snackbar(snackbarName);
        
        this.fillListView();

        // create gateway
        this.parser = new JSONParser();

        final String currentSnackbarName = this.snackbarName;
        this.snackbarGateway = new SnackbarGateway() {
            @Override
            public void onSubscribedMessage(byte[] body) {
                System.out.println(currentSnackbarName + " message received");
            }
        };
        this.snackbarGateway.getSubscriber().subscribeForMessages(ConnectionUtils.SNACKBAR_ORDERS_EXCHANGE);
    }

    public SnackbarGateway getSnackbarGateway() {
        return snackbarGateway;
    }
    
    /**
     * Fill combobox with all snacks
     */
    private void fillListView() {
        ArrayList<Snack> allSnacks = this.snackbar.getSnacks();
        this.lvPriceList.getItems().clear();
        this.lvPriceList.getItems().addAll(allSnacks);
        this.lvPriceList.getSelectionModel().select(0);
    }
    
    private void setUpFXML(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/SnackbarController.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
