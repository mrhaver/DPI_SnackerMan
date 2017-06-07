package com.frankhaver.snackbar.controllers;

import com.frankhaver.snackbar.gateways.SnackbarGateway;
import com.frankhaver.snackermaninterfaces.utils.ConnectionUtils;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.json.simple.parser.JSONParser;

public class SnackbarController extends AnchorPane {
    
    private final SnackbarGateway snackbarGateway;

    private final JSONParser parser;
    
    private final String snackbarName;
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            System.out.println(this.snackbarName + ": run another app");
        } catch (Exception ex) {
            Logger.getLogger(SnackbarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public SnackbarController(String snackbarName) throws IOException, TimeoutException{
        this.snackbarName = snackbarName;
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/SnackbarController.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        System.out.println("snackbar started with name: " + this.snackbarName);
        
        // create gateway
        this.parser = new JSONParser();

        final String currentSnackbarName = this.snackbarName;
        this.snackbarGateway = new SnackbarGateway(){
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
    
    
}
