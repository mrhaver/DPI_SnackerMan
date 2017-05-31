/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackermaninterfaces.implementations;

import com.frankhaver.snackermaninterfaces.IMessageReceiver;
import com.frankhaver.snackermaninterfaces.utils.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Frank Haver
 */
public abstract class MessageReceiverJMSImpl implements IMessageReceiver {

    private final ConnectionFactory factory;
    private final Connection connection;
    private final Channel channel;

    private final JSONParser parser;

    public MessageReceiverJMSImpl() throws IOException, TimeoutException {
        this.factory = new ConnectionFactory();
        this.factory.setHost(ConnectionUtils.HOST_NAME);
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        this.parser = new JSONParser();
    }

    @Override
    public void receiveMessages(String fromDestination) {

        try {
            channel.queueDeclare(fromDestination, false, false, false, null);
            System.out.println(" [*] Waiting for messages on queue " + fromDestination + ". To exit press CTRL+C");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    onMessage(body);
                }
            };

            channel.basicConsume(fromDestination, true, consumer);

        } catch (IOException ex) {
            Logger.getLogger(MessageReceiverJMSImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public abstract void onMessage(byte[] body);

}
