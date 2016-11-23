package controls;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.User;

/**
 * 
 * @author Jacob Rizer
 * @author Terence Williams
 *
 */

/**
 * 
 * This class controls the user screen
 *
 */
public class user_controller {
	
	private static Stage stage;
	private static int next_scene = 0;
	
	/**
	 * This is the list of albums that the user has
	 */
	private ObservableList<Album> albumsList;
	
	/**
	 * This is the current selected album
	 */
	private static Album activeAlbum;
	
	/**
	 * List of the active album details
	 */
	private ObservableList<String> details;
	
	/**
	 * current user who is logged in
	 */
	private User activeUser;
	
	@FXML
	private TextField new_album_text;
	
	@FXML
	private ListView<String> album_details = new ListView<>();
	
	@FXML
	private ListView<Album> listview = new ListView<>();
	
	@FXML
	private void initialize () {
		
		activeUser = Controller.getActiveUser();
		
		albumsList = FXCollections.observableArrayList(activeUser.getAlbums());
		sort();
		 
		listview.setItems(albumsList);
		details = FXCollections.observableArrayList();
		stage = new Stage ();
	}
	
	/**
	 * Initializes the user controller stage
	 */
	public void start () {
		Stage primary = Controller.getStage();
		stage.initOwner(primary);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("User screen");
		stage.setScene( Controller.getScene() );
		
		listview.getSelectionModel().selectedItemProperty().addListener
		(((obs, oldVal, newVal) -> showDetails()));
		
		show_stage();
	}
	
	/**
	 * Shows the details of the currently selected songs
	 */
	public void showDetails () {
		
		details.clear();
		
		Album a = listview.getSelectionModel().getSelectedItem();
		
		if (a == null)
			return;
		
		String name = "Album Name: "+ a.getName();
		String pics = "Total Photos: " + a.getPhotos().size();
		
		Date oldestDate = new Date();
		Date latestDate = new Date();
		oldestDate.setTime(Long.MAX_VALUE);
		latestDate.setTime(Long.MIN_VALUE);
		
		for(Photo p : a.getPhotos()){
			if(p.getDate().getTime() < oldestDate.getTime()){
				oldestDate = p.getDate();
			}
			
			if (p.getDate().getTime() > latestDate.getTime()) {
				latestDate = p.getDate();
			}
		}
		
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String oldDate = formatter.format(oldestDate);
		String newDate = formatter.format(latestDate);
		
		String oldestPhoto;
		String dateRange;
		
		if(a.getPhotos().size() > 0){
			oldestPhoto = "Date of oldest photo: " + oldDate;
			dateRange = "Date range: " + oldDate + " - " + newDate;
		}else{
			oldestPhoto = "Date of oldest photo: \t --";
			dateRange = "Date range: \t --";
		}	
		
		details.add(name);
		details.add(pics);
		details.add(oldestPhoto);
		details.add(dateRange);
		
		album_details.setItems(details);
	}
	
	/**
	 * logs out the current user 
	 */
	public void log_out () {
		next_scene = 1;
		close_stage();
	}
	
	/**
	 * signals for the application to be closed
	 */
	public void quit () {
		next_scene = 0;
		close_stage();
	}
	
	public void show_stage () {
		stage.showAndWait();	
	}
	
	/**
	 * sorts the user albums in alphabetical order
	 */
	public void sort () {
		
		Collections.sort(albumsList, new Comparator<Album>(){
			public int compare(Album a, Album b) {
				return a.getName().compareTo(b.getName());
			}
		});
	}

	/**
	 * opens the selected album and show its photos
	 */
	public void open () {
		
		if(listview.getSelectionModel().getSelectedItem() == null){
			
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(stage);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("No album selected");
			alert.showAndWait();
			
			return;
		}	
		
		activeAlbum = listview.getSelectionModel().getSelectedItem();
		
		next_scene = 4;
		close_stage();
	}

	/**
	 * signals the main controller to open the search stage
	 */
	public void search () {
		next_scene = 5;
		close_stage ();
	}
	
