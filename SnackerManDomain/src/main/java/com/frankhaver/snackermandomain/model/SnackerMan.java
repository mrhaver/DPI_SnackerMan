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
public class SnackerMan {
    
    public static final String NAME = "name";
    
    private String name;
    
    public SnackerMan(){
        
    }
    
    public SnackerMan(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @return json object of this SnackerMan object
     */
    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put(SnackerMan.NAME, this.name);
        return obj;
    }
    
    /**
     * get all fields from json object
     * @param obj
     * @return SnackerMan object from param obj
     */
    public static SnackerMan fromJSON(JSONObject obj){
        SnackerMan snackerMan = new SnackerMan();
        
        String snackName = (String) obj.get(SnackerMan.NAME);
        if(snackName != null){
            snackerMan.name = snackName;
        }
         
        return snackerMan;
    }
   

}
