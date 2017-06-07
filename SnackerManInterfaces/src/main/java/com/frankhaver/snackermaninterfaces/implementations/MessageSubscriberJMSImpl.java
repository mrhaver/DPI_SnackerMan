/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackermaninterfaces.implementations;

import com.frankhaver.snackermaninterfaces.IMessageSubscriber;
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

/**
 *
 * @author Frank Haver
 */
public abstract class MessageSubscriberJMSImpl implements IMessageSubscriber {

    private final ConnectionFactory factory;
    private final Connection connection;
    private final Channel channel;
    
    private String subscribeQueueName;
    private String exchangeName;

    public MessageSubscriberJMSImpl() throws IOException, TimeoutException {
        this.factory = new ConnectionFactory();
        this.factory.setHost(ConnectionUtils.HOST_NAME);
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
    }

    @Override
    public void subscribeForMessages(String onExchange) {
        try {
            this.exchangeName = onExchange;
            
            channel.exchangeDeclare(onExchange, "fanout");
            String queueName = channel.queueDeclare().getQueue();
            this.subscribeQueueName = queueName;
            channel.queueBind(queueName, onExchange, "");

            System.out.println(" [*] Waiting for messages on exchange " + onExchange);

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                        AMQP.BasicProperties properties, byte[] body) throws IOException {
                    onPublishedMessage(body);
                }
            };

            channel.basicConsume(queueName, true, consumer);

        } catch (IOException ex) {
            Logger.getLogger(MessageSubscriberJMSImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void close() {
        try {
            channel.queueUnbind(this.subscribeQueueName, this.exchangeName, "");
        } catch (IOException ex) {
            Logger.getLogger(MessageSubscriberJMSImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public abstract void onPublishedMessage(byte[] body);

}
