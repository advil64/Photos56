package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Photo;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;

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
	 * The combo box to specify tag type
	 */
	@FXML ComboBox<String> tagTypeBox;
	/**
	 * The text field for user inputted tag type
	 */
	@FXML TextField tagTypeText;
	@FXML ComboBox<String> moveTypeBox;

	/**
	 * This method is triggered at the start of the openAlbum page
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void start(Stage mainStage){

		//setting the combo box default values
		tagTypeBox.getItems().setAll("Location", "Person");

		//getting albums
		ArrayList<String> myAlbums = new ArrayList<>();
		for (Album x: currUser.getAlbums()){
			myAlbums.add(x.getAlbumName());
		}
		moveTypeBox.getItems().setAll(myAlbums);

		//populating the listview of captions and photos
		try {
			openedAlbum.setPhotos(readApp3(openedAlbum));
		} catch (ClassNotFoundException | IOException | ParseException e) {
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
		setStage("Login Page", "../view/nonadmin.fxml");
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
	private void slideShow() throws IOException, ClassNotFoundException {
		setStage("Slide Show", "../view/slideshow.fxml");
	}
	
	/**
	 * This method is triggered when the caption/recaption button is clicked
	 * @throws IOException 
	 */
	@FXML
	private void captionRecaption() throws IOException {
		if(photoList.getSelectionModel().getSelectedIndex() < 0) {
			setErrorWindow("Error", "Please select an item from the list");
			return;
		}
		Photo curr = photoList.getSelectionModel().getSelectedItem();
		String cap = captionTextField.getText().trim();

		//error if nothing is entered in the text field
		if(cap.equals("")) {
			setErrorWindow("Invalid Entry", "Please make sure you enter a valid caption");
			return;
		}
		curr.setCaption(cap);
		writeApp3(openedAlbum);
		writeApp2(currUser.getAlbums());
		photoList.refresh();
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
		if(photoList.getSelectionModel().getSelectedIndex() < 0) {
			setErrorWindow("Error", "Please select a photo before removing");
			return;
		}
		Photo currPhoto = photoList.getSelectionModel().getSelectedItem();
		openedAlbum.removePhoto(currPhoto);
		writeApp2(currUser.getAlbums());
		writeApp3(openedAlbum);
	}
	
	/**
	 * This method is triggered when the move photo button is clicked
	 */
	@FXML
	private void movePhoto() {
		if(photoList.getSelectionModel().getSelectedIndex() < 0) {
			setErrorWindow("Error", "Please select a photo before moving to different album");
			return;
		}
	}

	@FXML
	private void selectPhoto() throws IOException, ClassNotFoundException, ParseException {
		String myAlbumName = moveTypeBox.getValue();
		Photo currPhoto = photoList.getSelectionModel().getSelectedItem();

		//check if user tries to move it into current album
		if(myAlbumName.equals(openedAlbum.albumName)){
			setErrorWindow("Unable to Comply", "Photo already exists in chosen album");
		} else{
			//get the chosen album
			for(Album x: currUser.getAlbums()){
				if(myAlbumName.equals(x.getAlbumName())){

					//load in the current images first
					x.setPhotos(readApp3(x));

					//then add the photo in and rewrite
					x.addPhoto(currPhoto);
					openedAlbum.removePhoto(currPhoto);
					writeApp3(x);
				}
			}
			photoList.refresh();
			writeApp3(openedAlbum);
			writeApp2(currUser.getAlbums());
		}
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
		writeApp3(openedAlbum);
		writeApp2(currUser.getAlbums());
	}

	public static void writeApp3(Album myAlbum) throws IOException{
    	FileOutputStream fos = new FileOutputStream("../data/" + currUser.getUsername() + "/" + myAlbum.getAlbumName() + "/photo.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		for(Photo x : myAlbum.getPhotos()) {
			oos.writeObject(x.toString());
		}
	}
	
	public static ObservableList<Photo> readApp3(Album myAlbum) throws IOException, ClassNotFoundException, ParseException{
    	//create the file if it doesn't exist
    	File temp = new File("../data/" + currUser.getUsername() + "/" + myAlbum.getAlbumName() + "/photo.dat");
    	temp.createNewFile();
    	
    	ObservableList<Photo> gapp = FXCollections.observableArrayList();
    	ObjectInputStream ois;
    	try{
    		ois = new ObjectInputStream(new FileInputStream("../data/" + currUser.getUsername() + "/" + myAlbum.getAlbumName() + "/photo.dat"));
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
