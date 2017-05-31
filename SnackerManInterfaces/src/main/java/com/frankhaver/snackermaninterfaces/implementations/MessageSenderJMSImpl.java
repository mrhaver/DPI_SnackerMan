/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackermaninterfaces.implementations;

import com.frankhaver.snackermaninterfaces.IMessageSender;
import com.frankhaver.snackermaninterfaces.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author Frank Haver
 */
public class MessageSenderJMSImpl implements IMessageSender {

    private final ConnectionFactory factory;
    private final Connection connection;
    private final Channel channel;

    public MessageSenderJMSImpl() throws IOException, TimeoutException {
        // set up connection and channel
        factory = new ConnectionFactory();
        factory.setHost(ConnectionUtils.HOST_NAME);
        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    @Override
    public void sendMessage(Object object, String destination) {
        try {
            // declare queue
            channel.queueDeclare(destination, false, false, false, null);
            
            // only send JSON objects with JMS
            JSONObject obj = (JSONObject) object;
            
            // send message to certain queue
            channel.basicPublish("", destination, null, obj.toJSONString().getBytes());
            
            System.out.println(" [x] Sent " + obj.toJSONString() + " to queue " + destination);
            
        } catch (IOException ex) {
            Logger.getLogger(MessageSenderJMSImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

}
