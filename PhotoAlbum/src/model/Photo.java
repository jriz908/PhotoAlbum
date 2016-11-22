package model;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class Photo implements Serializable {
	
	private File file;
	private String path;
	private String name;
	private String caption;
	private Map<String, String> tags;
	private Date date;
	
	public Photo(File file){
		this.file = file;
		this.name = file.getName();
		
		long milliseconds = file.lastModified();
		this.date = new Date(milliseconds);
		
		this.caption = "";
		this.tags = new HashMap<String, String>();
		
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
	public Map<String, String> getTags() {
		return tags;
	}
	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
