/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frankhaver.props;

import java.util.ArrayList;

/**
 *
 * @author Frank Haver
 */
public class PropertyUtils {
    
    public static final String SNACKBAR_FILE_NAME = "snackbars.props";
    public static final String AMOUNT_OF_SNACKBARS = "snackbar_amount";
    
    public static ArrayList<String> getAllSnackbars(){
        ArrayList<String> snackbarNames = new ArrayList<>();
        snackbarNames.add("Braadspit");
        snackbarNames.add("Jopie");
        snackbarNames.add("Henkie");
        snackbarNames.add("Kees Kroket");
        snackbarNames.add("KFC");
        return snackbarNames;
    }
}
