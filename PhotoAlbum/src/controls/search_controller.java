package controls;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Path;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;

public class search_controller {
	private static Stage stage;
	private static int next_scene;
	
	@FXML
	private TilePane tilepane;
	
	@FXML
	private ScrollPane scrollpane;
	
	@FXML
	private DatePicker start_date;
	
	@FXML
	private DatePicker end_date;
	
	@FXML
	private TextField search_tag;
	
	@FXML
	private TextField album_title;
	
	@FXML
	private ComboBox<String> combobox = new ComboBox<>();;
	
	@FXML
	private void initialize () {
		
		
		tilepane.setStyle("-fx-background-color: #FFFFFF;");
		tilepane.setPadding(new Insets(15,15,15,15));
		tilepane.setHgap(10);
		tilepane.setVgap(10);
		
		scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    // Horizontal scroll bar
		scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);    // Vertical scroll bar
		scrollpane.setFitToHeight(true);
		scrollpane.setFitToWidth(true);
		scrollpane.setContent(tilepane);
		
		ObservableList<String> options = 
			    FXCollections.observableArrayList
			    ("None", "Location", "Nature",
	    		"Food", "Party", 
	    		"Company", "Vacation", 
	    		"Family", "Pets", "Friends", 
	    		"Animal", "People", "School", 
	    		"Fashion", "Clothing", "Other");
		
		
		combobox.setItems(options);
		
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
		
		long startTime = 0;
		long endTime = 0;
		
		//clear all current photos
		tilepane.getChildren().clear();
		
		LocalDate startDate = start_date.getValue();
		LocalDate endDate = end_date.getValue();
		
		if(startDate != null)
			startTime = startDate.toEpochDay();
		
		if(endDate != null)	
			endTime = endDate.toEpochDay();
		
		for(User u : Controller.getData().getUsers()){
			for(Album a : u.getAlbums()){
				for(Photo p : a.getPhotos()){
						
					boolean duplicate = false;
					
					//check for duplicates
					for(int i = 0; i < tilepane.getChildren().size(); i++){
						if(tilepane.getChildren().get(i).getUserData().equals(p)){
							//duplicate found
							duplicate = true;
							break;
						}
					}
					
					//dont add duplicate, just continue through photos.
					if(duplicate){
						continue;
					}
					
					//not a duplicate, now check search criteria
					
					
					LocalDate localdate = Instant.ofEpochMilli(p.getDate().getTime())
							.atZone(ZoneId.systemDefault()).toLocalDate();
					
					//check date range
					
					if(startDate != null){
						if(localdate.toEpochDay() < startTime){
							//too early
							continue;
						}
					}
					
					if(endDate != null){
						if(localdate.toEpochDay() > endTime){
							//too early
							continue;
						}
					}
					
					String type = combobox.getSelectionModel().getSelectedItem();
					
					//set no type if selection is none
					if(type != null){
						if(type.equals("None")){
							type = null;
						}
					}
					
					boolean typeFound = true;
					
					if(type != null){
						
						//need to check if type exists in tags
						typeFound = false;
						
						for(Tag t : p.getTags()){
							if(t.getTag().equals(type)){
								typeFound = true;
							}
						}
					}
					
					if(!typeFound){
						//tag type not found
						continue;
					}
					
					String value = search_tag.getText();
					
					boolean valueFound = true;
					
					if(value.length() > 0){
						
						//need to check if value exists in tags
						valueFound= false;
						
						for(Tag t : p.getTags()){
							if(t.getTag().equals(type) && t.getValue().equals(value)){
								valueFound = true;
							}
						}
					}
					
					if(!valueFound){
						//value type not found
						continue;
					}
					
					
					
					//All conditions met, add photo to tiles.
					tilepane.getChildren().add(createTile(p));
				
				}
			}
		}
		
	}
	
	public void create () {
		
		//check for empty search window
		if(tilepane.getChildren().size() < 1){
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(stage);
			alert.setTitle("ERROR");
			alert.setHeaderText("No photos available");
			alert.setContentText("There are no photos to make an album with");

			alert.showAndWait();
			return;
		}
		
		String name = album_title.getText();
		
		//check for empty album title
		if(name.length() < 1){
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(stage);
			alert.setTitle("ERROR");
			alert.setHeaderText("No album title given");
			alert.setContentText("Please give a title for the new album");

			alert.showAndWait();
			return;
		}
		
		//check for duplicate album title
		//check for duplicate
		for(Album a : Controller.getActiveUser().getAlbums()){
			   if(a.getName().equals(name)){
				   Alert alert = new Alert(AlertType.WARNING);
				   alert.initOwner(stage);
				   alert.setTitle("ERROR");
				   alert.setHeaderText("Duplicate found");
				   alert.setContentText("Album name already exists. Duplicates not allowed.");

				   alert.showAndWait();
				   return;
			   }
		   }
		
		Album newAlbum = new Album(name);
		
		File f = new File ("data/" + Controller.getActiveUser().getUsername()+ File.separator + name);
		f.mkdir();
		
		for(int i = 0; i < tilepane.getChildren().size(); i++){
			Photo p = (Photo) tilepane.getChildren().get(i).getUserData();
			
			newAlbum.getPhotos().add(p);
			
			String pathString = "data/" + Controller.getActiveUser().getUsername()+ File.separator + name + File.separator + p.getName();
			
			File newFile = new File(pathString);
			try {
				Files.copy(p.getFile().toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Controller.getActiveUser().getAlbums().add(newAlbum);
		
		
		
		
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(stage);
		alert.setTitle("DONE");
		alert.setHeaderText("DONE");
		alert.setContentText("New Album added.");

		alert.showAndWait();
		
		
	}
	
	public StackPane createTile(Photo photo){
		
		String name = photo.getCaption();
		
		Label label = new Label(name);
		
		if(name.length() <= 0){
			label.setText("(No caption)");
		}
		
		ImageView iv = new ImageView();
		
		iv.setImage(new Image(photo.getFile().toURI().toString()));
		iv.setFitHeight(47);
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		//iv.setEffect(new DropShadow());
		
		StackPane pane = new StackPane(iv, label);
		pane.setMinHeight(iv.getFitHeight() + 20);
		pane.setAlignment(iv, Pos.TOP_CENTER);
		pane.setAlignment(label, Pos.BOTTOM_CENTER);
		pane.setUserData(photo);
		pane.setStyle("-fx-border-color: black;");
		
		return pane;
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
