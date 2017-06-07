package com.frankhaver.snackbar;

import com.frankhaver.props.PropertyUtils;
import com.frankhaver.snackbar.controllers.SnackbarController;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

// Snackbar
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // if amount of snackbars is 1 create first in this function
        int amountOfSnackbars = MainApp.getAmountOfSnackbars();
        if (amountOfSnackbars < 1) {
            MainApp.runApp(MainApp.class);
        }

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    // lower amount of active snackbars
                    int amountOfSnackbars = MainApp.getAmountOfSnackbars();
                    MainApp.setAmountOfSnackbars(amountOfSnackbars - 1);

                    // close entire application if amountOfSnackbars -1 == 0
                    if (amountOfSnackbars - 1 == 0) {
                        Platform.exit();
                        System.exit(0);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * run a new instance of the snackbar app
     *
     * @param anotherAppClass
     * @throws Exception
     */
    public static void runApp(Class<? extends Application> anotherAppClass) throws Exception {

        String snackbarName = getNewSnackbarName();
        MainApp.setAmountOfSnackbars(MainApp.getAmountOfSnackbars() + 1);

        Application app2 = anotherAppClass.newInstance();
        SnackbarController main = new SnackbarController(snackbarName);
        Stage anotherStage = new Stage();
        anotherStage.setScene(new Scene(main));
        anotherStage.setTitle("Snackbar " + snackbarName);
        app2.start(anotherStage);
        anotherStage.show();
    }

    private static int getAmountOfSnackbars() throws IOException {
        String snackbars;
        try (FileInputStream in = new FileInputStream(PropertyUtils.SNACKBAR_FILE_NAME)) {
            Properties props = new Properties();
            props.load(in);
            snackbars = props.getProperty(PropertyUtils.AMOUNT_OF_SNACKBARS);
            if (snackbars == null) {
                snackbars = "0";
            }
        }

        int amount = Integer.parseInt(snackbars);
//        System.out.println("get amount of snackbars " + amount);
        return amount;
    }

    private static void setAmountOfSnackbars(int amount) throws IOException {
//        System.out.println("set amount of snackbars " + amount);
        Properties props = new Properties();
        props.setProperty(PropertyUtils.AMOUNT_OF_SNACKBARS, String.valueOf(amount));
        try (FileOutputStream out = new FileOutputStream(PropertyUtils.SNACKBAR_FILE_NAME)) {
            props.store(out, null);
        }
    }
    
    private static String getNewSnackbarName() throws IOException{
        int amountOfSnackbars = getAmountOfSnackbars();
        ArrayList<String> allSnackbarNames = PropertyUtils.getAllSnackbars();
        if(amountOfSnackbars < allSnackbarNames.size()){
            return allSnackbarNames.get(amountOfSnackbars);
        }
        else{
            System.out.println("no new snackbar available");
            return "";
        }
    }

}
