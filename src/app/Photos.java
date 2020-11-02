/**
 * @author Advith Chegu
 * @author Banty Patel
*/
package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import view.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Photos extends Application {

    public Scene scene;
    public AnchorPane root;
    public Controller controller;
    public static Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        // TODO Auto-generated method stub
        window = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/login.fxml"));
        root = (AnchorPane)loader.load();
        controller= loader.getController();
        controller.start(primaryStage);
        scene = new Scene(root, 714.0, 440.0);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Page");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }
}
    