/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.snackbar.props;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Frank Haver
 */
public class PropertyUtils {
    
    public static final String SNACKBAR_FILE_NAME = "snackbars.props";
    public static final String AMOUNT_OF_SNACKBARS = "snackbar_amount";
    
    public static ArrayList<String> getAllSnackbarNames(){
        ArrayList<String> snackbarNames = new ArrayList<>();
        snackbarNames.add("Braadspit");
        snackbarNames.add("Jopie");
        snackbarNames.add("Henkie");
        snackbarNames.add("Kees Kroket");
        snackbarNames.add("Den gouden rakker");
        snackbarNames.add("Erpels");
        snackbarNames.add("Piepers");
        snackbarNames.add("Piet Friet");
        snackbarNames.add("Peter Patat");
        return snackbarNames;
    }
    
    public static String getRandomSnackbarName(){
        ArrayList<String> allSnackbarNames = getAllSnackbarNames();
        int random = new Random().nextInt(allSnackbarNames.size());
        return allSnackbarNames.get(random);
    }
}
