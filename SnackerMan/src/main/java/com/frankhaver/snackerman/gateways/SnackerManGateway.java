/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackerman.gateways;

import com.frankhaver.snackermaninterfaces.IMessageReceiver;
import com.frankhaver.snackermaninterfaces.IMessageSender;
import com.frankhaver.snackermaninterfaces.implementations.MessageReceiverJMSImpl;
import com.frankhaver.snackermaninterfaces.implementations.MessageSenderJMSImpl;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Frank Haver
 */
public abstract class SnackerManGateway {

    private IMessageSender sender;
    private IMessageReceiver receiver;

    public SnackerManGateway() {
        try {
            sender = new MessageSenderJMSImpl();

            this.receiver = new MessageReceiverJMSImpl() {

                @Override
                public void onMessage(byte[] body) {
                    onMessageReceived(body);
                }

            };
        } catch (IOException | TimeoutException ex) {
            Logger.getLogger(SnackerManGateway.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public abstract void onMessageReceived(byte[] body);

    public IMessageSender getSender() {
        return sender;
    }

    public IMessageReceiver getReceiver() {
        return receiver;
    }
}
