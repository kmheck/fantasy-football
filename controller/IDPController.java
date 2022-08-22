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

public class IDPController
{
    private final ObservableList<Player> idpData = FXCollections.observableArrayList();
    private final ObservableList<IDPStats> previousIDPStats = FXCollections.observableArrayList();
    private final ObservableList<IDPStats> previousIDPStats1 = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Player, String> idpPlayerColumn;
    
    @FXML 
    private TableColumn<Player, Double> idpFantasyPointsColumn;

    @FXML 
    private TableColumn<Player, Double> idpDiffColumn;

    @FXML
    private TableView<Player> idpTableView;

    @FXML
    private TableColumn<IDPStats, String> idpExaminePlayerColumn;
    
    @FXML
    private TableColumn<IDPStats, String> idpExamineYearColumn;

    @FXML
    private TableColumn<IDPStats, Double> idpExamineGamesColumn;
    
    @FXML
    private TableColumn<IDPStats, Double> idpExamineTacklesColumn;

    @FXML
    private TableColumn<IDPStats, Double> idpExamineAssistsColumn;
    
    @FXML
    private TableColumn<IDPStats, Double> idpExamineSacksColumn;

    @FXML
    private TableColumn<IDPStats, Double> idpExamineInterceptionsColumn;

    @FXML
    private TableColumn<IDPStats, Double> idpExamineFumbleRecoveriesColumn;

    @FXML
    private TableColumn<IDPStats, Double> idpExamineTDsColumn;

    @FXML
    private TableColumn<IDPStats, Double> idpExamineFantasyPointsColumn;
    
    @FXML
    private TableView<IDPStats> idpExamineTableView;

    @FXML
    private TableColumn<IDPStats, String> idpExaminePlayerColumn1;
    
    @FXML
    private TableColumn<IDPStats, String> idpExamineYearColumn1;

    @FXML
    private TableColumn<IDPStats, Double> idpExamineGamesColumn1;
    
    @FXML
    private TableColumn<IDPStats, Double> idpExamineTacklesColumn1;

    @FXML
    private TableColumn<IDPStats, Double> idpExamineAssistsColumn1;
    
    @FXML
    private TableColumn<IDPStats, Double> idpExamineSacksColumn1;

    @FXML
    private TableColumn<IDPStats, Double> idpExamineInterceptionsColumn1;

    @FXML
    private TableColumn<IDPStats, Double> idpExamineFumbleRecoveriesColumn1;

    @FXML
    private TableColumn<IDPStats, Double> idpExamineTDsColumn1;

    @FXML
    private TableColumn<IDPStats, Double> idpExamineFantasyPointsColumn1;
    
    @FXML
    private TableView<IDPStats> idpExamineTableView1;


    public IDPController()
    {
        super();
        System.out.println("IDPController::IDPController()");
        this.idpTableView = new TableView<Player>();
        this.idpTableView.setEditable(true);
        
        updateTableView();

    }

