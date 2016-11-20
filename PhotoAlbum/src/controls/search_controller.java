package controls;

import javafx.fxml.FXML;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class search_controller {
	private static Stage stage;
	private static int next_scene;
	
	@FXML
	private void initialize () {
		stage = new Stage ();
	}
	
	public void start () {
		Stage primary = Controller.getStage();
		stage.initOwner(primary);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Search Screen");
		stage.setScene( Controller.getScene() );
		show_stage ();
	}
	
	public void quit () {
		next_scene = 0;
		close_stage();
	}
	
	public void search () {
		
		/**
		 * insert code here
		 */
	}
	
	public void create () {
		
		/**
		 * insert code here
		 */
	}
	
	public void end_search () {
		next_scene = 3;
		stage.close();
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