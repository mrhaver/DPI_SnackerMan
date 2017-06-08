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
    
    
}
