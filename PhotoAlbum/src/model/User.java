package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacob Rizer
 * @author Terence Williams
 * 
 * This is our User class which holds
 * a list of albums.
 */
public class User implements Serializable{
	/**
	 * Username for user
	 */
	private String username;
	
	/**
	 * List of albums for user
	 */
	private List<Album> albums;
	
	static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 * 
	 * @param username
	 */
	public User(String username){
		this.username = username;
		albums = new ArrayList<Album> ();
	}

	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String toString(){
		return this.username;
	}
	
	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}
	
	
	
}
