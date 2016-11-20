package application;
	
import controls.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	private static Stage stage;
	private AnchorPane root;
	private Controller ctrl;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/views/log_in.fxml"));
			root = (AnchorPane) loader.load();
			Scene scene = new Scene (root);
			
			stage = primaryStage;
			stage.setScene(scene);
			stage.setTitle("Photo Album");
			
			ctrl = (Controller) loader.getController();
			ctrl.setStage();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop () {
		ctrl.activeUser_data();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static Stage getStage () {
		return stage;
	}
	
}
