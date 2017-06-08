/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackercentrale.gateways;

import com.frankhaver.snackermaninterfaces.IMessagePublisher;
import com.frankhaver.snackermaninterfaces.IMessageReceiver;
import com.frankhaver.snackermaninterfaces.IMessageSender;
import com.frankhaver.snackermaninterfaces.implementations.MessagePublisherJMSImpl;
import com.frankhaver.snackermaninterfaces.implementations.MessageReceiverJMSImpl;
import com.frankhaver.snackermaninterfaces.implementations.MessageSenderJMSImpl;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Frank Haver
 */
public abstract class SnackerCentraleGateway {
    
    private final IMessageSender sender;
    private final IMessageReceiver receiver;
    private final IMessagePublisher publisher;
    
    public SnackerCentraleGateway() throws IOException, TimeoutException{
        this.receiver = new MessageReceiverJMSImpl(){
            
            @Override
            public void onMessage(byte[] body) {
                onMessageReceived(body);
            }
            
        };
        
        this.publisher = new MessagePublisherJMSImpl();
        this.sender = new MessageSenderJMSImpl();
    }
    
    public abstract void onMessageReceived(byte[] body);
    
    public IMessageReceiver getReceiver(){
        return this.receiver;
    }

    public IMessagePublisher getPublisher() {
        return this.publisher;
    }

    public IMessageSender getSender() {
        return this.sender;
    }
    
    
}
