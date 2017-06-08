package com.frankhaver.snackbar;

import com.frankhaver.snackbar.controllers.SnackbarController;
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
    
    private static String snackbarName;

    @Override
    public void start(Stage stage) throws Exception {

        String newSnackbarName = snackbarName;
        snackbarController = new SnackbarController(newSnackbarName);

        stage.setScene(new Scene(snackbarController)); 
        stage.setTitle("Snackbar - " + newSnackbarName);
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
     * 
     * java -jar Snackbar-1.0.jar
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            snackbarName = args[0];
        } 
        else {
            snackbarName = "unknown";
        }
        
        launch(args);
    }

}
