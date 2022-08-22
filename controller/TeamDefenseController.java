package controller;


import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import model.DraftUtility;
import model.DraftedPlayerCollection;
import model.IDPStats;
import model.IndividualDefensivePlayer;
import model.OffensivePlayer;
import model.OffensiveStats;
import model.Player;
import model.PlayerPositionCollection;
import model.Position;
import model.TableUtility;
import model.TeamDefense;
import model.TeamDefenseStats;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class TeamDefenseController
{
    private final ObservableList<Player> teamDefenseData = FXCollections.observableArrayList();
    private final ObservableList<TeamDefenseStats> previousTeamDefenseStats = FXCollections.observableArrayList();
    private final ObservableList<TeamDefenseStats> previousTeamDefenseStats1 = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Player, String> teamDefensePlayerColumn;
    
    @FXML 
    private TableColumn<Player, Double> teamDefenseFantasyPointsColumn;

    @FXML 
    private TableColumn<Player, Double> teamDefenseDiffColumn;

    @FXML
    private TableView<Player> teamDefenseTableView;

    @FXML
    private TableColumn<TeamDefenseStats, String> teamDefenseExaminePlayerColumn;
    
    @FXML
    private TableColumn<TeamDefenseStats, String> teamDefenseExamineYearColumn;

    @FXML
    private TableColumn<TeamDefenseStats, Double> teamDefenseExamineGamesColumn;
    
    @FXML
    private TableColumn<TeamDefenseStats, Double> teamDefenseExamineSacksColumn;

    @FXML
    private TableColumn<TeamDefenseStats, Double> teamDefenseExamineInterceptionsColumn;

    @FXML
    private TableColumn<TeamDefenseStats, Double> teamDefenseExamineFumbleRecoveriesColumn;

    @FXML
    private TableColumn<TeamDefenseStats, Double> teamDefenseExamineTDsColumn;

    @FXML
    private TableColumn<TeamDefenseStats, Double> teamDefenseExaminePointsAllowedColumn;
    
    @FXML
    private TableColumn<TeamDefenseStats, Double> teamDefenseExamineYardsAllowedColumn;

    @FXML
    private TableColumn<TeamDefenseStats, Double> teamDefenseExamineFantasyPointsColumn;
    
    @FXML
    private TableView<TeamDefenseStats> teamDefenseExamineTableView;

    @FXML
    private TableColumn<TeamDefenseStats, String> teamDefenseExaminePlayerColumn1;
    
    @FXML
    private TableColumn<TeamDefenseStats, String> teamDefenseExamineYearColumn1;

    @FXML
    private TableColumn<TeamDefenseStats, Double> teamDefenseExamineGamesColumn1;
    
    @FXML
    private TableColumn<TeamDefenseStats, Double> teamDefenseExamineSacksColumn1;

    @FXML
    private TableColumn<TeamDefenseStats, Double> teamDefenseExamineInterceptionsColumn1;

    @FXML
    private TableColumn<TeamDefenseStats, Double> teamDefenseExamineFumbleRecoveriesColumn1;

    @FXML
    private TableColumn<TeamDefenseStats, Double> teamDefenseExamineTDsColumn1;

    @FXML
    private TableColumn<TeamDefenseStats, Double> teamDefenseExaminePointsAllowedColumn1;
    
    @FXML
    private TableColumn<TeamDefenseStats, Double> teamDefenseExamineYardsAllowedColumn1;

    @FXML
    private TableColumn<TeamDefenseStats, Double> teamDefenseExamineFantasyPointsColumn1;
    
    @FXML
    private TableView<TeamDefenseStats> teamDefenseExamineTableView1;

    
    public TeamDefenseController()
    {
        super();
        System.out.println("TeamDefenseController::TeamDefenseController()");
        this.teamDefenseTableView = new TableView<Player>();
        this.teamDefenseTableView.setEditable(true);
        
        updateTableView();

    }

    public void updateTableView()
    {
        this.teamDefensePlayerColumn = new TableColumn<Player, String>("Player");
        this.teamDefensePlayerColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("displayName"));
        this.teamDefenseFantasyPointsColumn = new TableColumn<Player, Double>("fsy");
        this.teamDefenseFantasyPointsColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("fantasyPoints"));
        this.teamDefenseDiffColumn = new TableColumn<Player, Double>("diff");
        this.teamDefenseDiffColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("diffValue"));

        // Attempt to get 1 digit in table cells
        TableUtility.formatTableColumnTableCell(this.teamDefenseFantasyPointsColumn);
        
        // Format diff column
        TableUtility.formatTableColumnTableCell(this.teamDefenseDiffColumn);
        
        
        //TODO: get all TeamDefenses
        ArrayList<Player> teamDefenses = PlayerPositionCollection.Instance().getPositionCollection(Position.TEAM_DEFENSE);
        if (teamDefenses != null)
        {
            Iterator<Player> i = teamDefenses.iterator();
            while (i.hasNext())
            {
                Player teamDefense = i.next();
                teamDefenseData.add(teamDefense);
            }
            this.teamDefenseTableView.setItems(teamDefenseData);
            this.teamDefenseTableView.getColumns().setAll(this.teamDefensePlayerColumn, this.teamDefenseFantasyPointsColumn, this.teamDefenseDiffColumn);
            TableUtility.formatTableView(this.teamDefenseTableView);
        }

    }
    
    @FXML
    private void teamDefenseDraftButtonAction(ActionEvent event)
    {
        System.out.println("TeamDefense Draft Button Pressed");
        
       Player player = this.teamDefenseTableView.getSelectionModel().getSelectedItem();
        
       boolean onMyTeam = true;
       if (player != null)
       {
           DraftUtility.draftOrStrikeoutAction(player, onMyTeam, Position.TEAM_DEFENSE);
           updateTableView();
       }

    }
    
    @FXML
    private void teamDefenseStrikeoutButtonAction(ActionEvent event)
    {
        System.out.println("TeamDefense Strikeout Button Pressed");

       Player player = this.teamDefenseTableView.getSelectionModel().getSelectedItem();
        
       boolean onMyTeam = false;
       if (player != null)
       {
           DraftUtility.draftOrStrikeoutAction(player, onMyTeam, Position.TEAM_DEFENSE);
           updateTableView();
       }
    }

    @FXML
    private void teamDefenseExamineButtonAction(ActionEvent event)
    {
        System.out.println("TeamDefense Examine Button Pressed");
        TeamDefense teamDefense = (TeamDefense) this.teamDefenseTableView.getSelectionModel().getSelectedItem();

        System.out.println("Attempting to examine stats for: " + teamDefense.getDisplayName());

        TeamDefenseStats defensiveStatsForPlayer = teamDefense.getPreviousYearStats();
        TeamDefenseStats projectedStats = teamDefense.getProjectedStats();
        
        this.previousTeamDefenseStats.clear();
        this.teamDefenseExaminePlayerColumn = new TableColumn<TeamDefenseStats, String>("Player");
        this.teamDefenseExaminePlayerColumn.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, String>("displayName"));
        this.teamDefenseExamineYearColumn = new TableColumn<TeamDefenseStats, String>("Year");
        this.teamDefenseExamineYearColumn.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, String>("year"));
        this.teamDefenseExamineGamesColumn = new TableColumn<TeamDefenseStats, Double>("gms");
        this.teamDefenseExamineGamesColumn.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, Double>("games"));
        this.teamDefenseExamineSacksColumn = new TableColumn<TeamDefenseStats, Double>("sack");
        this.teamDefenseExamineSacksColumn.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, Double>("sacks"));
        this.teamDefenseExamineInterceptionsColumn = new TableColumn<TeamDefenseStats, Double>("ints");
        this.teamDefenseExamineInterceptionsColumn.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, Double>("interceptions"));
        this.teamDefenseExamineFumbleRecoveriesColumn = new TableColumn<TeamDefenseStats, Double>("frs");
        this.teamDefenseExamineFumbleRecoveriesColumn.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, Double>("fumbleRecoveries"));
        this.teamDefenseExamineTDsColumn = new TableColumn<TeamDefenseStats, Double>("tds");
        this.teamDefenseExamineTDsColumn.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, Double>("TDs"));
        this.teamDefenseExaminePointsAllowedColumn = new TableColumn<TeamDefenseStats, Double>("pa");
        this.teamDefenseExaminePointsAllowedColumn.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, Double>("pointsAllowed"));
        this.teamDefenseExamineYardsAllowedColumn = new TableColumn<TeamDefenseStats, Double>("ya");
        this.teamDefenseExamineYardsAllowedColumn.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, Double>("yardsAllowed"));
        this.teamDefenseExamineFantasyPointsColumn = new TableColumn<TeamDefenseStats, Double>("fsy");
        this.teamDefenseExamineFantasyPointsColumn.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, Double>("fantasyPoints"));

        TableUtility.formatTeamDefenseTableColumnDoubleTableCell(this.teamDefenseExamineFantasyPointsColumn);

        this.previousTeamDefenseStats.add(projectedStats);
        this.previousTeamDefenseStats.add(defensiveStatsForPlayer);
        this.teamDefenseExamineTableView.setItems(this.previousTeamDefenseStats);
        this.teamDefenseExamineTableView.getColumns().setAll(
                this.teamDefenseExaminePlayerColumn, 
                this.teamDefenseExamineYearColumn,
                this.teamDefenseExamineGamesColumn, 
                this.teamDefenseExamineSacksColumn,
                this.teamDefenseExamineInterceptionsColumn,
                this.teamDefenseExamineFumbleRecoveriesColumn,
                this.teamDefenseExamineTDsColumn,
                this.teamDefenseExaminePointsAllowedColumn,
                this.teamDefenseExamineYardsAllowedColumn,
                this.teamDefenseExamineFantasyPointsColumn);
        
    }

    @FXML
    private void teamDefenseExamineButton1Action(ActionEvent event)
    {
        System.out.println("TeamDefense Examine 1 Button Pressed");
        TeamDefense teamDefense = (TeamDefense) this.teamDefenseTableView.getSelectionModel().getSelectedItem();
        
        TeamDefenseStats defensiveStatsForPlayer = teamDefense.getPreviousYearStats();
        TeamDefenseStats projectedStats = teamDefense.getProjectedStats();
        
        this.previousTeamDefenseStats1.clear();
        this.teamDefenseExaminePlayerColumn1 = new TableColumn<TeamDefenseStats, String>("Player");
        this.teamDefenseExaminePlayerColumn1.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, String>("displayName"));
        this.teamDefenseExamineYearColumn1 = new TableColumn<TeamDefenseStats, String>("Year");
        this.teamDefenseExamineYearColumn1.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, String>("year"));
        this.teamDefenseExamineGamesColumn1 = new TableColumn<TeamDefenseStats, Double>("gms");
        this.teamDefenseExamineGamesColumn1.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, Double>("games"));
        this.teamDefenseExamineSacksColumn1 = new TableColumn<TeamDefenseStats, Double>("sack");
        this.teamDefenseExamineSacksColumn1.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, Double>("sacks"));
        this.teamDefenseExamineInterceptionsColumn1 = new TableColumn<TeamDefenseStats, Double>("ints");
        this.teamDefenseExamineInterceptionsColumn1.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, Double>("interceptions"));
        this.teamDefenseExamineFumbleRecoveriesColumn1 = new TableColumn<TeamDefenseStats, Double>("frs");
        this.teamDefenseExamineFumbleRecoveriesColumn1.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, Double>("fumbleRecoveries"));
        this.teamDefenseExamineTDsColumn1 = new TableColumn<TeamDefenseStats, Double>("tds");
        this.teamDefenseExamineTDsColumn1.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, Double>("TDs"));
        this.teamDefenseExaminePointsAllowedColumn1 = new TableColumn<TeamDefenseStats, Double>("pa");
        this.teamDefenseExaminePointsAllowedColumn1.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, Double>("pointsAllowed"));
        this.teamDefenseExamineYardsAllowedColumn1 = new TableColumn<TeamDefenseStats, Double>("ya");
        this.teamDefenseExamineYardsAllowedColumn1.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, Double>("yardsAllowed"));
        this.teamDefenseExamineFantasyPointsColumn1 = new TableColumn<TeamDefenseStats, Double>("fsy");
        this.teamDefenseExamineFantasyPointsColumn1.setCellValueFactory(new PropertyValueFactory<TeamDefenseStats, Double>("fantasyPoints"));

        TableUtility.formatTeamDefenseTableColumnDoubleTableCell(this.teamDefenseExamineFantasyPointsColumn1);

        this.previousTeamDefenseStats1.add(projectedStats);
        this.previousTeamDefenseStats1.add(defensiveStatsForPlayer);
        this.teamDefenseExamineTableView1.setItems(this.previousTeamDefenseStats1);
        this.teamDefenseExamineTableView1.getColumns().setAll(
                this.teamDefenseExaminePlayerColumn1, 
                this.teamDefenseExamineYearColumn1,
                this.teamDefenseExamineGamesColumn1, 
                this.teamDefenseExamineSacksColumn1,
                this.teamDefenseExamineInterceptionsColumn1,
                this.teamDefenseExamineFumbleRecoveriesColumn1,
                this.teamDefenseExamineTDsColumn1,
                this.teamDefenseExaminePointsAllowedColumn1,
                this.teamDefenseExamineYardsAllowedColumn1,
                this.teamDefenseExamineFantasyPointsColumn1);
    }
    
}
