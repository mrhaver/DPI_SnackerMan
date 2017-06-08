package com.frankhaver.snackercentrale.controllers;

import com.frankhaver.snackercentrale.gateways.SnackerCentraleGateway;
import com.frankhaver.snackermandomain.model.Snack;
import com.frankhaver.snackermaninterfaces.implementations.MessageReceiverJMSImpl;
import com.frankhaver.snackermaninterfaces.utils.ConnectionUtils;
import com.frankhaver.snackermaninterfaces.utils.JSONUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CentraleController extends AnchorPane {

    private final SnackerCentraleGateway centraleGateway;

    private final JSONParser parser;

    @FXML
    private Label label;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        // send ordered snacks to snackbar
        JSONObject obj = new JSONObject();
        obj.put(JSONUtils.SNACK, new Snack("frikandelletje").toJSON()); // Snack.toJSONArray(this.getOrderedSnacks())
        this.centraleGateway.getPublisher().publishMessage(obj, ConnectionUtils.SNACKBAR_ORDERS_EXCHANGE);
    }

    public CentraleController() throws IOException, TimeoutException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/CentraleController.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parser = new JSONParser();

        // create snacker centrale gateway
        this.centraleGateway = new SnackerCentraleGateway() {
            @Override
            public void onMessageReceived(byte[] body) {
                try {

                    // read and parse incoming JSON message
                    String message = new String(body, "UTF-8");
                    JSONObject obj = (JSONObject) parser.parse(message);
                    System.out.println(" [x] Received " + obj.toJSONString());

                    switch (JSONUtils.getFirstJSONKey(obj)) {
                        case JSONUtils.SNACK:
                            System.out.println("snack received");
                            Snack orderedSnack = Snack.fromJSON((JSONObject) obj.get(JSONUtils.SNACK));
                            System.out.println(orderedSnack.toString());
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

        this.centraleGateway.getReceiver().receiveMessages(ConnectionUtils.QUEUE_NAME_HELLO);
    }

    /**
     * dirty fix for multiple json values
     * @param obj 
     */
    public void processSnackOrder(JSONObject obj) {
        System.out.println("snack order received");
        ArrayList<Snack> orderedSnacks = Snack.fromJSONArray((JSONArray) obj.get(JSONUtils.SNACK_ORDER));
        System.out.println("amount of snacks: " + orderedSnacks.size());

        // send order object to snackbars
        centraleGateway.getPublisher().publishMessage(obj, ConnectionUtils.SNACKBAR_ORDERS_EXCHANGE);
    }

}
