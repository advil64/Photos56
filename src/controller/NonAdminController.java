/**
 * @author Advith Chegu
 * @author Banty Patel
*/
package controller;

import app.ReadWrite;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.io.*;
import java.util.ArrayList;

import app.Photos;

/**
 * This class is used to control the non-admin login page
 */
public class NonAdminController extends Photos implements Serializable{
	
	/**
	 * Holds the album entered in the textfield
	 */
	@FXML TextField album_textfield;
	/**
	 * ListView of the albums
	 */
	@FXML ListView<Album> albumlist;
	/**
	 * Album that is opened is stored
	 */
	public static Album openedAlbum;

	/**
	 * This method is ran at the beginning of the nonAdminController
	 * @param mainStage - main stage of photo app
	 * @throws ClassNotFoundException File is not found
	 * @throws IOException File is not found
	 */
	public void start(Stage mainStage) throws ClassNotFoundException, IOException {
		//setting up the combo box
		File temp = new File("photoData/" + currUser.getUsername() + "/combo.dat");
        if(temp.exists() == false) {
        	temp.createNewFile();
        	combo.add("New");
    		combo.add("Location");
    		combo.add("Person");
    		ReadWrite.writeCombo(combo, currUser);
        }
        combo = ReadWrite.readCombo(currUser);
		//display the album list for user who signed in
		ReadWrite.readAlbums(currUser);
		albumlist.setItems(currUser.getAlbums());
	}
	
	/**
	 * This method is engaged when the user clicks the create button
	 * Adds an album to the list
	 * @throws IOException File is not found
	 */
	@FXML
	private void create() throws IOException {

		//get the name of the album first and run checks
		String albumName = album_textfield.getText().trim();
		if(albumName.equals("")) {
			setErrorWindow("Invalid Album Name", "Please make sure you enter a valid album name");
			return;
		}
		
		//create the album and add it to the list for that specific user
		for (Album x: currUser.getAlbums()){
			if(x.getAlbumName().equalsIgnoreCase(albumName)){
				setErrorWindow("Invalid Album Name", "Album already exists");
				return;
			}
		}

		//otherwise we have a valid album name
		Album newAlbum = new Album(albumName, 0, "");
		currUser.getAlbums().add(newAlbum);
		ReadWrite.writeAlbums(currUser.getAlbums(), currUser);

		//create a directory for the album
		new File("photoData/" + currUser.getUsername() + "/" + albumName).mkdir();
	}

	/**
	 * This method is engaged when the user clicks the rename button
	 * Renames an album to the list
	 * @throws IOException File is not found
	 */
	@FXML
	private void rename() throws IOException {
		//get the name of the album first and run checks
		String albumName = album_textfield.getText().trim();

		if(albumName.equals("")) {
			setErrorWindow("Invalid Album Name", "Please make sure you enter a valid album name");
			return;
		} else if(albumlist.getSelectionModel().getSelectedIndex() < 0){
			return;
		}
		//checking if renaming is not allowed
		for (Album x: currUser.getAlbums()){
			if(x.getAlbumName().equalsIgnoreCase(albumName)){
				setErrorWindow("Invalid Album Name", "Album already exists");
				return;
			}
		}

		Album curr = albumlist.getSelectionModel().getSelectedItem();
		String oldName = curr.getAlbumName();
		curr.setAlbumName(albumName);
		ReadWrite.writeAlbums(currUser.getAlbums(), currUser);
		albumlist.refresh();

		//rename the album's directory
		File newName = new File("photoData/" + currUser.getUsername() + "/" + albumName);
		File file = new File("photoData/" + currUser.getUsername() + "/" + oldName);
		file.renameTo(newName);
	}
	
	/**
	 * This method is engaged when the user clicks the delete button
	 * Deletes an album to the list
	 * @throws IOException File is not found
	 */
	@FXML
	private void delete() throws IOException {

		//if nothing is select
		if(albumlist.getSelectionModel().getSelectedIndex() < 0) {
			setErrorWindow("Cannot Delete", "Please make sure you select an item from the list");
			return;
		}

		//remove album from User's album list
		Album temp = albumlist.getSelectionModel().getSelectedItem();
		currUser.getAlbums().remove(temp);
		ReadWrite.writeAlbums(currUser.getAlbums(), currUser);

		//delete the directory
		File file = new File("photoData/" + currUser.getUsername() + "/" + temp.getAlbumName());
		if(file.isDirectory() && file != null) {
			deleteDir(file);
		}
	}	
	
	/**
	 * Method used to delete an album's directory
	 * @param file - directory
	 * @return - returns true if directory is deleted
	 */
	public static boolean deleteDir(File file) {
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			for (File value : files) {
				boolean boo = deleteDir(value);
				if (boo == false) {
					return false;
				}
			}
		}
		return file.delete();
	}
	
	/**
	 * This method is engaged when the user clicks the openAlbum button which sets the scene to the openAlbum page
	 * @throws ClassNotFoundException File is not found
	 * @throws IOException File is not found
	 */
	@FXML
	private void openAlbum() throws IOException, ClassNotFoundException {
		//make sure an album is selected
		if(albumlist.getSelectionModel().getSelectedIndex() == -1) {
			setErrorWindow("Cannot Open Album", "Please make sure you select an album");
			return;
		}
		openedAlbum = albumlist.getSelectionModel().getSelectedItem();
		//setting the scene to open album page
		combo = ReadWrite.readCombo(currUser);
		setStage("Open Album", "../view/openAlbum.fxml");
	}
	/**
	 * This method is engaged when the user clicks the search photos button which sets the scene to the searchPhotos page
	 * @throws IOException File is not found
	 * @throws ClassNotFoundException File is not found
	 */
	@FXML
	private void searchPhotos() throws IOException, ClassNotFoundException{
		setStage("Searching Photos", "../view/searchPhotos.fxml");
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
}
