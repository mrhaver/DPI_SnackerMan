/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackermaninterfaces.utils;

/**
 *
 * @author Frank Haver
 */
public class SnackLog {
    
    public static final String SNACKBAR = "Snackbar";
    public static final String SNACKERMAN = "SnackerMan";
    public static final String CENTRALE = "Centrale";
    
    public static void LOG(String component, String tag, String message){
        SnackLog.LOG(component + " " + tag, message);
    }
    
    public static void LOG(String tag, String message){
        System.out.println(tag + ": " + message);
    }
}
