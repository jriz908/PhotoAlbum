package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacob Rizer
 * @author Terence Williams
 *
 * This is our album class which holds
 * a list of photos.
 */
public class Album implements Serializable{
	
	/**
	 * Name of album
	 */
	private String name;
	
	/**
	 * List of photos
	 */
	private List<Photo> photos;
	
	/**
	 * Constructor
	 * 
	 * @param name - Album name
	 */
	public Album(String name){
		this.name = name;
		photos = new ArrayList<Photo>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public String toString(){
		return this.name;
	}
}
