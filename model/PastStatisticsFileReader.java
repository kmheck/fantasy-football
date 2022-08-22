package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;

public class PastStatisticsFileReader extends StatisticsFileReader
{

    
    
    public PastStatisticsFileReader(String statisticsFileDirectory)
    {
        super();
        this.statisticsFileDirectory = statisticsFileDirectory;
        System.out.println("Constructing PastStatisticsFileReader with directory: " + this.statisticsFileDirectory);
        assignStatisticFiles(ConfigurationData.Instance().getCurrentYear() - 1);
        readStatisticFiles();
    }

    @Override
    public void readQBFile()
    {
        System.out.println("PastStatisticsFileReader.readQBFile()");
        // TODO Auto-generated method stub
        try
        {
          System.out.println("Attempting to read file: " + this.qbFile.getAbsolutePath());
          String year = extractYearFromFileName(this.qbFile.getAbsolutePath());
          BufferedReader reader = new BufferedReader(new FileReader(this.qbFile.getAbsolutePath()));
          String REGEX_TAB = "\t";

          Pattern tab_pattern = Pattern.compile(REGEX_TAB);

          String line;
          while ((line = reader.readLine()) != null)
          {
            //TODO: Read and parse each line of file and store in
            //      some sort of player collection
//              System.out.println(line);

              OffensivePlayer offensivePlayer = new OffensivePlayer();
              String[] items = tab_pattern.split(line);
              
              //QB file should contain (fftoday.com):
              //0:  First Name Last Name
              //1:  NFL Team
              //2:  Games
              //3:  Pass completions
              //4:  Pass attempts
              //5:  Pass yards
              //6:  Pass TDs
              //7:  Interceptions
              //8:  Rush attempts
              //9:  Rush yards
              //10:  Rush TDs
              //11: Fantasy points
              //12: Fantasy point/game
              
              processName(items[0], offensivePlayer);
              try
              {
                 NFLTeam nflTeam = NFLTeam.valueOf(items[1]);
                 offensivePlayer.setNFLTeam(nflTeam);
              }
              catch (IllegalArgumentException e)
              {
                  offensivePlayer.setNFLTeam(NFLTeam.Unknown);
              }
              offensivePlayer.setPlayerPosition(Position.QUARTER_BACK);
              
              //TODO: Replace with reading into OffensiveStats 
              OffensiveStats offensiveStats = new OffensiveStats();
              
              offensiveStats.setDisplayName(offensivePlayer.getDisplayName());
              offensiveStats.setYear(year);
              offensiveStats.setGames(Double.valueOf(items[2]));
              offensiveStats.setPassingYards(Double.valueOf(items[5]));
              offensiveStats.setPassingTDs(Double.valueOf(items[6]));
              offensiveStats.setRushingYards(Double.valueOf(items[9]));
              offensiveStats.setRushingTDs(Double.valueOf(items[10]));
              offensiveStats.setFumbles(0.0);
              offensiveStats.setInterceptions(Double.valueOf(items[7]));
              
              offensiveStats.calculateFantasyPoints(Position.QUARTER_BACK);
              
              //TODO: Find player in player pool, and set offensive stats to those read
              

              offensivePlayer.setPreviousYearStats(offensiveStats);
              
              OffensivePlayer playerInCollection = (OffensivePlayer)PlayerPositionCollection.Instance().findMatchingPlayer(offensivePlayer, Position.QUARTER_BACK);

              if (playerInCollection != null)
              {
            	  playerInCollection.setPreviousYearStats(offensiveStats);
              }
              
//              System.out.println("Offensive Player: " + offensivePlayer.toString());
          }
          reader.close();
        }
        catch (Exception e)
        {
          System.err.format("Exception occurred trying to read '%s'.", this.qbFile.getAbsolutePath());
          e.printStackTrace();
        }
    }

