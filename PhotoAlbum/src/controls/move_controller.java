package controls;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Album;
import model.Photo;

/**
 * 
 * @author Jacob Rizer
 * @author Terence Williams
 *
 * This class controls the screen for moving/copying photos to other albums.
 */
public class move_controller {
	private static Stage stage;
	private static int next_scene;
	
	/**
	 * Imageview that holds the image of the photo being moved.
	 */
	@FXML
	private ImageView imageview;
	
	/**
	 * List of potential albums to move photo to.
	 * Does not include current album.
	 */
	@FXML
	private ListView<Album> listview;
	
	/**
	 * The photo that is currently selected. Retreived from album_controller.
	 */
	private Photo activePhoto;
	
	/**
	 * List of albums to choose from that is placed into listview.
	 */
	private ObservableList<Album> albums;
	
	@FXML
	private void initialize () {
		
		activePhoto = album_controller.getActivePhoto();
		
		imageview.setImage(new Image(activePhoto.getFile().toURI().toString()));
		
		albums = FXCollections.observableArrayList(Controller.getActiveUser().getAlbums());
		
		albums.remove(user_controller.getActiveAlbum());
		
		listview.setItems(albums);
		
		
		
		
		stage = new Stage ();
	}
	
	public void start () {
		Stage primary = Controller.getStage();
		stage.initOwner(primary);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Move/Copy Screen");
		stage.setScene( Controller.getScene() );
		show_stage ();
	}
	
	/**
	 * Called when safe quit is pressed. Stores all data to drive.
	 */
	public void quit () {
		next_scene = 0;
		close_stage();
	}
	
	/**
	 * Called when move button is pressed. Moves photo to selected album unless
	 * the photo would be a duplicate in the other album. Deletes photo from this album.
	 */
	public void move () {
		
		if(!copyAlbum()){
			return;
		}
		
		//remove from current album
		user_controller.getActiveAlbum().getPhotos().remove(activePhoto);
		
		String path = "data/" + Controller.getActiveUser().getUsername() + File.separator + user_controller.getActiveAlbum().getName() + File.separator + activePhoto.getName();
		
		try {
			Files.delete(Paths.get(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(stage);
		alert.setTitle("Done");
		alert.setHeaderText("Photo moved");
		alert.setContentText("The photo has been moved to the selected album.");
		alert.showAndWait();
		
		close();
		
	}
	
	/**
	 * Copies photo from this album to selected album unless it is a duplicate
	 * in the selected album. Does not delete photo from current album.
	 */
	public void copy(){
		
		if(!copyAlbum()){
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(stage);
		alert.setTitle("Done");
		alert.setHeaderText("Photo copied");
		alert.setContentText("The photo has been copied to the selected album.");
		alert.showAndWait();
		
		close();
		
		
		
		
	}
	
	/**
	 * Used by both copy() and move() methods to copy the photo
	 * to the new album. The move() method then deletes the photo from its original album.
	 * 
	 * @return true if copy is successful
	 */
	private boolean copyAlbum(){
		
		Album a = listview.getSelectionModel().getSelectedItem();
		
		if(a == null){
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(stage);
			alert.setTitle("Error");
			alert.setHeaderText("No album selected");
			alert.setContentText("Please select an album to move/copy this photo to");
			alert.showAndWait();
			return false;
		}
		
		//check for duplicate
		if(a.getPhotos().contains(activePhoto)){
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(stage);
			alert.setTitle("Error");
			alert.setHeaderText("Duplicate found");
			alert.setContentText("Can not copy photo to an album that already has it.");
			alert.showAndWait();
			return false;
		}
		
		//add photo to album and directory
		a.getPhotos().add(activePhoto);
		
		String pathString = "data/" + Controller.getActiveUser().getUsername() + File.separator + a.getName() + File.separator + activePhoto.getName();
		
		File newFile = new File(pathString);
		
		try {
			Files.copy(activePhoto.getFile().toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	/**
	 * Called to go back to album content screen
	 */
	public void close () {
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
