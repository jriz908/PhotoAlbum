package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User {
	
	String username;
	ObservableList<Album> albums;

	public User(String username){
		this.username = username;
		albums = FXCollections.observableArrayList();
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
	
	public ObservableList<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(ObservableList<Album> albums) {
		this.albums = albums;
	}
	
	
	
}
