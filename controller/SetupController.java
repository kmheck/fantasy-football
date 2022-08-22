package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.ConfigurationData;
import model.FantasyProsProjectionsFileReader;
import model.LeagueRosterParameters;
import model.LeagueScoring;
import model.LeagueScoringParameters;
import model.PastStatisticsFileReader;
import model.Roster;
import model.StatisticsFileReader;

import com.google.gson.Gson;


public class SetupController
{


    @FXML private TextField selectedLeagueTextField;
    @FXML private TextArea usageTextArea;
    @FXML private Label usageLabel;
    @FXML private TextField yearTextField;
    
    @FXML private Button usageCloseButton;
    
    private String rosterFilePath;
    private String scoringFilePath;
    StatisticsFileReader projectionsFileReader = null;
    StatisticsFileReader pastStatisticsFileReader = null;
    private MainController mainController = null;
    

    public SetupController()
    {
        super();
        System.out.println("SetupController.SetupController()");
        // TODO Auto-generated constructor stub
    }



    private void setYear()
    {
        LocalDate currentDate = java.time.LocalDate.now();
        int currentYear = currentDate.getYear();
        if (this.yearTextField.getText().isEmpty())
        {
            System.out.println("SetupController.setYear - currentDate: " + currentDate.toString());
            ConfigurationData.Instance().setCurrentYear(currentYear);
            this.yearTextField.setText(Integer.toString(ConfigurationData.Instance().getCurrentYear()));
        }
        else
        {
            currentYear = Integer.valueOf(this.yearTextField.getText());
            ConfigurationData.Instance().setCurrentYear(currentYear);
        }
    }

