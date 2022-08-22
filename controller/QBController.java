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
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class QBController
{
    private final ObservableList<Player> qbData = FXCollections.observableArrayList();
    
    private final ObservableList<OffensiveStats> previousQBStats = FXCollections.observableArrayList();
    private final ObservableList<OffensiveStats> previousQBStats1 = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Player, String> qbPlayerColumn;

    @FXML
    private TableColumn<Player, String> qbNFLTeamColumn;

    @FXML 
    private TableColumn<Player, Double> qbFantasyPointsColumn;

    @FXML 
    private TableColumn<Player, Double> qbDiffColumn;
    
    @FXML 
    private TableColumn<Player, Boolean> qbDraftedColumn;

    @FXML
    private TableView<Player> qbTableView;
    
    @FXML
    private TableColumn<OffensiveStats, String> qbExaminePlayerColumn;

    @FXML
    private TableColumn<OffensiveStats, String> qbExamineYearColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> qbExamineGamesColumn;
    
    @FXML
    private TableColumn<OffensiveStats, Double> qbExaminePassYardsColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> qbExaminePassTDsColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> qbExamineRushYardsColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> qbExamineRushTDsColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> qbExamineFantasyPointsColumn;
    
    @FXML
    private TableView<OffensiveStats> qbExamineTableView;

    @FXML
    private TableColumn<OffensiveStats, String> qbExaminePlayerColumn1;
    
    @FXML
    private TableColumn<OffensiveStats, String> qbExamineYearColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> qbExamineGamesColumn1;
    
    @FXML
    private TableColumn<OffensiveStats, Double> qbExaminePassYardsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> qbExaminePassTDsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> qbExamineRushYardsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> qbExamineRushTDsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> qbExamineFantasyPointsColumn1;
    
    @FXML
    private TableView<OffensiveStats> qbExamineTableView1;

    public QBController()
    {
        super();
        System.out.println("QBController::QBController()");
        this.qbTableView = new TableView<Player>();
        this.qbTableView.setEditable(true);
        
        updateTableView();

    }

    public void refreshTableView()
    {
    	this.qbPlayerColumn.setVisible(false);
    	this.qbNFLTeamColumn.setVisible(false);
    	this.qbFantasyPointsColumn.setVisible(false);
    	this.qbDiffColumn.setVisible(false);
    	this.qbDraftedColumn.setVisible(false);
    	this.qbPlayerColumn.setVisible(true);
    	this.qbNFLTeamColumn.setVisible(true);
    	this.qbFantasyPointsColumn.setVisible(true);
    	this.qbDiffColumn.setVisible(true);
    	this.qbDraftedColumn.setVisible(true);
    	
    }
    public void updateTableView()
    {
        System.out.println("QBController.updateTableView()");
        
        this.qbData.removeAll(this.qbData);
        this.qbPlayerColumn = new TableColumn<Player, String>("Player");
        this.qbPlayerColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("displayName"));
        this.qbNFLTeamColumn = new TableColumn<Player, String>("Team");
        this.qbNFLTeamColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("nflTeamString"));
        this.qbFantasyPointsColumn = new TableColumn<Player, Double>("fsy");
        this.qbFantasyPointsColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("fantasyPoints"));
        this.qbDiffColumn = new TableColumn<Player, Double>("diff");
        this.qbDiffColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("diffValue"));
//        this.qbDraftedColumn = new TableColumn<Player, Boolean>("drafted");
//        this.qbDraftedColumn.setCellValueFactory(new PropertyValueFactory<Player, Boolean>("drafted"));

