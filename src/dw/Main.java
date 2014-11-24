package dw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
	try {
			
			
			Parent root = FXMLLoader.load(getClass().getResource("views/StartView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
		//	root.getChildren().add(b);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
