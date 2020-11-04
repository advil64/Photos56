/**
 * @author Advith Chegu
 * @author Banty Patel
*/
package app;

import java.io.EOFException;
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
import view.OpenAlbumController;
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
    public OpenAlbumController openAlbumController;
    public static Stage window;
    public static Alert errorWindow;
    
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
    /**
     * This method is used to write to the users.dat file
     * @param obsList - list of usernames
     * @throws IOException
     */
    public static void writeApp(ObservableList<String> obsList) throws IOException{
    	FileOutputStream fos= new FileOutputStream(storeFile);
		ObjectOutputStream oos= new ObjectOutputStream(fos);
		for(int i=0; i<obsList.size(); i++) {
			oos.writeObject(obsList.get(i));
		}
	}
    /**
     * This method is used to read the users.dat file and populate the 
     * usernames list (obsList)
     * @return - returns the list of usernames
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ObservableList<String> readApp() throws IOException, ClassNotFoundException{
    	//create the file if it doesn't exist
    	File temp = new File("users.dat");
    	temp.createNewFile();
    	
    	ObservableList<String> gapp = FXCollections.observableArrayList();
    	ObjectInputStream ois;
    	try{
    		ois = new ObjectInputStream(new FileInputStream(storeFile));
    	} catch(EOFException e) {
			return gapp;
		}
    	
    	//read the .dat file and populate the obsList (list of users)
    	while(true) {
    		try {
    			gapp.add((String)ois.readObject());	
    		} catch (EOFException e) {
    			return gapp;
    		}
    	}
    }
    /**
     * Method stores all data when GUI is closed
     */
    @Override
    public void stop() throws IOException{
        //Write list of users to file
        writeApp(obsList);
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
    