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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class RBController
{
    private final ObservableList<Player> rbData = FXCollections.observableArrayList();

    private final ObservableList<OffensiveStats> previousRBStats = FXCollections.observableArrayList();

    private final ObservableList<OffensiveStats> previousRBStats1 = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Player, String> rbPlayerColumn;

    @FXML
    private TableColumn<Player, String> rbNFLTeamColumn;

    @FXML 
    private TableColumn<Player, Double> rbFantasyPointsColumn;

    @FXML 
    private TableColumn<Player, Double> rbDiffColumn;

    @FXML 
    private TableColumn<Player, Boolean> rbDraftedColumn;

    @FXML
    private TableView<Player> rbTableView;

    @FXML
    private TableColumn<OffensiveStats, String> rbExaminePlayerColumn;
    
    @FXML
    private TableColumn<OffensiveStats, String> rbExamineYearColumn;
    
    @FXML
    private TableColumn<OffensiveStats, Double> rbExamineGamesColumn;
    
    @FXML
    private TableColumn<OffensiveStats, Double> rbExamineRushYardsColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> rbExamineRushTDsColumn;
    
    @FXML
    private TableColumn<OffensiveStats, Double> rbExamineReceivingYardsColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> rbExamineReceivingTDsColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> rbExamineReceptionsColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> rbExamineFantasyPointsColumn;
    
    @FXML
    private TableView<OffensiveStats> rbExamineTableView;

    @FXML
    private TableColumn<OffensiveStats, String> rbExaminePlayerColumn1;
    
    @FXML
    private TableColumn<OffensiveStats, String> rbExamineYearColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> rbExamineGamesColumn1;
    
    @FXML
    private TableColumn<OffensiveStats, Double> rbExamineRushYardsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> rbExamineRushTDsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> rbExamineReceivingYardsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> rbExamineReceivingTDsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> rbExamineReceptionsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> rbExamineFantasyPointsColumn1;
    
    @FXML
    private TableView<OffensiveStats> rbExamineTableView1;

    public RBController()
    {
        super();
        System.out.println("RBController::RBController()");
        this.rbTableView = new TableView<Player>();
        this.rbTableView.setEditable(false);
        
        updateTableView();

    }

    public void updateTableView()
    {
        this.rbPlayerColumn = new TableColumn<Player, String>("Player");
        this.rbPlayerColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("displayName"));
        this.rbNFLTeamColumn = new TableColumn<Player, String>("Team");
        this.rbNFLTeamColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("nflTeamString"));
        this.rbFantasyPointsColumn = new TableColumn<Player, Double>("fsy");
        this.rbFantasyPointsColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("fantasyPoints"));
        this.rbDiffColumn = new TableColumn<Player, Double>("diff");
        this.rbDiffColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("diffValue"));
