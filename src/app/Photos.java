/**
 * @author Advith Chegu
 * @author Banty Patel
*/
package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import view.AdminController;
import view.Controller;
import view.NonAdminController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Photos extends Application implements Serializable{

    public Scene scene;
    public AnchorPane root;
    public Controller controller;
    public AdminController adminController;
    public NonAdminController nonAdminController;
    public static Stage window;
    public static Alert errorWindow;
    
  //public static final String storeDir= "dat";
  	public static final String storeFile= "users.dat";
    
    //temporary arraylist of users
    public static ObservableList<String> obsList;     

    @Override
    public void start(Stage primaryStage) throws Exception{
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
        errorWindow = new Alert(AlertType.ERROR);
        errorWindow.initOwner(primaryStage);
    }
    
    public static void main(String[] args) {
    	obsList = FXCollections.observableArrayList();
        launch(args);
    }
    
    public static void writeApp(ObservableList<String> obsList) throws IOException{
    	FileOutputStream fos= new FileOutputStream(storeFile);
		ObjectOutputStream oos= new ObjectOutputStream(fos);
		for(int i=0; i<obsList.size(); i++) {
			oos.writeObject(obsList.get(i));
		}
	}
    public static ObservableList<String> readApp() throws IOException, ClassNotFoundException{
    	ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeFile));
    	ObservableList<String> gapp = FXCollections.observableArrayList();
    	gapp.add((String)ois.readObject());
    	return gapp;
    }
    
    /**
     * Sets the error window
     * @param header
     * @param body
     */
    public static void setErrorWindow(String header, String body){
        errorWindow.setTitle("Error");
        errorWindow.setHeaderText(header);
        errorWindow.setContentText(body);
        errorWindow.showAndWait();
    }
}
    