package com.frankhaver.snackerman;

import com.frankhaver.snackerman.controllers.SnackerManController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

// SnackerMan
public class MainApp extends Application {

    private static String clientName;

    @Override
    public void start(Stage stage) throws Exception {
        SnackerManController main = new SnackerManController(clientName);

        stage.setScene(new Scene(main));
        stage.setTitle("Snacker Man - " + clientName);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
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
     * java -jar target/SnackerMan-1.0.jar name
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            clientName = args[0];
        } 
        else {
            clientName = "unknown";
        }

        launch(args);
    }

}