    @Override
    public void readRBFile()
    {
        // TODO Auto-generated method stub
        System.out.println("PastStatisticsFileReader.readRBFile()");
        try
        {
          System.out.println("Attempting to read file: " + this.rbFile.getAbsolutePath());
          String year = extractYearFromFileName(this.rbFile.getAbsolutePath());
          BufferedReader reader = new BufferedReader(new FileReader(this.rbFile.getAbsolutePath()));
          String REGEX_TAB = "\t";

          Pattern tab_pattern = Pattern.compile(REGEX_TAB);

          String line;
          while ((line = reader.readLine()) != null)
          {
            //TODO: Read and parse each line of file and store in
            //      some sort of player collection
//              System.out.println(line);

              OffensivePlayer offensivePlayer = new OffensivePlayer();
              String[] items = tab_pattern.split(line);
              
              //RB file should contain:
              //0:  First Name Last Name
              //1:  NFL Team
              //2:  Games
              //3:  Rush attempts
              //4:  Rush yards
              //5:  Rush TDs
              //6:  Receptions
              //7:  Receiving yards
              //8:  Receiving TDs
              //9:  Fumbles
              
              processName(items[0], offensivePlayer);
              try
              {
                 NFLTeam nflTeam = NFLTeam.valueOf(items[1]);
                 offensivePlayer.setNFLTeam(nflTeam);
              }
              catch (IllegalArgumentException e)
              {
                  offensivePlayer.setNFLTeam(NFLTeam.Unknown);
              }
              offensivePlayer.setPlayerPosition(Position.RUNNING_BACK);
              
              OffensiveStats offensiveStats = new OffensiveStats();
              
              offensiveStats.setDisplayName(offensivePlayer.getDisplayName());
              offensiveStats.setYear(year);
              offensiveStats.setGames(Double.valueOf(items[2]));
              offensiveStats.setRushingYards(Double.valueOf(items[4]));
              offensiveStats.setRushingTDs(Double.valueOf(items[5]));
              offensiveStats.setReceptions(Double.valueOf(items[6]));
              offensiveStats.setReceivingYards(Double.valueOf(items[7]));
              offensiveStats.setReceivingTDs(Double.valueOf(items[8]));
              offensiveStats.setFumbles(Double.valueOf(items[9]));
              
              offensiveStats.calculateFantasyPoints(Position.RUNNING_BACK);
              
              OffensivePlayer playerInCollection = (OffensivePlayer)PlayerPositionCollection.Instance().findMatchingPlayer(offensivePlayer, Position.RUNNING_BACK);

              if (playerInCollection != null)
              {
                  System.out.println("Setting previous year stats for: " + playerInCollection.getDisplayName());
            	  playerInCollection.setPreviousYearStats(offensiveStats);
              }
//              System.out.println("Offensive Player: " + offensivePlayer.toString());
          }
          reader.close();
        }
        catch (Exception e)
        {
          System.err.format("Exception occurred trying to read '%s'.", this.rbFile.getAbsolutePath());
          e.printStackTrace();
        }
    }

    @Override
    public void readWRFile()
    {
        // TODO Auto-generated method stub
        System.out.println("PastStatisticsFileReader.readWRFile()");

        try
        {
          System.out.println("Attempting to read file: " + this.wrFile.getAbsolutePath());
          String year = extractYearFromFileName(this.wrFile.getAbsolutePath());
          BufferedReader reader = new BufferedReader(new FileReader(this.wrFile.getAbsolutePath()));
          String REGEX_TAB = "\t";

          Pattern tab_pattern = Pattern.compile(REGEX_TAB);

          String line;
          while ((line = reader.readLine()) != null)
          {
            //TODO: Read and parse each line of file and store in
            //      some sort of player collection
//              System.out.println(line);

              OffensivePlayer offensivePlayer = new OffensivePlayer();
              String[] items = tab_pattern.split(line);
              
              //WR file should contain:
              //0:  First Name Last Name
              //1:  NFL Team
              //2:  Games
              //3:  Rush attempts
              //4:  Rush yards
              //5:  Rush TDs
              //6:  Receptions
              //7:  Receiving yards
              //8:  Receiving TDs
              
              processName(items[0], offensivePlayer);
              try
              {
                 NFLTeam nflTeam = NFLTeam.valueOf(items[1]);
                 offensivePlayer.setNFLTeam(nflTeam);
              }
              catch (IllegalArgumentException e)
              {
                  offensivePlayer.setNFLTeam(NFLTeam.Unknown);
              }
              offensivePlayer.setPlayerPosition(Position.WIDE_RECEIVER);
              
              OffensiveStats offensiveStats = new OffensiveStats();
              
              offensiveStats.setDisplayName(offensivePlayer.getDisplayName());
              offensiveStats.setYear(year);
              offensiveStats.setGames(Double.valueOf(items[2]));
              offensiveStats.setRushingYards(Double.valueOf(items[4]));
              offensiveStats.setRushingTDs(Double.valueOf(items[5]));
              offensiveStats.setReceptions(Double.valueOf(items[6]));
              offensiveStats.setReceivingYards(Double.valueOf(items[7]));
              offensiveStats.setReceivingTDs(Double.valueOf(items[8]));
              
              offensiveStats.calculateFantasyPoints(Position.WIDE_RECEIVER);
              
              OffensivePlayer playerInCollection = (OffensivePlayer)PlayerPositionCollection.Instance().findMatchingPlayer(offensivePlayer, Position.WIDE_RECEIVER);

              if (playerInCollection != null)
              {
            	  playerInCollection.setPreviousYearStats(offensiveStats);
              }

//              System.out.println("Offensive Player: " + offensivePlayer.toString());
          }
          reader.close();
        }
        catch (Exception e)
        {
          System.err.format("Exception occurred trying to read '%s'.", this.wrFile.getAbsolutePath());
          e.printStackTrace();
        }

    }

