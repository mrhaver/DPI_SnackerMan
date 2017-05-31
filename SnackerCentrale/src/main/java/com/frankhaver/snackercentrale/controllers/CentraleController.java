package com.frankhaver.snackercentrale.controllers;

import com.frankhaver.snackercentrale.gateways.SnackerCentraleGateway;
import com.frankhaver.snackermaninterfaces.implementations.MessageReceiverJMSImpl;
import com.frankhaver.snackermaninterfaces.utils.ConnectionUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CentraleController extends AnchorPane {

    private SnackerCentraleGateway centraleGateway;
    
    private final JSONParser parser;

    @FXML
    private Label label;

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
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            label.setText("Dit is de SnackerCentrale");
                        }
                    });
                    
                    String message = new String(body, "UTF-8");
                    JSONObject obj = (JSONObject) parser.parse(message);
                    System.out.println(" [x] Received " + obj.toJSONString());
                } catch (ParseException | UnsupportedEncodingException ex) {
                    Logger.getLogger(MessageReceiverJMSImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        
        this.centraleGateway.getReceiver().receiveMessages(ConnectionUtils.QUEUE_NAME_HELLO);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Dit is de SnackerCentrale");
    }

}
