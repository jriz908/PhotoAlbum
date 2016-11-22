package controls;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
	
	private StackPane activePane;
	
	private Album activeAlbum;
	
	@FXML
	private TilePane tilepane;
	
	@FXML
	private ScrollPane scrollpane;
	
	@FXML
	private void initialize () {
		
		scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    // Horizontal scroll bar
		scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);    // Vertical scroll bar
		scrollpane.setFitToHeight(true);
		scrollpane.setFitToWidth(true);
		scrollpane.setContent(tilepane);
		
		activeAlbum = user_controller.getActiveAlbum();
		
		
		tilepane.setStyle("-fx-background-color: #FFFFFF;");
		tilepane.setPadding(new Insets(15,15,15,15));
		tilepane.setHgap(10);
		tilepane.setVgap(10);
		
		showImages();
		
		System.out.println(tilepane.getChildren().size());
		
		stage = new Stage ();
	}
	
	public void showImages(){
		for(Photo p : activeAlbum.getPhotos()){
			tilepane.getChildren().add(createTile(p));
		}
	}
	
	public StackPane createTile(Photo photo){
		
		String name = photo.getCaption();
		
		Label label = new Label(name);
		
		if(name.length() <= 0){
			label.setText("(No caption)");
		}
		
		ImageView iv = new ImageView();
		
		iv.setImage(new Image(photo.getFile().toURI().toString()));
		iv.setFitHeight(120);
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		//iv.setEffect(new DropShadow());
		
		StackPane pane = new StackPane(iv, label);
		pane.setMinHeight(iv.getFitHeight() + 20);
		pane.setAlignment(iv, Pos.TOP_CENTER);
		pane.setAlignment(label, Pos.BOTTOM_CENTER);
		pane.setUserData(photo);
		pane.setOnMouseClicked(e -> setActivePhoto(pane));
		pane.setStyle("-fx-border-color: black;");
		
		return pane;
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
		
		if(activePane == null){
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(stage);
			alert.setTitle("ERROR");
			alert.setHeaderText("No photo selected");
			alert.setContentText("Please select a photo to open");

			alert.showAndWait();
			return;
		}
		
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
		
		//check for duplicates
		for(Photo p : activeAlbum.getPhotos()){
			if(p.getFile().equals(file)){
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(stage);
				alert.setTitle("ERROR");
				alert.setHeaderText("Duplicate photo");
				alert.setContentText("Can not add a duplicate photo to album.");

				alert.showAndWait();
				return;
			}
		}
		
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
		
		
		tilepane.getChildren().add(createTile(newPhoto));
		
		
		
		
		
		
		System.out.println(tilepane.getChildren().size());
		
		
		//System.out.println(activeAlbum.getPhotos().size());
		
		
	
		
		
		
		
		
	}
	
	public void remove () {
		
		if(activePane == null){
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(stage);
			alert.setTitle("ERROR");
			alert.setHeaderText("No photo selected");
			alert.setContentText("Please select a photo to delete");

			alert.showAndWait();
			return;
		}
		
		Photo photoToDelete = (Photo) activePane.getUserData();
		
		activeAlbum.getPhotos().remove(photoToDelete);
		
		tilepane.getChildren().remove(activePane);
		
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
	
	public void setActivePhoto(StackPane pane) {
		
		if(activePane != null)
			activePane.setStyle("-fx-border-color: black;");
		
		activePane = pane;
		
		activePane.setStyle("-fx-border-color: red;");
		
		activePhoto = (Photo) pane.getUserData();
		
		
	}

	
}
