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

public class WRController
{
    private final ObservableList<Player> wrData = FXCollections.observableArrayList();

    private final ObservableList<OffensiveStats> previousWRStats = FXCollections.observableArrayList();
    private final ObservableList<OffensiveStats> previousWRStats1 = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Player, String> wrPlayerColumn;
    
    @FXML
    private TableColumn<Player, String> wrNFLTeamColumn;

    @FXML 
    private TableColumn<Player, Double> wrFantasyPointsColumn;

    @FXML 
    private TableColumn<Player, Double> wrDiffColumn;

    @FXML
    private TableView<Player> wrTableView;

    @FXML
    private TableColumn<OffensiveStats, String> wrExaminePlayerColumn;
    
    @FXML
    private TableColumn<OffensiveStats, String> wrExamineYearColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> wrExamineGamesColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> wrExamineRushYardsColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> wrExamineRushTDsColumn;
    
    @FXML
    private TableColumn<OffensiveStats, Double> wrExamineReceivingYardsColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> wrExamineReceivingTDsColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> wrExamineReceptionsColumn;

    @FXML
    private TableColumn<OffensiveStats, Double> wrExamineFantasyPointsColumn;
    
    @FXML
    private TableView<OffensiveStats> wrExamineTableView;

    @FXML
    private TableColumn<OffensiveStats, String> wrExaminePlayerColumn1;
    
    @FXML
    private TableColumn<OffensiveStats, String> wrExamineYearColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> wrExamineGamesColumn1;
    
    @FXML
    private TableColumn<OffensiveStats, Double> wrExamineRushYardsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> wrExamineRushTDsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> wrExamineReceivingYardsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> wrExamineReceivingTDsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> wrExamineReceptionsColumn1;

    @FXML
    private TableColumn<OffensiveStats, Double> wrExamineFantasyPointsColumn1;
    
    @FXML
    private TableView<OffensiveStats> wrExamineTableView1;


    public WRController()
    {
        super();
        System.out.println("WRController::WRController()");
        this.wrTableView = new TableView<Player>();
        this.wrTableView.setEditable(true);
        
        updateTableView();

    }

