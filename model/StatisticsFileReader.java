package model;

import java.io.File;
import java.util.regex.Pattern;

public abstract class StatisticsFileReader
{

    protected String statisticsFileDirectory = null;

    protected File qbFile = null;
    protected File rbFile = null;
    protected File wrFile = null;
    protected File teFile = null;
    protected File kFile = null;
    
    protected File teamDefenseFile = null;
    protected File idpFile = null;
    
    
    public abstract void readQBFile();
    public abstract void readRBFile();
    public abstract void readWRFile();
    public abstract void readTEFile();
    public abstract void readKFile();
    public abstract void readTeamDefenseFile();
    public abstract void readIDPFile();
    
    protected File[] getStatisticFiles(String fileDirectory)
    {
        File[] statisticFiles = null;
        
        File folder = new File(fileDirectory);
        statisticFiles = folder.listFiles();

        return statisticFiles;
    }
    
    protected String extractYearFromFileName(String filename)
    {
        String REGEX_UNDERSCORE = "_";
        Pattern underscorePattern = Pattern.compile(REGEX_UNDERSCORE);
        String[] filenameItems = underscorePattern.split(filename);
        String year = "2019";
        String TwoThousand = "20";
        for (int i = 0; i < filenameItems.length; i++)
        {
          if (filenameItems[i].startsWith(TwoThousand))
          {
              year = filenameItems[i];
              int extensionIndex = year.lastIndexOf(".");
              year = year.substring(0, extensionIndex);
              break;
          }
        }

        return year;
    }
    
    protected void assignStatisticFiles(int year)
    {
        boolean qbFileFound = false;
        boolean rbFileFound = false;
        boolean wrFileFound = false;
        boolean teFileFound = false;
        boolean kickerFileFound = false;
        boolean teamDefenseFileFound = false;
        boolean idpFileFound = false;
        String yearString = Integer.toString(year);
        if (this.statisticsFileDirectory != null)
        {
            File[] statisticFiles = getStatisticFiles(this.statisticsFileDirectory);
            for (int i = 0; i < statisticFiles.length; i++)
            {
                if (statisticFiles[i].isFile()) 
                {
                    String filename = statisticFiles[i].getName();
                    System.out.println("File " + filename);
                    if (filename.contains("qb") && filename.contains(yearString))
                    {
                        qbFile = statisticFiles[i];
                        qbFileFound = true;
                    }
                    else if (filename.contains("rb_") && filename.contains(yearString))
                    {
                        rbFile = statisticFiles[i];
                        rbFileFound = true;
                    }
                    else if (filename.contains("wr_")&& filename.contains(yearString))
                    {
                        wrFile = statisticFiles[i];
                        wrFileFound = true;
                    }
                    else if (filename.contains("te_")&& filename.contains(yearString))
                    {
                        teFile = statisticFiles[i];
                        teFileFound = true;
                    }
                    else if (filename.contains("kicker")&& filename.contains(yearString))
                    {
                        kFile = statisticFiles[i];
                        kickerFileFound = true;
                    }
                    else if (filename.contains("team_defense")&& filename.contains(yearString))
                    {
                        teamDefenseFile = statisticFiles[i];
                        teamDefenseFileFound = true;
                    }
                    else if (filename.contains("idp")&& filename.contains(yearString))
                    {
                        idpFile = statisticFiles[i];
                        idpFileFound = true;
                    }
                } 
                else if (statisticFiles[i].isDirectory()) 
                {
                    System.out.println("Directory " + statisticFiles[i].getName());
                }
            }
            if (!qbFileFound)
            {
                System.out.println("Could not find QB file for year: " + yearString + " in directory: " + this.statisticsFileDirectory);
            }
            if (!rbFileFound)
            {
                System.out.println("Could not find RB file for year: " + yearString + " in directory: " + this.statisticsFileDirectory);
            }
            if (!wrFileFound)
            {
                System.out.println("Could not find WR file for year: " + yearString + " in directory: " + this.statisticsFileDirectory);
            }
            if (!teFileFound)
            {
                System.out.println("Could not find TE file for year: " + yearString + " in directory: " + this.statisticsFileDirectory);
            }
            if (!kickerFileFound)
            {
                System.out.println("Could not find kicker file for year: " + yearString + " in directory: " + this.statisticsFileDirectory);
            }
            if (!teamDefenseFileFound)
            {
                System.out.println("Could not find team defense file for year: " + yearString + " in directory: " + this.statisticsFileDirectory);
            }
            if (!idpFileFound)
            {
                System.out.println("Could not find IDP file for year: " + yearString + " in directory: " + this.statisticsFileDirectory);
            }
            
        }
    }
    
    protected void readStatisticFiles()
    {
        if (this.qbFile != null)
        {
            readQBFile();
        }
        else
        {
            System.out.println("QB file is null, will not be read...");
        }
        
        if (this.rbFile != null)
        {
            readRBFile();
        }
        else
        {
            System.out.println("RB file is null, will not be read...");
        }

        if (this.wrFile != null)
        {
            readWRFile();
        }
        else
        {
            System.out.println("WR file is null, will not be read...");
        }
        
        if (this.teFile != null)
        {
            readTEFile();
        }
        else
        {
            System.out.println("TE file is null, will not be read...");
        }

        if (this.kFile != null)
        {
            readKFile();
        }
        else
        {
            System.out.println("Kicker file is null, will not be read...");
        }

        if (this.teamDefenseFile != null)
        {
            readTeamDefenseFile();
        }
        else
        {
            System.out.println("Team Defense file is null, will not be read...");
        }

        if (this.idpFile != null)
        {
            readIDPFile();
        }
        else
        {
            System.out.println("IDP file is null, will not be read...");
        }
        
//        // After projection files are read, calculate diff values for
//        // each position.
//        for (Position position : Position.values())
//        {
//            if (position != Position.UNKNOWN)
//            {
//                if (PlayerPositionCollection.Instance().getPositionCollection(position) != null)
//                {
//                    PlayerPositionCollection.Instance().sort(position);
//                    PlayerPositionCollection.Instance().calculateMean(position);
//                    PlayerPositionCollection.Instance().calculateDiffValues(position);
//                }
//            }
//        }
//        AllPlayerCollection.Instance().assemble();
        
    }
    
    protected void processName(String fullName, OffensivePlayer offensivePlayer)
    {
        String REGEX_SPACE = " ";
        Pattern space_pattern = Pattern.compile(REGEX_SPACE);
        
        String name[] = space_pattern.split(fullName);
        offensivePlayer.setFirstName(name[0]);
        offensivePlayer.setLastName(name[1]);
        
        offensivePlayer.createDisplayName();
        //TODO: This should be more sophisticated when there are more than two
        // items in name.
    }
    
    protected void processName(String fullName, IndividualDefensivePlayer idp)
    {
        String REGEX_SPACE = " ";
        Pattern space_pattern = Pattern.compile(REGEX_SPACE);
        
        String name[] = space_pattern.split(fullName);
        idp.setFirstName(name[0]);
        idp.setLastName(name[1]);
        
        idp.createDisplayName();
        //TODO: This should be more sophisticated when there are more than two
        // items in name.
    }
}
