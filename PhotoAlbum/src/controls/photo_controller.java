package controls;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Photo;

public class photo_controller {
	private static Stage stage;
	private static int next_scene;
	
	private Photo activePhoto;
	
	@FXML 
	private ImageView imageview;
	
	@FXML
	private TextField photo_caption;
	
	@FXML
	private TextField photo_tags;
	
	@FXML
	private TextField date;
	
	@FXML
	private void initialize () {
		
		activePhoto = album_controller.getActivePhoto();
		
		imageview.setImage(new Image(activePhoto.getFile().toURI().toString()));
		
		
		photo_caption.setText(activePhoto.getCaption());
		
		if(activePhoto.getDate() != null)
			date.setText(activePhoto.getDate().toString());
		
		//date.setEditable(false);
		
		
		
		stage = new Stage ();
	}
	
	public void start () {
		Stage primary = Controller.getStage();
		stage.initOwner(primary);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Photo Screen");
		stage.setScene( Controller.getScene() );
		show_stage ();
	}
	
	public void quit () {
		next_scene = 0;
		close_stage();
	}
	
	public void edit () {
		
		activePhoto.setCaption(photo_caption.getText());
		
	}
	
	public void album () {
		next_scene = 4;
		close_stage();
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
