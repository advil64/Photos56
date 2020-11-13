package model;

import java.io.Serializable;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Defines an album
 * @author Advith Chegu
 * @author Banty Patel
 */
public class Album implements Serializable{

    //album variables
    public String albumName;
    public int numPhotos;
    public String dateRange;
    public ObservableList<Photo> albumPhotos;

    /**
     * Creates a new Album object
     * @param albumName name of the new album
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
    
    public void setPhotos(ObservableList<Photo> photos) {
    	this.albumPhotos = photos;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    @Override
    public String toString() {
    	return "Album name: " + this.albumName+"\n"+"Num. of Photos: " + numPhotos + "\n" + "Date Range: " + dateRange;
    }
}
