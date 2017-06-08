/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackermaninterfaces.utils;

import java.util.Set;
import org.json.simple.JSONObject;

/**
 *
 * @author Frank Haver
 */
public class JSONUtils {
    private static final String KEY_NOT_FOUND = "key_not_found";
    
    public static final String SNACKER_MAN = "snacker_man";
    public static final String SNACK = "snack";
    public static final String SNACK_ORDER = "snack_order";
    public static final String SNACK_ORDER_CLIENT = "snack_order_client";
    public static final String SNACK_ORDER_SNACKBAR = "snack_order_snackbar";
    
    // there could be only 1 key in JSONObject in this application
    public static final String getFirstJSONKey(JSONObject object){
        Set jsonKeySet = object.keySet();
        for(Object o: jsonKeySet){
            return (String) o;
        }
        
        return KEY_NOT_FOUND;
    }
}
