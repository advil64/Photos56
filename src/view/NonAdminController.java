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
import model.Album;
import model.Photo;
import model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.io.*;

import app.Photos;

/**
 * This class is used to control the non-admin login page
 */
public class NonAdminController extends Photos implements Serializable{

	@FXML TextField album_textfield;
	@FXML ListView<Album> albumlist;

	
	public void start(Stage mainStage) throws ClassNotFoundException, IOException {
		//display the albumlist for user who signed in
		User temp = new User(random.get(0));
		userList.get(userList.indexOf(temp)).setAlbums(readApp2());
		albumlist.setItems(userList.get(userList.indexOf(temp)).getAlbums());
		albumlist.getSelectionModel().select(0);
	}
	
	public static void writeApp2(ObservableList<Album> myUsers) throws IOException{
    	FileOutputStream fos = new FileOutputStream(random.get(0)+ "-" + "albums.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		for(Album x : myUsers) {
			oos.writeObject(x.toString());
		}
	}
	
	public static ObservableList<Album> readApp2() throws IOException, ClassNotFoundException{
    	//create the file if it doesn't exist
    	File temp = new File(random.get(0)+ "-" + "albums.dat");
    	temp.createNewFile();
    	
    	ObservableList<Album> gapp = FXCollections.observableArrayList();
    	ObjectInputStream ois;
    	try{
    		ois = new ObjectInputStream(new FileInputStream(random.get(0)+ "-" + "albums.dat"));
    	} catch(EOFException e) {
			return gapp;
		}
    	
    	//read the .dat file and populate the observable list (list of albums)
    	while(true) {
    		try {
    			String temp1 = (String)ois.readObject();
    			//find substrings of album, num, and date range
    			int delimeter1 = temp1.indexOf("\n");
    			//album name
    			String album = temp1.substring(12,delimeter1);
    			int delimeter2 = temp1.lastIndexOf("\n");
    			//number of photos
    			int pnum = Integer.valueOf(temp1.substring(delimeter1+17,delimeter2));
    			//date range
    			String dateRange = temp1.substring(delimeter2+13);
    			gapp.add(new Album(album,pnum,dateRange));
    		} catch (EOFException e) {
    			return gapp;
    		}
    	}
    }
    
	
	/**
	 * This method is engaged when the user clicks the create button
	 * Adds an album to the list
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	@FXML
	private void create() throws IOException, ClassNotFoundException {
		//get the name of the album first and run checks
		String albumName = album_textfield.getText().trim();

		if(albumName.equals("")) {
			Photos.setErrorWindow("Invalid Album Name", "Please make sure you enter a valid album name");
			return;
		}
		
		//create the album and add it to the list for that specific user
		Album album = new Album(albumName, 0, null);
		for(int i=0; i<userList.size(); i++) {
			//check to see if album already exists
			for(int j=0; j<userList.get(i).albums.size(); j++) {
				if(userList.get(i).getAlbums().contains(album)) {
					Photos.setErrorWindow("Invalid Album Name", "Album already exists");
					return;
				}
			}
			//add album to albums arraylist in User Object and update the ListView
			if(userList.get(i).getUsername().equals(random.get(0))) {
				userList.get(i).addAlbum(album);
				albumlist.setItems(userList.get(i).getAlbums());
				writeApp2(userList.get(i).getAlbums());
				break;
			}
		}
		
	}
	/**
	 * This method is engaged when the user clicks the rename button
	 * Renames an album to the list
	 * @throws IOException 
	 */
	@FXML
	private void rename() throws IOException {
		//get the name of the album first and run checks
		String albumName = album_textfield.getText().trim();

		if(albumName.equals("")) {
			Photos.setErrorWindow("Invalid Album Name", "Please make sure you enter a valid album name");
			return;
		}
		//get the index selected
		Album selectedIndex = albumlist.getSelectionModel().getSelectedItem();
		
		//find the user in the userlist and change it's album name
		for(User i: userList) {
			//finding the user
			if(i.getUsername().equals(random.get(0))) {
				//set it's album name to albumName
				for(Album j: i.getAlbums()) {
					if(j.getAlbumName().equals(selectedIndex.getAlbumName())) {
						j.setAlbumName(albumName);
						albumlist.setItems(i.getAlbums());
						writeApp2(i.getAlbums());
						break;
					}
				}
			}
		}
	}
	/**
	 * This method is engaged when the user clicks the delete button
	 * Deletes an album to the list
	 * @throws IOException 
	 */
	@FXML
	private void delete() throws IOException {
		int index = albumlist.getSelectionModel().getSelectedIndex();
		//if nothing is select
		if(index == -1) {
			Photos.setErrorWindow("Cannot Delete", "Please make sure you select and item from the list");
			return;
		}
		Album temp = albumlist.getSelectionModel().getSelectedItem();
		//remove album from User's album list
		for(User i: userList) {
			if(i.getUsername().equals(random.get(0))) {
				for(Album j: i.getAlbums()) {
					if(j.getAlbumName().equals(temp.getAlbumName())) {
						i.getAlbums().remove(temp);
						albumlist.setItems(i.getAlbums());
						writeApp2(i.getAlbums());
						break;
					}
				}
			}
		}
	}
	/**
	 * This method is engaged when the user clicks the openAlbum button which sets the scene to the openAlbum page
	 * @throws Exception 
	 */
	@FXML
	private void openAlbum() throws Exception {
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
