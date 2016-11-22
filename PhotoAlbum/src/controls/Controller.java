package controls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Data;
import model.User;

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
	
	private static Data data;
	private static User activeUser;
	
	//fxml elements
	@FXML
	private TextField username_text;
	
	@FXML
	private void initialize () {
		
		data = new Data();
		stage = Main.getStage();
		read_in_users();
	}
	
	public void login () {
		
		String text = username_text.getText().trim();
		
		if(text.equals("")){
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(stage);
			alert.setTitle("Error");
			alert.setHeaderText("Invaild Username");
			alert.setContentText("Username field is empty");
			alert.showAndWait();
			return;
		}
		
		if (text.equals("admin")) {
			next_scene = ADMIN;
		} 
		
		for(User u : data.getUsers()){
			
			if(u.getUsername().equals(text)){
				next_scene = USER;
				activeUser = u;
			}
				
		}
		
		if(next_scene == LOGIN){
			Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(stage);
	        alert.setTitle("Error");
	        alert.setHeaderText("Invaild Username");
	        alert.setContentText("User does not exist");
	        alert.showAndWait();
	        return;
	        
		}
		
		
		username_text.setText("");
		
		next_scene ();
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
	
	
	public void admin_screen () {
		
		try{
			FXMLLoader loader = new FXMLLoader ();
			loader.setLocation(Controller.class.getResource("/views/admin_screen.fxml"));
			root = (AnchorPane) loader.load();
			scene = new Scene (root);
			
			admin_controller ctrl= (admin_controller) loader.getController();
			stage.hide();
			Thread.sleep(100);
			ctrl.start();
			next_scene = admin_controller.get_next();
			write_users();
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
				
				scene.getStylesheets().addAll(this.getClass().getResource("/views/tilepane.css").toExternalForm());
				
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
	
	public void activeUser_data () {
		
		if (activeUser == null)
			return;
		
		String u = activeUser.getUsername();
		
		if (u == null)
			return;
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream ("data/" + u+ "/user_info"));
			oos.writeObject(activeUser);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void read_in_users () {
		File folder = new File ("data");
		
		if (folder.listFiles() == null)
			return;
		
		File[] files = folder.listFiles();
		
		for (File f: files) {
			
			if (!f.isDirectory())
				continue;
			try {
				ObjectInputStream ois = new ObjectInputStream (new FileInputStream ("data/"+f.getName()+"/user_info"));
				User u = (User) ois.readObject();
				data.getUsers().add(u);
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public static void write_users () {
		List<User> users_added = admin_controller.get_UsersAdded();
		List<User> users_deleted = admin_controller.get_UsersDeleted();
		
		if (!users_deleted.isEmpty()) {
			for (User u: users_deleted) {
				try {
					File f = new File ("data/"+u.getUsername());
					deleteDir(f);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		if (!users_added.isEmpty()) {
			for (User u: users_added) {
				try {
					File f = new File ("data/"+u.getUsername());
					f.mkdir();
					ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (f+"/user_info"));
					oos.writeObject(u);
					oos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void deleteDir(File dir) {
	      if (dir.isDirectory()) {
	         String[] children = dir.list();
	         for (int i = 0; i < children.length; i++) {
	            deleteDir(new File(dir, children[i]));
	         }
	      }
	      
	      dir.delete();
	}
	
	public void next_scene () {
		
		switch (next_scene) {
		case CLOSE:
			activeUser_data();
			System.exit(0);
			break;
		case LOGIN:
			activeUser_data();
			setStage ();
			break;
		case ADMIN:
			admin_screen ();
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
	
	public static Data getData(){
		return data;
	}
	
	public static User getActiveUser(){
		return activeUser;
	}
	
}
