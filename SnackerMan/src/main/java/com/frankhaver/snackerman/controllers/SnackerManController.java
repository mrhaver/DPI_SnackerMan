package com.frankhaver.snackerman.controllers;

import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

// SnackerMan Controller
public class SnackerManController extends AnchorPane {
    
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    
    public SnackerManController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/SnackerManController.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
//        this.factory = new ConnectionFactory();
//        this.factory.setHost("localhost");
    }
    
    @FXML
    private Label label;
    
    @FXML
    private void sendOrder(ActionEvent event) {
        System.out.println("Send Order");
        label.setText("Dit is de SnackerMan App");
    } 
    
    public void listenToQueueWithName(String queueName) throws IOException, TimeoutException{
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
