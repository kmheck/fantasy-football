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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class KController
{
    private final ObservableList<Player> kData = FXCollections.observableArrayList();

    private final ObservableList<OffensiveStats> previousKStats = FXCollections.observableArrayList();
    private final ObservableList<OffensiveStats> previousKStats1 = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Player, String> kPlayerColumn;

    @FXML
    private TableColumn<Player, String> kNFLTeamColumn;

    @FXML
    private TableColumn<Player, Double> kFantasyPointsColumn;

    @FXML
    private TableColumn<Player, Double> kDiffColumn;

    @FXML
    private TableView<Player> kTableView;

    @FXML
    private TableColumn<OffensiveStats, String> kExaminePlayerColumn;
    
    @FXML
    private TableColumn<OffensiveStats, String> kExamineYearColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> kExamineGamesColumn;
    
    @FXML
    private TableColumn<OffensiveStats, Double> kExamineFieldGoalsColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> kExamineExtraPointsColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> kExamineFantasyPointsColumn;
    
    @FXML
    private TableView<OffensiveStats> kExamineTableView;

    @FXML
    private TableColumn<OffensiveStats, String> kExaminePlayerColumn1;
    
    @FXML
    private TableColumn<OffensiveStats, String> kExamineYearColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> kExamineGamesColumn1;
    
    @FXML
    private TableColumn<OffensiveStats, Double> kExamineFieldGoalsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> kExamineExtraPointsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> kExamineFantasyPointsColumn1;
    
    @FXML
    private TableView<OffensiveStats> kExamineTableView1;


    public KController()
    {
        super();
        System.out.println("KController::KController()");
        this.kTableView = new TableView<Player>();
        this.kTableView.setEditable(true);

        updateTableView();

    }

    public void updateTableView()
    {
        this.kPlayerColumn = new TableColumn<Player, String>("Player");
        this.kPlayerColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("displayName"));
        this.kNFLTeamColumn = new TableColumn<Player, String>("Team");
        this.kNFLTeamColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("nflTeamString"));
        this.kFantasyPointsColumn = new TableColumn<Player, Double>("fsy");
        this.kFantasyPointsColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("fantasyPoints"));
        this.kDiffColumn = new TableColumn<Player, Double>("diff");
        this.kDiffColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("diffValue"));

        // Attempt to get 1 digit in table cells
        TableUtility.formatTableColumnTableCell(this.kFantasyPointsColumn);

        // Format diff column
        TableUtility.formatTableColumnTableCell(this.kDiffColumn);

        this.kData.removeAll(this.kData);
        ArrayList<Player> ks = PlayerPositionCollection.Instance().getPositionCollection(Position.KICKER);
        if (ks != null)
        {
            Iterator<Player> i = ks.iterator();
            while (i.hasNext())
            {
                Player k = i.next();
                kData.add(k);
            }
            this.kTableView.setItems(kData);
            this.kTableView.getColumns().setAll(this.kPlayerColumn, this.kNFLTeamColumn, this.kFantasyPointsColumn, this.kDiffColumn);
            TableUtility.formatTableView(this.kTableView);
        }

    }

    @FXML
    private void kDraftButtonAction(ActionEvent event)
    {
        System.out.println("K Draft Button Pressed");

        Player player = this.kTableView.getSelectionModel().getSelectedItem();
        boolean onMyTeam = true;
        if (player != null)
        {
            DraftUtility.draftOrStrikeoutAction(player, onMyTeam, Position.KICKER);
            updateTableView();
        }
    }

    @FXML
    private void kStrikeoutButtonAction(ActionEvent event)
    {
        System.out.println("K Strikeout Button Pressed");

        boolean onMyTeam = false;
        Player player = this.kTableView.getSelectionModel().getSelectedItem();

        if (player != null)
        {
            DraftUtility.draftOrStrikeoutAction(player, onMyTeam, Position.KICKER);
            updateTableView();
        }
    }

    @FXML
    private void kExamineButtonAction(ActionEvent event)
    {
        System.out.println("QB Examine Button Pressed");
        OffensivePlayer offensivePlayer = (OffensivePlayer) this.kTableView.getSelectionModel().getSelectedItem();
        
        System.out.println("Attempting to examine stats for: " + offensivePlayer.getDisplayName());
        OffensiveStats offensiveStatsForPlayer = offensivePlayer.getPreviousYearStats();
        
        OffensiveStats projectedStats = offensivePlayer.getProjectedStats();
        
        this.previousKStats.clear();
        this.kExaminePlayerColumn = new TableColumn<OffensiveStats, String>("Player");
        this.kExaminePlayerColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("displayName"));
        this.kExamineYearColumn = new TableColumn<OffensiveStats, String>("Year");
        this.kExamineYearColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("year"));
        this.kExamineGamesColumn = new TableColumn<OffensiveStats, Double>("gms");
        this.kExamineGamesColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("games"));
        this.kExamineFieldGoalsColumn = new TableColumn<OffensiveStats, Double>("fg");
        this.kExamineFieldGoalsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("fieldGoals"));
        this.kExamineExtraPointsColumn = new TableColumn<OffensiveStats, Double>("xp");
        this.kExamineExtraPointsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("extraPoints"));
        this.kExamineFantasyPointsColumn = new TableColumn<OffensiveStats, Double>("fsy");
        this.kExamineFantasyPointsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("fantasyPoints"));

        TableUtility.formatTableColumnDoubleTableCell(this.kExamineFantasyPointsColumn);

        this.previousKStats.add(projectedStats);
        this.previousKStats.add(offensiveStatsForPlayer);
        this.kExamineTableView.setItems(this.previousKStats);
        this.kExamineTableView.getColumns().setAll(
                this.kExaminePlayerColumn, 
                this.kExamineYearColumn,
                this.kExamineGamesColumn, 
                this.kExamineFieldGoalsColumn,
                this.kExamineExtraPointsColumn,
                this.kExamineFantasyPointsColumn);
        
    }

    @FXML
    private void kExamineButton1Action(ActionEvent event)
    {
        System.out.println("QB Examine 1 Button Pressed");
        OffensivePlayer offensivePlayer = (OffensivePlayer) this.kTableView.getSelectionModel().getSelectedItem();
        
        OffensiveStats offensiveStatsForPlayer = offensivePlayer.getPreviousYearStats();
        OffensiveStats projectedStats = offensivePlayer.getProjectedStats();
        
        this.previousKStats1.clear();
        this.kExaminePlayerColumn1 = new TableColumn<OffensiveStats, String>("Player");
        this.kExaminePlayerColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("displayName"));
        this.kExamineYearColumn1 = new TableColumn<OffensiveStats, String>("Year");
        this.kExamineYearColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("year"));
        this.kExamineGamesColumn1 = new TableColumn<OffensiveStats, Double>("gms");
        this.kExamineGamesColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("games"));
        this.kExamineFieldGoalsColumn1 = new TableColumn<OffensiveStats, Double>("fg");
        this.kExamineFieldGoalsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("fieldGoals"));
        this.kExamineExtraPointsColumn1 = new TableColumn<OffensiveStats, Double>("xp");
        this.kExamineExtraPointsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("extraPoints"));
        this.kExamineFantasyPointsColumn1 = new TableColumn<OffensiveStats, Double>("fsy");
        this.kExamineFantasyPointsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("fantasyPoints"));

        TableUtility.formatTableColumnDoubleTableCell(this.kExamineFantasyPointsColumn1);

        this.previousKStats1.add(projectedStats);
        this.previousKStats1.add(offensiveStatsForPlayer);
        this.kExamineTableView1.setItems(this.previousKStats1);
        this.kExamineTableView1.getColumns().setAll(
                this.kExaminePlayerColumn1, 
                this.kExamineYearColumn1,
                this.kExamineGamesColumn1, 
                this.kExamineFieldGoalsColumn1,
                this.kExamineExtraPointsColumn1,
                this.kExamineFantasyPointsColumn1);
    }

}