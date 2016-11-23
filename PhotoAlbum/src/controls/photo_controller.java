package controls;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Photo;
import model.Tag;

/**
 * 
 * @author Jacob Rizer
 * @author Terence Williams
 *
 */

/**
 * 
 * controls the photo scene
 *
 */
public class photo_controller {
	private static Stage stage;
	private static int next_scene;
	
	/**
	 * decides which item from the tag type
	 * ComboBox should be auto selected
	 * it is defaulted to the first element
	 */
	private static int i;
	
	/**
	 * the current photo that is being displayed
	 */
	private Photo activePhoto;
	
	/**
	 * list of all tags associated with this photo
	 * 
	 */
	private static ObservableList<Tag> photo_tags;
	
	@FXML 
	private ImageView imageview;
	
	@FXML
	private TextField photo_caption;
	
	@FXML
	private ListView<Tag> tags = new ListView<> ();
	
	@FXML
	private TextField date;
	
	@FXML
	private void initialize () {
		
		activePhoto = album_controller.getActivePhoto();
		
		imageview.setImage(new Image(activePhoto.getFile().toURI().toString()));
				
		
		photo_tags = FXCollections.observableArrayList (activePhoto.getTags());
		sort();
		 
		tags.setItems(photo_tags);
		
		photo_caption.setText(activePhoto.getCaption());
		
		if(activePhoto.getDate() != null) {
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			String date_text = formatter.format(activePhoto.getDate());
			date.setText(date_text);
		}
		
		//date.setEditable(false);
		
		i = 0;
		
		stage = new Stage ();
	}
	
	/**
	 * initializes the photo stage
	 */
	public void start () {
		Stage primary = Controller.getStage();
		stage.initOwner(primary);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Photo Screen");
		stage.setScene( Controller.getScene() );
		show_stage ();
	}
	
	/**
	 * sorts the tags in alphabetic order based on the values
	 */
	public void sort () {
		
		Collections.sort(photo_tags, new Comparator<Tag>(){
			public int compare(Tag a, Tag b) {
				return a.getValue().compareTo(b.getValue());
			}
		});
	}
	
	/**
	 * signals for the application to close
	 */
	public void quit () {
		next_scene = 0;
		close_stage();
	}
	
	/**
	 * edits the photo caption
	 */
	public void edit () {
		
		activePhoto.setCaption(photo_caption.getText());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Caption");
		alert.setHeaderText("New Caption");
		alert.setContentText("Photo Caption has been changed!");

		alert.showAndWait();
		
	}
	
	/**
	 * adds a new tag based on the type and value entered
	 */
	public void add_tag () {
		
		String tag = choose_tagType();
		
		if (tag == null)
			return;
		
		if (tag.equals("Location")) {
			
			for (Tag t: activePhoto.getTags()) {
				
				if (t.getTag().equals("Location")) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.initOwner(stage);
					alert.setTitle("Error");
					alert.setHeaderText("Duplicate Tag");
					alert.setContentText("Photo cannot have multiple locations");
					alert.showAndWait();
					return;
				}
			}
		}
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String value = enter_value("");
		
		if (value == null)
			return;
		
		if (value.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(stage);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("No tag value entered");
			alert.showAndWait();
			return;
		}
		
