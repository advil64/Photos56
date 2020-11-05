package model;

import java.util.ArrayList;

/**
 * Defines a photos app user
 * @author Advith Chegu
 * @author Banty Patel
 */
public class User {

    //user variables
    private String username;
    private ArrayList albums;
    private ArrayList photos;

    /**
     * Creates a new user object
     * @param username unique name given to each user to store photos
     */
    public User(String username){
        this.username = username;
        albums = new ArrayList<Album>();
        photos = new ArrayList<Photo>();
    }

    /**
     * Returns corresponding username
     * @return username of the user
     */
    private String getUsername() {
        return username;
    }

    /**
     * Returns an ArrayList of the user's albums
     * @return user's albums
     */
    private ArrayList getAlbums() {
        return albums;
    }

    /**
     * Returns an ArrayList of the user's photos
     * @return user's photos
     */
    private ArrayList getPhotos() {
        return photos;
    }

    /**
     * Add a new album to the arraylist
     * @param newAlbum the new album to be added
     */
    private void addAlbum(Album newAlbum){
        albums.add(newAlbum);
    }

    /**
     * Add a new photo to the arraylist
     * @param newPhoto the new photo to be added
     */
    private void addPhoto(Photo newPhoto){
        photos.add(newPhoto);
    }
}
