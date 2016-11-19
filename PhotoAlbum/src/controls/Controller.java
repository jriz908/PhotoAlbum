package controls;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller {
	
	private static final int CLOSE = 0;
	private static final int LOGIN = 1;
	private static final int ADMIN = 2;
	private static final int USER = 3;
	private static final int ALBUM = 4;
	private static final int SEARCH = 5;
	private static final int PHOTO = 6;
	private static final int MOVE_COPY = 7;
	private static final int SLIDESHOW = 8;
	
	private static Stage stage;
	private static AnchorPane root;
	private static Scene scene;
	private static int next_scene = 1;
	
	@FXML
	private void initialize () {
		stage = Main.getStage();
	}
	
	public void user_screen () {
		try{
			FXMLLoader loader = new FXMLLoader ();
			loader.setLocation(Controller.class.getResource("/views/user_screen.fxml"));
			root = (AnchorPane) loader.load();
			scene = new Scene (root);

			user_controller ctrl = (user_controller) loader.getController();
			stage.hide();
			Thread.sleep(100);
			ctrl.start();
			next_scene = user_controller.get_next();
			next_scene ();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void album_screen () {
			
			try {
				FXMLLoader loader = new FXMLLoader ();
				loader.setLocation(Controller.class.getResource("/views/album_screen.fxml"));
				root = (AnchorPane) loader.load();
				scene = new Scene (root);
				
				album_controller ctrl= (album_controller) loader.getController();
				stage.hide();
				Thread.sleep(100);
				ctrl.start();
				next_scene = album_controller.get_next();
				next_scene ();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void photo_screen () {
		
		try {
			FXMLLoader loader = new FXMLLoader ();
			loader.setLocation(Controller.class.getResource("/views/photo_screen.fxml"));
			root = (AnchorPane) loader.load();
			scene = new Scene (root);
			
			photo_controller ctrl= (photo_controller) loader.getController();
			stage.hide();
			Thread.sleep(100);
			ctrl.start();
			next_scene = photo_controller.get_next();
			next_scene ();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void move_copy () {
		
		try {
			FXMLLoader loader = new FXMLLoader ();
			loader.setLocation(Controller.class.getResource("/views/move_copy.fxml"));
			root = (AnchorPane) loader.load();
			scene = new Scene (root);
			
			move_controller ctrl= (move_controller) loader.getController();
			stage.hide();
			Thread.sleep(100);
			ctrl.start();
			next_scene = move_controller.get_next();
			next_scene ();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void slideshow () {
		
		try {
			FXMLLoader loader = new FXMLLoader ();
			loader.setLocation(Controller.class.getResource("/views/slideshow.fxml"));
			root = (AnchorPane) loader.load();
			scene = new Scene (root);
			
			slideshow_controller ctrl= (slideshow_controller) loader.getController();
			stage.hide();
			Thread.sleep(100);
			ctrl.start();
			next_scene = slideshow_controller.get_next();
			next_scene ();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void next_scene () {
		
		switch (next_scene) {
		case CLOSE:
			System.exit(0);
			break;
		case LOGIN:
			setStage ();
			break;
		case USER:
			user_screen ();
			break;
		case ALBUM:
			album_screen ();
			break;
		case SEARCH:
			search_screen();
			break;
		case PHOTO:
			photo_screen();
			break;
		case MOVE_COPY:
			move_copy ();
			break;
		case SLIDESHOW:
			slideshow ();
			break;
		}
		
	}
	
	public void search_screen () {
		
		try {
			FXMLLoader loader = new FXMLLoader ();
			loader.setLocation(Controller.class.getResource("/views/search_screen.fxml"));
			root = (AnchorPane) loader.load();
			scene = new Scene (root);
			
			search_controller ctrl= (search_controller) loader.getController();
			stage.hide();
			Thread.sleep(100);
			ctrl.start();
			next_scene = search_controller.get_next();
			next_scene ();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * controls the main stage
	 * switches between the respective stages
	 */
	public void setStage () {
		stage = Main.getStage();
		stage.show();
	}
	
	public static Stage getStage () {
		return stage;
	}
	
	public static Scene getScene () {
		return scene;
	}
	
}
