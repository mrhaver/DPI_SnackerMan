package com.frankhaver.snackerman.controllers;

import com.frankhaver.snackerman.gateways.SnackerManGateway;
import com.frankhaver.snackermandomain.data.SnackGenerator;
import com.frankhaver.snackermandomain.model.Snack;
import com.frankhaver.snackermandomain.model.SnackOrder;
import com.frankhaver.snackermandomain.model.SnackerMan;
import com.frankhaver.snackermaninterfaces.utils.JSONUtils;
import com.frankhaver.snackermaninterfaces.utils.ConnectionUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// SnackerMan Controller
public class SnackerManController extends AnchorPane {

    private final SnackerManGateway snackerManGateway;

    private final SnackerMan snackerMan;

    private final JSONParser parser;

    @FXML
    private ComboBox cmbChooseProduct;
    @FXML
    private ListView lvOrder;
    @FXML
    private Rectangle rectVisibleOrder;
    @FXML
    private Label lblSnackbarName;
    @FXML
    private Label lblOrderPrice;
    @FXML
    private ImageView loadingIcon;
    @FXML
    private Label lblProgress;

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
        // show loading icon
        this.setOrderInProgress(true);
        this.rectVisibleOrder.setVisible(true);
        
        // send ordered snacks to centrale
        JSONObject obj = new JSONObject();

        SnackOrder snackOrder = new SnackOrder();
        snackOrder.setClientName(this.snackerMan.getName());
        snackOrder.setSnacks(this.getOrderedSnacks());

        obj.put(JSONUtils.SNACK_ORDER_SNACKBAR, snackOrder.toJSON());
        this.snackerManGateway.getSender().sendMessage(obj, ConnectionUtils.CLIENT_SEND_ORDER);
    }

    @FXML
    private void onNewOrder(ActionEvent event) {
        this.rectVisibleOrder.setVisible(true);
        this.setOrderInProgress(false);

        this.clearOrder();
    }

    public SnackerManController(String clientName) {
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
        // set order in not in progress and hide order screen
        this.setOrderInProgress(false);
        this.rectVisibleOrder.setVisible(true);

        this.loadingIcon.setVisible(false);

        this.parser = new JSONParser();

        // create snackerman gateway
        this.snackerManGateway = new SnackerManGateway() {

            @Override
            public void onMessageReceived(byte[] body) {

                try {
                    // read and parse incoming JSON message
                    String message = new String(body, "UTF-8");
                    JSONObject obj = (JSONObject) parser.parse(message);
                    System.out.println(" [x] Received " + obj.toJSONString());

                    switch (JSONUtils.getFirstJSONKey(obj)) {
                        case JSONUtils.SNACK_ORDER_CLIENT:
                            SnackOrder snackOrder = SnackOrder.fromJSON((JSONObject) obj.get(JSONUtils.SNACK_ORDER_CLIENT));
                            processReceivedOrder(snackOrder);
                            break;

                        default:
                            System.out.println("Unknown json key");

                    }
                } catch (UnsupportedEncodingException | ParseException ex) {
                    Logger.getLogger(SnackerManController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        this.snackerMan = new SnackerMan(clientName);

        // receive order messages from centrale
        this.snackerManGateway.getReceiver().receiveMessages(ConnectionUtils.SEND_ORDER_PRICE + clientName);

        System.out.println("Snackerman started with name: " + clientName);
    }

    public void processReceivedOrder(final SnackOrder snackOrder) {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                setOrderInProgress(false);
                rectVisibleOrder.setVisible(false);
                lblSnackbarName.setText(snackOrder.getSnackbarName());
                lblOrderPrice.setText("€" + String.valueOf(snackOrder.getPrice()));
            }
        });
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
     *
     * @return the ordered snacks
     */
    private ArrayList<Snack> getOrderedSnacks() {
        ArrayList<Snack> orderedSnacks = new ArrayList<>();

        for (Object item : lvOrder.getItems()) {
            Snack snackItem = (Snack) item;
            orderedSnacks.add(snackItem);
        }

        return orderedSnacks;
    }

    public SnackerManGateway getSnackerManGateway() {
        return snackerManGateway;
    }
    
    private void setOrderInProgress(boolean setInProgress){
        this.loadingIcon.setVisible(setInProgress);
        this.lblProgress.setVisible(setInProgress);
    }

}
