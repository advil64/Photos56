package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Defines a photos
 * @author Advith Chegu
 * @author Banty Patel
 */
public class Photo implements Comparable<Photo>{

    //photo variables
    private String caption;
    private Date dateTime;
    private ArrayList tags;
    private String photoPath;

    /**
     * Creates a new photo object
     * @param caption caption of the photo
     * @param photoPath filepath of the photo on user's computer
     * @param tags tags of the photo in tagename: tagvalue format
     */
    public Photo(String caption, ArrayList<String> tags, String photoPath){
        this.caption = caption;
        this.tags = tags;
        this.photoPath = photoPath;

        //get last modified date time
        File photoFile = new File(photoPath);
        long modified = photoFile.lastModified();
        this.dateTime = new Date(modified * 1000);
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void addTag(String tag){
        tags.add(tag);
    }

    public String getCaption() {
        return caption;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public ArrayList getTags() {
        return tags;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    @Override
    public int compareTo(Photo toCompare) {
        return this.dateTime.compareTo(toCompare.getDateTime());
    }
}
