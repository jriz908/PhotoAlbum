package controls;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Album;
import model.Data;
import model.User;

public class user_controller {
	private static Stage stage;
	private static int next_scene = 0;
	
	private ObservableList<Album> albumsList;
	
	private Data temp;
	
	private User activeUser;
	
	@FXML
	private TextField new_album_text;
	@FXML
	private ListView<Album> listview = new ListView<>();
	
	@FXML
	private void initialize () {
		
		activeUser = Controller.getActiveUser();
		
		albumsList = activeUser.getAlbums();
		
		listview.setItems(albumsList);
		
		stage = new Stage ();
	}
	
	public void start () {
		Stage primary = Controller.getStage();
		stage.initOwner(primary);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("User screen");
		stage.setScene( Controller.getScene() );
		show_stage();
	}
	
	public void log_out () {
		next_scene = 1;
		close_stage();
	}
	
	public void quit () {
		next_scene = 0;
		close_stage();
	}
	
	public void show_stage () {
		stage.showAndWait();	
	}
	
	public void open () {
		next_scene = 4;
		close_stage();
	}
	
	public void search () {
		next_scene = 5;
		close_stage ();
	}
	
	public void rename () {
		
		/**
		 * complete code later
		 */
	
	}
	
	public void delete () {
		
		/**
		 * complete code later
		 */
	}
	
	public void create () {
		
		String text = new_album_text.getText().trim();
		
		if(text != null && text.length() > 0){
			Album newAlbum = new Album(text);
			
			activeUser.getAlbums().add(newAlbum);
			
			new_album_text.setText("");
			
		}
		
	}
	
	public void close_stage () {
		stage.close();
	}
	
	public static int get_next () {
		return next_scene;
	}
	
}
