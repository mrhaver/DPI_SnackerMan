package com.frankhaver.snackercentrale;

import com.frankhaver.snackercentrale.controllers.CentraleController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Snacker Centrale
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
       
        CentraleController main = new CentraleController();

        stage.setScene(new Scene(main));
        stage.setTitle("Centrale");
        stage.show();
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
