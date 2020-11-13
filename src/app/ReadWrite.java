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

public class ReadWrite{

    public static void writePhotos(Album myAlbum, User curr) throws IOException{
        FileOutputStream fos = new FileOutputStream("../data/" + curr.getUsername() + "/" + myAlbum.getAlbumName() + "/photo.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        for(Photo x : myAlbum.getPhotos()) {
            oos.writeObject(x.toString());
        }
    }

    public static void writeAlbums(ObservableList<Album> myAlbums, User curr) throws IOException{
        FileOutputStream fos = new FileOutputStream("../data/" + curr.getUsername() + "/" + "albums.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        for(Album x : myAlbums) {
            oos.writeObject(x.toString());
        }
    }

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
                String[] tagTemp2 = tagTemp.split(",");
                for(int i=0; i<tagTemp2.length; i++) {
                	tagTemp2[i] = tagTemp2[i].trim();
                }
                ArrayList<String> tags = new ArrayList<>(Arrays.asList(tagTemp2));
                //getting the location
                String location = temp1.substring(delimeter2+1);
                gapp.add(new Photo(caption,tags,location));
            } catch (EOFException e) {
                return gapp;
            }
        }
    }
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
            	String[] arr = list.split(",");
            	for(int i=0; i<arr.length; i++) {
                	arr[i] = arr[i].trim();
                	tagTypes.add(arr[i]);
            	}
            } catch (EOFException e) {
                return tagTypes;
            }
        return tagTypes;
        }
    }
    public static void writeCombo(ObservableList<String> combo, User curr) throws IOException{
        FileOutputStream fos = new FileOutputStream("../data/" + curr.getUsername() + "/combo.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(combo.toString());
    }
}
