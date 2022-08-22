package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import model.AllPlayerCollection;
import model.DraftManager;
import model.DraftUtility;
import model.DraftedPlayerCollection;
import model.Player;
import model.PlayerPositionCollection;
import model.Position;
import model.TableUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DraftController
{

    private final ObservableList<Player> topPlayerData = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Player, String> topPlayerNameColumn;
    
    @FXML 
    private TableColumn<Player, Double> topPlayerFantasyPointsColumn;

    @FXML 
    private TableColumn<Player, Double> topPlayerDiffColumn;

    @FXML
    private TableView<Player> topPlayerTableView;
    
    @FXML
    private TextField pickCountTextField = new TextField();
    
    @FXML 
    private RadioButton draftRelativeDiffRadioButton;
    

    public boolean relativeDiffIsSelected()
    {
        return this.draftRelativeDiffRadioButton.isSelected();
    }
    
    public DraftController()
    {
        super();
        System.out.println("DraftController::DraftController()");
        this.topPlayerTableView = new TableView<Player>();
        this.topPlayerTableView.setEditable(true);
        
        
        updateTableView();

    }

    
    public void updateTableView()
    {
        System.out.println("DraftController.updateTableView()");
        
        this.topPlayerNameColumn = new TableColumn<Player, String>("Player");
        this.topPlayerNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("displayName"));
        this.topPlayerFantasyPointsColumn = new TableColumn<Player, Double>("fsy");
        this.topPlayerFantasyPointsColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("fantasyPoints"));
        this.topPlayerDiffColumn = new TableColumn<Player, Double>("diff");
        this.topPlayerDiffColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("diffValue"));

        this.topPlayerData.removeAll(this.topPlayerData);

        // Attempt to get 1 digit in table cells
        TableUtility.formatTableColumnTableCell(this.topPlayerFantasyPointsColumn);
        
        // Format diff column
        TableUtility.formatTableColumnTableCell(this.topPlayerDiffColumn);
        
        ArrayList<Player> players = AllPlayerCollection.Instance().getCollection();
        if (players != null)
        {
            Iterator<Player> i = players.iterator();
            while (i.hasNext())
            {
                Player player = i.next();
                topPlayerData.add(player);
            }
            this.topPlayerTableView.setItems(topPlayerData);
            this.topPlayerTableView.getColumns().setAll(this.topPlayerNameColumn, this.topPlayerFantasyPointsColumn, this.topPlayerDiffColumn);
        }

        String pickCountString = Integer.toString(DraftManager.Instance().getPickCount());
        this.pickCountTextField.setText(pickCountString);
    }

    public void updateTableViewWithRelativeDiffValues()
    {
        System.out.println("DraftController.updateTableViewWithRelativeDiffValues()");
        
        this.topPlayerNameColumn = new TableColumn<Player, String>("Player");
        this.topPlayerNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("displayName"));
        this.topPlayerFantasyPointsColumn = new TableColumn<Player, Double>("fsy");
        this.topPlayerFantasyPointsColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("fantasyPoints"));
        this.topPlayerDiffColumn = new TableColumn<Player, Double>("rdiff");
        this.topPlayerDiffColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("relativeDiffValue"));

        this.topPlayerData.removeAll(this.topPlayerData);

        // Attempt to get 1 digit in table cells
        TableUtility.formatTableColumnTableCell(this.topPlayerFantasyPointsColumn);
        
        // Format diff column
        TableUtility.formatTableColumnTableCell(this.topPlayerDiffColumn);
        
        ArrayList<Player> players = AllPlayerCollection.Instance().getRelativeDiffPlayers();
        if (players != null)
        {
            Iterator<Player> i = players.iterator();
            while (i.hasNext())
            {
                Player player = i.next();
                topPlayerData.add(player);
            }
            this.topPlayerTableView.setItems(topPlayerData);
            this.topPlayerTableView.getColumns().setAll(this.topPlayerNameColumn, this.topPlayerFantasyPointsColumn, this.topPlayerDiffColumn);
        }

        String pickCountString = Integer.toString(DraftManager.Instance().getPickCount());
        this.pickCountTextField.setText(pickCountString);
    }

    @FXML
    private void topPlayerDraftButtonAction(ActionEvent event)
    {
        System.out.println("DraftController.topPlayerDraftButtonAction() - Top Player Draft Button Pressed");
        Player player = this.topPlayerTableView.getSelectionModel().getSelectedItem();
        
        if (player != null)
        {
            Player matchedPlayer = PlayerPositionCollection.Instance().findMatchingPlayer(player, player.getPlayerPosition());
            if (matchedPlayer != null)
            {
                System.out.println("DraftController.topPlayerDraftButtonAction() - Found player: " + matchedPlayer.toString());
                DraftUtility.draftOrStrikeoutAction(matchedPlayer, true, player.getPlayerPosition());
            }
            else
            {
                System.out.println("DraftController.topPlayerDraftButtonAction() - Unable to find player: " + player.toString());
            }
        }
        updateTableView();
    }
    
    @FXML
    private void topPlayerStrikeoutButtonAction(ActionEvent event)
    {
        System.out.println("DraftController.topPlayerStrikeoutButtonAction() - Top Player Strikeout Button Pressed");

        Player player = this.topPlayerTableView.getSelectionModel().getSelectedItem();
        
        if (player != null)
        {
            Player matchedPlayer = PlayerPositionCollection.Instance().findMatchingPlayer(player, player.getPlayerPosition());
            if (matchedPlayer != null)
            {
                System.out.println("DraftController.topPlayerStrikeoutButtonAction() - Found player: " + matchedPlayer.toString());
                matchedPlayer.setDrafted(true);
                DraftUtility.draftOrStrikeoutAction(matchedPlayer, false, player.getPlayerPosition());
            }
            else
            {
                System.out.println("DraftController.topPlayerStrikeoutButtonAction() - Unable to find player: " + player.toString());
            }
        }
        updateTableView();
    }

    @FXML
    private void draftUndoButtonAction(ActionEvent event)
    {
    	System.out.println("DraftController.draftUndoButtonAction - Draft Undo button pressed");
    	
    	DraftManager.Instance().undoPick();
    	updateTableView();
    }
    
    @FXML
    private void draftRelativeDiffRadioButtonAction(ActionEvent event)
    {
        System.out.println("DraftController.draftRelativeDiffRadioButtonAction - Draft Relative Diff radio button pressed");
        System.out.println("relative diff selected: " + this.draftRelativeDiffRadioButton.isSelected());
        if (this.draftRelativeDiffRadioButton.isSelected())
        {
            updateTableViewWithRelativeDiffValues();
        }
        else
        {
            updateTableView();
        }
    }
    
    @FXML
    private void saveDraftButtonAction(ActionEvent event)
    {
        DraftManager.Instance().saveDraft(true);
    }
    
    @FXML
    private void restoreDraftButtonAction(ActionEvent event)
    {
        DraftManager.Instance().restoreDraft();
        updateTableView();
    }
    
    @FXML
    private void generateTopPlayerFileButtonAction(ActionEvent event)
    {
        
        BufferedWriter bw = null;
        FileWriter fw = null;

        try
        {

            fw = new FileWriter("resources/top_players.txt");
            bw = new BufferedWriter(fw);

            for (int i = 0; i < 100; i++)
            {
                this.topPlayerTableView.getSelectionModel().selectFirst();
                Player player = this.topPlayerTableView.getSelectionModel().getSelectedItem();

                if (player != null)
                {
                    Player matchedPlayer = PlayerPositionCollection.Instance().findMatchingPlayer(player,
                            player.getPlayerPosition());
                    if (matchedPlayer != null)
                    {
                        System.out.println("DraftController.topPlayerDraftButtonAction() - Found player: "
                                + matchedPlayer.toString());
                        bw.write(player.getDisplayName() + " " + player.getDisplayPosition() + "\n");
                        DraftUtility.draftOrStrikeoutAction(matchedPlayer, false, player.getPlayerPosition());
                    }
                    else
                    {
                        System.out.println("DraftController.topPlayerDraftButtonAction() - Unable to find player: "
                                + player.toString());
                    }
                }
                updateTableView();

            }

            System.out.println("Done");

        }
        catch (IOException e)
        {

            e.printStackTrace();

        }
        finally
        {

            try
            {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            }
            catch (IOException ex)
            {

                ex.printStackTrace();

            }
        }
    }
}
