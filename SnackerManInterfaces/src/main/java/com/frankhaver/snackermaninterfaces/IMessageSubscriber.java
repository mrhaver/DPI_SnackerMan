/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackermaninterfaces;

/**
 * Subscriber and publisher are used for multiple consumers
 * @author Frank Haver
 */
public interface IMessageSubscriber {
    
    /**
     * Receive a message listening to a certain exchange
     * @param onExchange
     */
    void subscribeForMessages(String onExchange);
    
    /**
     * stop all services of the subscriber
     */
    void close();
    
    /**
     * callback function to react on messages
     * @param body 
     */
    abstract void onPublishedMessage(byte[] body);
}
