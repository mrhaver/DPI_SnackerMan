/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackermandomain.model;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Frank Haver
 */
public class SnackOrder {
    
    public static final String CLIENT_NAME = "client_name";
    public static final String SNACKBAR_NAME = "snackbar_name";
    public static final String PRICE = "price";
    public static final String SNACKS = "snacks";
    
    private String clientName;
    private String snackbarName;
    private double price;
    private ArrayList<Snack> snacks;
    
    public SnackOrder(){
        this.clientName = "";
        this.snackbarName = "";
        this.price = 0.0d;
        snacks = new ArrayList<>();
    }
    
    public SnackOrder(double price, ArrayList<Snack> snacks){
        this.clientName = "";
        this.snackbarName = "";
        this.price = price;
        this.snacks = snacks;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Snack> getSnacks() {
        return snacks;
    }

    public void setSnacks(ArrayList<Snack> snacks) {
        this.snacks = snacks;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getSnackbarName() {
        return snackbarName;
    }

    public void setSnackbarName(String snackbarName) {
        this.snackbarName = snackbarName;
    }
    
    /**
     * 
     * @return json object of this SnackOrder object
     */
    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        
        obj.put(SnackOrder.PRICE, this.price); 
        obj.put(SnackOrder.SNACKS, Snack.toJSONArray(this.snacks));
        obj.put(SnackOrder.CLIENT_NAME, this.clientName);
        obj.put(SnackOrder.SNACKBAR_NAME, this.snackbarName);
        
        return obj;
    }
    
    /**
     * get all fields from json object
     * @param obj
     * @return SnackOrder object from param obj
     */
    public static SnackOrder fromJSON(JSONObject obj){
        SnackOrder snackOrder = new SnackOrder();
        
        String clientName = (String) obj.get(SnackOrder.CLIENT_NAME);
        if(!clientName.equals("")){
            snackOrder.clientName = clientName;
        }
        
        String snackbarName = (String) obj.get(SnackOrder.SNACKBAR_NAME);
        if(snackbarName != null && !snackbarName.equals("")){
            snackOrder.snackbarName = snackbarName;
        }
        
        double orderPrice = (double) obj.get(SnackOrder.PRICE);
        if(orderPrice != 0.0d){
            snackOrder.price = orderPrice;
        }
        
        ArrayList<Snack> orderSnacks = Snack.fromJSONArray((JSONArray) obj.get(SnackOrder.SNACKS));
        if(orderSnacks != null){
            snackOrder.snacks = orderSnacks;
        }
         
        return snackOrder;
    }
}
