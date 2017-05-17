package com.frankhaver.snackerman;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

// SnackerMan Controller
public class FXMLController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void sendOrder(ActionEvent event) {
        System.out.println("Send Order");
        label.setText("Dit is de SnackerMan App");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
