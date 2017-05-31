package com.frankhaver.snackermaninterfaces;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Frank Haver
 */
public interface IMessageReceiver {
    
    /**
     * Receive a message listening to a certain destination
     * @param fromDestination
     */
    void receiveMessages(String fromDestination);
    
    /**
     * callback function to react on messages
     * @param body 
     */
    abstract void onMessage(byte[] body);
}
