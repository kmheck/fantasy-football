package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import model.ConfigurationData;
import model.LeagueRosterParameters;
import model.LeagueScoringParameters;
import model.Position;
import model.Roster;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RosterController
{

    Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }

    @FXML private TextField number_of_teams_text_box;
    @FXML private TextField roster_size_text_box;
    @FXML private TextField qb_starters_text_box;
    @FXML private TextField rb_starters_text_box;
    @FXML private TextField wr_starters_text_box;
    @FXML private TextField te_starters_text_box;
    @FXML private TextField k_starters_text_box;
    @FXML private TextField team_defense_starters_text_box;
    @FXML private TextField idp_starters_text_box;
    @FXML private TextField rb_wr_te_starters_text_box;
    @FXML private TextField wr_te_starters_text_box;
    
    @FXML private TextField rosterSelectedLeagueTextBox;
    
    @FXML private TextField selectedRosterFileTextBox;
    
    @FXML private Label rosterSaveLabel;
    @FXML private Button saveRosterFileButton;
    @FXML private Button closeRosterLabelButton;
    
    @FXML
    private void rosterButtonAction(ActionEvent event)
    {
        
        //TODO: Modify Roster class in model with values in textboxes.
        System.out.println("Roster Settings Button Pressed");
        
        Roster.Instance().setNumberOfTeamsInLeague(Integer.valueOf(this.number_of_teams_text_box.getText()));
        System.out.println("Set number of teams in league to: " + Roster.Instance().getNumberOfTeamsInLeague());

        Roster.Instance().setRosterSize(Integer.valueOf(this.roster_size_text_box.getText()));
        System.out.println("Set roster size in league to: " + Roster.Instance().getRosterSize());

        Roster.Instance().setNumberOfStarters(Position.QUARTER_BACK, Integer.valueOf(this.qb_starters_text_box.getText()));
        System.out.println("Number of QB starters: " + Roster.Instance().getNumberOfStarters(Position.QUARTER_BACK));

        Roster.Instance().setNumberOfStarters(Position.RUNNING_BACK, Integer.valueOf(this.rb_starters_text_box.getText()));
        System.out.println("Number of RB starters: " + Roster.Instance().getNumberOfStarters(Position.RUNNING_BACK));

        Roster.Instance().setNumberOfStarters(Position.WIDE_RECEIVER, Integer.valueOf(this.wr_starters_text_box.getText()));
        System.out.println("Number of WR starters: " + Roster.Instance().getNumberOfStarters(Position.WIDE_RECEIVER));

        Roster.Instance().setNumberOfStarters(Position.TIGHT_END, Integer.valueOf(this.te_starters_text_box.getText()));
        System.out.println("Number of TE starters: " + Roster.Instance().getNumberOfStarters(Position.TIGHT_END));

        Roster.Instance().setNumberOfStarters(Position.TEAM_DEFENSE, Integer.valueOf(this.team_defense_starters_text_box.getText()));
        System.out.println("Number of Team Defense starters: " + Roster.Instance().getNumberOfStarters(Position.TEAM_DEFENSE));

        Roster.Instance().setNumberOfStarters(Position.KICKER, Integer.valueOf(this.k_starters_text_box.getText()));
        System.out.println("Number of K starters: " + Roster.Instance().getNumberOfStarters(Position.KICKER));

        Roster.Instance().setNumberOfStarters(Position.INDIVIDUAL_DEFENSIVE_PLAYER, Integer.valueOf(this.idp_starters_text_box.getText()));
        System.out.println("Number of IDP starters: " + Roster.Instance().getNumberOfStarters(Position.INDIVIDUAL_DEFENSIVE_PLAYER));

        //TODO: How to handle the "flex" positions?
        int number_of_flex_rb_wr_te = Integer.valueOf(this.rb_wr_te_starters_text_box.getText());
        int number_of_flex_wr_te = Integer.valueOf(this.wr_te_starters_text_box.getText());
        int number_of_flex_positions = number_of_flex_rb_wr_te + number_of_flex_wr_te;
        Roster.Instance().setNumberOfStarters(Position.FLEX, number_of_flex_positions);
        System.out.println("Number of Flex starters: " + Roster.Instance().getNumberOfStarters(Position.FLEX));
        
        ArrayList<Position> eligibleFlexPositions = new ArrayList<Position>();
        eligibleFlexPositions.clear();
        if (number_of_flex_rb_wr_te > 0)
        {
            eligibleFlexPositions.add(Position.RUNNING_BACK);
            eligibleFlexPositions.add(Position.WIDE_RECEIVER);
            eligibleFlexPositions.add(Position.TIGHT_END);
        }
        else if (number_of_flex_wr_te > 0)
        {
            eligibleFlexPositions.add(Position.WIDE_RECEIVER);
            eligibleFlexPositions.add(Position.TIGHT_END);
        }
        Roster.Instance().setFlexPositions(eligibleFlexPositions);
    }
    
    public void fillTextBoxesWithLeagueRosterParameters(LeagueRosterParameters parameters)
    {
        System.out.println("fillTextBoxesWithLeagueRosterParameters() - Enter");
        
        if (parameters != null)
        {
            number_of_teams_text_box.setText(Integer.toString(parameters.getNumberOfTeamsInLeague()));
            roster_size_text_box.setText(Integer.toString(parameters.getRosterSize()));
            qb_starters_text_box.setText(Integer.toString(parameters.getNumberOfQuarterbackStarters()));
            rb_starters_text_box.setText(Integer.toString(parameters.getNumberOfRunningbackStarters()));
            wr_starters_text_box.setText(Integer.toString(parameters.getNumberOfWideReceiverStarters()));
            te_starters_text_box.setText(Integer.toString(parameters.getNumberOfTightEndStarters()));
            k_starters_text_box.setText(Integer.toString(parameters.getNumberOfKickerStarters()));
            team_defense_starters_text_box.setText(Integer.toString(parameters.getNumberOfTeamDefenseStarters()));
            idp_starters_text_box.setText(Integer.toString(parameters.getNumberOfIDPStarters()));
            rb_wr_te_starters_text_box.setText(Integer.toString(parameters.getNumberOfFlexRBWRTEStarters()));
            wr_te_starters_text_box.setText(Integer.toString(parameters.getNumberOfFlexWRTEStarters()));
            
            rosterSelectedLeagueTextBox.setText(parameters.getLeagueName());
        }
        
    }
    
    @FXML
    private void DJCRosterButtonAction(ActionEvent event)
    {
        System.out.println("DJC Button Pressed");
        Gson gson = new Gson();
        //TODO: Read in data/roster_djc.txt file, convert JSON
        //      to values to set starting roster sizes for each position.
        //      Also fill in text boxes with proper values for DJC league.
        String fileName = "src/data/roster_djc.txt";
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            LeagueRosterParameters DJCRosterParameters = gson.fromJson(bufferedReader, LeagueRosterParameters.class);
            System.out.println("DJC Roster Parameters: " + DJCRosterParameters.toString());
            fillTextBoxesWithLeagueRosterParameters(DJCRosterParameters);
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
    private void ChooseRosterFileAction(ActionEvent event)
    {
        System.out.println("Choose Roster File Menu item selected");
        Gson gson = new Gson();
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose League Roster File");
        
        // Projections directory is just /src/data which will suffice
        String rosterDirectory = ConfigurationData.Instance().getProjectionsDirectory();
        fileChooser.setInitialDirectory(new File(rosterDirectory));
        File chosenFile = fileChooser.showOpenDialog(this.primaryStage);
        if (chosenFile != null) 
        {
            String chosenFileName = chosenFile.getName();
            System.out.println("Roster file chosen: " + chosenFileName);
            selectedRosterFileTextBox.setText(chosenFileName);        
            FileReader fileReader;
            try
            {
                fileReader = new FileReader(chosenFile.getAbsoluteFile());
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                LeagueRosterParameters chosenFileRosterParameters = gson.fromJson(bufferedReader, LeagueRosterParameters.class);
                System.out.println("Chosen File Roster Parameters: " + chosenFileRosterParameters.toString());
                fillTextBoxesWithLeagueRosterParameters(chosenFileRosterParameters);
                bufferedReader.close();
            }
            catch (FileNotFoundException e)
            {
                // TODO Auto-generated catch block
                System.out.println("File: " + chosenFile.getAbsoluteFile() + " not found");
                e.printStackTrace();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    private void saveRosterSettingsAction(ActionEvent event)
    {
        //TODO:
        // Show rosterSaveLabel
        // Make saveRosterFileButton visible
        // 
        rosterSaveLabel.setVisible(true);
        saveRosterFileButton.setVisible(true);
        selectedRosterFileTextBox.setText("<Enter league name>");
    }
    
    private LeagueRosterParameters createLeagueRosterParametersFromSettings(String leagueName)
    {
        LeagueRosterParameters rosterParams = new LeagueRosterParameters();
        rosterParams.setLeagueName(leagueName);
        
        rosterParams.setNumberOfTeamsInLeague(Integer.valueOf(number_of_teams_text_box.getText()));
        rosterParams.setRosterSize(Integer.valueOf(roster_size_text_box.getText()));
        rosterParams.setNumberOfQuarterbackStarters(Integer.valueOf(qb_starters_text_box.getText()));
        rosterParams.setNumberOfRunningbackStarters(Integer.valueOf(rb_starters_text_box.getText()));
        rosterParams.setNumberOfWideReceiverStarters(Integer.valueOf(wr_starters_text_box.getText()));
        rosterParams.setNumberOfTightEndStarters(Integer.valueOf(te_starters_text_box.getText()));
        rosterParams.setNumberOfKickerStarters(Integer.valueOf(k_starters_text_box.getText()));
        rosterParams.setNumberOfTeamDefenseStarters(Integer.valueOf(team_defense_starters_text_box.getText()));
        rosterParams.setNumberOfIDPStarters(Integer.valueOf(idp_starters_text_box.getText()));
        rosterParams.setNumberOfFlexRBWRTEStarters(Integer.valueOf(rb_wr_te_starters_text_box.getText()));
        rosterParams.setNumberOfFlexWRTEStarters(Integer.valueOf(wr_te_starters_text_box.getText()));

        return rosterParams;
    }
    
    @FXML
    private void saveRosterFileButtonAction(ActionEvent event)
    {
        Gson gson = new Gson();
        LeagueRosterParameters rosterParams = new LeagueRosterParameters();
        String rosterFileName = "roster_";
        String rosterFileString = selectedRosterFileTextBox.getText();
        if (rosterFileString.isEmpty() || rosterFileString.contains("Enter"))
        {
            selectedRosterFileTextBox.setText("<Must enter league name>");
        }
        else
        {
            System.out.println("Entered league name: " + rosterFileString);
            rosterFileString = rosterFileString.replace(' ', '_') + ".txt";
            System.out.println("league name after replacing spaces: " + rosterFileString);
            rosterFileName = rosterFileName.concat(rosterFileString);
            System.out.println("league name after concatenation: " + rosterFileName);
            rosterParams = createLeagueRosterParametersFromSettings(rosterFileString);
            String jsonRosterParams = gson.toJson((Object)rosterParams);
            
            String filePath = "src/data/" + rosterFileName;
            BufferedWriter bw = null;
            FileWriter fw = null;        
            
            try
            {
                fw = new FileWriter(filePath);
                bw = new BufferedWriter(fw);
                System.out.println("Attempting to write json: " + jsonRosterParams + " to file: " + filePath);
                bw.write(jsonRosterParams);
                String successfulString = rosterFileName + " successfully created";
                selectedRosterFileTextBox.setText(successfulString);
                rosterSaveLabel.setVisible(false);
                saveRosterFileButton.setVisible(false);
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally {
    
                try {
    
                    if (bw != null)
                        bw.close();
    
                    if (fw != null)
                        fw.close();
    
                } catch (IOException ex) {
    
                    ex.printStackTrace();
    
                }
            }
        }
    }
    
    @FXML
    void closeRosterLabelAction(ActionEvent event)
    {
        closeRosterLabelButton.setVisible(false);
        rosterSaveLabel.setVisible(false);
    }
}
