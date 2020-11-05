package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Defines an album
 * @author Advith Chegu
 * @author Banty Patel
 */
public class Album {

    //album variables
    private String albumName;
    private int numPhotos;
    private String dateRange;
    private ArrayList<Photo> albumPhotos;

    /**
     * Creates a new Album object
     * @param albumName name of the new album
     */
    public Album(String albumName){
        this.albumName = albumName;
        this.albumPhotos = new ArrayList<Photo>();
        this.numPhotos = 0;
    }

    /**
     * Add a new photo to the album and sets the date range
     * @param newPhoto photo to be added to the album
     */
    private void addPhoto(Photo newPhoto){
        albumPhotos.add(newPhoto);
        numPhotos++;

        //set new date range
        Collections.sort(albumPhotos);
        dateRange = albumPhotos.get(0).getDateTime().toString();
        dateRange = dateRange + " - ";
        dateRange = dateRange + albumPhotos.get(albumPhotos.size()-1).getDateTime().toString();
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
}
