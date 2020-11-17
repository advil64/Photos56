/**
 * @author Advith Chegu
 * @author Banty Patel
*/
package controller;

import app.Photos;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * This class is used to control the Admin Page
 */
public class AdminController extends Photos implements Serializable{
	
	/**
	 * ListView of usernames
	 */
	@FXML ListView<User> username_list;
	/**
	 * Textfield user to take in username input
	 */
	@FXML TextField username_textfield;
	
	/**
	 * method ran at the start of the admin page
	 * @param mainStage - stage of the photo app
	 * @throws IOException File is not found
	 * @throws ClassNotFoundException File is not found
	 */
	public void start(Stage mainStage) throws IOException, ClassNotFoundException {
		username_list.setItems(userList);
		username_list.getSelectionModel().select(0);
	}
	
	/**
	 * This method is engaged when the user clicks the logout button which sets the scene back to the login page
	 * @throws IOException File is not found
	 * @throws ClassNotFoundException File is not found
	 */
	@FXML
	private void logout() throws IOException, ClassNotFoundException {
		setStage("Login Page", "../view/login.fxml");
	}
	
	/**
	 * This method is called when the user clicks on the add button
	 * Adds a user to the list
	 * @throws IOException File is not found
	 */
	@FXML
	private void add() throws IOException{
		String username = username_textfield.getText().trim();
		//error if nothing is entered in the text field
		if(username.equals("")) {
			setErrorWindow("Invalid Entry", "Please make sure you enter a valid username");
			return;
		}
		//trying to add admin to user list
		if(username.trim().equals("admin")) {
			setErrorWindow("Invalid Entry", "Please make sure you enter a valid username");
			return;
		}
		//no duplicate users
		for(int i=0; i<userList.size(); i++) {
			if(userList.get(i).getUsername().equalsIgnoreCase(username)) {
				setErrorWindow("Invalid Entry", "Username already exists");
				return;
			}
		}
		
		//create a new user and set the list
		userList.add(new User(username));
		username_list.setItems(userList);
		writeApp(userList);
		//create a user folder
		new File("photoData/" + username).mkdir();
		//create the combo box date file
		new File("photoData/" + username + "/combo.dat");
		
	}
	
	/**
	 * This method is called when the user clicks on the delete button
	 * Deletes a user to the list
	 * @throws IOException File is not found
	 */
	@FXML
	private void delete() throws IOException{
		//if nothing is selected and user clicks delete
		if(username_list.getSelectionModel().getSelectedItem() == null || username_list.getSelectionModel().getSelectedIndex() == -1) {
			setErrorWindow("Error", "Please select an item before deleting");
			return;
		}
		String username = username_list.getSelectionModel().getSelectedItem().getUsername();
		//removing item from list
		User myUser = username_list.getSelectionModel().getSelectedItem();
		userList.remove(myUser);
		username_list.setItems(userList);
		writeApp(userList);
		//delete the directory
		File file = new File("photoData/" + username);
		if(file.isDirectory() && file != null) {
			deleteDir(file);
		}
	}
	
	/**
	 * Method to delete user's files
	 * @param file File to be deleted
	 * @return File is not found
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
