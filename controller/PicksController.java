package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import model.DraftedPlayerCollection;
import model.OffensivePlayer;
import model.Player;
import model.Position;
import model.Roster;
import model.TableUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PicksController implements Initializable
{

    private final ObservableList<Player> picksData = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Player, String> picksPositionColumn;

    @FXML
    private TableColumn<Player, String> picksNameColumn;
    
    @FXML 
    private TableColumn<Player, Double> picksFantasyPointsColumn;

    @FXML 
    private TableColumn<Player, Double> picksDiffColumn;

    @FXML
    private TableView<Player> picksTableView;

    public PicksController()
    {
        super();
        System.out.println("PicksController::PicksController()");
        this.picksTableView = new TableView<Player>();
        this.picksTableView.setEditable(true);
        
        updateTableView();

    }

    public void updateTableView()
    {
        this.picksPositionColumn = new TableColumn<Player, String>("Pos");
        this.picksPositionColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("displayPosition"));
        this.picksNameColumn = new TableColumn<Player, String>("Player");
        this.picksNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("displayName"));
        this.picksFantasyPointsColumn = new TableColumn<Player, Double>("Fsy");
        this.picksFantasyPointsColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("fantasyPoints"));
        this.picksDiffColumn = new TableColumn<Player, Double>("Diff");
        this.picksDiffColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("diffValue"));

        // Attempt to get 1 digit in table cells
        TableUtility.formatTableColumnTableCell(this.picksFantasyPointsColumn);
        
        // Format diff column
        TableUtility.formatTableColumnTableCell(this.picksDiffColumn);
        
        
        //TODO: get all picks
        assemblePicksForDisplay();
        System.out.println("Adding picksData of size: " + this.picksData.size() + " to picksTableView...");
        this.picksTableView.setItems(this.picksData);
        this.picksTableView.getColumns().setAll(this.picksPositionColumn, this.picksNameColumn, this.picksFantasyPointsColumn, this.picksDiffColumn);

    }

    public void assemblePicksForDisplay()
    {
        //Go through positions in order of QB,RB,WR,TE,K,Def,IDP and
        //add all drafted players at each position.  Insert "empty" players
        //at each position that does not meet league roster requirements.
        System.out.println("PicksController::assemblePicksForDisplay()");
        this.picksData.clear();
        ArrayList<Player> playersAtPosition;
        for (Position position : Position.values())
        {
            if (position != Position.UNKNOWN && position != Position.FLEX)
            {
                int numberOfPlayers = 0;
                playersAtPosition = DraftedPlayerCollection.Instance().getPositionCollection(position);
                if (playersAtPosition != null)
                {
                    numberOfPlayers = playersAtPosition.size();
                    Iterator<Player> playerIter = playersAtPosition.iterator();
                    while (playerIter.hasNext())
                    {
                        Player player = playerIter.next();
                        this.picksData.add(player);
                    }
                }
                int numberOfPlayersLeftToFill = Roster.Instance().getNumberOfStarters(position) - numberOfPlayers;
                System.out.println("PicksController::assemblePicksForDisplay() - numberOfPlayersLeftToFill: " + 
                  numberOfPlayersLeftToFill + " at position: " + position.toString());
                for (int i = 0; i < numberOfPlayersLeftToFill; i++)
                {
                    Player emptyPlayer = new OffensivePlayer(position);
                    System.out.println("Adding empty player: " + emptyPlayer.toString() + " to picksData");
                    this.picksData.add(emptyPlayer);
                }
            }
            else
            {
                //TODO: Add flex player position when not yet filled?
            }
        }
    }

    @FXML
    private void refreshButtonAction(ActionEvent event)
    {
        System.out.println("Refresh Button Pressed");
        
        updateTableView();

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        // TODO Auto-generated method stub
        System.out.println("PicksController::initialize()");
    }
}

