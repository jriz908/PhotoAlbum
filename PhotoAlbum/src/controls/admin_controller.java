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
 * This is our admin controller which controls
 * the admin screen
 */
public class admin_controller {
	
	private static Stage stage;
	private static int next_scene;
	
	/**
	 * List of users. Can be changed by admin.
	 */
	private ObservableList<User> usersList;
	
	/**
	 * A list of users added during this session. For serialization.
	 */
	private static List<User> users_added;
	
	/**
	 * A list of users deleted during this session. For serialization.
	 */
	private static List<User> users_deleted;
	
	/**
	 * stores static data retrieved from main controller in a temp variable.
	 */
	private Data temp;
	
	/**
	 * The textfield where a username is entered.
	 */
	@FXML
	private TextField new_username_text;
	
	/**
	 * List view containing list of users.
	 */
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
	
	/**
	 * Called when admin screen starts.
	 */
	public void start () {
		Stage primary = Controller.getStage();
		stage.initOwner(primary);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Admin Screen");
		stage.setScene( Controller.getScene() );
		show_stage ();
	}
	
	/**
	 * Called when safe quit is pressed. Closes application and stores all data.
	 */
	public void quit () {
		next_scene = 0;
		close_stage();
	}
	
	/**
	 * Called when log out is pressed. Goes back to login screen.
	 */
	public void log_out () {
		next_scene = 1;
		close_stage();
	}
	
	/**
	 * Called when create user is pressed. Creates a new
	 * user object which is added to the list of users. No duplicate 
	 * names are allowed.
	 */
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
	
	/**
	 * Called when delete user button is pressed. If a user is selected
	 * in the list it deletes the user object and removes it from the list.
	 */
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
	
	/**
	 * Keeps list of users sorted alphabetically
	 */
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
