package controller;

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
	/**
	 * TextField holding the username entered at the login page
	 */
	@FXML TextField username;
	
	/**
	 * Start method ran at the beginning of the login controller
	 * @param mainStage - main stage of the photo app
	 */
	public void start(Stage mainStage){
		
	}
	
	/**
	 * This method is activated when the login button is clicked and sets the scene to the appropriate page
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	@FXML
	private void login() throws IOException, ClassNotFoundException {
		userList = readApp();
		String loginName = username.getText().trim();
		int e = 0;

		//check if username is valid and is in the user list
		if(loginName.equals("")) {
			setErrorWindow("Invalid Entry", "Please make sure you enter a valid username");
			return;
		} else if(loginName.equals("admin")) {
			setStage("Admin Page", "../view/admin.fxml");
		} else if((e = userList.indexOf(new User(loginName))) > -1) {
			Photos.currUser = new User(loginName);
			setStage("Non-Admin Page", "../view/nonadmin.fxml");
		} else{
			setErrorWindow("Invalid Entry", "Username does not exist");
		}
	}
}
