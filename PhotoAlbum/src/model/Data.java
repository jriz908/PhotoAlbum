package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Data{
	
	private ObservableList<User> users;
	
	public Data(){
		users = FXCollections.observableArrayList();
	}

	public ObservableList<User> getUsers() {
		return users;
	}

	public void setUsers(ObservableList<User> users) {
		this.users = users;
	}
	
	public void printUsers(){
		for(User u : users){
			System.out.println(u.getUsername());
		}
	}
	
}
