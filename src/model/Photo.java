package model;

import javafx.scene.image.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Defines a photos
 * @author Advith Chegu
 * @author Banty Patel
 */
public class Photo implements Comparable<Photo>{

    /**
     * caption of the photo
     */
    private String caption;
    /**
     * date of the photo
     */
    private Date dateTime;
    /**
     * list of tags for the photo
     */
    private ArrayList<String> tags;
    /**
     * path of the photo
     */
    private String photoPath;
    /**
     * image of the photo
     */
    private Image image;

    /**
     * Creates a new photo object
     * @param caption caption of the photo
     * @param photoPath filepath of the photo on user's computer
     * @param tags tags of the photo in tagename: tagvalue format
     * @return Photo object
     */
    public Photo(String caption, ArrayList<String> tags, String photoPath){
        this.caption = caption;
        this.tags = tags;
        this.photoPath = photoPath;
        this.image = new Image("file:" + photoPath);

        //get last modified date time
        File photoFile = new File(photoPath);
        long modified = photoFile.lastModified();
        this.dateTime = new Date(modified * 1000);
    }
    
    /**
     * method sets the caption of the photo
     * @param caption - caption of photo
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }
    
    /**
     * method to add a tag to a photo
     * @param tag - tag to be added
     */
    public void addTag(String tag){
        tags.add(tag);
    }
    /**
     * method to remove a tag from a photo
     * @param tag - tag to be removed
     */
    public void removeTag(String tag) {
    	for(String s: tags) {
    		if(s.equals(tag)) {
    			tags.remove(s);
    			break;
    		}
    	}
    }
    
    /**
     * method to get the caption of a photo
     * @return - the caption of the photo
     */
    public String getCaption() {
        return caption;
    }
    
    /**
     * method to get the date of a photo
     * @return - date
     */
    public Date getDateTime() {
        return dateTime;
    }
    
    /**
     * method to get the list of tags for a photo
     * @return - arraylist of tags
     */
    public ArrayList<String> getTags() {
        return tags;
    }
    
    /**
     * method to get the photo path of a photo
     * @return - path of photo
     */
    public String getPhotoPath() {
        return photoPath;
    }
    
    /**
     * method to get the image object of a photo
     * @return - image object of photo
     */
    public Image getPhoto(){
        return this.image;
    }
    
    /**
     * method used to display the caption of a photo
     * @return - caption string
     */
    public String toString2(){
        return "Caption: " + this.caption;
    }
    
    /**
     * method used to serialize the photo data
     * @return - string to be serialized
     */
    @Override
    public String toString() {
    	return this.caption + "|" + this.tags + "|" + this.photoPath;
    }

    /**
     * method to produce a hashcode of a caption
     * @return - hashcode
     */
    @Override
    public int hashCode() {
        return this.caption.hashCode();
    }
    
    /**
     * method to compare the dates of a photo
     * @return - int used for comparison
     */
    @Override
    public int compareTo(Photo toCompare) {
        return this.dateTime.compareTo(toCompare.getDateTime());
    }
}
