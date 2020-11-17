package model;

import java.io.Serializable;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class that defines an album
 * @author Advith Chegu
 * @author Banty Patel
 */
public class Album implements Serializable{

    /**
     * albumName
     */
    public String albumName;
    /**
     * Number of photos for the album
     */
    public int numPhotos;
    /**
     * date range for the album
     */
    public String dateRange;
    /**
     * list of photos in the album
     */
    public ObservableList<Photo> albumPhotos;

    /**
     * Creates a new Album object
     * @param albumName name of the new album
     * @param dateRange range of dates of the album
     * @param numPhotos number of photos in the album
     */
    public Album(String albumName, int numPhotos, String dateRange){
        this.albumName = albumName;
        this.numPhotos = numPhotos;
        this.dateRange = dateRange;
        this.albumPhotos = FXCollections.observableArrayList();
    }

    /**
     * Add a new photo to the album and sets the date range
     * @param newPhoto photo to be added to the album
     */
    public void addPhoto(Photo newPhoto){
        this.albumPhotos.add(newPhoto);
        this.numPhotos++;

        //set new date range
        Collections.sort(albumPhotos);
        this.dateRange = albumPhotos.get(0).getDateTime().toString();
        this.dateRange = dateRange + " - ";
        this.dateRange = dateRange + albumPhotos.get(albumPhotos.size()-1).getDateTime().toString();
    }
    
    /**
     * Removes a photo from the list and set the new date range
     * @param oldPhoto - photo to be removed
     */
    public void removePhoto(Photo oldPhoto){
        this.albumPhotos.remove(oldPhoto);
        this.numPhotos--;

        //set new date range
        if(this.numPhotos > 0) {
            Collections.sort(albumPhotos);
            this.dateRange = albumPhotos.get(0).getDateTime().toString();
            this.dateRange = dateRange + " - ";
            this.dateRange = dateRange + albumPhotos.get(albumPhotos.size() - 1).getDateTime().toString();
        } else{
            this.dateRange = "";
        }
    }

    /**
     * Returns an ArrayList of the user's photos
     * @return album's photos
     */
    public ObservableList<Photo> getPhotos() {
        return this.albumPhotos;
    }
    
    /**
     * sets the photos list for the album
     * @param photos - list of photos
     */
    public void setPhotos(ObservableList<Photo> photos) {
    	this.albumPhotos = photos;
    }
    
    /**
     * method to get the name of the album
     * @return - albumname
     */
    public String getAlbumName() {
        return albumName;
    }
    
    /**
     * method to set the name of the album
     * @param albumName - name of album
     */
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
    
    /**
     * To string method used for serializing album data
     */
    @Override
    public String toString() {
    	return "Album name: " + this.albumName+"\n"+"Num. of Photos: " + numPhotos + "\n" + "Date Range: " + dateRange;
    }
}
