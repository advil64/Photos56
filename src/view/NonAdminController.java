/**
 * @author Advith Chegu
 * @author Banty Patel
*/
package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.io.*;

import app.Photos;

/**
 * This class is used to control the non-admin login page
 */
public class NonAdminController extends Photos {
	
	public void start(Stage mainStage) {
		
	}
	
	/**
	 * This method is engaged when the user clicks the create button
	 * Adds an album to the list
	 */
	@FXML
	private void create() {
		
	}
	/**
	 * This method is engaged when the user clicks the rename button
	 * Renames an album to the list
	 */
	@FXML
	private void rename() {
		
	}
	/**
	 * This method is engaged when the user clicks the delete button
	 * Deletes an album to the list
	 */
	@FXML
	private void delete() {
		
	}
	/**
	 * This method is engaged when the user clicks the openAlbum button which sets the scene to the openAlbum page
	 * @throws IOException 
	 */
	@FXML
	private void openAlbum() throws IOException {
		//setting the scene to open album page
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("openAlbum.fxml"));
			root = (AnchorPane)loader.load();
			openAlbumController= loader.getController();
			openAlbumController.start(Photos.window);
			Scene scene = new Scene(root, 714.0, 440.0);
			Photos.window.setScene(scene);
			Photos.window.setTitle("Open Album");
			Photos.window.setResizable(false);
			Photos.window.show();
	}
	/**
	 * This method is engaged when the user clicks the openAlbum button which sets the scene to the searchPhotos page
	 */
	@FXML
	private void searchPhotos() {
		
	}
	
	/**
	 * This method is engaged when the user clicks the logout button which sets the scene back to the login page
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	@FXML
	private void logout() throws IOException, ClassNotFoundException {
		//setting the scene back to the login page
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("login.fxml"));
		root = (AnchorPane)loader.load();
		controller= loader.getController();
		controller.start(Photos.window);
		Scene scene = new Scene(root, 714.0, 440.0);
		Photos.window.setScene(scene);
		Photos.window.setTitle("Login Page");
		Photos.window.setResizable(false);
		Photos.window.show();
	}
}