		for (Tag t: activePhoto.getTags()) {
			if (t.getValue().equals(value)) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(stage);
				alert.setTitle("Error");
				alert.setHeaderText("Duplicate Tag");
				alert.setContentText("Tag already exists");
				alert.showAndWait();
				return;
			}
		}
		
		Tag t = new Tag (tag,value);
		
		photo_tags.add(t);
		activePhoto.getTags().add(t);
		sort();
	}
	
	/**
	 * edits the currently selected tag
	 */
	public void edit_tag () {
		Tag t = tags.getSelectionModel().getSelectedItem();
		
		if (t == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(stage);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("No tag selected");
			alert.showAndWait();
			return;
		}
		
		String tag = t.getTag();
		
		switch (tag) {
		case "Location":
			i = 0;
			break;
		case "Nature":
			i = 1;
			break;
		case "Food":
			i = 2;
			break;
		case "Party" :
			i = 3;
			break;
		case "Company":
			i = 4;
			break;
		case "Vacation":
			i = 5;
			break;
		case "Family":
			i = 6;
			break;
		case "Pets":
			i = 7;
			break;
		case "Friends":
			i = 8;
			break;
		case "Animal":
			i = 9;
			break;
		case "People":
			i = 10;
			break;
		case "School":
			i = 11;
			break;
		case "Fashion":
			i = 12;
			break;
		case "Clothing":
			i = 13;
			break;
		case "Other":
			i = 14;
			break;
		}
		
		String newTag = choose_tagType();
		
		if (newTag == null)
			return;
		
		if (!t.getTag().equals("Location")) {
			if (newTag.equals("Location")) {
				
				for (Tag temp: activePhoto.getTags()) {
					
					if (temp.getTag().equals("Location")) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.initOwner(stage);
						alert.setTitle("Error");
						alert.setHeaderText("Duplicate Tag");
						alert.setContentText("Photo cannot have multiple locations");
						alert.showAndWait();
						return;
					}
				}
			}
		}
		
		String newValue = enter_value(t.getValue());
		
		if (newValue == null)
			return;
		
		if (newValue.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(stage);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("No tag value entered");
			alert.showAndWait();
			return;
		}
		
		if (!t.getValue().equals(newValue)) {
			for (Tag temp: activePhoto.getTags()) {
				if (temp.getValue().equals(newValue)) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.initOwner(stage);
					alert.setTitle("Error");
					alert.setHeaderText("Duplicate Tag");
					alert.setContentText("Tag already exists");
					alert.showAndWait();
					return;
				}
			}
		}
		
		photo_tags.remove(t);
		activePhoto.getTags().remove(t);
		
		Tag temp = new Tag (newTag, newValue);
		photo_tags.add(temp);
		activePhoto.getTags().add(temp);
		sort();
		
	}
	
	
	/**
	 * deletes the currently selected tag
	 */
	public void delete_tag () {
		Tag t = tags.getSelectionModel().getSelectedItem();
		
		if (t == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(stage);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("No tag selected");
			alert.showAndWait();
			return;
		}
		
		
		photo_tags.remove(t);
		activePhoto.getTags().remove(t);
		sort();
	}
	
	/**
	 * pop up dialog box which allows the user to choose the tag type
	 * @return
	 */
	public String choose_tagType () {
		Dialog<String> dialog = new Dialog<>();
		dialog.initOwner(stage);
		dialog.setTitle("Choose Tag Type");
		dialog.setWidth(300);
		
		Label name = new Label("Tag Types:");
		
		ObservableList<String> options = 
			    FXCollections.observableArrayList
			    ("Location", "Nature",
	    		"Food", "Party", 
	    		"Company", "Vacation", 
	    		"Family", "Pets", "Friends", 
	    		"Animal", "People", "School", 
	    		"Fashion", "Clothing", "Other");
		
		ComboBox<String> comboBox = new ComboBox<String>(options);
		   
		GridPane grid = new GridPane();
		grid.setHgap(5);
		grid.add(name, 1, 1);
		grid.add(comboBox, 2, 1);
		   
		dialog.getDialogPane().setContent(grid);
		   
		ButtonType button = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(button);
		
		ButtonType button2 = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().add(button2);
		comboBox.getSelectionModel().select(i);
		
		dialog.setResultConverter(new Callback<ButtonType, String>() {
			@Override
			public String call(ButtonType b) {
				   
		    if(b == button){
				return comboBox.getSelectionModel().getSelectedItem();
			}	   
				   
				return null;
				   
			}
		 });  
		
		 Optional<String> result = dialog.showAndWait();
		
		 if (result.isPresent()) {
			 return result.get();
		 }
		 
		return null;
	}
	
	/**
	 * pop up dialog box which allows the user to enter the tag value
	 * 
	 * @param s
	 * @return
	 */
	public String enter_value (String s) {
		Dialog<String> dialog = new Dialog<>();
		dialog.initOwner(stage);
		dialog.setTitle("Tag Value");
		//dialog.setResizable(true);
		dialog.setWidth(300);
		
		Label name = new Label("Please Enter Tag:");
		   
		TextField nameText = new TextField();
		nameText.setText(s);
		   
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
		
		 if (result.isPresent()) {
			 return result.get();
		 }
		 
		return null;
	}
	
	/**
	 * signals for the application to go to the album stage
	 */
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
