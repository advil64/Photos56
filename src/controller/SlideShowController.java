package controller;

import java.io.IOException;
import java.util.ArrayList;

import app.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Photo;

/**
 * This class is used to control the slide show page
 * @author Advith Chegu
 * @author Banty Patel
 */

public class SlideShowController extends OpenAlbumController {
	
	/**
	 * This is the imageview used to display images
	 */
	@FXML ImageView img;
	
	/**
	 * This is the obs list of photos in the album
	 */
	public ObservableList<Photo> pics = FXCollections.observableArrayList();
	/**
	 * This is the index of which image we are on in the list
	 */
	int index = 0;
	
	/**
	 * This method is triggered at the start of the slideshow page
	 */
	public void start(Stage mainStage){
		pics = openedAlbum.getPhotos();
		//show the first item in the photo list
		Image loc = new Image("file:" + pics.get(0).getPhotoPath());
		img.getLayoutBounds();
		img.setImage(loc);
	}
	
	/**
	 * This method is triggered when the back button is clicked
	 * Sets the scene to the previous page
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	@FXML
	private void goBack() throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../view/openAlbum.fxml"));
		root = (AnchorPane)loader.load();
		openAlbumController= loader.getController();
		openAlbumController.start(Photos.window);
		scene = new Scene(root, 714.0, 440.0);
		Photos.window.setScene(scene);
		Photos.window.setTitle("Open Album");
		Photos.window.setResizable(false);
		Photos.window.show();
	}
	
	/**
	 * This method is triggered when the back button is clicked
	 */
	@FXML
	private void prevImage() {
		index--;
		if(index == -1) {
			Photos.setErrorWindow("Error", "This is the first picture, cannot go further back");
			index++;
			return;
		}
		
		Image loc = new Image("file:" + pics.get(index).getPhotoPath());
		img.getLayoutBounds();
		img.setImage(loc);
	}
	
	/**
	 * This method is triggered when the next button is clicked
	 */
	@FXML
	private void nextImage() {
		index++;
		if(index == pics.size()) {
			Photos.setErrorWindow("Error", "This is the last picture, cannot go any further");
			index--;
			return;
		}
		Image loc = new Image("file:" + pics.get(index).getPhotoPath());
		img.getLayoutBounds();
		img.setImage(loc);
	}
}
