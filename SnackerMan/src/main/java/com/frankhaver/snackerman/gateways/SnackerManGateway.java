/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackerman.gateways;

import com.frankhaver.snackermaninterfaces.IMessageSender;
import com.frankhaver.snackermaninterfaces.implementations.MessageSenderJMSImpl;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Frank Haver
 */
public class SnackerManGateway{
    
    private IMessageSender sender;
    
    public SnackerManGateway(){
        try {
            sender = new MessageSenderJMSImpl();
        } catch (IOException | TimeoutException ex) {
            Logger.getLogger(SnackerManGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public IMessageSender getSender() {
        return sender;
    }
}
