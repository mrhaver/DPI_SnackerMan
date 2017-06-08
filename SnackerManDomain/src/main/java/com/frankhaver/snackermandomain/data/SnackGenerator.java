/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackermandomain.data;

import com.frankhaver.snackermandomain.model.Snack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import static jdk.nashorn.internal.objects.NativeMath.round;

/**
 *
 * @author Frank Haver
 */
public class SnackGenerator {

    private static SnackGenerator instance;

    private SnackGenerator() {

    }

    public static SnackGenerator getInstance() {
        if (instance == null) {
            instance = new SnackGenerator();
        }
        return instance;
    }

    /**
     * Get all different snacks without price
     *
     * @return all different snacks
     */
    public ArrayList<Snack> getAllSnacks() {
        ArrayList<Snack> allSnacks = new ArrayList<>();
        allSnacks.add(new Snack("Frikandel"));
        allSnacks.add(new Snack("Viandel"));
        allSnacks.add(new Snack("Kroket"));
        allSnacks.add(new Snack("Nasipuk"));
        allSnacks.add(new Snack("Bamischijf"));
        allSnacks.add(new Snack("Patatje Ruzie"));
        allSnacks.add(new Snack("Berenbal"));
        allSnacks.add(new Snack("Mexicaantje"));
        allSnacks.add(new Snack("Hotwings"));
        allSnacks.add(new Snack("Kipnuggets"));
        allSnacks.add(new Snack("Halve haan"));
        allSnacks.add(new Snack("Kipcorn"));
        allSnacks.add(new Snack("Maiskolf"));
        Collections.sort(allSnacks);
        return allSnacks;
    }

    /**
     * Get all snacks and give all snacks a random price
     *
     * @return all snacks with price
     */
    public ArrayList<Snack> randomPriceAllSnacks() {
        
        ArrayList<Snack> randomPricedSnacks = new ArrayList<>();
        
        double rangeMin = 0.80;
        double rangeMax = 3.20;
        this.getAllSnacks().forEach((Snack s) -> {
            Random r = new Random();

            double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            randomValue = Math.floor(randomValue * 100) / 100;
            s.setPrice(randomValue);
            randomPricedSnacks.add(s);
        });
        
        return randomPricedSnacks;
    }
}
