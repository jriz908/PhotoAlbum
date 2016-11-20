package controls;

import model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class admin_controller {
	private static Stage stage;
	private static int next_scene;
	
	private ObservableList<User> usersList;
	
	private Data temp;
	
	@FXML
	private TextField new_username_text;
	@FXML
	private ListView<User> listview = new ListView<>();
	
	
	
	@FXML
	private void initialize () {
		
		temp = Controller.getData();
		
		usersList = temp.getUsers();
		
		listview.setItems(usersList);
		
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
			
			
			temp.getUsers().add(newUser);
			
			
			//for debugging
			//temp.printUsers();
			
			
			new_username_text.setText("");
			
		}
		
	}
	
	public void delete () {
		
		User userToDelete = listview.getSelectionModel().getSelectedItem();
		
		if(userToDelete == null)
			return;
		
		temp.getUsers().remove(userToDelete);
		
		//temp.printUsers();
		
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
}