    @Override
    public void readTEFile()
    {
        // TODO Auto-generated method stub
        System.out.println("PastStatisticsFileReader.readTEFile()");
        
        try
        {
          System.out.println("Attempting to read file: " + this.teFile.getAbsolutePath());
          String year = extractYearFromFileName(this.teFile.getAbsolutePath());
          BufferedReader reader = new BufferedReader(new FileReader(this.teFile.getAbsolutePath()));
          String REGEX_TAB = "\t";

          Pattern tab_pattern = Pattern.compile(REGEX_TAB);

          String line;
          while ((line = reader.readLine()) != null)
          {
            //TODO: Read and parse each line of file and store in
            //      some sort of player collection
//              System.out.println(line);

              OffensivePlayer offensivePlayer = new OffensivePlayer();
              String[] items = tab_pattern.split(line);
              
              //TE file should contain:
              //0: First Name Last Name
              //1: NFL Team
              //2: Games
              //3: Receptions
              //4: Receiving yards
              //5: Receiving TDs
              
              processName(items[0], offensivePlayer);
              try
              {
                 NFLTeam nflTeam = NFLTeam.valueOf(items[1]);
                 offensivePlayer.setNFLTeam(nflTeam);
              }
              catch (IllegalArgumentException e)
              {
                  offensivePlayer.setNFLTeam(NFLTeam.Unknown);
              }
              offensivePlayer.setPlayerPosition(Position.TIGHT_END);
              
              OffensiveStats offensiveStats = new OffensiveStats();
              
              offensiveStats.setDisplayName(offensivePlayer.getDisplayName());
              offensiveStats.setYear(year);
              offensiveStats.setGames(Double.valueOf(items[2]));
              offensiveStats.setReceptions(Double.valueOf(items[3]));
              offensiveStats.setReceivingYards(Double.valueOf(items[4]));
              offensiveStats.setReceivingTDs(Double.valueOf(items[5]));
              
              offensiveStats.calculateFantasyPoints(Position.TIGHT_END);
              
              OffensivePlayer playerInCollection = (OffensivePlayer)PlayerPositionCollection.Instance().findMatchingPlayer(offensivePlayer, Position.TIGHT_END);

              if (playerInCollection != null)
              {
                  playerInCollection.setPreviousYearStats(offensiveStats);
              }


//              System.out.println("Offensive Player: " + offensivePlayer.toString());
          }
          reader.close();
        }
        catch (Exception e)
        {
          System.err.format("Exception occurred trying to read '%s'.", this.teFile.getAbsolutePath());
          e.printStackTrace();
        }

    }

