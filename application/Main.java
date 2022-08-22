package application;
    
import controller.ProjectionsFileController;
import controller.RosterController;
import controller.SetupController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application {
    
//    @FXML
//    ProjectionsFileController projectionsFileController;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Attempt to get stage into controller
            FXMLLoader projectionsFileLoader = new FXMLLoader(getClass().getResource("/view/ProjectionsFile.fxml"));
            projectionsFileLoader.load();
            ProjectionsFileController projectionsFileController = projectionsFileLoader.getController();
            projectionsFileController.setStage(primaryStage);
            FXMLLoader rosterLoader = new FXMLLoader(getClass().getResource("/view/Roster.fxml"));
            rosterLoader.load();
            RosterController rosterController = rosterLoader.getController();
            rosterController.setPrimaryStage(primaryStage);

            FXMLLoader setupLoader = new FXMLLoader(getClass().getResource("/view/Setup.fxml"));
            setupLoader.load();
            // End attempt
            Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Fantasy Football Draft Program");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
