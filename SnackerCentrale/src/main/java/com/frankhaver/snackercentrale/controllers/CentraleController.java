package com.frankhaver.snackercentrale.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CentraleController extends AnchorPane {

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
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Dit is de SnackerCentrale");
    }

}
