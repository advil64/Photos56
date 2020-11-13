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

    //photo variables
    private String caption;
    private Date dateTime;
    private ArrayList<String> tags;
    private String photoPath;
    private Image image;

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
        this.image = new Image("file:" + photoPath);

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
    public void removeTag(String tag) {
    	for(String s: tags) {
    		if(s.equals(tag)) {
    			tags.remove(s);
    			break;
    		}
    	}
    }

    public String getCaption() {
        return caption;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getPhotoPath() {
        return photoPath;
    }
    
    public Image getPhoto(){
        return this.image;
    }

    public String toString2(){
        return "Caption: " + this.caption;
    }
    
    @Override
    public String toString() {
    	return this.caption + "|" + this.tags + "|" + this.photoPath;
    }

    @Override
    public int hashCode() {
        return this.caption.hashCode();
    }

    @Override
    public int compareTo(Photo toCompare) {
        return this.dateTime.compareTo(toCompare.getDateTime());
    }
}
