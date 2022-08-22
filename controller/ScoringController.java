package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.EnumMap;


import model.ConfigurationData;
import model.LeagueScoring;
import model.LeagueScoringParameters;
import model.Position;

import model.ScoringCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import com.google.gson.Gson;

public class ScoringController
{

    @FXML private TextField points_per_rushing_yard_text_box;
    @FXML private TextField points_per_receiving_yard_text_box;
    @FXML private TextField points_per_rushing_td_text_box;
    @FXML private TextField points_per_receiving_td_text_box;
    @FXML private TextField points_per_passing_yard_text_box;
    @FXML private TextField points_per_passing_td_text_box;
    @FXML private TextField points_per_interception_text_box;
    @FXML private TextField points_per_fumble_text_box;
    @FXML private TextField points_per_reception_text_box;
    @FXML private TextField points_per_field_goal_text_box;
    @FXML private TextField points_per_extra_point_text_box;
    @FXML private TextField points_per_sack_text_box;
    @FXML private TextField points_per_team_defense_interception_text_box;
    @FXML private TextField points_per_team_defense_fumble_recovery_text_box;
    @FXML private TextField points_per_team_defense_td_text_box;
    @FXML private TextField team_defense_yards_allowed_cutoff_text_box;
    @FXML private TextField team_defense_points_per_yards_allowed_text_box;
    @FXML private TextField team_defense_points_allowed_cutoff_text_box;
    @FXML private TextField team_defense_points_per_points_allowed_text_box;
    @FXML private TextField points_per_idp_interception_text_box;
    @FXML private TextField points_per_idp_td_text_box;
    @FXML private TextField points_per_idp_fumble_recovery_text_box;
    @FXML private TextField points_per_idp_sack_text_box;
    @FXML private TextField points_per_idp_tackle_text_box;
    @FXML private TextField points_per_idp_assist_text_box;
    
    @FXML private TextField selectedLeagueTextBox;
    @FXML private TextArea usageTextArea;
    @FXML private Label usageLabel;
    
    @FXML private Button usageCloseButton;
    
    @FXML private TextField yearTextBox;
    
    public ScoringController()
    {
        super();
        System.out.println("ScoringController::ScoringController()");
        if (LeagueScoring.Instance().getScoringParameters() != null)
        {
            fillTextBoxesWithLeagueScoringParameters(LeagueScoring.Instance().getScoringParameters());
        }
        // TODO If 
    }

    
    @FXML
    private void leagueScoringButtonAction(ActionEvent event)
    {
        
        //TODO: Modify League class in model with values in textboxes.
        System.out.println("League Scoring Button Pressed");
        setRunningbackScoring();
        setQuarterbackScoring();
        setWidereceiverScoring();
        setTightendScoring();
        setKickerScoring();
        setTeamDefenseScoring();
        setIndividualDefensivePlayerScoring();
        
    }
    
