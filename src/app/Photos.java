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
import java.util.ArrayList;

import controller.AdminController;
import controller.Controller;
import controller.NonAdminController;
import controller.OpenAlbumController;
import controller.SearchPhotosController;
import controller.SlideShowController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class Photos extends Application implements Serializable{

    private Scene scene;
    private AnchorPane root;
    private Controller controller;
    private AdminController adminController;
    private NonAdminController nonAdminController;
    private OpenAlbumController openAlbumController;
    private SlideShowController slideShowController;
    private SearchPhotosController searchPhotosController;
    private static Stage window;
    private static Alert errorWindow;
    
  	public static final String storeFile= "../data/users.dat";
    
    //temporary arraylist of users
    public static ObservableList<User> userList = FXCollections.observableArrayList();
    //holds the username entered - to be used in NonAdminController
    public static User currUser;

	@Override
    public void start(Stage primaryStage) throws Exception{
		//create data directory if it doesn't exist
		File dir = new File("../data");
		if(!dir.exists()) {
			dir.mkdir();
		}
        window = primaryStage;
		setStage("Login Page", "../view/login.fxml");
        errorWindow = new Alert(AlertType.ERROR);
        errorWindow.initOwner(primaryStage);
    }
    
    public static void main(String[] args) {
    	userList = FXCollections.observableArrayList();
        launch(args);
    }
    /**
     * This method is used to write to the users.dat file
     * @param myUsers - list of users
     * @throws IOException
     */
    public static void writeApp(ObservableList<User> myUsers) throws IOException{
    	FileOutputStream fos = new FileOutputStream(storeFile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		for(User x : myUsers) {
			oos.writeObject(x.toString());
		}
	}
    /**
     * This method is used to read the users.dat file and populate the 
     * usernames list (obsList)
     * @return - returns the list of usernames
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ObservableList<User> readApp() throws IOException, ClassNotFoundException{
    	//create the file if it doesn't exist
    	File temp = new File("../data/users.dat");
    	temp.createNewFile();
    	
    	ObservableList<User> gapp = FXCollections.observableArrayList();
    	ObjectInputStream ois = null;
    	try{
    		ois = new ObjectInputStream(new FileInputStream(storeFile));
    	} catch(EOFException e) {
			return gapp;
		}
    	
    	//read the .dat file and populate the obsList (list of users)
    	while(true) {
    		try {
    			gapp.add(new User((String)ois.readObject()));
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
        writeApp(userList);
    }
    
    /**
     * Sets the error window
     * @param header header label
     * @param body body content
     */
    public void setErrorWindow(String header, String body){
        errorWindow.setTitle("Error");
        errorWindow.setHeaderText(header);
        errorWindow.setContentText(body);
        errorWindow.showAndWait();
    }

    /**
     * set the scene to the non-admin page
     * @param title title label
     * @param resource resource to be loaded
     * @throws IOException thrown when resource can't be found
     * @throws ClassNotFoundException
     */
    public void setStage(String title, String resource) throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(resource));
        root = (AnchorPane)loader.load();
        //figure out which controller to show
        switch (resource){
            case "../view/admin.fxml":
                adminController = loader.getController();
                adminController.start(window);
                break;
            case "../view/nonadmin.fxml":
                nonAdminController = loader.getController();
                nonAdminController.start(window);
                break;
            case "../view/login.fxml":
                controller = loader.getController();
                controller.start(window);
                break;
            case "../view/openAlbum.fxml":
                openAlbumController = loader.getController();
                openAlbumController.start(window);
                break;
            case "../view/searchPhotos.fxml":
                searchPhotosController = loader.getController();
                searchPhotosController.start(window);
                break;
            case "../view/slideshow.fxml":
                slideShowController = loader.getController();
                slideShowController.start(window);
                break;
            default:
                throw new ClassNotFoundException();
        }
        Scene scene = new Scene(root, 714.0, 440.0);
        window.setScene(scene);
        window.setTitle(title);
        window.setResizable(false);
        window.show();
    }
}
    