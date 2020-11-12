package controller;

import java.io.IOException;

import app.Photos;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
	
	/**
	 * This method is triggered at the start of the search photos page
	 */
	public void start(Stage mainStage){
		
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
		
	}
	/**
	 * Method is called when the date search button is clicked
	 */
	@FXML
	private void searchDate() {
		
	}
	/**
	 * Method is called when the tag search button is clicked
	 */
	@FXML
	private void searchTag() {
		
	}
}