    private void setLeagueRosterParameters()
    {
        System.out.println("Choose Roster File Menu item selected");
        Gson gson = new Gson();
        
        
        // Projections directory is just /src/data which will suffice
        try
        {
            FileReader fileReader = new FileReader(this.rosterFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            LeagueRosterParameters rosterParameters = gson.fromJson(bufferedReader, LeagueRosterParameters.class);
            System.out.println("League Roster Parameters: " + rosterParameters.toString());
            Roster.Instance().setRosterParameters(rosterParameters);
            Roster.Instance().setLeagueRosterSettingsFromParameters();
            bufferedReader.close();
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            System.out.println("File: " + this.rosterFilePath + " not found");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private void setLeagueScoringParameters()
    {
        Gson gson = new Gson();
        try
        {
            FileReader fileReader = new FileReader(this.scoringFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //TODO: Somehow read the file in with JSON methods
            LeagueScoringParameters leagueScoringParameters = gson.fromJson(bufferedReader, LeagueScoringParameters.class);
            System.out.println("Scoring Parameters for league " + ConfigurationData.Instance().getLeagueName() +  ": " + leagueScoringParameters.toString());
            LeagueScoring.Instance().setScoringParameters(leagueScoringParameters);
            LeagueScoring.Instance().setPositionScoringFromParameters();
            bufferedReader.close();
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("Unable to open file: " + this.scoringFilePath);
        }
        catch(IOException ex)
        {
            System.out.println("Error reading file: " + this.scoringFilePath);
        }

    }
    
    @FXML
    void selectLeagueButtonAction(ActionEvent event)
    {
        //TODO: Determine if league scoring and roster settings files exist and are parseable
        // Read in projection/past statistics files if so.
        // If not, popup something about needing to create them.
        String leagueName = this.selectedLeagueTextField.getText();
        ConfigurationData.Instance().setLeagueName(leagueName);
        
        System.out.println("Selected League is: " + leagueName);
        this.setYear();

        boolean scoringFileExists = checkForScoringFile();
        if (scoringFileExists)
        {
            System.out.println("Scoring file found");
            setLeagueScoringParameters();
        }
        else
        {
            System.out.println("Scoring file not found in: " + ConfigurationData.Instance().getProjectionsDirectory());
        }

        boolean rosterFileExists = checkForRosterFile();
        if (rosterFileExists)
        {
            System.out.println("Roster file found");
            setLeagueRosterParameters();
        }
        else
        {
            System.out.println("Roster file not found in: " + ConfigurationData.Instance().getProjectionsDirectory());
        }
        
        boolean projectionFilesExist = checkForProjectionFiles();
        if (projectionFilesExist)
        {
            System.out.println("Projection files found");
            ConfigurationData.Instance().setProjectionsFilesExist(true);
            this.projectionsFileReader = new FantasyProsProjectionsFileReader(ConfigurationData.Instance().getProjectionsDirectory());
        }
        else
        {
            System.out.println("Projection files not found in: " + ConfigurationData.Instance().getProjectionsDirectory());
        }
        
        boolean pastStatisticFilesExist = checkForPastStatisticsFiles();
        if (pastStatisticFilesExist)
        {
            System.out.println("Past Statistics files found");
            ConfigurationData.Instance().setPastStatisticsFilesExist(true);
            this.pastStatisticsFileReader = new PastStatisticsFileReader(ConfigurationData.Instance().getPastStatisticsDirectory());
        }
        else
        {
            System.out.println("Past Statistics files not found in: " + ConfigurationData.Instance().getPastStatisticsDirectory());
        }
            
        if (this.mainController != null)
        {
            this.mainController.setExternallySelectedTab("draft");
        }
        else
        {
            System.out.println("this.mainController is null, cannot call methods...");
        }
                
    }
    
    @FXML
    void usageCloseButtonAction(ActionEvent event)
    {
        usageLabel.setVisible(false);
        usageCloseButton.setVisible(false);
    }

    @FXML
    void helpSetupUsageAction(ActionEvent event)
    {
        System.out.println("helpSetupUsageAction - Enter");
//        usageTextArea.setVisible(true);
        usageLabel.setVisible(true);
        usageCloseButton.setVisible(true);
    }
    
    private boolean checkForRosterFile()
    {
        boolean rosterFileExists = false;
        
        String filePath = "src/data/roster_" + ConfigurationData.Instance().getLeagueName() + ".txt";
        
        File rosterFile = new File(filePath);
        if (rosterFile.exists() && rosterFile.canRead())
        {
            rosterFileExists = true;
            this.rosterFilePath = filePath;
            ConfigurationData.Instance().setRosterFilePath(this.rosterFilePath);
        }
        else
        {
            System.out.println("Could not find roster file: " + filePath);
        }

        return rosterFileExists;
    }
    
    private boolean checkForScoringFile()
    {
        boolean scoringFileExists = false;

        String filePath = "src/data/league_scoring_" + ConfigurationData.Instance().getLeagueName() + ".txt";
        
        File scoringFile = new File(filePath);
        if (scoringFile.exists() && scoringFile.canRead())
        {
            scoringFileExists = true;
            this.scoringFilePath = filePath;
            ConfigurationData.Instance().setScoringFilePath(this.scoringFilePath);
        }
        else
        {
            System.out.println("Could not find league scoring file: " + filePath);
        }

        return scoringFileExists;
        
    }
    
    private boolean checkForProjectionFiles()
    {
        boolean projectionFilesExist = false;
        boolean qbProjectionFileFound = false;
        boolean wrProjectionFileFound = false;
        boolean teProjectionFileFound = false;
        boolean kickerProjectionFileFound = false;
        boolean rbProjectionFileFound = false;
        String dateString = Integer.toString(ConfigurationData.Instance().getCurrentYear());
        
        System.out.println("Open File Projections Menu item selected");
        ArrayList<String> directoryFiles = new ArrayList<String>();
        String projectionsDirectory = ConfigurationData.Instance().getProjectionsDirectory();
        File projectionsPath = new File(projectionsDirectory);
        for (final File fileEntry : projectionsPath.listFiles())
        {
            if (fileEntry.isFile())
            {
                System.out.println(fileEntry.getName());
                directoryFiles.add(fileEntry.getName());
            }
        }
        Iterator<String> filesIterator = directoryFiles.iterator();
        while (filesIterator.hasNext())
        {
            String filename = filesIterator.next();
            if (filename.contains("qb") && filename.contains(dateString))
            {
                qbProjectionFileFound = true;
            }
            else if (filename.contains("rb_") && filename.contains(dateString))
            {
                rbProjectionFileFound = true;
            }
            else if (filename.contains("wr_") && filename.contains(dateString))
            {
                wrProjectionFileFound = true;
            }
            else if (filename.contains("te_") && filename.contains(dateString))
            {
                teProjectionFileFound = true;
            }
            else if (filename.contains("kicker") && filename.contains(dateString))
            {
                kickerProjectionFileFound = true;
            }

        }
        if (qbProjectionFileFound && rbProjectionFileFound && wrProjectionFileFound &&
                teProjectionFileFound && kickerProjectionFileFound)
        {
            projectionFilesExist = true;
        }
        return projectionFilesExist;
    }
    
    private boolean checkForPastStatisticsFiles()
    {
        boolean pastStatisticsFilesExist = false;
        boolean qbStatisticsFileFound = false;
        boolean wrStatisticsFileFound = false;
        boolean teStatisticsFileFound = false;
        boolean kickerStatisticsFileFound = false;
        boolean rbStatisticsFileFound = false;
        String dateString = Integer.toString(ConfigurationData.Instance().getCurrentYear() - 1);
        
        System.out.println("Open File Statisticss Menu item selected");
        ArrayList<String> directoryFiles = new ArrayList<String>();
        String statisticsDirectory = ConfigurationData.Instance().getPastStatisticsDirectory();
        File statisticsPath = new File(statisticsDirectory);
        for (final File fileEntry : statisticsPath.listFiles())
        {
            if (fileEntry.isFile())
            {
                System.out.println(fileEntry.getName());
                directoryFiles.add(fileEntry.getName());
            }
        }
        Iterator<String> filesIterator = directoryFiles.iterator();
        while (filesIterator.hasNext())
        {
            String filename = filesIterator.next();
            if (filename.contains("qb") && filename.contains(dateString))
            {
                qbStatisticsFileFound = true;
            }
            else if (filename.contains("rb_") && filename.contains(dateString))
            {
                rbStatisticsFileFound = true;
            }
            else if (filename.contains("wr_") && filename.contains(dateString))
            {
                wrStatisticsFileFound = true;
            }
            else if (filename.contains("te_") && filename.contains(dateString))
            {
                teStatisticsFileFound = true;
            }
            else if (filename.contains("kicker") && filename.contains(dateString))
            {
                kickerStatisticsFileFound = true;
            }

        }
        if (qbStatisticsFileFound || rbStatisticsFileFound || wrStatisticsFileFound ||
                teStatisticsFileFound || kickerStatisticsFileFound)
        {
            pastStatisticsFilesExist = true;
        }
        return pastStatisticsFilesExist;
    }



    public void setMainController(MainController controller)
    {
        System.out.println("SetupController.setMainController");
        this.mainController = controller;
    }
}
