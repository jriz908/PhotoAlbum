package controls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Data;
import model.User;

/**
 * 
 * @author Jacob Rizer
 * @author Terence Williams
 *
 * This is our admin controller
 */
public class admin_controller {
	private static Stage stage;
	private static int next_scene;
	
	private ObservableList<User> usersList;
	private static List<User> users_added;
	private static List<User> users_deleted;
	
	private Data temp;
	
	@FXML
	private TextField new_username_text;
	@FXML
	private ListView<User> listview = new ListView<>();
	
	
	
	@FXML
	private void initialize () {
		
		temp = Controller.getData();
		usersList = temp.getUsers();
		sort();
		 
		listview.setItems(usersList);
		
		users_added = new ArrayList<User>();
		users_deleted = new ArrayList<User>();
		
		stage = new Stage ();
	}
	
	public void start () {
		Stage primary = Controller.getStage();
		stage.initOwner(primary);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Admin Screen");
		stage.setScene( Controller.getScene() );
		show_stage ();
	}
	
	public void quit () {
		next_scene = 0;
		close_stage();
	}
	
	public void log_out () {
		next_scene = 1;
		close_stage();
	}
	
	public void create () {
		
		String text = new_username_text.getText().trim();
		
		if(text != null && text.length() > 0){
			User newUser = new User(text);
			
			//check for duplicate
			for(User u : temp.getUsers()){
				   if(u.getUsername().equals(text)){
					   Alert alert = new Alert(AlertType.WARNING);
					   alert.initOwner(stage);
					   alert.setTitle("ERROR");
					   alert.setHeaderText("Duplicate found");
					   alert.setContentText("Username already exists. Duplicates not allowed.");
					   
					   new_username_text.setText("");

					   alert.showAndWait();
					   return;
				   }
			   }
			
			
			temp.getUsers().add(newUser);
			users_added.add(newUser);
			new_username_text.setText("");
			sort();
			
		}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(stage);
			alert.setTitle("ERROR");
			alert.setHeaderText("No username entered");
			alert.setContentText("Please enter a username.");

			new_username_text.setText("");

			alert.showAndWait();
			return;
		}
		
	}
	
	public void delete () {
		
		User userToDelete = listview.getSelectionModel().getSelectedItem();
		
		if(userToDelete == null){
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(stage);
			alert.setTitle("ERROR");
			alert.setHeaderText("No user selected.");
			alert.setContentText("Please select a user to delete.");

			new_username_text.setText("");

			alert.showAndWait();
			return;
		}
			
		
		temp.getUsers().remove(userToDelete);
		users_deleted.add(userToDelete);
		sort();
		
	}
	
	public void sort () {
		
		Collections.sort(usersList, new Comparator<User>(){
			public int compare(User a, User b) {
				return a.getUsername().compareTo(b.getUsername());
			}
		});
	}

	
	public void show_stage () {
		stage.showAndWait();
	}
	
	public void close_stage () {
		stage.close();
	}
	
	public static int get_next () {
		return next_scene;
	}
	
	public static List<User> get_UsersAdded () {
		return users_added;
	}
	
	public static List<User> get_UsersDeleted () {
		return users_deleted;
	}
	
}
