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
 * This class is used to control the Admin Page
 */
public class AdminController extends Photos implements Serializable{
	
	@FXML ListView<String> username_list;
	@FXML TextField username_textfield;
	
	public void start(Stage mainStage) throws IOException, ClassNotFoundException {
		username_list.setItems(obsList);
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
	
	/**
	 * This method is called when the user clicks on the add button
	 * Adds a user to the list
	 * @throws IOException
	 */
	@FXML
	private void add() throws IOException{
		String username = username_textfield.getText().trim();
		//error if nothing is entered in the textfield
		if(username.equals("")) {
			Photos.setErrorWindow("Invalid Entry", "Please make sure you enter a valid username");
			return;
		}
		//no duplicate users??
		if(obsList.contains(username)) {
			Photos.setErrorWindow("Invalid Entry", "Username already exists");
			return;
		}
		//adding username to the list
		obsList.add(username);
		username_list.setItems(obsList);
		//serialize the user list
		writeApp(obsList);
		
	}
	
	/**
	 * This method is called when the user clicks on the delete button
	 * Deletes a user to the list
	 * @throws IOException 
	 */
	@FXML
	private void delete() throws IOException{
		//if nothing is selected and user clicks delete
		if(username_list.getSelectionModel().getSelectedItem() == null) {
			Photos.setErrorWindow("Error", "Please select an item before deleting");
			return;
		}
		//removing item from list
		String username = username_list.getSelectionModel().getSelectedItem();
		obsList.remove(username);
		username_list.setItems(obsList);
		writeApp(obsList);
	}
}