    public void updateTableView()
    {
        this.wrPlayerColumn = new TableColumn<Player, String>("Player");
        this.wrPlayerColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("displayName"));
        this.wrNFLTeamColumn = new TableColumn<Player, String>("Team");
        this.wrNFLTeamColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("nflTeamString"));
        this.wrFantasyPointsColumn = new TableColumn<Player, Double>("fsy");
        this.wrFantasyPointsColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("fantasyPoints"));
        this.wrDiffColumn = new TableColumn<Player, Double>("diff");
        this.wrDiffColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("diffValue"));

        // Attempt to get 1 digit in table cells
        TableUtility.formatTableColumnTableCell(this.wrFantasyPointsColumn);
        
        // Format diff column
        TableUtility.formatTableColumnTableCell(this.wrDiffColumn);
        
        this.wrData.removeAll(this.wrData);
        
        //TODO: get all WRs
        ArrayList<Player> wrs = PlayerPositionCollection.Instance().getPositionCollection(Position.WIDE_RECEIVER);
        if (wrs != null)
        {
            Iterator<Player> i = wrs.iterator();
            while (i.hasNext())
            {
                Player wr = i.next();
                this.wrData.add(wr);
            }
            this.wrTableView.setItems(this.wrData);
            this.wrTableView.getColumns().setAll(this.wrPlayerColumn, this.wrNFLTeamColumn, this.wrFantasyPointsColumn, this.wrDiffColumn);
            TableUtility.formatTableView(this.wrTableView);
        }

    }
    
    @FXML
    private void wrDraftButtonAction(ActionEvent event)
    {
        System.out.println("WR Draft Button Pressed");
        
        Player player = this.wrTableView.getSelectionModel().getSelectedItem();
        
        boolean onMyTeam = true;
        if (player != null)
        {
            DraftUtility.draftOrStrikeoutAction(player, onMyTeam, Position.WIDE_RECEIVER);
            updateTableView();
        }

    }
    
    @FXML
    private void wrStrikeoutButtonAction(ActionEvent event)
    {
        System.out.println("WR Strikeout Button Pressed");

        Player player = this.wrTableView.getSelectionModel().getSelectedItem();
        
        System.out.println("Player selected for strikeout: " + player.getDisplayName());
        boolean onMyTeam = false;
        if (player != null)
        {
            DraftUtility.draftOrStrikeoutAction(player, onMyTeam, Position.WIDE_RECEIVER);
            updateTableView();
        }
    }
    
    @FXML
    private void wrExamineButtonAction(ActionEvent event)
    {
        System.out.println("WR Examine Button Pressed");
        OffensivePlayer offensivePlayer = (OffensivePlayer) this.wrTableView.getSelectionModel().getSelectedItem();

        System.out.println("Attempting to examine stats for: " + offensivePlayer.getDisplayName());

        OffensiveStats offensiveStatsForPlayer = offensivePlayer.getPreviousYearStats();
        OffensiveStats projectedStats = offensivePlayer.getProjectedStats();
        
        this.previousWRStats.clear();
        this.wrExaminePlayerColumn = new TableColumn<OffensiveStats, String>("Player");
        this.wrExaminePlayerColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("displayName"));
        this.wrExamineYearColumn = new TableColumn<OffensiveStats, String>("Year");
        this.wrExamineYearColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("year"));
        this.wrExamineGamesColumn = new TableColumn<OffensiveStats, Double>("gms");
        this.wrExamineGamesColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("games"));
        this.wrExamineRushYardsColumn = new TableColumn<OffensiveStats, Double>("ryds");
        this.wrExamineRushYardsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("rushingYards"));
        this.wrExamineRushTDsColumn = new TableColumn<OffensiveStats, Double>("rtds");
        this.wrExamineRushTDsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("rushingTDs"));
        this.wrExamineReceivingYardsColumn = new TableColumn<OffensiveStats, Double>("recyds");
        this.wrExamineReceivingYardsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("receivingYards"));
        this.wrExamineReceivingTDsColumn = new TableColumn<OffensiveStats, Double>("rectds");
        this.wrExamineReceivingTDsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("receivingTDs"));
        this.wrExamineReceptionsColumn = new TableColumn<OffensiveStats, Double>("recept");
        this.wrExamineReceptionsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("receptions"));
        this.wrExamineFantasyPointsColumn = new TableColumn<OffensiveStats, Double>("fsy");
        this.wrExamineFantasyPointsColumn.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("fantasyPoints"));

        TableUtility.formatTableColumnDoubleTableCell(this.wrExamineFantasyPointsColumn);

        this.previousWRStats.add(projectedStats);
        this.previousWRStats.add(offensiveStatsForPlayer);
        this.wrExamineTableView.setItems(this.previousWRStats);
        this.wrExamineTableView.getColumns().setAll(
                this.wrExaminePlayerColumn, 
                this.wrExamineYearColumn,
                this.wrExamineGamesColumn, 
                this.wrExamineRushYardsColumn,
                this.wrExamineRushTDsColumn,
                this.wrExamineReceivingYardsColumn,
                this.wrExamineReceivingTDsColumn,
                this.wrExamineReceptionsColumn,
                this.wrExamineFantasyPointsColumn);
        
    }

    @FXML
    private void wrExamineButton1Action(ActionEvent event)
    {
        System.out.println("WR Examine 1 Button Pressed");
        OffensivePlayer offensivePlayer = (OffensivePlayer) this.wrTableView.getSelectionModel().getSelectedItem();
        
        OffensiveStats offensiveStatsForPlayer = offensivePlayer.getPreviousYearStats();
        OffensiveStats projectedStats = offensivePlayer.getProjectedStats();
        
        this.previousWRStats1.clear();
        this.wrExaminePlayerColumn1 = new TableColumn<OffensiveStats, String>("Player");
        this.wrExaminePlayerColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("displayName"));
        this.wrExamineYearColumn1 = new TableColumn<OffensiveStats, String>("Year");
        this.wrExamineYearColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, String>("year"));
        this.wrExamineGamesColumn1 = new TableColumn<OffensiveStats, Double>("gms");
        this.wrExamineGamesColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("games"));
        this.wrExamineRushYardsColumn1 = new TableColumn<OffensiveStats, Double>("ryds");
        this.wrExamineRushYardsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("rushingYards"));
        this.wrExamineRushTDsColumn1 = new TableColumn<OffensiveStats, Double>("rtds");
        this.wrExamineRushTDsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("rushingTDs"));
        this.wrExamineReceivingYardsColumn1 = new TableColumn<OffensiveStats, Double>("recyds");
        this.wrExamineReceivingYardsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("receivingYards"));
        this.wrExamineReceivingTDsColumn1 = new TableColumn<OffensiveStats, Double>("rectds");
        this.wrExamineReceivingTDsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("receivingTDs"));
        this.wrExamineReceptionsColumn1 = new TableColumn<OffensiveStats, Double>("recept");
        this.wrExamineReceptionsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("receptions"));
        this.wrExamineFantasyPointsColumn1 = new TableColumn<OffensiveStats, Double>("fsy");
        this.wrExamineFantasyPointsColumn1.setCellValueFactory(new PropertyValueFactory<OffensiveStats, Double>("fantasyPoints"));

        TableUtility.formatTableColumnDoubleTableCell(this.wrExamineFantasyPointsColumn1);

        this.previousWRStats1.add(projectedStats);
        this.previousWRStats1.add(offensiveStatsForPlayer);
        this.wrExamineTableView1.setItems(this.previousWRStats1);
        this.wrExamineTableView1.getColumns().setAll(
                this.wrExaminePlayerColumn1, 
                this.wrExamineYearColumn1,
                this.wrExamineGamesColumn1, 
                this.wrExamineRushYardsColumn1,
                this.wrExamineRushTDsColumn1,
                this.wrExamineReceivingYardsColumn1,
                this.wrExamineReceivingTDsColumn1,
                this.wrExamineReceptionsColumn1,
                this.wrExamineFantasyPointsColumn1);
    }
}
