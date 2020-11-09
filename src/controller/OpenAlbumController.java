/**
 * @author Advith Chegu
 * @author Banty Patel
*/
package controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import app.Photos;
/**
 * This class is used to control the OpenAlbum page
 */
public class OpenAlbumController extends NonAdminController{
	
	Image image1 = new Image("file:sample1.jpg");
	Image image2 = new Image("file:sample2.jpg");
	Image image3 = new Image("file:sample3.jpg");
	Image image4 = new Image("file:sample4.jpg");
	
	Image[] ImageList = {image1,image2,image3,image4};
	ObservableList<String> items = FXCollections.observableArrayList("Caption1", "Caption2", "Caption3", "Caption4");
	
	@FXML ListView<String> photolist;
	@FXML ImageView image;
	@FXML ListView<String> tagslist;
	@FXML TextField tags_textfield;
	@FXML TextField caption_textfield;
	
	public void start(Stage mainStage){
		//populating the listview of captions and photos
        photolist.setItems(items);
        photolist.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if(name.equals("Caption1")) {
                        imageView.setImage(ImageList[0]);
                    	imageView.setFitHeight(50);
                    	imageView.setFitWidth(50);
                    }else if(name.equals("Caption2")) {
                        imageView.setImage(ImageList[1]);
                        imageView.setFitHeight(50);
                        imageView.setFitWidth(50);
                    }else if(name.equals("Caption3")) {
                        imageView.setImage(ImageList[2]);
                        imageView.setFitHeight(50);
                        imageView.setFitWidth(50);
                    }else if(name.equals("Caption4")) {
                        imageView.setImage(ImageList[3]);
                        imageView.setFitHeight(50);
                		imageView.setFitWidth(50);
                    }
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
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
		int index = photolist.getSelectionModel().getSelectedIndex();
		image.setImage(ImageList[index]);
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
	 * This method is triggered when the add photo button is clicked
	 */
	@FXML
	private void add_photo() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("File Chooser");
		Window mainStage = null;
		
		File file = fileChooser.showOpenDialog(mainStage);
		if (file != null) {
	        //path of the file selected is stored
	        String photoPath =(file.getPath());
	        System.out.println(photoPath);
	    } else  {
	        return;
	    }
	}
}
