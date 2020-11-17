package controller;

import app.ReadWrite;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Photo;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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
	 * The tags text field used for obtaining data
	 */
	@FXML TextField tagsTextField;
	/**
	 * The caption text field used for obtaining data
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
	/**
	 * The combo box to specify album to move to 
	 */
	@FXML ComboBox<String> moveTypeBox;
	/**
	 * The combo box to specify album to copy to
	 */
	@FXML ComboBox<String> copyComBox;
	

	/**
	 * This method is triggered at the start of the openAlbum page
	 * @param mainStage - main stage of the photo app
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void start(Stage mainStage) throws ClassNotFoundException, IOException{
		combo = ReadWrite.readCombo(currUser);
		//setting the combo box default values
		tagTypeBox.getItems().setAll(combo);

		//getting albums
		ArrayList<String> myAlbums = new ArrayList<>();
		for (Album x: currUser.getAlbums()){
			myAlbums.add(x.getAlbumName());
		}
		moveTypeBox.getItems().setAll(myAlbums);
		copyComBox.getItems().setAll(myAlbums);

		//populating the listview of captions and photos
		try {
			openedAlbum.setPhotos(ReadWrite.readPhotos(openedAlbum, currUser));
			//currUser.setPhotos(ReadWrite.readPhotos(openedAlbum, currUser));
		} catch (ClassNotFoundException | IOException e) {
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
	 * This method displays the image in the image view when an item is clicked on in the photo list
	 */
	@FXML
	private void displayImage(){
		try {
			Photo curr = photoList.getSelectionModel().getSelectedItem();
			int index = photoList.getSelectionModel().getSelectedIndex();
			if(curr == null) {
				return;
			}
			image.setImage(curr.getPhoto());
			datetimeLabel.setText(curr.getDateTime().toString());
			captionTextField.setText(curr.getCaption());
			tagsList.getItems().setAll(openedAlbum.getPhotos().get(index).getTags());
		} catch(Exception e){
			e.printStackTrace();
		}
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
	private void captionRecaption() throws IOException, ClassNotFoundException {
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
		//go through the albums to see which ones have that picture
		Photo temp;
		for(Album a: currUser.getAlbums()){
			//load in the current images first
			a.setPhotos(ReadWrite.readPhotos(a, currUser));
			if(a.getPhotos().contains(curr)){
				temp = a.getPhotos().get(a.getPhotos().indexOf(curr));
				temp.setCaption(cap);
			}
		}
		for(Album al: currUser.getAlbums()){
			ReadWrite.writePhotos(al, currUser);
			ReadWrite.writeAlbums(currUser.getAlbums(), currUser);
		}
		photoList.setItems(openedAlbum.getPhotos());
		photoList.refresh();
	}
	
	/**
	 * This method is triggered when the add tag button is clicked
	 * @throws IOException 
	 */
	@FXML
	private void addTag() throws IOException, ClassNotFoundException {
		String boxType = tagTypeBox.getSelectionModel().getSelectedItem();
		String textType = tagTypeText.getText().trim();
		String tag = tagsTextField.getText().trim();
		if(photoList.getSelectionModel().getSelectedIndex() == -1) {
			setErrorWindow("Error", "Please select an image");
			return;
		}
		if(tag == null || tag.equals("")) {
			setErrorWindow("Error", "Please enter a tag");
			return;
		}
		if(boxType == null && (textType == null || textType.equals(""))) {
			setErrorWindow("Error", "Select an item from the combobox or input your own tag type");
			return;
		}
		if(boxType == null && textType != null) {
			setErrorWindow("Error", "Select \"New\" from combo box to input cutom tag type");
			return;
		}
		if(boxType.equals("New") && (textType == null || textType.equals(""))) {
			setErrorWindow("Error", "Enter a tag type in the combo box");
			return;
		}
		//if the user chooses something from the combobox and for the textfield
		if(!boxType.equals("New") && (!textType.equals("") && textType != null)) {
			if(!boxType.equalsIgnoreCase(textType)) {
				setErrorWindow("Error", "Select new from the combo box or empty tag type");
				return;
			}
		}
		
		//choosing something from the combobox
		if(((textType == null || textType.equals("")) && boxType != null) || (boxType.equalsIgnoreCase(textType))) {
			String result = boxType + ": " + tag;
			Photo curr = photoList.getSelectionModel().getSelectedItem();
			int index = openedAlbum.getPhotos().indexOf(curr);
			//search for duplicates
			for(String s: openedAlbum.getPhotos().get(index).getTags()) {
				if(s.equalsIgnoreCase(result)) {
					setErrorWindow("Error", "Tag already exists");
					return;
				}
			}
			//go through the albums to see which ones have that picture
			Photo temp;
			for(Album a: currUser.getAlbums()){
				//load in the current images first
				a.setPhotos(ReadWrite.readPhotos(a, currUser));
				if(a.getPhotos().contains(curr)){
					temp = a.getPhotos().get(a.getPhotos().indexOf(curr));
					temp.addTag(result);
				}
			}
			for(Album al: currUser.getAlbums()){
				ReadWrite.writePhotos(al, currUser);
				ReadWrite.writeAlbums(currUser.getAlbums(), currUser);
			}
			photoList.setItems(openedAlbum.getPhotos());
			photoList.refresh();
			ReadWrite.writePhotos(openedAlbum, currUser);
			tagsList.getItems().setAll(openedAlbum.getPhotos().get(index).getTags());
			return;
		}
		
		//checking if tag is in combo box
		boolean unique = true;
		for(String s: combo) {
			if(s.equalsIgnoreCase(textType)) {
				unique = false;
			}
		}
		//entering a new tag type
		if(unique == true) {
			combo.add(textType);
		}
		tagTypeBox.getItems().setAll(combo);
		String result = textType + ": " + tag;
		Photo curr = photoList.getSelectionModel().getSelectedItem();
		int index = openedAlbum.getPhotos().indexOf(curr);
		//search for duplicates
		for(String s: openedAlbum.getPhotos().get(index).getTags()) {
			if(s.equalsIgnoreCase(result)) {
				setErrorWindow("Error", "Tag already exists");
				return;
			}
		}
		//go through the albums to see which ones have that picture
		Photo temp;
		for(Album a: currUser.getAlbums()){
			//load in the current images first
			a.setPhotos(ReadWrite.readPhotos(a, currUser));
			if(a.getPhotos().contains(curr)){
				temp = a.getPhotos().get(a.getPhotos().indexOf(curr));
				temp.addTag(result);
			}
		}
		for(Album al: currUser.getAlbums()){
			ReadWrite.writePhotos(al, currUser);
			ReadWrite.writeAlbums(currUser.getAlbums(), currUser);
		}
		photoList.setItems(openedAlbum.getPhotos());
		photoList.refresh();
		ReadWrite.writeCombo(combo, currUser);
		tagsList.getItems().setAll(openedAlbum.getPhotos().get(index).getTags());
	}
	
	/**
	 * This method is triggered when the delete tag button is clicked
	 * @throws IOException 
	 */
	@FXML
	private void deleteTag() throws IOException, ClassNotFoundException {
		if(photoList.getSelectionModel().getSelectedIndex() == -1) {
			setErrorWindow("Error", "Please select an image");
			return;
		}
		if(tagsList.getSelectionModel().getSelectedIndex() == -1) {
			setErrorWindow("Error", "Please select a tag");
			return;
		}
		Photo curr = photoList.getSelectionModel().getSelectedItem();
		int index = openedAlbum.getPhotos().indexOf(curr);
		String temp = tagsList.getSelectionModel().getSelectedItem();
		//go through the albums to see which ones have that picture
		Photo temp1;
		for(Album a: currUser.getAlbums()){
			//load in the current images first
			a.setPhotos(ReadWrite.readPhotos(a, currUser));
			if(a.getPhotos().contains(curr)){
				temp1 = a.getPhotos().get(a.getPhotos().indexOf(curr));
				temp1.removeTag(temp);
			}
		}
		for(Album al: currUser.getAlbums()){
			ReadWrite.writePhotos(al, currUser);
			ReadWrite.writeAlbums(currUser.getAlbums(), currUser);
		}
		photoList.setItems(openedAlbum.getPhotos());
		photoList.refresh();
		tagsList.getItems().setAll(openedAlbum.getPhotos().get(index).getTags());	
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
		ReadWrite.writeAlbums(currUser.getAlbums(), currUser);
		ReadWrite.writePhotos(openedAlbum, currUser);
	}
	
	/**
	 * This method is triggered when the move photo button is clicked
	 */
	@FXML
	private void moveCopyPhoto() {
		if(photoList.getSelectionModel().getSelectedIndex() < 0) {
			setErrorWindow("Error", "Select a photo before moving to different album");
			return;
		}
	}
	
	/**
	 * This method moves the photo to an album
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void movePhoto() throws IOException, ClassNotFoundException {
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
					x.setPhotos(ReadWrite.readPhotos(x, currUser));

					//check if the album has the photo already
					if(!x.getPhotos().contains(currPhoto)) {

						//then add the photo in and rewrite
						x.addPhoto(currPhoto);
						openedAlbum.removePhoto(currPhoto);
						ReadWrite.writePhotos(x, currUser);
					} else{
						setErrorWindow("Duplicate Photo", "The photo trying to be moved already exists in the album");
					}
				}
			}
			photoList.refresh();
			ReadWrite.writePhotos(openedAlbum, currUser);
			ReadWrite.writeAlbums(currUser.getAlbums(), currUser);
		}
		moveTypeBox.getSelectionModel().clearSelection();
	}
	
	/**
	 * This method is triggered when the copy photo button is clicked
	 */
	@FXML
	private void copyPhoto() throws IOException, ClassNotFoundException {
		String myAlbumName = copyComBox.getValue();
		Photo currPhoto = photoList.getSelectionModel().getSelectedItem();

		//check if user tries to move it into current album
		if(myAlbumName.equals(openedAlbum.albumName)){
			setErrorWindow("Unable to Comply", "Photo already exists in chosen album");
		} else{
			//get the chosen album
			for(Album x: currUser.getAlbums()){
				if(myAlbumName.equals(x.getAlbumName())){

					//load in the current images first
					x.setPhotos(ReadWrite.readPhotos(x, currUser));

					//check if the album has the photo already
					if(!x.getPhotos().contains(currPhoto)) {
						//then add the photo in and rewrite
						x.addPhoto(currPhoto);
						ReadWrite.writePhotos(x, currUser);
					} else{
						setErrorWindow("Duplicate Photo", "The photo trying to be copied already exists in the album");
					}
				}
			}
			photoList.refresh();
			ReadWrite.writeAlbums(currUser.getAlbums(), currUser);
		}
		copyComBox.getSelectionModel().clearSelection();
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
	 * @throws ClassNotFoundException 
	 */
	@FXML
	private void addPhoto() throws IOException, ClassNotFoundException {

		//open dialog for choosing a new photo
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("File Chooser");
		File file = fileChooser.showOpenDialog(null);

		//add the new photo to the current user's images
		if (file != null) {
			//check if it's an image
			boolean b = false;
			try {
		        b = (ImageIO.read(file) != null);
		    } catch (IOException e) {
		        setErrorWindow("Error", "File is not an image");
		        return;
		    }
			if(b==false) {
				setErrorWindow("Error", "File is not an image");
		        return;
			}
			
	        //path of the file selected is stored
	        String photoPath = (file.getPath());
	        Photo newPhoto = new Photo("", new ArrayList<>(), photoPath);
	        //checking for duplicates
	        for(Photo p: openedAlbum.getPhotos()) {
	        	if(p.getPhotoPath().equals(photoPath)) {
	        		setErrorWindow("Error", "Photo already exists in album");
	        		return;
	        	}
	        }
	        ArrayList<Photo> allPhotos = new ArrayList<>();
	        for(Album a: currUser.getAlbums()) {
	        	allPhotos.addAll(ReadWrite.readPhotos(a, currUser));
	        }
	        //go through the albums to see which ones have that picture
			Photo temp1;
			for(Album a: currUser.getAlbums()){
				//load in the current images first
				a.setPhotos(ReadWrite.readPhotos(a, currUser));
				for(Photo p: a.getPhotos()) {
					if(p.getPhotoPath().equals(photoPath)) {
						openedAlbum.addPhoto(p);
						currUser.addPhoto(p);
						setPhotos();
						ReadWrite.writePhotos(openedAlbum, currUser);
						ReadWrite.writeAlbums(currUser.getAlbums(), currUser);
						return;
					}
				}
			}
	        openedAlbum.addPhoto(newPhoto);
	        currUser.addPhoto(newPhoto);
	        setPhotos();
	    }
		ReadWrite.writePhotos(openedAlbum, currUser);
		ReadWrite.writeAlbums(currUser.getAlbums(), currUser);
	}
}
