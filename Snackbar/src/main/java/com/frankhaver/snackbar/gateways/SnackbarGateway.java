/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackbar.gateways;

import com.frankhaver.snackermaninterfaces.IMessageSubscriber;
import com.frankhaver.snackermaninterfaces.implementations.MessageSubscriberJMSImpl;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Frank Haver
 */
public abstract class SnackbarGateway {
    
    private final IMessageSubscriber subscriber;
    
    public SnackbarGateway() throws IOException, TimeoutException{
        this.subscriber = new MessageSubscriberJMSImpl(){
            @Override
            public void onPublishedMessage(byte[] body) {
                onSubscribedMessage(body);
            }
        };
    }
    
    public abstract void onSubscribedMessage(byte[] body);

    public IMessageSubscriber getSubscriber() {
        return subscriber;
    }
    
    
}
