package com.frankhaver.snackbar;

import com.frankhaver.snackbar.props.PropertyUtils;
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

    private SnackbarController snackbarController;

    @Override
    public void start(Stage stage) throws Exception {

        String newSnackbarName = PropertyUtils.getRandomSnackbarName();
        snackbarController = new SnackbarController(newSnackbarName);

        stage.setScene(new Scene(snackbarController)); 
        stage.setTitle("Snackbar " + newSnackbarName);
        stage.show();
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                // close gateway services of controller
                snackbarController.getSnackbarGateway().getSubscriber().close();

                Platform.exit();
                System.exit(0);
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

}
