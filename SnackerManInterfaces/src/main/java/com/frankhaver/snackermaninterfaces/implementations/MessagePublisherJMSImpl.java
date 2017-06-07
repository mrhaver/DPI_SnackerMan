/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackermaninterfaces.implementations;

import com.frankhaver.snackermaninterfaces.IMessagePublisher;
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
public class MessagePublisherJMSImpl implements IMessagePublisher {

    private final ConnectionFactory factory;
    private final Connection connection;
    private final Channel channel;
    
    public MessagePublisherJMSImpl() throws IOException, TimeoutException{
        // set up connection and channel
        factory = new ConnectionFactory();
        factory.setHost(ConnectionUtils.HOST_NAME);
        connection = factory.newConnection();
        channel = connection.createChannel();
    }
    
    @Override
    public void publishMessage(Object object, String destination) {
        try {
            // declare an exchange destination
            channel.exchangeDeclare(destination, "fanout");
            
            // only send JSON objects with JMS
            JSONObject obj = (JSONObject) object;
            
            // public JSON object on exchange
            channel.basicPublish(destination, "", null, obj.toJSONString().getBytes());
            
            System.out.println(" [x] Published " + obj.toJSONString() + " on exchange " + destination);
        
        } catch (IOException ex) {
            Logger.getLogger(MessagePublisherJMSImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