//        refreshTableView();

        // Attempt to get 1 digit in table cells
        TableUtility.formatTableColumnTableCell(this.qbFantasyPointsColumn);
        
        // Format diff column
        TableUtility.formatTableColumnTableCell(this.qbDiffColumn);
        
        
        // Get all QBs
        ArrayList<Player> qbs = PlayerPositionCollection.Instance().getPositionCollection(Position.QUARTER_BACK);
        if (qbs != null)
        {
            Iterator<Player> i = qbs.iterator();
            while (i.hasNext())
            {
                Player qb = i.next();
                this.qbData.add(qb);
            }
            this.qbTableView.setItems(this.qbData);
            this.qbTableView.getColumns().setAll(this.qbPlayerColumn, this.qbNFLTeamColumn, this.qbFantasyPointsColumn, this.qbDiffColumn);
//            this.qbTableView.getColumns().setAll(this.qbPlayerColumn, this.qbNFLTeamColumn, this.qbFantasyPointsColumn, this.qbDiffColumn, this.qbDraftedColumn);
            TableUtility.formatTableView(this.qbTableView);
        }
        else
        {
            System.out.println("No QBs found in PlayerPositionCollection for position QUARTER_BACK - cannot format table");
        }

    }
    
    @FXML
    private void qbDraftButtonAction(ActionEvent event)
    {
        System.out.println("QB Draft Button Pressed");
        
        Player player = this.qbTableView.getSelectionModel().getSelectedItem();
        
        boolean onMyTeam = true;
        if (player != null)
        {
            DraftUtility.draftOrStrikeoutAction(player, onMyTeam, Position.QUARTER_BACK);
            updateTableView();
        }

    }
    
    @FXML
    private void qbStrikeoutButtonAction(ActionEvent event)
    {
        System.out.println("QB Strikeout Button Pressed");
        Player player = this.qbTableView.getSelectionModel().getSelectedItem();
        
        boolean onMyTeam = false;
        if (player != null)
        {
            DraftUtility.draftOrStrikeoutAction(player, onMyTeam, Position.QUARTER_BACK);
            updateTableView();
        }

    }

    @FXML
    private void qbExamineButtonAction(ActionEvent event)
    {
    	System.out.println("QB Examine Button Pressed");
        OffensivePlayer offensivePlayer = (OffensivePlayer) this.qbTableView.getSelectionModel().getSelectedItem();
        
        System.out.println("Attempting to examine stats for: " + offensivePlayer.getDisplayName());
        OffensiveStats offensiveStatsForPlayer = offensivePlayer.getPreviousYearStats();
        OffensiveStats projectedStatsForPlayer = offensivePlayer.getProjectedStats();
        
        this.previousQBStats.clear();
        this.qbExaminePlayerColumn = new TableColumn<OffensiveStats, String>("Player");
        this.qbExaminePlayerColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("displayName"));
        this.qbExamineYearColumn = new TableColumn<OffensiveStats, String>("Year");
        this.qbExamineYearColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("year"));
        this.qbExamineGamesColumn = new TableColumn<OffensiveStats, Double>("gms");
        this.qbExamineGamesColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("games"));
        this.qbExaminePassYardsColumn = new TableColumn<OffensiveStats, Double>("pyds");
        this.qbExaminePassYardsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("passingYards"));
        this.qbExaminePassTDsColumn = new TableColumn<OffensiveStats, Double>("ptds");
        this.qbExaminePassTDsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("passingTDs"));
        this.qbExamineRushYardsColumn = new TableColumn<OffensiveStats, Double>("ryds");
        this.qbExamineRushYardsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("rushingYards"));
        this.qbExamineRushTDsColumn = new TableColumn<OffensiveStats, Double>("rtds");
        this.qbExamineRushTDsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("rushingTDs"));
        this.qbExamineFantasyPointsColumn = new TableColumn<OffensiveStats, Double>("fsy");
        this.qbExamineFantasyPointsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("fantasyPoints"));

        TableUtility.formatTableColumnDoubleTableCell(this.qbExamineFantasyPointsColumn);

        this.previousQBStats.add(projectedStatsForPlayer);
        this.previousQBStats.add(offensiveStatsForPlayer);
        this.qbExamineTableView.setItems(this.previousQBStats);
        this.qbExamineTableView.getColumns().setAll(
        		this.qbExaminePlayerColumn, 
        		this.qbExamineYearColumn,
        		this.qbExamineGamesColumn, 
        		this.qbExaminePassYardsColumn,
        		this.qbExaminePassTDsColumn,
        		this.qbExamineRushYardsColumn,
        		this.qbExamineRushTDsColumn,
        		this.qbExamineFantasyPointsColumn);
        
    }

    @FXML
    private void qbExamineButton1Action(ActionEvent event)
    {
    	System.out.println("QB Examine 1 Button Pressed");
        OffensivePlayer offensivePlayer = (OffensivePlayer) this.qbTableView.getSelectionModel().getSelectedItem();
        
        OffensiveStats offensiveStatsForPlayer = offensivePlayer.getPreviousYearStats();
        OffensiveStats projectedStatsForPlayer = offensivePlayer.getProjectedStats();
        
        this.previousQBStats1.clear();
        this.qbExaminePlayerColumn1 = new TableColumn<OffensiveStats, String>("Player");
        this.qbExaminePlayerColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("displayName"));
        this.qbExamineYearColumn1 = new TableColumn<OffensiveStats, String>("Year");
        this.qbExamineYearColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("year"));
        this.qbExamineGamesColumn1 = new TableColumn<OffensiveStats, Double>("gms");
        this.qbExamineGamesColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("games"));
        this.qbExaminePassYardsColumn1 = new TableColumn<OffensiveStats, Double>("pyds");
        this.qbExaminePassYardsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("passingYards"));
        this.qbExaminePassTDsColumn1 = new TableColumn<OffensiveStats, Double>("ptds");
        this.qbExaminePassTDsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("passingTDs"));
        this.qbExamineRushYardsColumn1 = new TableColumn<OffensiveStats, Double>("ryds");
        this.qbExamineRushYardsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("rushingYards"));
        this.qbExamineRushTDsColumn1 = new TableColumn<OffensiveStats, Double>("rtds");
        this.qbExamineRushTDsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("rushingTDs"));
        this.qbExamineFantasyPointsColumn1 = new TableColumn<OffensiveStats, Double>("fsy");
        this.qbExamineFantasyPointsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("fantasyPoints"));

        TableUtility.formatTableColumnDoubleTableCell(this.qbExamineFantasyPointsColumn1);

        this.previousQBStats1.add(projectedStatsForPlayer);
        this.previousQBStats1.add(offensiveStatsForPlayer);
        this.qbExamineTableView1.setItems(this.previousQBStats1);
        this.qbExamineTableView1.getColumns().setAll(
        		this.qbExaminePlayerColumn1, 
        		this.qbExamineYearColumn1,
        		this.qbExamineGamesColumn1, 
        		this.qbExaminePassYardsColumn1,
        		this.qbExaminePassTDsColumn1,
        		this.qbExamineRushYardsColumn1,
        		this.qbExamineRushTDsColumn1,
        		this.qbExamineFantasyPointsColumn1);
    }
    
}