	/**
	 * renames the selected album 
	 */
	public void rename () {
		
		Album albumToRename = listview.getSelectionModel().getSelectedItem();
		
		if(albumToRename == null){
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(stage);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("No album selected");
			alert.showAndWait();
			return;
		}
		
		Dialog<String> dialog = new Dialog<>();
		dialog.initOwner(stage);
		dialog.setTitle("Rename Album");
		//dialog.setResizable(true);
		dialog.setWidth(300);
		
		Label name = new Label("Name:");
		   
		TextField nameText = new TextField(albumToRename.getName());
		   
		GridPane grid = new GridPane();
		grid.setHgap(5);
		grid.add(name, 1, 1);
		grid.add(nameText, 2, 1);
		   
		dialog.getDialogPane().setContent(grid);
		   
		ButtonType button = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(button);
		
		ButtonType button2 = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().add(button2);
		
		dialog.setResultConverter(new Callback<ButtonType, String>() {
			@Override
			public String call(ButtonType b) {
				   
		    if(b == button){
				return nameText.getText();
			}	   
				   
				return null;
				   
			}
		 });   

		 Optional<String> result = dialog.showAndWait();
		   
		 if(result.isPresent()){
			   
			   String newName = result.get();
			   
			   if(newName.length() <= 0){
				   Alert alert = new Alert(AlertType.ERROR);
				   alert.initOwner(stage);
				   alert.setTitle("ERROR");
				   alert.setHeaderText("INVALID INPUT");
				   alert.setContentText("Album name empty");

				   alert.showAndWait();
				   return;
			   }
			   
			   //check for duplicate
			   for(Album a : activeUser.getAlbums()){
				   if(a.getName().equals(newName) && a != albumToRename) {
					   Alert alert = new Alert(AlertType.ERROR);
					   alert.initOwner(stage);
					   alert.setTitle("ERROR");
					   alert.setHeaderText("Duplicate found");
					   alert.setContentText("Album name already exists. Duplicates not allowed.");

					   alert.showAndWait();
					   return;
				   }
			   }
			   
			
			   File f = new File ("data/" + activeUser.getUsername() + File.separator + albumToRename.getName());
			   File f2 = new File ("data/" + activeUser.getUsername() + File.separator + newName);
			   
			   albumToRename.setName(newName);		   
			   f.renameTo(f2);
			   sort();
			   listview.refresh();
			  
		   }
	
	}
	
	/**
	 * deletes the selected album
	 */
	public void delete () {
		
		Album albumToDelete = listview.getSelectionModel().getSelectedItem();
		
		if(albumToDelete == null){
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(stage);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("No album selected");
			alert.showAndWait();
			return;
		}
		
		activeUser.getAlbums().remove(albumToDelete);
		albumsList.remove(albumToDelete);
		
		
		File f = new File ("data/" + activeUser.getUsername() + File.separator + albumToDelete.getName());
		
		try {
			Controller.deleteDir(f);
		} catch (Exception e) {
			
		}
		
		 sort();
	}
	
	/**
	 * creates a new album and names it based on
	 * the name entered in the create album text field
	 */
	public void create () {
		
		String text = new_album_text.getText().trim();
		
		if(text != null && text.length() > 0){
			Album newAlbum = new Album(text);
			
			//check for duplicate
			for(Album a : activeUser.getAlbums()){
				   if(a.getName().equals(text)){
					   Alert alert = new Alert(AlertType.WARNING);
					   alert.initOwner(stage);
					   alert.setTitle("ERROR");
					   alert.setHeaderText("Duplicate found");
					   alert.setContentText("Album name already exists. Duplicates not allowed.");

					   alert.showAndWait();
					   return;
				   }
			   }
			
			activeUser.getAlbums().add(newAlbum);
			albumsList.add(newAlbum);
			
			File f = new File ("data/" + activeUser.getUsername()+ File.separator + text);
			f.mkdir();
			
			new_album_text.setText("");
			sort();
		
			
		}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(stage);
			alert.setTitle("ERROR");
			alert.setHeaderText("No album name entered.");
			alert.setContentText("No album name entered.");

			alert.showAndWait();
			return;
		}
		
	}
	
	public void close_stage () {
		stage.close();
	}
	
	public static int get_next () {
		return next_scene;
	}

	public static Album getActiveAlbum() {
		return activeAlbum;
	}


	
}
