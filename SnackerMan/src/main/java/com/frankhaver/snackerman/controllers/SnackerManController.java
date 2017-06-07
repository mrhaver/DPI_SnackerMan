package com.frankhaver.snackerman.controllers;

import com.frankhaver.snackerman.gateways.SnackerManGateway;
import com.frankhaver.snackermandomain.data.SnackGenerator;
import com.frankhaver.snackermandomain.model.Snack;
import com.frankhaver.snackermaninterfaces.utils.JSONUtils;
import com.frankhaver.snackermaninterfaces.utils.ConnectionUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import org.json.simple.JSONObject;

// SnackerMan Controller
public class SnackerManController extends AnchorPane {

    private final SnackerManGateway snackerManGateway;
    
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
        // show order screen
        this.rectVisibleOrder.setVisible(false);
        
        // send ordered snacks to centrale
        JSONObject obj = new JSONObject();
        obj.put(JSONUtils.SNACK, new Snack("frikandelletje").toJSON()); // Snack.toJSONArray(this.getOrderedSnacks())
        this.snackerManGateway.getSender().sendMessage(obj, ConnectionUtils.QUEUE_NAME_HELLO);
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
        
        // create snackerman gateway
        this.snackerManGateway = new SnackerManGateway();
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
    
    /**
     * get all the ordered snacks from lvOrders
     * @return the ordered snacks
     */
    private ArrayList<Snack> getOrderedSnacks(){       
        ArrayList<Snack> orderedSnacks = new ArrayList<>();
        
        for(Object item: lvOrder.getItems()){
            Snack snackItem = (Snack) item;
            orderedSnacks.add(snackItem);
        }
        
        return orderedSnacks;
    }

}
