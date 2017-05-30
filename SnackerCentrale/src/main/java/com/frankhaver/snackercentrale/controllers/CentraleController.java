package com.frankhaver.snackercentrale.controllers;

import com.frankhaver.snackercentrale.gateways.SnackerCentraleGateway;
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

public class CentraleController extends AnchorPane {

    private SnackerCentraleGateway centraleGateway;
    
    @FXML
    private Label label;

    public CentraleController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/CentraleController.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        try {
            // create snacker centrale gateway
            this.centraleGateway = new SnackerCentraleGateway();
            this.centraleGateway.getReceiver().receiveMessages(ConnectionUtils.QUEUE_NAME_HELLO);
        } catch (IOException | TimeoutException ex) {
            Logger.getLogger(CentraleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Dit is de SnackerCentrale");
    }

}
