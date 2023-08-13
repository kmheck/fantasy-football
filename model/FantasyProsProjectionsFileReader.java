package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;

public class FantasyProsProjectionsFileReader extends StatisticsFileReader
{



    public FantasyProsProjectionsFileReader(String projectionsFileDirectory)
    {
        super();
        this.statisticsFileDirectory = projectionsFileDirectory;
        System.out.println("Constructing FantasyProsProjectionsFileReader with directory: " + this.statisticsFileDirectory);
        assignStatisticFiles(ConfigurationData.Instance().getCurrentYear());
        readStatisticFiles();
        // After projection files are read, calculate diff values for
        // each position.
        for (Position position : Position.values())
        {
            if (position != Position.UNKNOWN && position != Position.FLEX)
            {
                if (PlayerPositionCollection.Instance().getPositionCollection(position) != null)
                {
                    PlayerPositionCollection.Instance().sort(position);
                    PlayerPositionCollection.Instance().calculateMean(position);
                    PlayerPositionCollection.Instance().calculateDiffValues(position);
                }
            }
        }
        AllPlayerCollection.Instance().assemble();
        AllPlayerCollection.Instance().calculateRelativeDiffValues();
    }

    @Override
    public void readQBFile()
    {
        System.out.println("FantasyProsProjectionsFileReader.readQBFile()");
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

              //QB file should contain:
              //0:  First Name Last Name
              //1:  NFL Team
              //2:  Pass attempts
              //3:  Pass completions
              //4:  Pass yards
              //5:  Pass TDs
              //6:  Interceptions
              //7:  Rush attempts
              //8:  Rush yards
              //9:  Rush TDs
              //10: Fumbles
              //11: Fantasy points

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

              StatisticalProjections projections = new StatisticalProjections();
              projections.setCategoryProjection(ScoringCategory.PASSING_YARDS, Double.valueOf(items[4]));
              projections.setCategoryProjection(ScoringCategory.PASSING_TDS, Double.valueOf(items[5]));
              projections.setCategoryProjection(ScoringCategory.INTERCEPTIONS, Double.valueOf(items[6]));
              projections.setCategoryProjection(ScoringCategory.RUSHING_YARDS, Double.valueOf(items[8]));
              projections.setCategoryProjection(ScoringCategory.RUSHING_TDS, Double.valueOf(items[9]));
              projections.setCategoryProjection(ScoringCategory.FUMBLES, Double.valueOf(items[10]));


              offensivePlayer.setProjections(projections);

              // Also set OffensiveStats for display purposes
              OffensiveStats projectedStats = new OffensiveStats();
              projectedStats.setDisplayName(offensivePlayer.getDisplayName());
              projectedStats.setYear(year);
              //Projections don't contain games unfortunately
              projectedStats.setGames(17);
              projectedStats.setPassingYards(Double.valueOf(items[4]));
              projectedStats.setPassingTDs(Double.valueOf(items[5]));
              projectedStats.setInterceptions(Double.valueOf(items[6]));
              projectedStats.setRushingYards(Double.valueOf(items[8]));
              projectedStats.setRushingTDs(Double.valueOf(items[9]));
              projectedStats.setFumbles(Double.valueOf(items[8]));

              projectedStats.calculateFantasyPoints(Position.QUARTER_BACK);
              offensivePlayer.setProjectedStats(projectedStats);

              PlayerPositionCollection.Instance().addPlayerToPosition(offensivePlayer, Position.QUARTER_BACK);

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
        System.out.println("FantasyProsProjectionsFileReader.readRBFile()");
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
              //2:  Rush attempts
              //3:  Rush yards
              //4:  Rush TDs
              //5:  Receptions
              //6:  Receiving yards
              //7:  Receiving TDs
              //8: Fumbles
              //9: Fantasy points

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

              StatisticalProjections projections = new StatisticalProjections();
              projections.setCategoryProjection(ScoringCategory.RUSHING_YARDS, Double.valueOf(items[3]));
              projections.setCategoryProjection(ScoringCategory.RUSHING_TDS, Double.valueOf(items[4]));
              projections.setCategoryProjection(ScoringCategory.RECEPTIONS, Double.valueOf(items[5]));
              projections.setCategoryProjection(ScoringCategory.RECEIVING_YARDS, Double.valueOf(items[6]));
              projections.setCategoryProjection(ScoringCategory.RECEIVING_TDS, Double.valueOf(items[7]));
              projections.setCategoryProjection(ScoringCategory.FUMBLES, Double.valueOf(items[8]));


              offensivePlayer.setProjections(projections);
              // Also set OffensiveStats for display purposes
              OffensiveStats projectedStats = new OffensiveStats();
              projectedStats.setDisplayName(offensivePlayer.getDisplayName());
              projectedStats.setYear(year);
              //Projections don't contain games unfortunately
              projectedStats.setGames(17);
              projectedStats.setRushingYards(Double.valueOf(items[3]));
              projectedStats.setRushingTDs(Double.valueOf(items[4]));
              projectedStats.setReceptions(Double.valueOf(items[5]));
              projectedStats.setReceivingYards(Double.valueOf(items[6]));
              projectedStats.setReceivingTDs(Double.valueOf(items[7]));
              projectedStats.setFumbles(Double.valueOf(items[8]));

              projectedStats.calculateFantasyPoints(Position.RUNNING_BACK);
              offensivePlayer.setProjectedStats(projectedStats);


              PlayerPositionCollection.Instance().addPlayerToPosition(offensivePlayer, Position.RUNNING_BACK);

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
//        System.out.println("FantasyProsProjectionsFileReader.readWRFile()");

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
              //2:  Receptions
              //3:  Receiving yards
              //4:  Receiving TDs
              //5:  Rush attempts
              //6:  Rush yards
              //7:  Rush TDs
              //8: Fumbles
              //9: Fantasy points

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

              StatisticalProjections projections = new StatisticalProjections();
              projections.setCategoryProjection(ScoringCategory.RUSHING_YARDS, Double.valueOf(items[6]));
              projections.setCategoryProjection(ScoringCategory.RUSHING_TDS, Double.valueOf(items[7]));
              projections.setCategoryProjection(ScoringCategory.RECEPTIONS, Double.valueOf(items[2]));
              projections.setCategoryProjection(ScoringCategory.RECEIVING_YARDS, Double.valueOf(items[3]));
              projections.setCategoryProjection(ScoringCategory.RECEIVING_TDS, Double.valueOf(items[4]));
              projections.setCategoryProjection(ScoringCategory.FUMBLES, Double.valueOf(items[8]));

              offensivePlayer.setProjections(projections);

              // Also set OffensiveStats for display purposes
              OffensiveStats projectedStats = new OffensiveStats();
              projectedStats.setDisplayName(offensivePlayer.getDisplayName());
              projectedStats.setYear(year);
              //Projections don't contain games unfortunately
              projectedStats.setGames(17);
              projectedStats.setRushingYards(Double.valueOf(items[6]));
              projectedStats.setRushingTDs(Double.valueOf(items[7]));
              projectedStats.setReceptions(Double.valueOf(items[2]));
              projectedStats.setReceivingYards(Double.valueOf(items[3]));
              projectedStats.setReceivingTDs(Double.valueOf(items[4]));
              projectedStats.setFumbles(Double.valueOf(items[8]));

              projectedStats.calculateFantasyPoints(Position.WIDE_RECEIVER);
              offensivePlayer.setProjectedStats(projectedStats);

              PlayerPositionCollection.Instance().addPlayerToPosition(offensivePlayer, Position.WIDE_RECEIVER);

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
        System.out.println("FantasyProsProjectionsFileReader.readTEFile()");

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
              //2: Receptions
              //3: Receiving yards
              //4: Receiving TDs
              //5: Fumbles
              //6: Fantasy points

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

              StatisticalProjections projections = new StatisticalProjections();
              projections.setCategoryProjection(ScoringCategory.RECEPTIONS, Double.valueOf(items[2]));
              projections.setCategoryProjection(ScoringCategory.RECEIVING_YARDS, Double.valueOf(items[3]));
              projections.setCategoryProjection(ScoringCategory.RECEIVING_TDS, Double.valueOf(items[4]));
              projections.setCategoryProjection(ScoringCategory.FUMBLES, Double.valueOf(items[5]));

              offensivePlayer.setProjections(projections);

              // Also set OffensiveStats for display purposes
              OffensiveStats projectedStats = new OffensiveStats();
              projectedStats.setDisplayName(offensivePlayer.getDisplayName());
              projectedStats.setYear(year);
              //Projections don't contain games unfortunately
              projectedStats.setGames(17);
              projectedStats.setReceptions(Double.valueOf(items[2]));
              projectedStats.setReceivingYards(Double.valueOf(items[3]));
              projectedStats.setReceivingTDs(Double.valueOf(items[4]));
              projectedStats.setFumbles(Double.valueOf(items[5]));

              projectedStats.calculateFantasyPoints(Position.TIGHT_END);
              offensivePlayer.setProjectedStats(projectedStats);

              PlayerPositionCollection.Instance().addPlayerToPosition(offensivePlayer, Position.TIGHT_END);

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
        System.out.println("FantasyProsProjectionsFileReader.readKFile()");

        try
        {
          System.out.println("Attempting to read file: " + this.kFile.getAbsolutePath());
          String year = extractYearFromFileName(this.teFile.getAbsolutePath());
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
              //2: Field Goals made
              //3: Field Goals attempted
              //4: Extra Points made
              //5: Fantasy points

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

              StatisticalProjections projections = new StatisticalProjections();
              projections.setCategoryProjection(ScoringCategory.FIELD_GOALS, Double.valueOf(items[2]));
              projections.setCategoryProjection(ScoringCategory.EXTRA_POINTS, Double.valueOf(items[4]));

              offensivePlayer.setProjections(projections);

              // Also set OffensiveStats for display purposes
              OffensiveStats projectedStats = new OffensiveStats();
              projectedStats.setDisplayName(offensivePlayer.getDisplayName());
              projectedStats.setYear(year);
              //Projections don't contain games unfortunately
              projectedStats.setGames(17);
              projectedStats.setFieldGoals(Double.valueOf(items[2]));
              projectedStats.setExtraPoints(Double.valueOf(items[4]));

              projectedStats.calculateFantasyPoints(Position.KICKER);
              offensivePlayer.setProjectedStats(projectedStats);

              PlayerPositionCollection.Instance().addPlayerToPosition(offensivePlayer, Position.KICKER);

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
        System.out.println("FantasyProsProjectionsFileReader.readTeamDefenseFile()");

        try
        {
            System.out.println("Attempting to read file: " + this.teamDefenseFile.getAbsolutePath());
            String year = extractYearFromFileName(this.teamDefenseFile.getAbsolutePath());
            BufferedReader reader = new BufferedReader(new FileReader(this.teamDefenseFile.getAbsolutePath()));
            String REGEX_TAB = "\t";

            Pattern tab_pattern = Pattern.compile(REGEX_TAB);

            String line;
            while ((line = reader.readLine()) != null)
            {

                TeamDefense teamDefense = new TeamDefense();
                String[] items = tab_pattern.split(line);

                //Team defense file (fantasypros) has the following format:
                // 0: NFL Team
                // 1: Sack
                // 2: Ints
                // 3: Fumbles Recovered
                // 4: Forced Fumbles
                // 5: TDs
                // 6: Safeties
                // 7: PtsAllowed
                // 8: YdsAllowed
                // 9: FantasyPts
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

                StatisticalProjections projections = new StatisticalProjections();
                projections.setCategoryProjection(ScoringCategory.RETURNS_FOR_TD, Double.valueOf(items[5]));
                projections.setCategoryProjection(ScoringCategory.SACKS, Double.valueOf(items[1]));
                projections.setCategoryProjection(ScoringCategory.INTERCEPTIONS, Double.valueOf(items[2]));
                projections.setCategoryProjection(ScoringCategory.FUMBLES, Double.valueOf(items[3]));
                projections.setCategoryProjection(ScoringCategory.POINTS_ALLOWED, Double.valueOf(items[7]));
                projections.setCategoryProjection(ScoringCategory.YARDS_ALLOWED, Double.valueOf(items[8]));

                teamDefense.setProjections(projections);

                // Also set projected stats for display purposes
                TeamDefenseStats projectedStats = new TeamDefenseStats();

                projectedStats.setDisplayName(teamDefense.getDisplayName());
                projectedStats.setYear(year);
                projectedStats.setGames(17.0);
                projectedStats.setSacks(Double.valueOf(items[1]));
                projectedStats.setFumbleRecoveries(Double.valueOf(items[3]));
                projectedStats.setInterceptions(Double.valueOf(items[2]));
                projectedStats.setTDs(Double.valueOf(items[5]));
                projectedStats.setPointsAllowed(Double.valueOf(items[7]));
                projectedStats.setYardsAllowed(Double.valueOf(items[8]));

                projectedStats.calculateFantasyPoints();

                teamDefense.setProjectedStats(projectedStats);

//                System.out.println("Team Defense: " + teamDefense.toString());
                PlayerPositionCollection.Instance().addPlayerToPosition(teamDefense, Position.TEAM_DEFENSE);
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
    public void readIDPFile()
    {
        // TODO Auto-generated method stub
        System.out.println("FantasyProsProjectionsFileReader.readIDPFile()");

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

              //IDP File (fftoday) - (fantasysharks not accurate) should contain:
              //0:  Name
              //1:  Team
              //2:  Bye week
              //3:  Tackles
              //4:  Assists
              //5:  Sacks
              //6:  Passes Defensed
              //7:  Interceptions
              //8:  Forced Fumbles
              //9:  Fumble Recoveries
              //10: TDs (file doesn't contain - set to 0)

              processName(items[0], idp);
              try
              {
                 NFLTeam nflTeam = NFLTeam.valueOf(items[2]);
                 idp.setNFLTeam(nflTeam);
              }
              catch (IllegalArgumentException e)
              {
                  idp.setNFLTeam(NFLTeam.Unknown);
              }
              idp.setPlayerPosition(Position.INDIVIDUAL_DEFENSIVE_PLAYER);

              StatisticalProjections projections = new StatisticalProjections();
              projections.setCategoryProjection(ScoringCategory.SACKS, Double.valueOf(items[5]));
              projections.setCategoryProjection(ScoringCategory.FUMBLES, Double.valueOf(items[9]));
              projections.setCategoryProjection(ScoringCategory.INTERCEPTIONS, Double.valueOf(items[7]));
              projections.setCategoryProjection(ScoringCategory.TACKLES, Double.valueOf(items[3]));
              projections.setCategoryProjection(ScoringCategory.ASSISTS, Double.valueOf(items[4]));
//              projections.setCategoryProjection(ScoringCategory.RETURNS_FOR_TD, Double.valueOf(items[10]));
              projections.setCategoryProjection(ScoringCategory.RETURNS_FOR_TD, 0.0);


              idp.setProjections(projections);

              // Also set projected stats for display purposes
              IDPStats idpProjectedStats = new IDPStats();

              idpProjectedStats.setDisplayName(idp.getDisplayName());
              idpProjectedStats.setYear(year);
              idpProjectedStats.setGames(17.0);
              idpProjectedStats.setTackles(Double.valueOf(items[3]));
              idpProjectedStats.setAssists(Double.valueOf(items[4]));
              idpProjectedStats.setSacks(Double.valueOf(items[5]));
              idpProjectedStats.setFumbleRecoveries(Double.valueOf(items[9]));
              idpProjectedStats.setInterceptions(Double.valueOf(items[7]));
              idpProjectedStats.setTDs(0.0);

              idpProjectedStats.calculateFantasyPoints();

              idp.setProjectedStats(idpProjectedStats);

              PlayerPositionCollection.Instance().addPlayerToPosition(idp, Position.INDIVIDUAL_DEFENSIVE_PLAYER);

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