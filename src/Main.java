import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // Make this my main page
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        // Sets title of  main page
        primaryStage.setTitle("Hello Worlds");

        // Sets window size
        primaryStage.setScene(new Scene(root, 400, 300));

        // Display page
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}