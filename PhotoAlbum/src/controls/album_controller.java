package controls;

import javafx.fxml.FXML;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class album_controller {
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
		stage.setTitle("Album Content");
		stage.setScene( Controller.getScene() );
		show_stage ();
	}
	
	public void log_out () {
		next_scene = 1;
		close_stage();
	}
	
	public void user_screen () {
		next_scene = 3;
		close_stage();
	}
	
	public void quit () {
		next_scene = 0;
		close_stage();
	}
	
	public void view () {
		next_scene = 6;
		close_stage();
	}
	
	public void move_copy () {
		next_scene = 7;
		close_stage();
	}
	
	public void slideshow () {
		next_scene = 8;
		close_stage ();
	}
	
	public void add () {
		
		/**
		 * complete code later
		 */
	}
	
	public void remove () {
		
		/**
		 * complete code later
		 */
		
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
