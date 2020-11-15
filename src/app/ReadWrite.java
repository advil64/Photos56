package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import model.Album;
import model.Photo;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class used to read and write to a file
 */
public class ReadWrite extends Photos{
	
	/**
	 * writes the photo data to a file
	 * @param myAlbum - album opened
	 * @param curr - user logged in
	 * @throws IOException
	 */
    public static void writePhotos(Album myAlbum, User curr) throws IOException{
        FileOutputStream fos = new FileOutputStream("../data/" + curr.getUsername() + "/" + myAlbum.getAlbumName() + "/photo.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        for(Photo x : myAlbum.getPhotos()) {
            oos.writeObject(x.toString());
        }
    }
    
    /**
     * writes the information about all albums for a user to a file
     * @param myAlbums - list of albums
     * @param curr - current user
     * @throws IOException
     */
    public static void writeAlbums(ObservableList<Album> myAlbums, User curr) throws IOException{
        FileOutputStream fos = new FileOutputStream("../data/" + curr.getUsername() + "/" + "albums.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        for(Album x : myAlbums) {
            oos.writeObject(x.toString());
        }
    }
    
    /**
     * reads the album file
     * @param curr - current user
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void readAlbums(User curr) throws IOException, ClassNotFoundException{

        //create the file if it doesn't exist
        File temp = new File("../data/" + curr.getUsername() + "/" + "albums.dat");
        temp.createNewFile();
        ObservableList<Album> newAlbums = FXCollections.observableArrayList();
        ObjectInputStream ois;

        try{
            ois = new ObjectInputStream(new FileInputStream("../data/" + curr.getUsername() + "/" + "albums.dat"));
        } catch(EOFException e) {
            return;
        }

        //read the .dat file and populate the observable list (list of albums)
        while(true) {
            try {
                String temp1 = (String)ois.readObject();
                //find substrings of album, num, and date range
                int del1 = temp1.indexOf("\n");
                //album name
                String album = temp1.substring(12,del1);
                int del2 = temp1.lastIndexOf("\n");
                //number of photos
                int numPhotos = Integer.parseInt(temp1.substring(del1+17,del2));
                //date range
                String dateRange = temp1.substring(del2+13);
                newAlbums.add(new Album(album,numPhotos,dateRange));
            } catch (EOFException e) {
                curr.setAlbums(newAlbums);
                return;
            }
        }
    }

    /**
     * reads the photos file
     * @param myAlbum - opened album
     * @param curr - current user
     * @return - list of photos
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ObservableList<Photo> readPhotos(Album myAlbum, User curr) throws IOException, ClassNotFoundException {
    	//create the file if it doesn't exist
        File temp = new File("../data/" + curr.getUsername() + "/" + myAlbum.getAlbumName() + "/photo.dat");
        temp.createNewFile();

        ObservableList<Photo> gapp = FXCollections.observableArrayList();
        ObjectInputStream ois;
        try{
            ois = new ObjectInputStream(new FileInputStream("../data/" + curr.getUsername() + "/" + myAlbum.getAlbumName() + "/photo.dat"));
        } catch(EOFException e) {
            return gapp;
        }

        //read the .dat file and populate the observable list (list of albums)
        while(true) {
            try {
                String temp1 = (String)ois.readObject();
                //find substrings of caption, tags, datetime, photoPath
                int delimeter1 = temp1.indexOf("|");
                //getting the captions
                String caption = temp1.substring(0, delimeter1);
                int delimeter2 = temp1.lastIndexOf("|");
                //getting the tags
                String tagTemp = temp1.substring(delimeter1+2, delimeter2-1);
                String[] arr = tagTemp.split(" ");
                ArrayList<String> tags = new ArrayList<>();
                String tag = "";
                int i = 1;
        		for(String s: arr) {
        			//to avoid the null pointer exception in next if statement
        			if(s.equals("")) {
        				continue;
        			}
        			//if it's in combo append s to tag
        			if(combo.contains(s.substring(0, s.length()-1))) {
        				//check to see if tag is empty (new starting)
        				if(tag.equals("")) {
        					tag = tag + s + " ";
        				}
        				//not empty add tag to list and start tag over
        				else {
        					//check for commas at the end
        					if(tag.charAt(s.length()-1) == ',') {
        						tags.add(tag.substring(0,s.length()-1).trim());
        						tag = "";
        						tag = tag + s + " ";
        					}
        					else {
        						tags.add(tag.trim());
        						tag = "";
        						tag = tag + s + " ";
        					}
        				}
        			}
        			//not a tag type
        			else {
        				//if we reach the end
        				if(i == arr.length) {
        					tag = tag + s + " ";
        					tags.add(tag.trim());
        				}
        				//append s to tag because type is already there and continue
        				else {
        					if(s.charAt(s.length()-1) == ',') {
        						tag = tag + s.substring(0, s.length()-1);
        					}
        					else {
        						tag = tag + s + " ";
        					}
        				}
        			}
        			//increment i to keep count of number of words to know when we are ending
        			i++;
        		}
                //getting the location
                String location = temp1.substring(delimeter2+1);
                gapp.add(new Photo(caption,tags,location));
            } catch (EOFException e) {
                return gapp;
            }
        }
    }
    
    /**
     * read the combo box's file
     * @param curr - current user
     * @return - list of items in the combo box
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ObservableList<String> readCombo(User curr) throws IOException, ClassNotFoundException{
    	//create the file if it doesn't exist
        File temp = new File("../data/" + curr.getUsername() + "/combo.dat");
        if(temp.exists() == false) {
        	temp.createNewFile();
        }
        ObservableList<String> tagTypes = FXCollections.observableArrayList();
        ObjectInputStream ois;
        try{
            ois = new ObjectInputStream(new FileInputStream("../data/" + curr.getUsername() + "/combo.dat"));
        } catch(EOFException e) {
            return tagTypes;
        }
        
      //read the .dat file and populate the observable list (list of albums)
        while(true) {
            try {
            	String temp1 = (String)ois.readObject();
            	//remove the brackets
            	String list = temp1.substring(1,temp1.length()-1);
            	String[] arr = list.split(" ");
            	for(int i=0; i<arr.length; i++) {
                	arr[i] = arr[i].trim();
            		if(arr[i].charAt(arr[i].length()-1) == ',') {
            			arr[i] = arr[i].substring(0,arr[i].length()-1);
            		}
                	tagTypes.add(arr[i]);
            	}
            } catch (EOFException e) {
                return tagTypes;
            }
        return tagTypes;
        }
    }
    /**
     * writes the list of items in the combo box
     * @param combo - list of items in the combo box
     * @param curr - current user
     * @throws IOException
     */
    public static void writeCombo(ObservableList<String> combo, User curr) throws IOException{
        FileOutputStream fos = new FileOutputStream("../data/" + curr.getUsername() + "/combo.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(combo.toString());
    }
}
