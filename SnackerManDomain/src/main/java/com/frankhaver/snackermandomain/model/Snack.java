/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackermandomain.model;

import java.util.Comparator;

/**
 *
 * @author Frank Haver
 */
public class Snack implements Comparable<Snack>{
    
    private String name;
    private double price;
    
    public Snack(){
        
    }
    
    public Snack(String name){
        this.name = name;
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
        return this.name;
    }


    @Override
    public int compareTo(Snack s) {
        return this.getName().compareTo(s.getName());
    }
    
    
    
    
}
