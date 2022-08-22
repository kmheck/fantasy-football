package controller;

import java.io.File;

import model.ConfigurationData;
import model.FantasyProsProjectionsFileReader;
import model.PastStatisticsFileReader;
import model.StatisticsFileReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ProjectionsFileController
{
    @FXML
    Parent root;
    
    @FXML
    TextField projectionsFileDirectoryTextField;
    
    @FXML
    TextField pastStatisticsFileDirectoryTextField;
    
    Stage primaryStage;
    
    StatisticsFileReader projectionsFileReader = null;
    StatisticsFileReader pastStatisticsFileReader = null;
    
    @FXML
    private void OpenProjectionsFileAction(ActionEvent event)
    {
        System.out.println("Open File Projections Menu item selected");
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Projections File");
//        fileChooser.setInitialDirectory(new File("C:/misc/football"));
        String projectionsDirectory = ConfigurationData.Instance().getProjectionsDirectory();
        fileChooser.setInitialDirectory(new File(projectionsDirectory));
        File chosenFile = fileChooser.showOpenDialog(this.primaryStage);
        if (chosenFile != null) 
        {
            String projectionsFilepath = chosenFile.getParent();
            System.out.println("File parent: " + projectionsFilepath);
            projectionsFileDirectoryTextField.setText(projectionsFilepath);
            //TODO: Extract the path to the directory out of the filename and then
            //  call the proper file reader type.  That file reader should iterate
            //  over all of the projections files in the directory calling the appropriate
            //  position reader to read the players into a player collection.
            //TODO: Determine both the file type (i.e. - Fantasy Pros file, ESPN file etc.
            //  as well as the position and then call the correct type of file reader to
            //  read in the projections.
            CharSequence fantasyProsSequence = "fantasypros";
//            if (projectionsFilepath.contains(fantasyProsSequence))
//            {
//                this.projectionsFileReader = new FantasyProsProjectionsFileReader(projectionsFilepath);
//            }
            this.projectionsFileReader = new FantasyProsProjectionsFileReader(projectionsFilepath);
        }
    }

    @FXML
    private void OpenPastStatisticsFileAction(ActionEvent event)
    {
        System.out.println("Open Past Statistics File Menu item selected");
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Past Statistics File");
        String pastStatisticsDirectory = ConfigurationData.Instance().getPastStatisticsDirectory();
        System.out.println("Past Statistics Directory: " + pastStatisticsDirectory);
        fileChooser.setInitialDirectory(new File(pastStatisticsDirectory));
        File chosenFile = fileChooser.showOpenDialog(this.primaryStage);
        if (chosenFile != null) 
        {
            String pastStatisticsFilepath = chosenFile.getParent();
            System.out.println("File parent: " + pastStatisticsFilepath);
            pastStatisticsFileDirectoryTextField.setText(pastStatisticsFilepath);
            //TODO: Extract the path to the directory out of the filename and then
            //  call the proper file reader type.  That file reader should iterate
            //  over all of the projections files in the directory calling the appropriate
            //  position reader to read the players into a player collection.
            //TODO: Determine both the file type (i.e. - Fantasy Pros file, ESPN file etc.
            //  as well as the position and then call the correct type of file reader to
            //  read in the projections.
            this.pastStatisticsFileReader = new PastStatisticsFileReader(pastStatisticsFilepath);
        }
    	
    }
    
    public void fillTextBoxes()
    {
        if (ConfigurationData.Instance().pastStatisticsFilesExist())
        {
            pastStatisticsFileDirectoryTextField.setText(ConfigurationData.Instance().getPastStatisticsDirectory());
        }
        else
        {
            pastStatisticsFileDirectoryTextField.setText("not configured");
        }
        
        if (ConfigurationData.Instance().projectionsFilesExist())
        {
            projectionsFileDirectoryTextField.setText(ConfigurationData.Instance().getProjectionsDirectory());
        }
        else
        {
            projectionsFileDirectoryTextField.setText("not configured");
        }
    }
    
    public void setStage(Stage primaryStage)
    {
        // TODO Auto-generated method stub
        this.primaryStage = primaryStage;
    }
}
