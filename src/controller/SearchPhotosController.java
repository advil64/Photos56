package controller;

import java.io.IOException;
import java.util.ArrayList;

import app.Photos;
import app.ReadWrite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
	
	@FXML ListView<Photo> photoList;
	@FXML ImageView image;
	@FXML Text datetimeLabel;
	@FXML ListView<String> tagsList;
	@FXML TextField dateFrom;
	@FXML TextField dateTo;
	@FXML ComboBox<String> firstTagType;
	@FXML ComboBox<String> secondTagType;
	@FXML ComboBox<String> tagConjunction;
	@FXML TextField firstTag;
	@FXML TextField secondTag;
	ArrayList<String> conj = new ArrayList<>();
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
	}
	
	/**
	 * Method is called when create album button is clicked
	 */
	@FXML
	private void create() {
		
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
