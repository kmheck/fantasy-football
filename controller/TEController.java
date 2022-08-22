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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class TEController
{
    private final ObservableList<Player> teData = FXCollections.observableArrayList();

    private final ObservableList<OffensiveStats> previousTEStats = FXCollections.observableArrayList();
    private final ObservableList<OffensiveStats> previousTEStats1 = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Player, String> tePlayerColumn;
    
    @FXML
    private TableColumn<Player, String> teNFLTeamColumn;
    
    @FXML 
    private TableColumn<Player, Double> teFantasyPointsColumn;

    @FXML 
    private TableColumn<Player, Double> teDiffColumn;

    @FXML
    private TableView<Player> teTableView;

    @FXML
    private TableColumn<OffensiveStats, String> teExaminePlayerColumn;
    
    @FXML
    private TableColumn<OffensiveStats, String> teExamineYearColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> teExamineGamesColumn;
    
    @FXML
    private TableColumn<OffensiveStats, Double> teExamineRushYardsColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> teExamineRushTDsColumn;
    
    @FXML
    private TableColumn<OffensiveStats, Double> teExamineReceivingYardsColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> teExamineReceivingTDsColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> teExamineReceptionsColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> teExamineFantasyPointsColumn;
    
    @FXML
    private TableView<OffensiveStats> teExamineTableView;

    @FXML
    private TableColumn<OffensiveStats, String> teExaminePlayerColumn1;
    
    @FXML
    private TableColumn<OffensiveStats, String> teExamineYearColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> teExamineGamesColumn1;
    
    @FXML
    private TableColumn<OffensiveStats, Double> teExamineRushYardsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> teExamineRushTDsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> teExamineReceivingYardsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> teExamineReceivingTDsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> teExamineReceptionsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> teExamineFantasyPointsColumn1;
    
    @FXML
    private TableView<OffensiveStats> teExamineTableView1;

    
    public TEController()
    {
        super();
        System.out.println("TEController::TEController()");
        this.teTableView = new TableView<Player>();
        this.teTableView.setEditable(true);
        
        updateTableView();

    }

    public void updateTableView()
    {
        this.tePlayerColumn = new TableColumn<Player, String>("Player");
        this.tePlayerColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("displayName"));
        this.teNFLTeamColumn = new TableColumn<Player, String>("Team");
        this.teNFLTeamColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("nflTeamString"));
        this.teFantasyPointsColumn = new TableColumn<Player, Double>("fsy");
        this.teFantasyPointsColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("fantasyPoints"));
        this.teDiffColumn = new TableColumn<Player, Double>("diff");
        this.teDiffColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("diffValue"));

        // Attempt to get 1 digit in table cells
        TableUtility.formatTableColumnTableCell(this.teFantasyPointsColumn);
        
        // Format diff column
        TableUtility.formatTableColumnTableCell(this.teDiffColumn);
        
        
        this.teData.removeAll(this.teData);
        ArrayList<Player> tes = PlayerPositionCollection.Instance().getPositionCollection(Position.TIGHT_END);
        if (tes != null)
        {
            Iterator<Player> i = tes.iterator();
            while (i.hasNext())
            {
                Player te = i.next();
                teData.add(te);
            }
            this.teTableView.setItems(teData);
            this.teTableView.getColumns().setAll(this.tePlayerColumn, this.teNFLTeamColumn, this.teFantasyPointsColumn, this.teDiffColumn);
            TableUtility.formatTableView(this.teTableView);
        }

    }
    
    @FXML
    private void teDraftButtonAction(ActionEvent event)
    {
        System.out.println("TE Draft Button Pressed");
        
        Player player = this.teTableView.getSelectionModel().getSelectedItem();
        
        boolean onMyTeam = true;
        if (player != null)
        {
            DraftUtility.draftOrStrikeoutAction(player, onMyTeam, Position.TIGHT_END);
            updateTableView();
        }

    }
    
    @FXML
    private void teStrikeoutButtonAction(ActionEvent event)
    {
        System.out.println("TE Strikeout Button Pressed");

        Player player = this.teTableView.getSelectionModel().getSelectedItem();
        
        boolean onMyTeam = false;
        if (player != null)
        {
            DraftUtility.draftOrStrikeoutAction(player, onMyTeam, Position.TIGHT_END);
            updateTableView();
        }
    }

    @FXML
    private void teExamineButtonAction(ActionEvent event)
    {
        System.out.println("TE Examine Button Pressed");
        OffensivePlayer offensivePlayer = (OffensivePlayer) this.teTableView.getSelectionModel().getSelectedItem();

        System.out.println("Attempting to examine stats for: " + offensivePlayer.getDisplayName());

        OffensiveStats offensiveStatsForPlayer = offensivePlayer.getPreviousYearStats();
        OffensiveStats projectedStats = offensivePlayer.getProjectedStats();
        
        this.previousTEStats.clear();
        this.teExaminePlayerColumn = new TableColumn<OffensiveStats, String>("Player");
        this.teExaminePlayerColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("displayName"));
        this.teExamineYearColumn = new TableColumn<OffensiveStats, String>("Year");
        this.teExamineYearColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("year"));
        this.teExamineGamesColumn = new TableColumn<OffensiveStats, Double>("gms");
        this.teExamineGamesColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("games"));
        this.teExamineRushYardsColumn = new TableColumn<OffensiveStats, Double>("ryds");
        this.teExamineRushYardsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("rushingYards"));
        this.teExamineRushTDsColumn = new TableColumn<OffensiveStats, Double>("rtds");
        this.teExamineRushTDsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("rushingTDs"));
        this.teExamineReceivingYardsColumn = new TableColumn<OffensiveStats, Double>("recyds");
        this.teExamineReceivingYardsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("receivingYards"));
        this.teExamineReceivingTDsColumn = new TableColumn<OffensiveStats, Double>("rectds");
        this.teExamineReceivingTDsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("receivingTDs"));
        this.teExamineReceptionsColumn = new TableColumn<OffensiveStats, Double>("recept");
        this.teExamineReceptionsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("receptions"));
        this.teExamineFantasyPointsColumn = new TableColumn<OffensiveStats, Double>("fsy");
        this.teExamineFantasyPointsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("fantasyPoints"));

        TableUtility.formatTableColumnDoubleTableCell(this.teExamineFantasyPointsColumn);

        this.previousTEStats.add(projectedStats);
        this.previousTEStats.add(offensiveStatsForPlayer);
        this.teExamineTableView.setItems(this.previousTEStats);
        this.teExamineTableView.getColumns().setAll(
                this.teExaminePlayerColumn, 
                this.teExamineYearColumn,
                this.teExamineGamesColumn, 
                this.teExamineRushYardsColumn,
                this.teExamineRushTDsColumn,
                this.teExamineReceivingYardsColumn,
                this.teExamineReceivingTDsColumn,
                this.teExamineReceptionsColumn,
                this.teExamineFantasyPointsColumn);
        
    }

    @FXML
    private void teExamineButton1Action(ActionEvent event)
    {
        System.out.println("TE Examine 1 Button Pressed");
        OffensivePlayer offensivePlayer = (OffensivePlayer) this.teTableView.getSelectionModel().getSelectedItem();
        
        OffensiveStats offensiveStatsForPlayer = offensivePlayer.getPreviousYearStats();
        OffensiveStats projectedStats = offensivePlayer.getProjectedStats();
        
        this.previousTEStats1.clear();
        this.teExaminePlayerColumn1 = new TableColumn<OffensiveStats, String>("Player");
        this.teExaminePlayerColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("displayName"));
        this.teExamineYearColumn1 = new TableColumn<OffensiveStats, String>("Year");
        this.teExamineYearColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("year"));
        this.teExamineGamesColumn1 = new TableColumn<OffensiveStats, Double>("gms");
        this.teExamineGamesColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("games"));
        this.teExamineRushYardsColumn1 = new TableColumn<OffensiveStats, Double>("ryds");
        this.teExamineRushYardsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("rushingYards"));
        this.teExamineRushTDsColumn1 = new TableColumn<OffensiveStats, Double>("rtds");
        this.teExamineRushTDsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("rushingTDs"));
        this.teExamineReceivingYardsColumn1 = new TableColumn<OffensiveStats, Double>("recyds");
        this.teExamineReceivingYardsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("receivingYards"));
        this.teExamineReceivingTDsColumn1 = new TableColumn<OffensiveStats, Double>("rectds");
        this.teExamineReceivingTDsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("receivingTDs"));
        this.teExamineReceptionsColumn1 = new TableColumn<OffensiveStats, Double>("recept");
        this.teExamineReceptionsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("receptions"));
        this.teExamineFantasyPointsColumn1 = new TableColumn<OffensiveStats, Double>("fsy");
        this.teExamineFantasyPointsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("fantasyPoints"));

        TableUtility.formatTableColumnDoubleTableCell(this.teExamineFantasyPointsColumn1);

        this.previousTEStats1.add(projectedStats);
        this.previousTEStats1.add(offensiveStatsForPlayer);
        this.teExamineTableView1.setItems(this.previousTEStats1);
        this.teExamineTableView1.getColumns().setAll(
                this.teExaminePlayerColumn1, 
                this.teExamineYearColumn1,
                this.teExamineGamesColumn1, 
                this.teExamineRushYardsColumn1,
                this.teExamineRushTDsColumn1,
                this.teExamineReceivingYardsColumn1,
                this.teExamineReceivingTDsColumn1,
                this.teExamineReceptionsColumn1,
                this.teExamineFantasyPointsColumn1);
    }    
}
