package model;

import java.util.Date;
import java.util.Map;

import javafx.scene.image.Image;

public class Photo {
	
	private Image image;
	private String path;
	private String caption;
	private Map<String, String> tags;
	private Date date;
	
	public Photo(Image image, String path, Date date){
		this.image = image;
		this.path = path;
		this.setDate(date);
	}
	
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
