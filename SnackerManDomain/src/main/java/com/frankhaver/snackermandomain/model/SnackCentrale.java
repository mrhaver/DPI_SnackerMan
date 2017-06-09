/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackermandomain.model;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Frank Haver
 */
public class SnackCentrale {

    private AtomicInteger nextId;
    
    private ArrayList<SnackOrder> openSnackerManOrders;
    private ArrayList<SnackOrder> respondedSnackbarOrders;

    public SnackCentrale() {
        nextId = new AtomicInteger(1);
        this.openSnackerManOrders = new ArrayList<>();
        this.respondedSnackbarOrders = new ArrayList<>();
    }

    public ArrayList<SnackOrder> getOpenSnackerManOrders() {
        return openSnackerManOrders;
    }

    public ArrayList<SnackOrder> getRespondedSnackbarOrders() {
        return respondedSnackbarOrders;
    }
    
    /**
     * get an open snackerman order by id
     * @param id
     * @return 
     */
    public SnackOrder getOpenSnackerManOrder(int id){
        return this.getOrderFromOrders(openSnackerManOrders, id);
    }
    
    /**
     * get responded snackbar orders by id
     * @param id
     * @return 
     */
    public ArrayList<SnackOrder> getRespondedSnackbarOrders(int id){
        ArrayList<SnackOrder> tempSnackOrders = new ArrayList<>();
        for(SnackOrder s: respondedSnackbarOrders){
            if(s.getId() == id){
                tempSnackOrders.add(s);
            }
        }
        return tempSnackOrders;
    }
    
    private SnackOrder getOrderFromOrders(ArrayList<SnackOrder> arr, int id){
        for(SnackOrder s: openSnackerManOrders){
            if(s.getId() == id){
                return s;
            }
        }
        return null;
    }
    
    public void addSnackerManOrder(SnackOrder s){
        this.openSnackerManOrders.add(s);
    }
    
    public void addRespondedSnackbarOrder(SnackOrder s){
        this.respondedSnackbarOrders.add(s);
    }
    
    /**
     * remove snackorder from both lists
     * @param id 
     */
    public void removeSnackOrder(int id){
        this.removeSnackerManOrder(id);
        this.removeRespondedSnackbarOrder(id);
    }
    
    private void removeSnackerManOrder(int id){
        for(SnackOrder s: this.openSnackerManOrders){
            if(s.getId() == id){
                this.openSnackerManOrders.remove(s);
            }
        }
    }
    
    private void removeRespondedSnackbarOrder(int id){
        for(SnackOrder s: this.respondedSnackbarOrders){
            if(s.getId() == id){
                this.openSnackerManOrders.remove(s);
            }
        }
    }
    
    public int getNextSnackOrderId(){
        return nextId.getAndIncrement();
    }
    
    public boolean respondedSnackOrderExists(int id){
        for(SnackOrder s: this.respondedSnackbarOrders){
            if(s.getId() == id){
                return true;
            }
        }
        
        return false;
    }
    
    public SnackOrder calculateCheapestSnackbarOrder(ArrayList<SnackOrder> respondedSnackbarOrders){
        SnackOrder cheapestSnackbarOrder = new SnackOrder();
        for(SnackOrder s: respondedSnackbarOrders){
            if(cheapestSnackbarOrder.getClientName().equals("")){
                cheapestSnackbarOrder = s;
            }
            else if(s.getPrice() < cheapestSnackbarOrder.getPrice()){
                cheapestSnackbarOrder = s;
            }
        }
        return cheapestSnackbarOrder;
    }

}
