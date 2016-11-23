package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Jacob Rizer
 * @author Terence Williams
 * 
 * This is our photo class which contains all the data
 * needed to represent one photo object, which will go into
 * albums.
 *
 */
public class Photo implements Serializable {
	
	static final long serialVersionUID = 1L;
	
	/**
	 * File for photo
	 */
	private File file;
	
	/**
	 * Path to photo object
	 */
	private String path;
	
	/**
	 * Photo name
	 */
	private String name;
	
	/**
	 * Caption that user can edit
	 */
	private String caption;
	
	/**
	 * List of tags associated with photo
	 */
	private List<Tag> tags;
	
	/**
	 * Date photo was taken.
	 */
	private Date date;
	
	/**
	 * Constructor
	 * 
	 * @param file - file containing image.
	 */
	public Photo(File file){
		this.file = file;
		this.name = file.getName();
		
		long milliseconds = file.lastModified();
		this.date = new Date(milliseconds);
		
		this.caption = "";
		this.tags = new ArrayList<Tag> ();
		
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public List<Tag> getTags() {
		return tags;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
