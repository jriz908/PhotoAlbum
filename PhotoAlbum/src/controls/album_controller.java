package controls;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Album;
import model.Photo;

public class album_controller {
	private static Stage stage;
	private static int next_scene;
	
	private static Photo activePhoto;
	
	private Album activeAlbum;
	
	@FXML
	private TilePane tilepane;
	
	@FXML
	private void initialize () {
		
		activeAlbum = user_controller.getActiveAlbum();
		activeAlbum.getPhotos();
		
		if(activeAlbum.getPhotos() == null){
			System.out.println("asdasda");
		}
		
		
		tilepane.setStyle("-fx-background-color: #FFFFFF;");
		
		showImages();
		
		System.out.println(tilepane.getChildren().size());
		
		stage = new Stage ();
	}
	
	public void showImages(){
		for(Photo p : activeAlbum.getPhotos()){
			ImageView iv = new ImageView();
			//iv.setStyle("-fx-border-width: 5;");
			iv.setImage(new Image(p.getFile().toURI().toString()));
			iv.setFitWidth(70);
			iv.setPreserveRatio(true);
			iv.setSmooth(true);
			
			tilepane.getChildren().add(iv);
		}
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
		
		FileChooser fc = new FileChooser();
		fc.setTitle("Select a photo");
		
		//set filter so that you can only see image files in the file chooser
		fc.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		
		File file = fc.showOpenDialog(stage);
		
		Photo newPhoto = new Photo(file);
		
		String path = "data/" + Controller.getActiveUser().getUsername() + File.separator + activeAlbum.getName() + File.separator + newPhoto.getName();
		
		newPhoto.setPath(path);
		
		//System.out.println(activeAlbum.toString());
		
		activeAlbum.getPhotos().add(newPhoto);
		
		
		File dir = new File(path);
		
		try {
			Files.copy(file.toPath(), dir.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ImageView iv = new ImageView();
		//iv.setStyle("-fx-border-width: 5;");
		iv.setImage(new Image(file.toURI().toString()));
		iv.setFitWidth(70);
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		iv.setOnMouseClicked(e -> {System.out.println("Clicked");});
		
		tilepane.getChildren().add(iv);
		
		
		
		
		
		
		System.out.println(tilepane.getChildren().size());
		
		
		//System.out.println(activeAlbum.getPhotos().size());
		
		
	
		
		
		
		
		
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

	public static Photo getActivePhoto() {
		return activePhoto;
	}
	
	public static void setActivePhoto(ImageView iv) {
		
		System.out.println("Clicked me");
		
	}

	
}