    @Override
    public void readKFile()
    {
        // TODO Auto-generated method stub
        System.out.println("PastStatisticsFileReader.readKFile()");
        
        try
        {
          System.out.println("Attempting to read file: " + this.kFile.getAbsolutePath());
          String year = extractYearFromFileName(this.kFile.getAbsolutePath());
          BufferedReader reader = new BufferedReader(new FileReader(this.kFile.getAbsolutePath()));
          String REGEX_TAB = "\t";

          Pattern tab_pattern = Pattern.compile(REGEX_TAB);

          String line;
          while ((line = reader.readLine()) != null)
          {
            //TODO: Read and parse each line of file and store in
            //      some sort of player collection
//              System.out.println(line);

              OffensivePlayer offensivePlayer = new OffensivePlayer();
              String[] items = tab_pattern.split(line);
              
              //K file should contain:
              //0: First Name Last Name
              //1: NFL Team
              //2: Games
              //3: Field Goals made
              //4: Extra Points made
              
              processName(items[0], offensivePlayer);
              try
              {
                 NFLTeam nflTeam = NFLTeam.valueOf(items[1]);
                 offensivePlayer.setNFLTeam(nflTeam);
              }
              catch (IllegalArgumentException e)
              {
                  offensivePlayer.setNFLTeam(NFLTeam.Unknown);
              }
              offensivePlayer.setPlayerPosition(Position.KICKER);
              
              OffensiveStats offensiveStats = new OffensiveStats();
              
              offensiveStats.setDisplayName(offensivePlayer.getDisplayName());
              offensiveStats.setYear(year);
              offensiveStats.setGames(Double.valueOf(items[2]));
              offensiveStats.setFieldGoals(Double.valueOf(items[3]));
              offensiveStats.setExtraPoints(Double.valueOf(items[4]));
              
              offensiveStats.calculateFantasyPoints(Position.KICKER);
              
              OffensivePlayer playerInCollection = (OffensivePlayer)PlayerPositionCollection.Instance().findMatchingPlayer(offensivePlayer, Position.KICKER);

              if (playerInCollection != null)
              {
                  playerInCollection.setPreviousYearStats(offensiveStats);
              }

//              System.out.println("Offensive Player: " + offensivePlayer.toString());
          }
          reader.close();
        }
        catch (Exception e)
        {
          System.err.format("Exception occurred trying to read '%s'.", this.kFile.getAbsolutePath());
          e.printStackTrace();
        }

    }

    @Override
    public void readTeamDefenseFile()
    {
        // TODO Auto-generated method stub
        System.out.println("PastStatisticsFileReader.readTeamDefenseFile()");

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(this.teamDefenseFile.getAbsolutePath()));
            String year = extractYearFromFileName(this.teamDefenseFile.getAbsolutePath());
            String REGEX_TAB = "\t";
    
            Pattern tab_pattern = Pattern.compile(REGEX_TAB);
            
