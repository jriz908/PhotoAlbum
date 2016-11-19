package controls;

import javafx.fxml.FXML;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class user_controller {
	private static Stage stage;
	private static int next_scene = 0;
	
	@FXML
	private void initialize () {
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
		
		/**
		 * complete code later
		 */
		
	}
	
	public void close_stage () {
		stage.close();
	}
	
	public static int get_next () {
		return next_scene;
	}
	
}
