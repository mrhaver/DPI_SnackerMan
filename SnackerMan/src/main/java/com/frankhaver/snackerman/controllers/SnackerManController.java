package com.frankhaver.snackerman.controllers;

import com.frankhaver.snackermandomain.data.SnackGenerator;
import com.frankhaver.snackermandomain.model.Snack;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

// SnackerMan Controller
public class SnackerManController extends AnchorPane {

    @FXML
    private ComboBox cmbChooseProduct;
    @FXML
    private ListView lvOrder;

    @FXML
    private Rectangle rectVisibleOrder;

    @FXML
    private void onAddProduct(ActionEvent event) {
        lvOrder.getItems().add(cmbChooseProduct.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void onRemoveProduct(ActionEvent event) {
        Object selectedItem = lvOrder.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lvOrder.getItems().remove(selectedItem);
            return;
        }
        System.out.println("no item selected");
    }

    @FXML
    private void onSendOrder(ActionEvent event) {
        this.rectVisibleOrder.setVisible(false);
    }

    @FXML
    private void onNewOrder(ActionEvent event) {
        this.rectVisibleOrder.setVisible(true);

        this.clearOrder();
    }

    public SnackerManController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/SnackerManController.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        // fill UI
        this.fillCombobox();
    }

    /**
     * Fill combobox with all snacks
     */
    private void fillCombobox() {
        ArrayList<Snack> allSnacks = SnackGenerator.getInstance().getAllSnacks();  
        this.cmbChooseProduct.getItems().clear();
        this.cmbChooseProduct.getItems().addAll(allSnacks);
        this.cmbChooseProduct.getSelectionModel().select(0);
    }

    /**
     * clear list view and order
     */
    private void clearOrder() {
        this.lvOrder.getItems().clear();
    }

}
