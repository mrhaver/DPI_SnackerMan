/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackermandomain.model;

import com.frankhaver.snackermandomain.data.SnackGenerator;
import java.util.ArrayList;

/**
 *
 * @author Frank Haver
 */
public class Snackbar {
    
    private String name;
    private ArrayList<Snack> snacks;
    
    public Snackbar(String name){
        this.snacks = SnackGenerator.getInstance().randomPriceAllSnacks();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Snack> getSnacks() {
        return snacks;
    }

    public void setSnacks(ArrayList<Snack> snacks) {
        this.snacks = snacks;
    }
    
    /**
     * find the price of a snack for this snackbar and add it to the total price
     * @param orderedSnacks
     * @return price of all snacks together
     */
    public double calculateOrderPrice(ArrayList<Snack> orderedSnacks){
        double totalPrice = 0.0d;
        totalPrice = orderedSnacks.stream().map((s) -> this.findPriceOfSnack(s)).reduce(totalPrice, (accumulator, _item) -> accumulator + _item);
        return Math.floor(totalPrice*100)/100;
    }
    
    private double findPriceOfSnack(Snack snack){
        for(Snack s: this.snacks){
            if(snack.getName().equals(s.getName())){
                return s.getPrice();
            }
        }
        return 0.0d;
    }
    
    public ArrayList<Snack> getPricedSnacks(ArrayList<Snack> snacks){
        snacks.forEach((s) -> {
            s.setPrice(this.findPriceOfSnack(s));
        });
        return snacks;
    }
    
    
}
