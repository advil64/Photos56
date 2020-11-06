package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import app.Photos;
import javafx.fxml.FXML;
import model.User;

import java.io.*;

/**
 * This class is used to Control the login page
 * @author Advith Chegu
 * @author Banty Patel
 */
public class Controller extends Photos {
	//@FXML TextField username;
	@FXML TextField username;
	public void start(Stage mainStage){
		
	}
	
	/**
	 * This method is activated when the login button is clicked and sets the scene to the appropriate page
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	@FXML
	private void login() throws IOException, ClassNotFoundException {
		random.clear();
		userList = readApp();
		String loginName = username.getText().trim();
		random.add(loginName);
		int e = 0;

		//check if username is valid and is in the user list
		if(loginName.equals("")) {
			Photos.setErrorWindow("Invalid Entry", "Please make sure you enter a valid username");
			return;
		} else if(loginName.equals("admin")) {
			setStage("Admin Page", "admin.fxml", null);
		} else if((e = userList.indexOf(new User(loginName))) > -1) {
			setStage("Non-Admin Page", "nonadmin.fxml", userList.get(e));
		} else{
			Photos.setErrorWindow("Invalid Entry", "Username does not exist");
		}
	}

	/**
	 * set the scene to the non-admin page
	 * @param title
	 * @param resource
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	private void setStage(String title, String resource, User myUser) throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(resource));
		root = (AnchorPane)loader.load();
		if(title.equals("Admin Page")) {
			adminController = loader.getController();
			adminController.start(Photos.window);
		}
		else {
			nonAdminController= loader.getController();
			//pass the user to the new class
			nonAdminController.start(Photos.window);
		}
		Scene scene = new Scene(root, 714.0, 440.0);
		Photos.window.setScene(scene);
		Photos.window.setTitle(title);
		Photos.window.setResizable(false);
		Photos.window.show();
	}
}
