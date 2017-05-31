package com.frankhaver.snackbar.controllers;

import com.frankhaver.snackbar.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SnackbarController extends AnchorPane {
    
    private String snackbarName;
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            System.out.println(this.snackbarName + ": run another app");
            MainApp.runApp(MainApp.class, "jopieee");
        } catch (Exception ex) {
            Logger.getLogger(SnackbarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public SnackbarController(String snackbarName){
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
    }
}
