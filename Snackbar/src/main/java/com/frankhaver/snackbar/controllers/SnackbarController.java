package com.frankhaver.snackbar.controllers;

import com.frankhaver.snackbar.gateways.SnackbarGateway;
import com.frankhaver.snackermandomain.model.Snack;
import com.frankhaver.snackermandomain.model.Snackbar;
import com.frankhaver.snackermandomain.model.SnackerMan;
import com.frankhaver.snackermaninterfaces.implementations.MessageReceiverJMSImpl;
import com.frankhaver.snackermaninterfaces.utils.ConnectionUtils;
import com.frankhaver.snackermaninterfaces.utils.JSONUtils;
import com.frankhaver.snackermaninterfaces.utils.SnackLog;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SnackbarController extends AnchorPane {

    private int index = 0;

    private final SnackbarGateway snackbarGateway;

    private final JSONParser parser;

    private final String snackbarName;

    private final Snackbar snackbar;

    @FXML
    private Label lblPriceList;

    @FXML
    private ListView lvPriceList;

    @FXML
    private ListView lvPriceRequests;

    public SnackbarController(String snackbarName) throws IOException, TimeoutException {
        this.snackbarName = snackbarName;

        this.setUpFXML();

        System.out.println("snackbar started with name: " + snackbarName);
        this.snackbar = new Snackbar(snackbarName);

        // fill list view of snacks with price
        this.fillListView();

        // create gateway
        this.parser = new JSONParser();

        final String currentSnackbarName = this.snackbarName;
        this.snackbarGateway = new SnackbarGateway() {
            @Override
            public void onSubscribedMessage(byte[] body) {
                try {

                    // read and parse incoming JSON message
                    String message = new String(body, "UTF-8");
                    JSONObject obj = (JSONObject) parser.parse(message);
                    System.out.println(" [x] Received " + obj.toJSONString());

                    switch (JSONUtils.getFirstJSONKey(obj)) {
                        case JSONUtils.SNACK:

                            break;
                            
                        case JSONUtils.SNACK_ORDER:
                            processSnackOrder(obj);
                            break;

                        case JSONUtils.SNACKER_MAN:
                            processSnackOrder(obj);
                            break;

                        default:
                            System.out.println("Unknown json key");
                    }

                } catch (ParseException | UnsupportedEncodingException ex) {
                    Logger.getLogger(MessageReceiverJMSImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        this.snackbarGateway.getSubscriber().subscribeForMessages(ConnectionUtils.SNACKBAR_ORDERS_EXCHANGE);
    }

    public void processSnackOrder(JSONObject obj) {
        SnackLog.LOG(SnackLog.SNACKBAR, this.snackbarName, "snack order received");

        // read incoming message
        ArrayList<Snack> orderedSnacks = Snack.fromJSONArray((JSONArray) obj.get(JSONUtils.SNACK_ORDER));
        SnackerMan snackerMan = SnackerMan.fromJSON((JSONObject) obj.get(JSONUtils.SNACKER_MAN));

        SnackLog.LOG(SnackLog.SNACKBAR, this.snackbarName, "amount of snacks: " + orderedSnacks.size());

        double price = snackbar.calculateOrderPrice(orderedSnacks);
        SnackLog.LOG(SnackLog.SNACKBAR, this.snackbarName, "Price: " + price);
        addToPriceRequestsOnTop(snackerMan.getName(), price);
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

    /**
     * add a price request on the top of a listview use platform.runlater use
     * Platform.runLater
     *
     * @param clientName
     * @param orderPrice
     */
    public void addToPriceRequestsOnTop(final String clientName, final double orderPrice) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lvPriceRequests.getItems().add(0, "Klant: " + clientName + ", €" + Math.floor(orderPrice * 100) / 100);
                lvPriceRequests.getSelectionModel().select(0);
            }
        });

    }

    private void setUpFXML() {
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