//        this.rbDraftedColumn = new TableColumn<Player, Boolean>("drafted");
//        this.rbDraftedColumn.setCellValueFactory(new PropertyValueFactory<Player, Boolean>("drafted"));

        // Attempt to get 1 digit in table cells
        TableUtility.formatTableColumnTableCell(this.rbFantasyPointsColumn);
        
        // Format diff column
        TableUtility.formatTableColumnTableCell(this.rbDiffColumn);
        
        
        //TODO: get all RBs
        this.rbData.removeAll(this.rbData);
        ArrayList<Player> rbs = PlayerPositionCollection.Instance().getPositionCollection(Position.RUNNING_BACK);
        if (rbs != null)
        {
            Iterator<Player> i = rbs.iterator();
            while (i.hasNext())
            {
                Player rb = i.next();
                this.rbData.add(rb);
            }
            this.rbTableView.setItems(this.rbData);
            this.rbTableView.getColumns().setAll
            (this.rbPlayerColumn, this.rbNFLTeamColumn, this.rbFantasyPointsColumn, this.rbDiffColumn);
//            this.rbTableView.getColumns().setAll
//            (this.rbPlayerColumn, this.rbNFLTeamColumn, this.rbFantasyPointsColumn, this.rbDiffColumn, this.rbDraftedColumn);
            TableUtility.formatTableView(this.rbTableView);
        }

    }
    
    @FXML
    private void rbDraftButtonAction(ActionEvent event)
    {
        System.out.println("RB Draft Button Pressed");
        
        Player player = this.rbTableView.getSelectionModel().getSelectedItem();
        
        boolean onMyTeam = true;
        if (player != null)
        {
            DraftUtility.draftOrStrikeoutAction(player, onMyTeam, Position.RUNNING_BACK);
            updateTableView();
        }

    }
    
    @FXML
    private void rbStrikeoutButtonAction(ActionEvent event)
    {
        System.out.println("RB Strikeout Button Pressed");
        Player player = this.rbTableView.getSelectionModel().getSelectedItem();
        
        boolean onMyTeam = false;
        if (player != null)
        {
            DraftUtility.draftOrStrikeoutAction(player, onMyTeam, Position.RUNNING_BACK);
            updateTableView();
        }

    }

    @FXML
    private void rbExamineButtonAction(ActionEvent event)
    {
        System.out.println("RB Examine Button Pressed");
        OffensivePlayer offensivePlayer = (OffensivePlayer) this.rbTableView.getSelectionModel().getSelectedItem();

        System.out.println("Attempting to examine stats for: " + offensivePlayer.getDisplayName());

        OffensiveStats offensiveStatsForPlayer = offensivePlayer.getPreviousYearStats();
        OffensiveStats projectedStatsForPlayer = offensivePlayer.getProjectedStats();
        
        this.previousRBStats.clear();
        this.rbExaminePlayerColumn = new TableColumn<OffensiveStats, String>("Player");
        this.rbExaminePlayerColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("displayName"));
        this.rbExamineYearColumn = new TableColumn<OffensiveStats, String>("Year");
        this.rbExamineYearColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("year"));
        this.rbExamineGamesColumn = new TableColumn<OffensiveStats, Double>("gms");
        this.rbExamineGamesColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("games"));
        this.rbExamineRushYardsColumn = new TableColumn<OffensiveStats, Double>("ryds");
        this.rbExamineRushYardsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("rushingYards"));
        this.rbExamineRushTDsColumn = new TableColumn<OffensiveStats, Double>("rtds");
        this.rbExamineRushTDsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("rushingTDs"));
        this.rbExamineReceivingYardsColumn = new TableColumn<OffensiveStats, Double>("recyds");
        this.rbExamineReceivingYardsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("receivingYards"));
        this.rbExamineReceivingTDsColumn = new TableColumn<OffensiveStats, Double>("rectds");
        this.rbExamineReceivingTDsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("receivingTDs"));
        this.rbExamineReceptionsColumn = new TableColumn<OffensiveStats, Double>("recept");
        this.rbExamineReceptionsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("receptions"));
        this.rbExamineFantasyPointsColumn = new TableColumn<OffensiveStats, Double>("fsy");
        this.rbExamineFantasyPointsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("fantasyPoints"));

        TableUtility.formatTableColumnDoubleTableCell(this.rbExamineFantasyPointsColumn);

        this.previousRBStats.add(projectedStatsForPlayer);
        this.previousRBStats.add(offensiveStatsForPlayer);
        this.rbExamineTableView.setItems(this.previousRBStats);
        this.rbExamineTableView.getColumns().setAll(
                this.rbExaminePlayerColumn, 
                this.rbExamineYearColumn,
                this.rbExamineGamesColumn, 
                this.rbExamineRushYardsColumn,
                this.rbExamineRushTDsColumn,
                this.rbExamineReceivingYardsColumn,
                this.rbExamineReceivingTDsColumn,
                this.rbExamineReceptionsColumn,
                this.rbExamineFantasyPointsColumn);
        
    }

    @FXML
    private void rbExamineButton1Action(ActionEvent event)
    {
        System.out.println("RB Examine 1 Button Pressed");
        OffensivePlayer offensivePlayer = (OffensivePlayer) this.rbTableView.getSelectionModel().getSelectedItem();
        
        OffensiveStats offensiveStatsForPlayer = offensivePlayer.getPreviousYearStats();
        OffensiveStats projectedStatsForPlayer = offensivePlayer.getProjectedStats();
        
        this.previousRBStats1.clear();
        this.rbExaminePlayerColumn1 = new TableColumn<OffensiveStats, String>("Player");
        this.rbExaminePlayerColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("displayName"));
        this.rbExamineYearColumn1 = new TableColumn<OffensiveStats, String>("Year");
        this.rbExamineYearColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("year"));
        this.rbExamineGamesColumn1 = new TableColumn<OffensiveStats, Double>("gms");
        this.rbExamineGamesColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("games"));
        this.rbExamineRushYardsColumn1 = new TableColumn<OffensiveStats, Double>("ryds");
        this.rbExamineRushYardsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("rushingYards"));
        this.rbExamineRushTDsColumn1 = new TableColumn<OffensiveStats, Double>("rtds");
        this.rbExamineRushTDsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("rushingTDs"));
        this.rbExamineReceivingYardsColumn1 = new TableColumn<OffensiveStats, Double>("recyds");
        this.rbExamineReceivingYardsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("receivingYards"));
        this.rbExamineReceivingTDsColumn1 = new TableColumn<OffensiveStats, Double>("rectds");
        this.rbExamineReceivingTDsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("receivingTDs"));
        this.rbExamineReceptionsColumn1 = new TableColumn<OffensiveStats, Double>("recept");
        this.rbExamineReceptionsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("receptions"));
        this.rbExamineFantasyPointsColumn1 = new TableColumn<OffensiveStats, Double>("fsy");
        this.rbExamineFantasyPointsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("fantasyPoints"));

        TableUtility.formatTableColumnDoubleTableCell(this.rbExamineFantasyPointsColumn1);

        this.previousRBStats1.add(projectedStatsForPlayer);
        this.previousRBStats1.add(offensiveStatsForPlayer);
        this.rbExamineTableView1.setItems(this.previousRBStats1);
        this.rbExamineTableView1.getColumns().setAll(
                this.rbExaminePlayerColumn1, 
                this.rbExamineYearColumn1,
                this.rbExamineGamesColumn1, 
                this.rbExamineRushYardsColumn1,
                this.rbExamineRushTDsColumn1,
                this.rbExamineReceivingYardsColumn1,
                this.rbExamineReceivingTDsColumn1,
                this.rbExamineReceptionsColumn1,
                this.rbExamineFantasyPointsColumn1);
    }
}