    @FXML
    private void djcButtonAction(ActionEvent event)
    {
        System.out.println("DJC Button Pressed");
        Gson gson = new Gson();
        //TODO: Read in data/league_scoring_djc.txt file, convert JSON
        //      to values to set league scoring for each position.
        //      Also fill in text boxes with proper values for DJC league.
        String fileName = "src/data/league_scoring_djc.txt";
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //TODO: Somehow read the file in with JSON methods
            LeagueScoringParameters DJCScoringParameters = gson.fromJson(bufferedReader, LeagueScoringParameters.class);
            System.out.println("DJC Scoring Parameters: " + DJCScoringParameters.toString());
            fillTextBoxesWithLeagueScoringParameters(DJCScoringParameters);
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

    @FXML
    private void bigDaddyButtonAction(ActionEvent event)
    {
        System.out.println("Big Daddy Button Pressed");
        
        Gson gson = new Gson();
        //TODO: Read in data/league_scoring_big_daddy.txt file, convert JSON
        //      to values to set league scoring for each position.
        //      Also fill in text boxes with proper values for Big Daddy league.
//        String fileName = "C:/eclipse/eclipse_workspace/fantasy_football/src/data";
        String fileName = "src/data/league_scoring_big_daddy.txt";
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            LeagueScoringParameters bigDaddyScoringParameters = gson.fromJson(bufferedReader, LeagueScoringParameters.class);
            System.out.println("Big Daddy Scoring Parameters: " + bigDaddyScoringParameters.toString());
            fillTextBoxesWithLeagueScoringParameters(bigDaddyScoringParameters);
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

    private void setRunningbackScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();
        
        String ppRushYard = points_per_rushing_yard_text_box.getText();
        points_per_category.put(ScoringCategory.RUSHING_YARDS, Double.valueOf(ppRushYard));

        String ppRushTD = points_per_rushing_td_text_box.getText();
        points_per_category.put(ScoringCategory.RUSHING_TDS, Double.valueOf(ppRushTD));

        String ppRecvTD = points_per_receiving_td_text_box.getText();
        points_per_category.put(ScoringCategory.RECEIVING_TDS, Double.valueOf(ppRecvTD));

        String ppRecvYard = points_per_receiving_yard_text_box.getText();
        points_per_category.put(ScoringCategory.RECEIVING_YARDS, Double.valueOf(ppRecvYard));

        String ppReception = points_per_reception_text_box.getText();
        points_per_category.put(ScoringCategory.RECEPTIONS, Double.valueOf(ppReception));

        LeagueScoring.Instance().setScoring(Position.RUNNING_BACK, points_per_category);
        
        System.out.println("Runningback scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = LeagueScoring.Instance().getScoring(Position.RUNNING_BACK, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();
        
        
    }

    private void setQuarterbackScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();
        
        String ppRushYard = points_per_rushing_yard_text_box.getText();
        points_per_category.put(ScoringCategory.RUSHING_YARDS, Double.valueOf(ppRushYard));

        String ppRushTD = points_per_rushing_td_text_box.getText();
        points_per_category.put(ScoringCategory.RUSHING_TDS, Double.valueOf(ppRushTD));

        String ppPassTD = points_per_passing_td_text_box.getText();
        points_per_category.put(ScoringCategory.PASSING_TDS, Double.valueOf(ppPassTD));

        String ppPassYard = points_per_passing_yard_text_box.getText();
        points_per_category.put(ScoringCategory.PASSING_YARDS, Double.valueOf(ppPassYard));

        String ppInt = points_per_interception_text_box.getText();
        points_per_category.put(ScoringCategory.INTERCEPTIONS, Double.valueOf(ppInt));

        String ppFumble = points_per_fumble_text_box.getText();
        points_per_category.put(ScoringCategory.FUMBLES, Double.valueOf(ppFumble));
        
        LeagueScoring.Instance().setScoring(Position.QUARTER_BACK, points_per_category);
        
        System.out.println("Quarterback scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = LeagueScoring.Instance().getScoring(Position.QUARTER_BACK, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();
        
        
    }
    
    private void setWidereceiverScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();
        
        String ppRushYard = points_per_rushing_yard_text_box.getText();
        points_per_category.put(ScoringCategory.RUSHING_YARDS, Double.valueOf(ppRushYard));

        String ppRushTD = points_per_rushing_td_text_box.getText();
        points_per_category.put(ScoringCategory.RUSHING_TDS, Double.valueOf(ppRushTD));

        String ppRecvTD = points_per_receiving_td_text_box.getText();
        points_per_category.put(ScoringCategory.RECEIVING_TDS, Double.valueOf(ppRecvTD));

        String ppRecvYard = points_per_receiving_yard_text_box.getText();
        points_per_category.put(ScoringCategory.RECEIVING_YARDS, Double.valueOf(ppRecvYard));

        String ppReception = points_per_reception_text_box.getText();
        points_per_category.put(ScoringCategory.RECEPTIONS, Double.valueOf(ppReception));
        
        LeagueScoring.Instance().setScoring(Position.WIDE_RECEIVER, points_per_category);
        
        System.out.println("Widereceiver scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = LeagueScoring.Instance().getScoring(Position.WIDE_RECEIVER, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();
        
        
    }

    private void setTightendScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();
        
        String ppRecvTD = points_per_receiving_td_text_box.getText();
        points_per_category.put(ScoringCategory.RECEIVING_TDS, Double.valueOf(ppRecvTD));

        String ppRecvYard = points_per_receiving_yard_text_box.getText();
        points_per_category.put(ScoringCategory.RECEIVING_YARDS, Double.valueOf(ppRecvYard));

        String ppReception = points_per_reception_text_box.getText();
        points_per_category.put(ScoringCategory.RECEPTIONS, Double.valueOf(ppReception));
        
        LeagueScoring.Instance().setScoring(Position.TIGHT_END, points_per_category);
        
        System.out.println("Tightend scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = LeagueScoring.Instance().getScoring(Position.TIGHT_END, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();
        
        
    }

    private void setKickerScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();
        
        String ppFieldGoal = points_per_field_goal_text_box.getText();
        points_per_category.put(ScoringCategory.FIELD_GOALS, Double.valueOf(ppFieldGoal));

        String ppExtraPoint = points_per_extra_point_text_box.getText();
        points_per_category.put(ScoringCategory.EXTRA_POINTS, Double.valueOf(ppExtraPoint));

        
        LeagueScoring.Instance().setScoring(Position.KICKER, points_per_category);
        
        System.out.println("Kicker scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = LeagueScoring.Instance().getScoring(Position.KICKER, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();
        
        
    }

    private void setTeamDefenseScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();
        
        String ppSack = points_per_sack_text_box.getText();
        points_per_category.put(ScoringCategory.SACKS, Double.valueOf(ppSack));

        String ppInterception = points_per_team_defense_interception_text_box.getText();
        points_per_category.put(ScoringCategory.INTERCEPTIONS, Double.valueOf(ppInterception));

        String ppFumbleRecovery = points_per_team_defense_fumble_recovery_text_box.getText();
        points_per_category.put(ScoringCategory.FUMBLES, Double.valueOf(ppFumbleRecovery));

        String ppTD = points_per_team_defense_td_text_box.getText();
        points_per_category.put(ScoringCategory.RETURNS_FOR_TD, Double.valueOf(ppTD));

        String yardsAllowedCutoff = team_defense_yards_allowed_cutoff_text_box.getText();
        points_per_category.put(ScoringCategory.YARDS_ALLOWED_CUTOFF, Double.valueOf(yardsAllowedCutoff));

        String ppYardsAllowed = team_defense_points_per_yards_allowed_text_box.getText();
        points_per_category.put(ScoringCategory.POINTS_PER_YARDS_ALLOWED, Double.valueOf(ppYardsAllowed));

        String pointsAllowedCutoff = team_defense_points_allowed_cutoff_text_box.getText();
        points_per_category.put(ScoringCategory.POINTS_ALLOWED_CUTOFF, Double.valueOf(pointsAllowedCutoff));

        String ppPointsAllowed = team_defense_points_per_points_allowed_text_box.getText();
        points_per_category.put(ScoringCategory.POINTS_PER_POINTS_ALLOWED, Double.valueOf(ppPointsAllowed));

        LeagueScoring.Instance().setScoring(Position.TEAM_DEFENSE, points_per_category);
        
        System.out.println("Team Defense scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = LeagueScoring.Instance().getScoring(Position.TEAM_DEFENSE, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();
        
        
    }
    
    private void setIndividualDefensivePlayerScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();
        
        String ppSack = points_per_idp_sack_text_box.getText();
        points_per_category.put(ScoringCategory.SACKS, Double.valueOf(ppSack));

        String ppInterception = points_per_idp_interception_text_box.getText();
        points_per_category.put(ScoringCategory.INTERCEPTIONS, Double.valueOf(ppInterception));

        String ppFumbleRecovery = points_per_idp_fumble_recovery_text_box.getText();
        points_per_category.put(ScoringCategory.FUMBLES, Double.valueOf(ppFumbleRecovery));

        String ppTD = points_per_idp_td_text_box.getText();
        points_per_category.put(ScoringCategory.RETURNS_FOR_TD, Double.valueOf(ppTD));

        String ppTackle = points_per_idp_tackle_text_box.getText();
        points_per_category.put(ScoringCategory.TACKLES, Double.valueOf(ppTackle));
        
        String ppAssist = points_per_idp_assist_text_box.getText();
        points_per_category.put(ScoringCategory.ASSISTS, Double.valueOf(ppAssist));

        LeagueScoring.Instance().setScoring(Position.INDIVIDUAL_DEFENSIVE_PLAYER, points_per_category);
        
        System.out.println("Individual Defensive Player scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = LeagueScoring.Instance().getScoring(Position.INDIVIDUAL_DEFENSIVE_PLAYER, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();

    }
    
    private void setYear()
    {
        LocalDate currentDate = java.time.LocalDate.now();
        System.out.println("ScoringController.setYear - currentDate: " + currentDate.toString());
        ConfigurationData.Instance().setCurrentYear(currentDate.getYear());
        yearTextBox.setText(Integer.toString(ConfigurationData.Instance().getCurrentYear()));
    }
    
    public void fillTextBoxesWithLeagueScoringParameters(LeagueScoringParameters leagueScoringParameters)
    {
        System.out.println("fillTextBoxesWithLeagueScoringParameters() - enter");
        if (leagueScoringParameters !=  null)
        {
             points_per_rushing_yard_text_box.setText(Double.toString(leagueScoringParameters.getOffensePointsPerRushingYard()));
             points_per_receiving_yard_text_box.setText(Double.toString(leagueScoringParameters.getOffensePointsPerReceivingYard()));
             points_per_rushing_td_text_box.setText(Double.toString(leagueScoringParameters.getOffensePointsPerRushingTD()));
             points_per_receiving_td_text_box.setText(Double.toString(leagueScoringParameters.getOffensePointsPerReceivingTD()));
             points_per_passing_yard_text_box.setText(Double.toString(leagueScoringParameters.getOffensePointsPerPassingYard()));
             points_per_passing_td_text_box.setText(Double.toString(leagueScoringParameters.getOffensePointsPerPassingTD()));
             points_per_interception_text_box.setText(Double.toString(leagueScoringParameters.getOffensePointsPerInterception()));
             points_per_reception_text_box.setText(Double.toString(leagueScoringParameters.getOffensePointsPerReception()));
             points_per_fumble_text_box.setText(Double.toString(leagueScoringParameters.getOffensePointsPerFumble()));
             points_per_field_goal_text_box.setText(Double.toString(leagueScoringParameters.getOffensePointsPerFieldGoal()));
             points_per_extra_point_text_box.setText(Double.toString(leagueScoringParameters.getOffensePointsPerExtraPoint()));
             points_per_sack_text_box.setText(Double.toString(leagueScoringParameters.getTeamDefensePointsPerSack()));
             points_per_team_defense_interception_text_box.setText(Double.toString(leagueScoringParameters.getTeamDefensePointsPerInterception()));
             points_per_team_defense_fumble_recovery_text_box.setText(Double.toString(leagueScoringParameters.getTeamDefensePointsPerFumbleRecovery()));
             points_per_team_defense_td_text_box.setText(Double.toString(leagueScoringParameters.getTeamDefensePointsPerTD()));
             team_defense_yards_allowed_cutoff_text_box.setText(Double.toString(leagueScoringParameters.getTeamDefenseYardsAllowedCutoff()));
             team_defense_points_per_yards_allowed_text_box.setText(Double.toString(leagueScoringParameters.getTeamDefensePointsPerYardsAllowed()));
             team_defense_points_allowed_cutoff_text_box.setText(Double.toString(leagueScoringParameters.getTeamDefensePointsAllowedCutoff()));
             team_defense_points_per_points_allowed_text_box.setText(Double.toString(leagueScoringParameters.getTeamDefensePointsPerPointsAllowed()));
             points_per_idp_interception_text_box.setText(Double.toString(leagueScoringParameters.getIdpPointsPerInterception()));
             points_per_idp_td_text_box.setText(Double.toString(leagueScoringParameters.getIdpPointsPerTD()));
             points_per_idp_fumble_recovery_text_box.setText(Double.toString(leagueScoringParameters.getIdpPointsPerFumbleRecovery()));
             points_per_idp_sack_text_box.setText(Double.toString(leagueScoringParameters.getIdpPointsPerSack()));
             points_per_idp_tackle_text_box.setText(Double.toString(leagueScoringParameters.getIdpPointsPerTackle()));
             points_per_idp_assist_text_box.setText(Double.toString(leagueScoringParameters.getIdpPointsPerAssist()));
            
             selectedLeagueTextBox.setText(leagueScoringParameters.getLeagueName());
             this.setYear();
        }
    }
    
    @FXML
    void usageCloseButtonAction(ActionEvent event)
    {
        usageLabel.setVisible(false);
        usageCloseButton.setVisible(false);
    }
    
    @FXML
    void helpUsageAction(ActionEvent event)
    {
        System.out.println("helpUsageAction - Enter");
//        usageTextArea.setVisible(true);
        usageLabel.setVisible(true);
        usageCloseButton.setVisible(true);
    }
}
