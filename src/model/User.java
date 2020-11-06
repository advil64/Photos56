package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Defines a photos app user
 * @author Advith Chegu
 * @author Banty Patel
 */
public class User {

    //user variables
    public String username;
    public ObservableList<Album> albums;
    public ObservableList<Photo> photos;

    /**
     * Creates a new user object
     * @param username unique name given to each user to store photos
     */
    public User(String username){
        this.username = username;
        albums = FXCollections.observableArrayList();
        photos = FXCollections.observableArrayList();
    }

    /**
     * Returns corresponding username
     * @return username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns an ArrayList of the user's albums
     * @return user's albums
     */
    public ObservableList<Album> getAlbums() {
        return albums;
    }

    /**
     * Returns an ArrayList of the user's photos
     * @return user's photos
     */
    public ObservableList<Photo> getPhotos() {
        return photos;
    }
    
    public void setAlbums(ObservableList<Album> albums) {
    	this.albums = albums;
    }

    /**
     * Add a new album to the arraylist
     * @param newAlbum the new album to be added
     */
    public void addAlbum(Album newAlbum){
        albums.add(newAlbum);
    }

    /**
     * Add a new photo to the arraylist
     * @param newPhoto the new photo to be added
     */
    public void addPhoto(Photo newPhoto){
        photos.add(newPhoto);
    }

    @Override
    public String toString(){
        return this.username;
    }

    @Override
    public boolean equals(Object o){
        //check what the object being compared is
        if(o instanceof String){
            return this.username.equals((String)o);
        } else if(o instanceof User){
            return this.username.equals(o.toString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }
}
