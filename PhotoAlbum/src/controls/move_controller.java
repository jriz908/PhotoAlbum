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

public class move_controller {
	private static Stage stage;
	private static int next_scene;
	
	@FXML
	private ImageView imageview;
	
	@FXML
	private ListView<Album> listview;
	
	private Photo activePhoto;
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
	
	public void quit () {
		next_scene = 0;
		close_stage();
	}
	
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
