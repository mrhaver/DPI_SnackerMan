/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackercentrale.gateways;

import com.frankhaver.snackermaninterfaces.IMessageReceiver;
import com.frankhaver.snackermaninterfaces.implementations.MessageReceiverJMSImpl;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Frank Haver
 */
public abstract class SnackerCentraleGateway {
    
    private final IMessageReceiver receiver;
    
    public SnackerCentraleGateway() throws IOException, TimeoutException{
        this.receiver = new MessageReceiverJMSImpl(){
            
            @Override
            public void onMessage(byte[] body) {
                onMessageReceived(body);
            }
            
        };
    }
    
    public abstract void onMessageReceived(byte[] body);
    
    public MessageReceiverJMSImpl getReceiver(){
        return (MessageReceiverJMSImpl) this.receiver;
    }

}
