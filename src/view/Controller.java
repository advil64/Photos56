/**
 * @author Advith Chegu
 * @author Banty Patel
*/
package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import app.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.io.*;

import app.Photos;

/**
 * This class is used to Control the login page
 */
public class Controller extends Photos {
	@FXML TextField username;
	
	public void start(Stage mainStage) {
		
	}
	
	/**
	 * This method is activated when the login button is clicked and sets the scene to the appropriate page
	 */
	@FXML
	private void login() throws IOException {
		String user = username.getText().trim();
		//error if nothing is entered in the textfield
		if(user.equals("")) {
			Photos.setErrorWindow("Invalid Entry", "Please make sure you enter a valid username");
		}
		//if username is admin, then set the scene to admin page
		if(user.equals("admin")) {
			//set the scene to the admin page
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("admin.fxml"));
			root = (AnchorPane)loader.load();
			adminController= loader.getController();
			adminController.start(Photos.window);
			Scene scene = new Scene(root, 714.0, 440.0);
			Photos.window.setScene(scene);
			Photos.window.setTitle("Admin Page");
			Photos.window.setResizable(false);
			Photos.window.show();
		}
		
		//if username is non-admin
		if(obsList.contains(user)) {
			//set the scene to the non-admin page
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("nonadmin.fxml"));
			root = (AnchorPane)loader.load();
			nonAdminController= loader.getController();
			nonAdminController.start(Photos.window);
			Scene scene = new Scene(root, 714.0, 440.0);
			Photos.window.setScene(scene);
			Photos.window.setTitle("Non-Admin Page");
			Photos.window.setResizable(false);
			Photos.window.show();
		}
		//if username doesn't exist
		Photos.setErrorWindow("Invalid Entry", "Username does not exist");
		
	}
}
