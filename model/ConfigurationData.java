package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class ConfigurationData
{
    
    private static ConfigurationData the_instance = new ConfigurationData();

    private ConfigurationParameters parameters;
    
    private int currentYear;
    
    private String leagueName;
    
    private String rosterFilePath;
    
    private String scoringFilePath;
    
    private boolean projectionsFilesExist = false;
    
    private boolean pastStatisticsFilesExist = false;
    
    static public ConfigurationData Instance() 
    {
        if (the_instance == null)
        {
            the_instance = new ConfigurationData();
        }
        return the_instance;
    }  

    private ConfigurationData()
    {
        super();
        Gson gson = new Gson();
        scoringFilePath = "";
        rosterFilePath = "";
        leagueName = "";
        
        String fileName = "src/data/configuration_data.txt";
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            this.parameters = gson.fromJson(bufferedReader, ConfigurationParameters.class);
            System.out.println("Parameters: " + this.parameters.toString());
            //TODO: Need to add something to LeagueScoring class to 
            //      set scoring based on LeagueScoringParameters
            //      Note - Current workaround is to just hit "Apply Scoring" button
            //      after hitting league button.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("Unable to open file: " + fileName);
        }
        catch(IOException ex)
        {
            System.out.println("Error reading file: " + fileName);
        }
        

    }
    
    public String getLeagueName()
    {
        return leagueName;
    }

    public void setLeagueName(String leagueName)
    {
        this.leagueName = leagueName;
    }

    public String getProjectionsDirectory()
    {
        return this.parameters.getProjectionsDirectory();
    }
    
    public String getPastStatisticsDirectory()
    {
    	return this.parameters.getPastStatisticsDirectory();
    }
    
    public String getSavedDraftPlayerFile()
    {
        return this.parameters.getSavedDraftPlayerFile();
    }
    
    public String getSavedDraftRosterFile()
    {
        return this.parameters.getSavedDraftRosterFile();
    }

    public int getCurrentYear()
    {
        return currentYear;
    }

    public void setCurrentYear(int currentYear)
    {
        System.out.println("Setting current year to: " + Integer.toString(currentYear));
        this.currentYear = currentYear;
    }

    public String getRosterFilePath()
    {
        return rosterFilePath;
    }

    public void setRosterFilePath(String rosterFilePath)
    {
        this.rosterFilePath = rosterFilePath;
    }

    public String getScoringFilePath()
    {
        return scoringFilePath;
    }

    public void setScoringFilePath(String scoringFilePath)
    {
        this.scoringFilePath = scoringFilePath;
    }

    public boolean projectionsFilesExist()
    {
        return projectionsFilesExist;
    }

    public void setProjectionsFilesExist(boolean projectionsFilesExist)
    {
        this.projectionsFilesExist = projectionsFilesExist;
    }

    public boolean pastStatisticsFilesExist()
    {
        return pastStatisticsFilesExist;
    }

    public void setPastStatisticsFilesExist(boolean pastStatisticsFilesExist)
    {
        this.pastStatisticsFilesExist = pastStatisticsFilesExist;
    }
}
