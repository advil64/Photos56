package controller;

import app.Photos;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Photo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used to control the OpenAlbum page
 * @author Advith Chegu
 * @author Banty Patel
 */
public class OpenAlbumController extends Photos{

	@FXML ListView<Photo> photoList;
	@FXML ImageView image;
	@FXML ListView<String> tagsList;
	@FXML TextField tagsTextField;
	@FXML TextField captionTextField;
	
	public void start(Stage mainStage){
		//populating the listview of captions and photos
		setPhotos();
	}
	
	/**
	 * This method is triggered when the back button is clicked
	 * Sets the scene to the previous page
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	@FXML
	private void back() throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../view/nonadmin.fxml"));
		root = (AnchorPane)loader.load();
		nonAdminController= loader.getController();
		nonAdminController.start(Photos.window);
		Scene scene = new Scene(root, 714.0, 440.0);
		Photos.window.setScene(scene);
		Photos.window.setTitle("Login Page");
		Photos.window.setResizable(false);
		Photos.window.show();
	}
	
	/**
	 * This method displays the image in the image view when an item is clicked on in the photolist
	 */
	@FXML
	private void displayImage(){
		Photo curr = photoList.getSelectionModel().getSelectedItem();
		image.setImage(curr.getPhoto());
	}
	
	/**
	 * This method is triggered when the user clicks on the view slideshow button
	 */
	@FXML
	private void slideshow() {
		
	}
	
	/**
	 * This method is triggered when the caption/recaption button is clicked
	 */
	@FXML
	private void caption_recaption() {
		
	}
	
	/**
	 * This method is triggered when the add tag button is clicked
	 */
	@FXML
	private void add_tag() {
		
	}
	
	/**
	 * This method is triggered when the delete tag button is clicked
	 */
	@FXML
	private void delete_tag() {
		
	}
	
	/**
	 * This method is triggered when the remove photo button is clicked
	 */
	@FXML
	private void remove_photo() {
		
	}
	
	/**
	 * This method is triggered when the move photo button is clicked
	 */
	@FXML
	private void move_photo() {
		
	}
	
	/**
	 * This method is triggered when the copy photo button is clicked
	 */
	@FXML
	private void copy_photo() {
		
	}

	/**
	 * Sets the photos list
	 */
	private void setPhotos(){
		photoList.setItems(currUser.getPhotos());
		photoList.setCellFactory(param -> new ListCell<Photo>() {
			private ImageView imageView = new ImageView();
			@Override
			protected void updateItem(Photo item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setText(null);
					setGraphic(null);
				} else {
					imageView.setImage(item.getPhoto());
					imageView.setFitHeight(50);
					imageView.setFitWidth(50);
					setText(item.toString());
					setGraphic(imageView);
				}
			}
		});
	}
	
	/**
	 * This method is triggered when the add photo button is clicked
	 */
	@FXML
	private void add_photo() {

		//open dialog for choosing a new photo
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("File Chooser");
		File file = fileChooser.showOpenDialog(null);

		//add the new photo to the current user's images
		if (file != null) {
	        //path of the file selected is stored
	        String photoPath = (file.getPath());
	        currUser.addPhoto(new Photo("", new ArrayList<>(), photoPath));
	        setPhotos();
	    }
	}
}
