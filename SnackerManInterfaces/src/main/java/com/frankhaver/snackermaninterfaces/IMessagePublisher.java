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
public interface IMessagePublisher {
    /**
     * Send a message to a certain exchange for multiple listeners
     * @param object
     * @param destination  
     */
    void publishMessage(Object object, String destination);
}
