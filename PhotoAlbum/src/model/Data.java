package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * @author Jacob Rizer
 * @author Terence Williams
 *
 * This is our data class. It holds our list of users.
 */
public class Data{
	
	/**
	 * List of users
	 */
	private ObservableList<User> users;
	
	/**
	 * Constructor
	 */
	public Data(){
		users = FXCollections.observableArrayList();
	}

	public ObservableList<User> getUsers() {
		return users;
	}

	public void setUsers(ObservableList<User> users) {
		this.users = users;
	}
	
	
}
