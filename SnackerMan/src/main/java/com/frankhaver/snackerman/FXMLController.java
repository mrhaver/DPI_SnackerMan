package com.frankhaver.snackerman;

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

// SnackerMan Controller
public class FXMLController implements Initializable {
    
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    
    @FXML
    private Label label;
    
    @FXML
    private void sendOrder(ActionEvent event) {
        System.out.println("Send Order");
        label.setText("Dit is de SnackerMan App");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.factory = new ConnectionFactory();
        this.factory.setHost("localhost");
        
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
