package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import model.ConfigurationData;
import model.LeagueScoring;
import model.Roster;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;


public class MainController implements Initializable {

    @FXML private TabPane mainTabPane;
    @FXML private QBController qbController;
    @FXML private RBController rbController;
    @FXML private WRController wrController;
    @FXML private TEController teController;
    @FXML private KController  kController;
    @FXML private TeamDefenseController teamDefenseController;
    @FXML private IDPController idpController;
    @FXML private DraftController draftController;
    @FXML private PicksController picksController;
    @FXML private ScoringController scoringController;
    @FXML private SetupController setupController;
    @FXML private RosterController rosterController;
    @FXML private ProjectionsFileController projectionsFileController;
    @FXML private Tab draftTab;

    private String externallySelectedTab;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        System.out.println("MainController.initialize()");
//        FXMLLoader qbLoader = new FXMLLoader(getClass().getResource("/view/QB.fxml"));
//        try
//        {
//            qbLoader.load();
//            this.qbController = qbLoader.getController();
//        }
//        catch (IOException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
        // Insure that configuration data is read.
        ConfigurationData.Instance();
        setupController.setMainController(this);
        mainTabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab oldTab, Tab newTab) {
                        System.out.println("Tab Selection changed - " + "oldTab: " + oldTab.getText() + " newTab: " + newTab.getText());
                        if (newTab.getText().contains("QB"))
                        {
                            System.out.println("Attempting to call qbController.updateTableView()");
                            if (qbController != null)
                            {
                              qbController.updateTableView();
                            }
                            else
                            {
                                System.out.println("qbController is null...cannot call updateTableView()");
                            }
                        }
                        else if (newTab.getText().contains("RB"))
                        {
                            System.out.println("Attempting to call rbController.updateTableView()");
                            if (rbController != null)
                            {
                              rbController.updateTableView();
                            }
                            else
                            {
                                System.out.println("rbController is null...cannot call updateTableView()");
                            }
                        }
                        else if (newTab.getText().contains("WR"))
                        {
                            System.out.println("Attempting to call wrController.updateTableView()");
                            if (wrController != null)
                            {
                              wrController.updateTableView();
                            }
                            else
                            {
                                System.out.println("wrController is null...cannot call updateTableView()");
                            }
                        }
                        else if (newTab.getText().contentEquals("TE"))
                        {
                            System.out.println("Attempting to call teController.updateTableView()");
                            if (teController != null)
                            {
                              teController.updateTableView();
                            }
                            else
                            {
                                System.out.println("teController is null...cannot call updateTableView()");
                            }
                        }
                        else if (newTab.getText().contentEquals("K"))
                        {
                            System.out.println("Attempting to call kController.updateTableView()");
                            if (kController != null)
                            {
                              kController.updateTableView();
                            }
                            else
                            {
                                System.out.println("kController is null...cannot call updateTableView()");
                            }
                        }
                        else if (newTab.getText().contains("Def"))
                        {
                            System.out.println("Attempting to call teamDefenseController.updateTableView()");
                            if (teamDefenseController != null)
                            {
                              teamDefenseController.updateTableView();
                            }
                            else
                            {
                                System.out.println("teamDefenseController is null...cannot call updateTableView()");
                            }
                        }
                        else if (newTab.getText().contains("IDP"))
                        {
                            System.out.println("Attempting to call idpController.updateTableView()");
                            if (idpController != null)
                            {
                              idpController.updateTableView();
                            }
                            else
                            {
                                System.out.println("idpController is null...cannot call updateTableView()");
                            }
                        }
                        else if (newTab.getText().contains("Draft"))
                        {
                            if (draftController != null)
                            {
                                if (draftController.relativeDiffIsSelected())
                                {
                                    draftController.updateTableViewWithRelativeDiffValues();
                                }
                                else
                                {
                                    draftController.updateTableView();
                                }
                            }
                            else
                            {
                                System.out.println("draftController is null...cannot call updateTableView()");
                            }
                        }
                        else if (newTab.getText().contains("Picks"))
                        {
                            if (picksController != null)
                            {
                                picksController.updateTableView();
                            }
                            else
                            {
                                System.out.println("picksController is null...cannot call updateTableView()");
                            }
                        }
                        else if (newTab.getText().contains("Scoring"))
                        {
                            if (scoringController != null)
                            {
                                scoringController.fillTextBoxesWithLeagueScoringParameters(LeagueScoring.Instance().getScoringParameters());
                            }
                            else
                            {
                                System.out.println("scoringController is null...cannot call methods");
                            }
                        }
                        else if (newTab.getText().contains("Roster"))
                        {
                            if (rosterController != null)
                            {
                                rosterController.fillTextBoxesWithLeagueRosterParameters(Roster.Instance().getRosterParameters());
                            }
                            else
                            {
                                System.out.println("rosterController is null...cannot call methods");
                            }
                        }
                        else if (newTab.getText().contains("Projections"))
                        {
                            if (projectionsFileController != null)
                            {
                                projectionsFileController.fillTextBoxes();
                            }
                            else
                            {
                                System.out.println("projectionsFileController is null...cannot call methods");
                            }
                        }
                        else
                        {
                            System.out.println("No controller to update for this tab: " + newTab.getText());
                        }
                    }
                }
            );    
   }




    public void setExternallySelectedTab(String externallySelectedTab)
    {
        this.externallySelectedTab = externallySelectedTab;
        //TODO: Create big if statement to select different tabs.
        // For now, only going to draft tab.
        mainTabPane.getSelectionModel().select(draftTab);
    }
}
