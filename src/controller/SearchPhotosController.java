package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import app.Photos;
import app.ReadWrite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.Photo;

/**
 * This class is used to control the search photos page
 * @author Advith Chegu
 * @author Banty Patel
 */

public class SearchPhotosController extends Photos{
	
	/**
	 * List view of the photos
	 */
	@FXML ListView<Photo> photoList;
	/**
	 * Image view of the image display
	 */
	@FXML ImageView image;
	/**
	 * Label of the date for the image
	 */
	@FXML Text datetimeLabel;
	/**
	 * List view of the tags
	 */
	@FXML ListView<String> tagsList;
	/**
	 * Textfield to take in date from
	 */
	@FXML TextField dateFrom;
	/**
	 * Textfield to take in date to
	 */
	@FXML TextField dateTo;
	/**
	 * Combo box for first tag type
	 */
	@FXML ComboBox<String> firstTagType;
	/**
	 * Combo box for second tag type
	 */
	@FXML ComboBox<String> secondTagType;
	/**
	 * Combo box for conjunction type
	 */
	@FXML ComboBox<String> tagConjunction;
	/**
	 * Text field for the first tag
	 */
	@FXML TextField firstTag;
	/**
	 * Text field for the second tag
	 */
	@FXML TextField secondTag;
	/**
	 * Create album button to create a new album
	 */
	@FXML Button createAlbum;
	/**
	 * Array list of conjunctions
	 */
	ArrayList<String> conj = new ArrayList<>();
	/**
	 * List of photos for the new album
	 */
	ObservableList<Photo> list = FXCollections.observableArrayList();
	
	/**
	 * This method is triggered at the start of the search photos page
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void start(Stage mainStage) throws ClassNotFoundException, IOException{
		ArrayList<String> temp = new ArrayList<String>();
		temp.addAll(combo);
		temp.remove("New");
		firstTagType.getItems().setAll(temp);
		secondTagType.getItems().setAll(temp);
		conj.add("ONE");
		conj.add("AND");
		conj.add("OR");
		tagConjunction.getItems().setAll(conj);
		createAlbum.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						TextInputDialog dialog = new TextInputDialog();
						dialog.setTitle("Creating Album");
						dialog.setHeaderText("Enter Album Name:");
						Optional<String> result = dialog.showAndWait();
						if (result.isPresent()){
						   try {
							create(result.get());
							} catch (ClassNotFoundException | IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
		
	}
	
	/**
	 * Method to handle create album button
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	private void create(String albumName) throws ClassNotFoundException, IOException {
		//loop through the albums and check if name already exists
		for(Album x: currUser.getAlbums()) {
			if(x.getAlbumName().equals(albumName.trim())) {
				setErrorWindow("Error", "Album name already exists");
				return;
			}
		}
		//get the size of the photolist to set the num. of photos value in album
		int size = list.size();
		
		//create the album
		Album newAlbum = new Album(albumName, size, "");
		currUser.getAlbums().add(newAlbum);
		//create dir for new album and add to albums.dat
		new File("../data/" + currUser.getUsername() + "/" + albumName).mkdir();
		ReadWrite.writeAlbums(currUser.getAlbums(), currUser);
		
		//add all photos to album
		for(Photo p: list) {
			newAlbum.addPhoto(p);
		}
		//write photos to file
		new File("../data/" + currUser.getUsername() + "/" + albumName + "/photo.dat");
		ReadWrite.writePhotos(newAlbum, currUser);
		
	}
	/**
	 * Method is called when go back button is clicked
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	@FXML
	private void back() throws IOException, ClassNotFoundException {
		setStage("Login Page", "../view/nonadmin.fxml");
	}
	/**
	 * Method is called when an item is selected from the photo list
	 */
	@FXML
	private void displayImage() {
		int index = photoList.getSelectionModel().getSelectedIndex();
		Photo curr = photoList.getSelectionModel().getSelectedItem();
		if(index != -1) {
			image.setImage(curr.getPhoto());
			tagsList.getItems().setAll(curr.getTags());
			datetimeLabel.setText(curr.getDateTime().toString());
		}
	}
	/**
	 * Method is called when the date search button is clicked
	 */
	@FXML
	private void searchDate() {
		
	}
	/**
	 * Method is called when the tag search button is clicked
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	@FXML
	private void searchTag() throws ClassNotFoundException, IOException {
		String tagType1 = firstTagType.getSelectionModel().getSelectedItem();
		String tagType2 = secondTagType.getSelectionModel().getSelectedItem();
		String conjunction = tagConjunction.getSelectionModel().getSelectedItem();
		if(conjunction == null) {
			setErrorWindow("Error", "Please select a conjunction type");
			return;
		}
		String f = firstTag.getText().trim();
		String s = secondTag.getText().trim();
		String firstTag = tagType1 + ": " + f;
		String secondTag = tagType2 + ": " + s;
		ObservableList<Photo> allPhotos = FXCollections.observableArrayList();
		//get all the photos in every album for the user
		for(Album x: currUser.getAlbums()) {
			allPhotos.addAll(ReadWrite.readPhotos(x, currUser));
		}
		//searching by only 1 tag
		if(conjunction.equals("ONE")) {
			if(tagType1 == null) {
				setErrorWindow("Error", "Please select a tag type");
				return;
			}
			if(f.equals("")) {
				setErrorWindow("Error", "Please input a tag in the first TextField");
				return;
			}
			for(Photo p: allPhotos) {
				if(p.getTags().contains(firstTag)) {
					//display them on the list
					list.add(p);
					setPhotos();
				}
			}
		}
		//searching with OR
		if(conjunction.equals("OR")) {
			if(f.equals("") && s.equals("")) {
				setErrorWindow("Error", "Please input a tag in the first and/or second textfield");
				return;
			}
			if(!f.equals("") && s.equals("") && tagType1 == null) {
				setErrorWindow("Error", "Please select a tag type for entry 1");
				return;
			}
			if(f.equals("") && !s.equals("") && tagType2 == null) {
				setErrorWindow("Error", "Please select a tag type for entry 2");
				return;
			}
			if(!f.equals("") && !s.equals("") && (tagType1 == null || tagType2 == null)) {
				setErrorWindow("Error", "Please select a tag type for both entries");
				return;
			}
			for(Photo p: allPhotos) {
				if(p.getTags().contains(firstTag) || p.getTags().contains(secondTag)) {
					//display them on the list
					list.add(p);
					setPhotos();
				}
			}
		}
		//searching with AND
		if(conjunction.equals("AND")) {
			if(f.equals("") || s.equals("")) {
				setErrorWindow("Error", "Please input a tag in the first and second textfield");
				return;
			}
			if(tagType1 == null || tagType2==null) {
				setErrorWindow("Error", "Please select a tag type for both entries");
				return;
			}
			for(Photo p: allPhotos) {
				if(p.getTags().contains(firstTag) && p.getTags().contains(secondTag)) {
					//display them on the list
					list.add(p);
					setPhotos();
				}
			}
		}
		
	}
	
	/**
	 * Method to set the list view
	 */
	private void setPhotos(){
		photoList.setItems(list);
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
}
