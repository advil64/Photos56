/**
 * @author Advith Chegu
 * @author Banty Patel
*/
package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import java.io.*;
import java.nio.file.Files;

import app.Photos;
import model.User;

/**
 * This class is used to control the Admin Page
 */
public class AdminController extends Photos implements Serializable{
	
	@FXML ListView<User> username_list;
	@FXML TextField username_textfield;
	
	public void start(Stage mainStage) throws IOException, ClassNotFoundException {
		username_list.setItems(userList);
		username_list.getSelectionModel().select(0);
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
		loader.setLocation(getClass().getResource("../view/login.fxml"));
		root = (AnchorPane)loader.load();
		controller= loader.getController();
		controller.start(Photos.window);
		Scene scene = new Scene(root, 714.0, 440.0);
		Photos.window.setScene(scene);
		Photos.window.setTitle("Login Page");
		Photos.window.setResizable(false);
		Photos.window.show();
	}
	
	/**
	 * This method is called when the user clicks on the add button
	 * Adds a user to the list
	 * @throws IOException
	 */
	@FXML
	private void add() throws IOException{
		String username = username_textfield.getText().trim();
		//error if nothing is entered in the text field
		if(username.equals("")) {
			Photos.setErrorWindow("Invalid Entry", "Please make sure you enter a valid username");
			return;
		}
		//trying to add admin to user list
		if(username.trim().equals("admin")) {
			Photos.setErrorWindow("Invalid Entry", "Please make sure you enter a valid username");
			return;
		}
		//no duplicate users
		for(int i=0; i<userList.size(); i++) {
			if(userList.get(i).getUsername().equals(username)) {
				Photos.setErrorWindow("Invalid Entry", "Username already exists");
				return;
			}
		}
		
		//create a new user and set the list
		userList.add(new User(username));
		username_list.setItems(userList);
		writeApp(userList);
		//create a user folder
		new File("data/" + username).mkdir();
		
	}
	
	/**
	 * This method is called when the user clicks on the delete button
	 * Deletes a user to the list
	 * @throws IOException 
	 */
	@FXML
	private void delete() throws IOException{
		//if nothing is selected and user clicks delete
		if(username_list.getSelectionModel().getSelectedItem() == null || username_list.getSelectionModel().getSelectedIndex() == -1) {
			Photos.setErrorWindow("Error", "Please select an item before deleting");
			return;
		}
		String username = username_list.getSelectionModel().getSelectedItem().getUsername();
		//removing item from list
		User myUser = username_list.getSelectionModel().getSelectedItem();
		userList.remove(myUser);
		username_list.setItems(userList);
		writeApp(userList);
		//delete the directory
		File file = new File("data/" + username);
		if(file.isDirectory() && file != null) {
			deleteDir(file);
		}
	}
	
	/**
	 * Method to delete user's files
	 * @param file
	 * @return
	 */
	public static boolean deleteDir(File file) {
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			for(int i=0; i<files.length; i++) {
				boolean boo = deleteDir(files[i]);
				if(boo == false) {
					return false;
				}
			}
		}
		return file.delete();
	}
}
