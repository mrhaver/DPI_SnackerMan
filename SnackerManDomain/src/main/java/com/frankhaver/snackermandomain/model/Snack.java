/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackermandomain.model;

import java.io.Serializable;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
 
/**
 *
 * @author Frank Haver
 */
public class Snack implements Serializable, Comparable<Snack>{
    
    // JSON Values
    public static final String NAME = "name";
    public static final String PRICE = "price";
    
    private String name;
    private double price;
    
    public Snack(){
        this.price = 0.0d;
    }
    
    public Snack(String name){
        this.name = name;
        this.price = 0.0d;
    }
    
    public Snack(String name, double price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    @Override
    public String toString(){
        if(this.price == 0.0d){
            return this.name;
        }
        else{
            
        }
        return this.name + " €" + this.price;
    }

    @Override
    public int compareTo(Snack s) {
        return this.getName().compareTo(s.getName());
    }
    
    /**
     * 
     * @return json object of this Snack object
     */
    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put(Snack.NAME, this.name);
        obj.put(Snack.PRICE, String.valueOf(this.price));
        return obj;
    }
    
    /**
     * 
     * @param snacks
     * @return json array of an arraylist of snacks
     */
    public static JSONArray toJSONArray(ArrayList<Snack> snacks){
        JSONArray arr = new JSONArray();
        snacks.forEach((s) -> {
            arr.add(s.toJSON());
        });
        return arr;
    }
    
    /**
     * get all fields from json object
     * @param obj
     * @return Snack object from param obj
     */
    public static Snack fromJSON(JSONObject obj){
        Snack snack = new Snack();
        
        String snackName = (String) obj.get(Snack.NAME);
        if(snackName != null){
            snack.name = snackName;
        }
        
        String snackPrice = (String) obj.get(Snack.PRICE);
        if(snackPrice != null){
            snack.price = Double.valueOf(snackPrice);
        }
         
        return snack;
    }
    
    public static ArrayList<Snack> fromJSONArray(JSONArray arr){
        ArrayList<Snack> snacks = new ArrayList<>();
        arr.stream().map((obj) -> (JSONObject)obj).forEachOrdered((jsonObj) -> {
            snacks.add(Snack.fromJSON((JSONObject)jsonObj));
        });
        return snacks;
    }
}
