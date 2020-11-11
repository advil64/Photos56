package controller;

import app.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class is used to control the OpenAlbum page
 * @author Advith Chegu
 * @author Banty Patel
 */
public class OpenAlbumController extends NonAdminController implements Serializable{

	/**
	 * ListView of the photos and captions
	 */
	@FXML ListView<Photo> photoList;
	/**
	 * ImageView to display an image
	 */
	@FXML ImageView image;
	/**
	 * ListView to display tags
	 */
	@FXML ListView<String> tagsList;
	/**
	 * The tags textfield used for obtaining data
	 */
	@FXML TextField tagsTextField;
	/**
	 * The caption textfield used for obtaining data
	 */
	@FXML TextField captionTextField;
	/**
	 * The label to display the dateTime of an image
	 */
	@FXML Text datetimeLabel;
	
	/**
	 * This method is triggered at the start of the openAlbum page
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void start(Stage mainStage) throws ClassNotFoundException, IOException{
		//populating the listview of captions and photos
		try {
			openedAlbum.setPhotos(readApp3());
		} catch (ClassNotFoundException | IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		datetimeLabel.setText(curr.getDateTime().toString());
		captionTextField.setText(curr.getCaption());
	}
	
	/**
	 * This method is triggered when the user clicks on the view slideshow button
	 * @throws IOException 
	 */
	@FXML
	private void slideShow() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../view/slideshow.fxml"));
		root = (AnchorPane)loader.load();
		slideShowController= loader.getController();
		slideShowController.start(Photos.window);
		scene = new Scene(root, 714.0, 440.0);
		Photos.window.setScene(scene);
		Photos.window.setTitle("Slide Show");
		Photos.window.setResizable(false);
		Photos.window.show();
	}
	
	/**
	 * This method is triggered when the caption/recaption button is clicked
	 * @throws IOException 
	 */
	@FXML
	private void captionRecaption() throws IOException {
		int index = photoList.getSelectionModel().getSelectedIndex();
		if(index == -1) {
			Photos.setErrorWindow("Error", "Please select an item from the list");
			return;
		}
		Photo curr = photoList.getSelectionModel().getSelectedItem();
		String cap = captionTextField.getText().trim();

		//error if nothing is entered in the text field
		if(cap.equals("")) {
			Photos.setErrorWindow("Invalid Entry", "Please make sure you enter a valid caption");
			return;
		}
		curr.setCaption(cap);
		writeApp3(currUser.getPhotos());
		setPhotos();
	}
	
	/**
	 * This method is triggered when the add tag button is clicked
	 */
	@FXML
	private void addTag() {
		
	}
	
	/**
	 * This method is triggered when the delete tag button is clicked
	 */
	@FXML
	private void deleteTag() {
		
	}
	
	/**
	 * This method is triggered when the remove photo button is clicked
	 * @throws IOException 
	 */
	@FXML
	private void removePhoto() throws IOException {
		
	}
	
	/**
	 * This method is triggered when the move photo button is clicked
	 */
	@FXML
	private void movePhoto() {
		
	}
	
	/**
	 * This method is triggered when the copy photo button is clicked
	 */
	@FXML
	private void copyPhoto() {
		
	}

	/**
	 * Sets the photos list
	 */
	private void setPhotos(){
		photoList.setItems(openedAlbum.getPhotos());
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
					setText(item.toString2());
					setGraphic(imageView);
				}
			}
		});
	}
	
	/**
	 * This method is triggered when the add photo button is clicked
	 * @throws IOException 
	 */
	@FXML
	private void addPhoto() throws IOException {

		//open dialog for choosing a new photo
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("File Chooser");
		File file = fileChooser.showOpenDialog(null);

		//add the new photo to the current user's images
		if (file != null) {
	        //path of the file selected is stored
	        String photoPath = (file.getPath());
	        Photo newPhoto = new Photo("", new ArrayList<>(), photoPath);
	        openedAlbum.addPhoto(newPhoto);
	        currUser.addPhoto(newPhoto);
	        setPhotos();
	    }
		//write to photo.dat file
		writeApp3(openedAlbum.getPhotos());
	}
	public static void writeApp3(ObservableList<Photo> myUsers) throws IOException{
    	FileOutputStream fos = new FileOutputStream("../data/" + currUser.getUsername() + "/" + openedAlbum.getAlbumName() + "/photo.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		for(Photo x : myUsers) {
			oos.writeObject(x.toString());
		}
	}
	
	public static ObservableList<Photo> readApp3() throws IOException, ClassNotFoundException, ParseException{
    	//create the file if it doesn't exist
    	File temp = new File("../data/" + currUser.getUsername() + "/" + openedAlbum.getAlbumName() + "/photo.dat");
    	temp.createNewFile();
    	
    	ObservableList<Photo> gapp = FXCollections.observableArrayList();
    	ObjectInputStream ois;
    	try{
    		ois = new ObjectInputStream(new FileInputStream("../data/" + currUser.getUsername() + "/" + openedAlbum.getAlbumName() + "/photo.dat"));
    	} catch(EOFException e) {
			return gapp;
		}
    	
    	//read the .dat file and populate the observable list (list of albums)
    	while(true) {
    		try {
    			String temp1 = (String)ois.readObject();
    			//find substrings of caption, tags, datetime, photoPath
    			int delimeter1 = temp1.indexOf("|");
    			//getting the captions
    			String caption = temp1.substring(0, delimeter1);
    			int delimeter2 = temp1.lastIndexOf("|");
    			ArrayList<String> tags = new ArrayList<>();
    			//getting the tags
    			String tagTemp = temp1.substring(delimeter1+2, delimeter2-1);
    			String[] tagTemp2 = tagTemp.split(",");
    			for(String x: tagTemp2) {
    				tags.add(x);
    			}
    			//getting the location
    			String location = temp1.substring(delimeter2+1);
    			gapp.add(new Photo(caption,tags,location));

    		} catch (EOFException e) {
    			return gapp;
    		}
    	}
    }
}