    public void updateTableView()
    {
        this.idpPlayerColumn = new TableColumn<Player, String>("Player");
        this.idpPlayerColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("displayName"));
        this.idpFantasyPointsColumn = new TableColumn<Player, Double>("fsy");
        this.idpFantasyPointsColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("fantasyPoints"));
        this.idpDiffColumn = new TableColumn<Player, Double>("diff");
        this.idpDiffColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("diffValue"));

        // Attempt to get 1 digit in table cells
        TableUtility.formatTableColumnTableCell(this.idpFantasyPointsColumn);
        
        // Format diff column
        TableUtility.formatTableColumnTableCell(this.idpDiffColumn);
        
        
        this.idpData.removeAll(this.idpData);
        ArrayList<Player> idps = PlayerPositionCollection.Instance().getPositionCollection(Position.INDIVIDUAL_DEFENSIVE_PLAYER);
        if (idps != null)
        {
            Iterator<Player> i = idps.iterator();
            while (i.hasNext())
            {
                Player idp = i.next();
                idpData.add(idp);
            }
            this.idpTableView.setItems(this.idpData);
            this.idpTableView.getColumns().setAll(this.idpPlayerColumn, this.idpFantasyPointsColumn, this.idpDiffColumn);
            TableUtility.formatTableView(this.idpTableView);
        }

    }
    
    @FXML
    private void idpDraftButtonAction(ActionEvent event)
    {
        System.out.println("IDP Draft Button Pressed");
        
       Player player = this.idpTableView.getSelectionModel().getSelectedItem();
        
       boolean onMyTeam = true;
       if (player != null)
       {
           DraftUtility.draftOrStrikeoutAction(player, onMyTeam, Position.INDIVIDUAL_DEFENSIVE_PLAYER);
           updateTableView();
       }

    }
    
    @FXML
    private void idpStrikeoutButtonAction(ActionEvent event)
    {
        System.out.println("IDP Strikeout Button Pressed");

       Player player = this.idpTableView.getSelectionModel().getSelectedItem();
        
       boolean onMyTeam = false;
       if (player != null)
       {
           DraftUtility.draftOrStrikeoutAction(player, onMyTeam, Position.INDIVIDUAL_DEFENSIVE_PLAYER);
           updateTableView();
       }
    }
    @FXML
    private void idpExamineButtonAction(ActionEvent event)
    {
        System.out.println("IDP Examine Button Pressed");
        IndividualDefensivePlayer idp = (IndividualDefensivePlayer) this.idpTableView.getSelectionModel().getSelectedItem();

        System.out.println("Attempting to examine stats for: " + idp.getDisplayName());

        IDPStats defensiveStatsForPlayer = idp.getPreviousYearStats();
        IDPStats projectedStats = idp.getProjectedStats();
        
        this.previousIDPStats.clear();
        this.idpExaminePlayerColumn = new TableColumn<IDPStats, String>("Player");
        this.idpExaminePlayerColumn.setCellValueFactory(new PropertyValueFactory<IDPStats, String>("displayName"));
        this.idpExamineYearColumn = new TableColumn<IDPStats, String>("year");
        this.idpExamineYearColumn.setCellValueFactory(new PropertyValueFactory<IDPStats, String>("year"));
        this.idpExamineGamesColumn = new TableColumn<IDPStats, Double>("gms");
        this.idpExamineGamesColumn.setCellValueFactory(new PropertyValueFactory<IDPStats, Double>("games"));
        this.idpExamineTacklesColumn = new TableColumn<IDPStats, Double>("tack");
        this.idpExamineTacklesColumn.setCellValueFactory(new PropertyValueFactory<IDPStats, Double>("tackles"));
        this.idpExamineAssistsColumn = new TableColumn<IDPStats, Double>("asst");
        this.idpExamineAssistsColumn.setCellValueFactory(new PropertyValueFactory<IDPStats, Double>("assists"));
        this.idpExamineSacksColumn = new TableColumn<IDPStats, Double>("sack");
        this.idpExamineSacksColumn.setCellValueFactory(new PropertyValueFactory<IDPStats, Double>("sacks"));
        this.idpExamineInterceptionsColumn = new TableColumn<IDPStats, Double>("ints");
        this.idpExamineInterceptionsColumn.setCellValueFactory(new PropertyValueFactory<IDPStats, Double>("interceptions"));
        this.idpExamineFumbleRecoveriesColumn = new TableColumn<IDPStats, Double>("frs");
        this.idpExamineFumbleRecoveriesColumn.setCellValueFactory(new PropertyValueFactory<IDPStats, Double>("fumbleRecoveries"));
        this.idpExamineTDsColumn = new TableColumn<IDPStats, Double>("tds");
        this.idpExamineTDsColumn.setCellValueFactory(new PropertyValueFactory<IDPStats, Double>("TDs"));
        this.idpExamineFantasyPointsColumn = new TableColumn<IDPStats, Double>("fsy");
        this.idpExamineFantasyPointsColumn.setCellValueFactory(new PropertyValueFactory<IDPStats, Double>("fantasyPoints"));

        TableUtility.formatIDPTableColumnDoubleTableCell(this.idpExamineFantasyPointsColumn);

        this.previousIDPStats.add(projectedStats);
        this.previousIDPStats.add(defensiveStatsForPlayer);
        this.idpExamineTableView.setItems(this.previousIDPStats);
        this.idpExamineTableView.getColumns().setAll(
                this.idpExaminePlayerColumn, 
                this.idpExamineYearColumn,
                this.idpExamineGamesColumn, 
                this.idpExamineTacklesColumn,
                this.idpExamineAssistsColumn,
                this.idpExamineSacksColumn,
                this.idpExamineInterceptionsColumn,
                this.idpExamineFumbleRecoveriesColumn,
                this.idpExamineTDsColumn,
                this.idpExamineFantasyPointsColumn);
        
    }

    @FXML
    private void idpExamineButton1Action(ActionEvent event)
    {
        System.out.println("IDP Examine 1 Button Pressed");
        IndividualDefensivePlayer idp = (IndividualDefensivePlayer) this.idpTableView.getSelectionModel().getSelectedItem();
        
        IDPStats defensiveStatsForPlayer = idp.getPreviousYearStats();
        IDPStats projectedStats = idp.getProjectedStats();
        
        this.previousIDPStats1.clear();
        this.idpExaminePlayerColumn1 = new TableColumn<IDPStats, String>("Player");
        this.idpExaminePlayerColumn1.setCellValueFactory(new PropertyValueFactory<IDPStats, String>("displayName"));
        this.idpExamineYearColumn1 = new TableColumn<IDPStats, String>("year");
        this.idpExamineYearColumn1.setCellValueFactory(new PropertyValueFactory<IDPStats, String>("year"));
        this.idpExamineGamesColumn1 = new TableColumn<IDPStats, Double>("gms");
        this.idpExamineGamesColumn1.setCellValueFactory(new PropertyValueFactory<IDPStats, Double>("games"));
        this.idpExamineTacklesColumn1 = new TableColumn<IDPStats, Double>("tack");
        this.idpExamineTacklesColumn1.setCellValueFactory(new PropertyValueFactory<IDPStats, Double>("tackles"));
        this.idpExamineAssistsColumn1 = new TableColumn<IDPStats, Double>("asst");
        this.idpExamineAssistsColumn1.setCellValueFactory(new PropertyValueFactory<IDPStats, Double>("assists"));
        this.idpExamineSacksColumn1 = new TableColumn<IDPStats, Double>("sack");
        this.idpExamineSacksColumn1.setCellValueFactory(new PropertyValueFactory<IDPStats, Double>("sacks"));
        this.idpExamineInterceptionsColumn1 = new TableColumn<IDPStats, Double>("ints");
        this.idpExamineInterceptionsColumn1.setCellValueFactory(new PropertyValueFactory<IDPStats, Double>("interceptions"));
        this.idpExamineFumbleRecoveriesColumn1 = new TableColumn<IDPStats, Double>("frs");
        this.idpExamineFumbleRecoveriesColumn1.setCellValueFactory(new PropertyValueFactory<IDPStats, Double>("fumbleRecoveries"));
        this.idpExamineTDsColumn1 = new TableColumn<IDPStats, Double>("tds");
        this.idpExamineTDsColumn1.setCellValueFactory(new PropertyValueFactory<IDPStats, Double>("TDs"));
        this.idpExamineFantasyPointsColumn1 = new TableColumn<IDPStats, Double>("fsy");
        this.idpExamineFantasyPointsColumn1.setCellValueFactory(new PropertyValueFactory<IDPStats, Double>("fantasyPoints"));

        TableUtility.formatIDPTableColumnDoubleTableCell(this.idpExamineFantasyPointsColumn1);

        this.previousIDPStats1.add(projectedStats);
        this.previousIDPStats1.add(defensiveStatsForPlayer);
        this.idpExamineTableView1.setItems(this.previousIDPStats1);
        this.idpExamineTableView1.getColumns().setAll(
                this.idpExaminePlayerColumn1, 
                this.idpExamineYearColumn1,
                this.idpExamineGamesColumn1, 
                this.idpExamineTacklesColumn1,
                this.idpExamineAssistsColumn1,
                this.idpExamineSacksColumn1,
                this.idpExamineInterceptionsColumn1,
                this.idpExamineFumbleRecoveriesColumn1,
                this.idpExamineTDsColumn1,
                this.idpExamineFantasyPointsColumn1);
    }

}