/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackermaninterfaces;

/**
 *
 * @author Frank Haver
 */
public interface IMessageSender {
    
    /**
     * Send a message to a certain destination
     * @param message
     * @param destination 
     */
    void sendMessage(Object object, String destination);
}
