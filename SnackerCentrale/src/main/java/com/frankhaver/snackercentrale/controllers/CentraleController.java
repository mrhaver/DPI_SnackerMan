package com.frankhaver.snackercentrale.controllers;

import com.frankhaver.snackercentrale.gateways.SnackerCentraleGateway;
import com.frankhaver.snackermandomain.model.Snack;
import com.frankhaver.snackermandomain.model.SnackCentrale;
import com.frankhaver.snackermandomain.model.SnackOrder;
import com.frankhaver.snackermaninterfaces.implementations.MessageReceiverJMSImpl;
import com.frankhaver.snackermaninterfaces.utils.ConnectionUtils;
import com.frankhaver.snackermaninterfaces.utils.JSONUtils;
import com.frankhaver.snackermaninterfaces.utils.SnackLog;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CentraleController extends AnchorPane {

    private final SnackCentrale centrale;
    
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
        
        // create snackcentrale
        this.centrale = new SnackCentrale();

        // create snacker centrale gateway
        this.centraleGateway = new SnackerCentraleGateway() {
            @Override
            public void onMessageReceived(byte[] body) {
                try {

                    // read and parse incoming JSON message
                    String message = new String(body, "UTF-8");
                    JSONObject obj = (JSONObject) parser.parse(message);
                    System.out.println(" [x] Received " + obj.toJSONString());
                    SnackOrder snackOrder;

                    switch (JSONUtils.getFirstJSONKey(obj)) {
                        case JSONUtils.SNACK:
                            System.out.println("snack received");
                            Snack orderedSnack = Snack.fromJSON((JSONObject) obj.get(JSONUtils.SNACK));
                            System.out.println(orderedSnack.toString());
                            break;

                        case JSONUtils.SNACK_ORDER_SNACKBAR:
                            // deserialize set id and save in centrale
                            snackOrder = SnackOrder.fromJSON((JSONObject) obj.get(JSONUtils.SNACK_ORDER_SNACKBAR));
                            snackOrder.setId(centrale.getNextSnackOrderId());
                            
                            centrale.addSnackerManOrder(snackOrder);
                            
                            // serialize and send through
                            obj.put(JSONUtils.SNACK_ORDER_SNACKBAR, snackOrder.toJSON());
                            
                            // send order to all snackbars
                            centraleGateway.getPublisher().publishMessage(obj, ConnectionUtils.SNACKBAR_ORDERS_EXCHANGE);
                            
                            // start timer to send back to client
                            
                            break;

                        case JSONUtils.SNACK_ORDER_CLIENT:
                            // send cheapest order to certain client
                            snackOrder = SnackOrder.fromJSON((JSONObject) obj.get(JSONUtils.SNACK_ORDER_CLIENT));
                            
                            // only if the order is new and id does not yet exist
                            if(!centrale.respondedSnackOrderExists(snackOrder.getId())){
                                processSnackOrderClient(snackOrder);
                            }
                            
                            centrale.addRespondedSnackbarOrder(snackOrder);
                            break;

                        default:
                            System.out.println("Unknown json key");
                    }

                } catch (ParseException | UnsupportedEncodingException ex) {
                    Logger.getLogger(MessageReceiverJMSImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        // listens to client for new orders
        this.centraleGateway.getReceiver().receiveMessages(ConnectionUtils.CLIENT_SEND_ORDER);

        // listens to snackbars for order prices
        this.centraleGateway.getReceiver().receiveMessages(ConnectionUtils.SEND_ORDER_PRICE);
    }

    /**
     * send cheapest order to the certain client
     */
    private void processSnackOrderClient(final SnackOrder snackOrder) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // after x seconds get snackbar orders with id of param snackOrder.getId()
                ArrayList<SnackOrder> respondedSnackbarOrders = centrale.getRespondedSnackbarOrders(snackOrder.getId());
                
                // calculate the cheapest of the specific snackbar orders
                SnackOrder cheapestOrder = centrale.calculateCheapestSnackbarOrder(respondedSnackbarOrders);
                
                // send message and remove all orders with id snackOrder.getId() in centrale
                JSONObject obj = new JSONObject();
                obj.put(JSONUtils.SNACK_ORDER_CLIENT, cheapestOrder.toJSON());
                centraleGateway.getSender().sendMessage(obj, ConnectionUtils.SEND_ORDER_PRICE + snackOrder.getClientName());
            }
        }, 3000
        );

    }

}