            String line;
            while ((line = reader.readLine()) != null)
            {
    
                TeamDefense teamDefense = new TeamDefense();
                String[] items = tab_pattern.split(line);
        
                //Team defense file has the following format:
                // 0: NFL Team
                // 1: Games
                // 2: Sack
                // 3: Fumbles
                // 4: Ints
                // 5: Def TDs
                // 6: PtsAllow
                // 7: YdsAllow
                // 8: Safeties
                // 9: Kick Return TDs

                try
                {
                   NFLTeam nflTeam = NFLTeam.valueOf(items[0]);
                   teamDefense.setNFLTeam(nflTeam);
                   teamDefense.setTeamDefenseName(nflTeam);
                   
                }
                catch (IllegalArgumentException e)
                {
                    teamDefense.setNFLTeam(NFLTeam.Unknown);
                    teamDefense.setTeamDefenseName(NFLTeam.Unknown);
                    System.out.println("Uh oh - could not parse NFL team for a team defense - " + items[0]);
                }
                
                teamDefense.setPlayerPosition(Position.TEAM_DEFENSE);

                TeamDefenseStats teamDefenseStats = new TeamDefenseStats();
                
                teamDefenseStats.setDisplayName(teamDefense.getDisplayName());
                teamDefenseStats.setYear(year);
                teamDefenseStats.setGames(Double.valueOf(items[1]));
                teamDefenseStats.setSacks(Double.valueOf(items[2]));
                teamDefenseStats.setFumbleRecoveries(Double.valueOf(items[3]));
                teamDefenseStats.setInterceptions(Double.valueOf(items[4]));
                teamDefenseStats.setTDs(Double.valueOf(items[5]));
                teamDefenseStats.setPointsAllowed(Double.valueOf(items[6]));
                teamDefenseStats.setYardsAllowed(Double.valueOf(items[7]));
                
                teamDefenseStats.calculateFantasyPoints();
                
                TeamDefense playerInCollection = (TeamDefense)PlayerPositionCollection.Instance().findMatchingPlayer(teamDefense, Position.TEAM_DEFENSE);

                if (playerInCollection != null)
                {
                    playerInCollection.setPreviousYearStats(teamDefenseStats);
                }
                else
                {
                    System.out.println("Not setting previous years stats since player " + teamDefense.getDisplayName() + " could not be found");
                }

//                System.out.println("Team Defense: " + teamDefense.toString());
//                PlayerPositionCollection.Instance().addPlayerToPosition(teamDefense, Position.TEAM_DEFENSE);
            }
            reader.close();
        }
        catch (Exception e)
        {
          System.err.format("Exception occurred trying to read '%s'.", this.teamDefenseFile.getAbsolutePath());
          e.printStackTrace();
        }
        
        
    }

    @Override
    public void readIDPFile()
    {
        // TODO Auto-generated method stub
        System.out.println("PastStatisticsFileReader.readIDPFile()");

        try
        {
          System.out.println("Attempting to read file: " + this.idpFile.getAbsolutePath());
          String year = extractYearFromFileName(this.idpFile.getAbsolutePath());
          BufferedReader reader = new BufferedReader(new FileReader(this.idpFile.getAbsolutePath()));
          String REGEX_TAB = "\t";

          Pattern tab_pattern = Pattern.compile(REGEX_TAB);

          String line;
          while ((line = reader.readLine()) != null)
          {
            //TODO: Read and parse each line of file and store in
            //      some sort of player collection
//              System.out.println(line);

              IndividualDefensivePlayer idp = new IndividualDefensivePlayer();
              String[] items = tab_pattern.split(line);
              
              //IDP File should contain:
              //0:  Name
              //1:  Team
              //2:  Games
              //3:  Tackles
              //4:  Assists
              //5:  Sacks
              //6:  Fumbles Recovered
              //7:  Interceptions
              //8:  TDs

              
              processName(items[0], idp);
              try
              {
                 NFLTeam nflTeam = NFLTeam.valueOf(items[1]);
                 idp.setNFLTeam(nflTeam);
              }
              catch (IllegalArgumentException e)
              {
                  idp.setNFLTeam(NFLTeam.Unknown);
              }
              idp.setPlayerPosition(Position.INDIVIDUAL_DEFENSIVE_PLAYER);
              
              IDPStats idpStats = new IDPStats();
              
              idpStats.setDisplayName(idp.getDisplayName());
              idpStats.setYear(year);
              idpStats.setGames(Double.valueOf(items[2]));
              idpStats.setTackles(Double.valueOf(items[3]));
              idpStats.setAssists(Double.valueOf(items[4]));
              idpStats.setSacks(Double.valueOf(items[5]));
              idpStats.setFumbleRecoveries(Double.valueOf(items[6]));
              idpStats.setInterceptions(Double.valueOf(items[7]));
              idpStats.setTDs(Double.valueOf(items[8]));
              
              idpStats.calculateFantasyPoints();
              
              IndividualDefensivePlayer playerInCollection = (IndividualDefensivePlayer)PlayerPositionCollection.Instance().findMatchingPlayer(idp, Position.INDIVIDUAL_DEFENSIVE_PLAYER);

              if (playerInCollection != null)
              {
                  playerInCollection.setPreviousYearStats(idpStats);
              }
              else
              {
//                  System.out.println("Could not find Individual Defensive Player in collection: "+ idp.toString());
              }

//              System.out.println("Individual Defensive Player: " + idp.toString());
          }
          reader.close();
        }
        catch (Exception e)
        {
          System.err.format("Exception occurred trying to read '%s'.", this.idpFile.getAbsolutePath());
          e.printStackTrace();
        }

    }

    
}
