package model;

public class ConfigurationParameters
{


    String configurationFile;
    String projectionsDirectory;
    String pastStatisticsDirectory;
    String savedDraftPlayerFile;
    String savedDraftRosterFile;

    public String getSavedDraftRosterFile()
    {
        return savedDraftRosterFile;
    }

    public void setSavedDraftRosterFile(String savedDraftRosterFile)
    {
        this.savedDraftRosterFile = savedDraftRosterFile;
    }

    public String getSavedDraftPlayerFile()
    {
        return savedDraftPlayerFile;
    }

    public void setSavedDraftPlayerFile(String savedDraftPlayerFile)
    {
        this.savedDraftPlayerFile = savedDraftPlayerFile;
    }

    public String getPastStatisticsDirectory() {
		return pastStatisticsDirectory;
	}

	public String getProjectionsDirectory()
    {
        return projectionsDirectory;
    }

    public void setProjectionsDirectory(String projectionsDirectory)
    {
        this.projectionsDirectory = projectionsDirectory;
    }

    public String getConfigurationFile()
    {
        return configurationFile;
    }

    public void setConfigurationFile(String configurationFile)
    {
        this.configurationFile = configurationFile;
    }

    @Override
    public String toString()
    {
        return "ConfigurationParameters [configurationFile=" + configurationFile + ", projectionsDirectory="
                + projectionsDirectory + ", pastStatisticsDirectory=" + pastStatisticsDirectory
                + ", savedDraftPlayerFile=" + savedDraftPlayerFile + ", savedDraftRosterFile=" + savedDraftRosterFile
                + "]";
    }
    
    
}
