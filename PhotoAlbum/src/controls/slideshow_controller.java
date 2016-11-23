package controls;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Photo;

/**
 * 
 * @author Jacob Rizer
 * @author Terence Williams
 *
 */

/**
 * 
 * This class controls slide show screen
 *
 */
public class slideshow_controller {
	private static Stage stage;
	private static int next_scene;
	
	/**
	 * used to traverses the photos in the album
	 */
	private static int i;
	
	/**
	 * list of the photos in the selected album
	 */
	private List<Photo> photos;
	
	@FXML 
	private StackPane stackpane;
	
	@FXML
	private ImageView imageview;
	
	@FXML
	private Label label;
	
	@FXML
	private void initialize () {
		stage = new Stage ();
		photos = user_controller.getActiveAlbum().getPhotos();
		imageview.setSmooth(true);
		stackpane.setStyle("-fx-background-color: #FFFFFF;");
		i = 0;
		show_photo();
	}
	
	/**
	 * initializes the slide show stage
	 */
	public void start () {
		Stage primary = Controller.getStage();
		stage.initOwner(primary);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("SlideShow");
		stage.setScene( Controller.getScene() );
		show_stage ();
	}
	
	/**
	 * signals for the application to be closed
	 */
	public void quit () {
		next_scene = 0;
		close_stage();
	}
	
	/**
	 * goes to the previous picture in the album
	 */
	public void prev () {
		if (photos.isEmpty())
			return;
		
		if (i == 0) 
			i = photos.size()-1;
		else
			i--;
		
		show_photo();
	}
	
	/**
	 * goes to the next picture in the album
	 */
	public void next () {
		if (photos.isEmpty())
			return;
		
		if (i == photos.size()-1) 
			i = 0;
		else
			i++;
		
		show_photo ();
	}
	
	/**
	 * shows the current photo
	 */
	public void show_photo () {
		
		Photo p = photos.get(i);
		
		
		imageview.setImage(new Image(p.getFile().toURI().toString()));
		imageview.autosize();
		
		String caption = p.getCaption();
		
		if(caption.length() > 0)
			label.setText(p.getCaption());
		else
			label.setText("(No Caption)");
		
	}
	
	/**
	 * signals to go to the album stage
	 */
	public void album () {
		next_scene = 4;
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
