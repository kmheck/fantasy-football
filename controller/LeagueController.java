package controller;

import java.util.EnumMap;
import java.util.Set;

import model.Position;
import model.Scoring;
import model.ScoringCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LeagueController
{
    @FXML private TextField points_per_rushing_yard_text_box;
    @FXML private TextField points_per_receiving_yard_text_box;
    @FXML private TextField points_per_rushing_td_text_box;
    @FXML private TextField points_per_receiving_td_text_box;
    @FXML private TextField points_per_passing_yard_text_box;
    @FXML private TextField points_per_passing_td_text_box;
    @FXML private TextField points_per_interception_text_box;
    @FXML private TextField points_per_field_goal_text_box;
    @FXML private TextField points_per_extra_point_text_box;

    Scoring leagueScoring = new Scoring();
    @FXML
    private void leagueScoringButtonAction(ActionEvent event)
    {
        
        //TODO: Modify League class in model with values in textboxes.
        System.out.println("League Scoring Button Pressed");
        setRunningbackScoring();
        setQuarterbackScoring();
        setWidereceiverScoring();
        setTightendScoring();
        setKickerScoring();
    }
    
    private void setRunningbackScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();
        
        String ppRushYard = points_per_rushing_yard_text_box.getText();
        points_per_category.put(ScoringCategory.RUSHING_YARDS, Double.valueOf(ppRushYard));

        String ppRushTD = points_per_rushing_td_text_box.getText();
        points_per_category.put(ScoringCategory.RUSHING_TDS, Double.valueOf(ppRushTD));

        String ppRecvTD = points_per_receiving_td_text_box.getText();
        points_per_category.put(ScoringCategory.RECEIVING_TDS, Double.valueOf(ppRecvTD));

        String ppRecvYard = points_per_receiving_yard_text_box.getText();
        points_per_category.put(ScoringCategory.RECEIVING_YARDS, Double.valueOf(ppRecvYard));

        
        leagueScoring.setScoring(Position.RUNNING_BACK, points_per_category);
        
        System.out.println("Runningback scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = leagueScoring.getScoring(Position.RUNNING_BACK, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();
        
        
    }

    private void setQuarterbackScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();
        
        String ppRushYard = points_per_rushing_yard_text_box.getText();
        points_per_category.put(ScoringCategory.RUSHING_YARDS, Double.valueOf(ppRushYard));

        String ppRushTD = points_per_rushing_td_text_box.getText();
        points_per_category.put(ScoringCategory.RUSHING_TDS, Double.valueOf(ppRushTD));

        String ppPassTD = points_per_receiving_td_text_box.getText();
        points_per_category.put(ScoringCategory.PASSING_TDS, Double.valueOf(ppPassTD));

        String ppPassYard = points_per_receiving_yard_text_box.getText();
        points_per_category.put(ScoringCategory.PASSING_YARDS, Double.valueOf(ppPassYard));

        String ppInt = points_per_interception_text_box.getText();
        points_per_category.put(ScoringCategory.INTERCEPTIONS, Double.valueOf(ppInt));
        
        leagueScoring.setScoring(Position.QUARTER_BACK, points_per_category);
        
        System.out.println("Quarterback scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = leagueScoring.getScoring(Position.QUARTER_BACK, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();
        
        
    }
    
    private void setWidereceiverScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();
        
        String ppRushYard = points_per_rushing_yard_text_box.getText();
        points_per_category.put(ScoringCategory.RUSHING_YARDS, Double.valueOf(ppRushYard));

        String ppRushTD = points_per_rushing_td_text_box.getText();
        points_per_category.put(ScoringCategory.RUSHING_TDS, Double.valueOf(ppRushTD));

        String ppRecvTD = points_per_receiving_td_text_box.getText();
        points_per_category.put(ScoringCategory.RECEIVING_TDS, Double.valueOf(ppRecvTD));

        String ppRecvYard = points_per_receiving_yard_text_box.getText();
        points_per_category.put(ScoringCategory.RECEIVING_YARDS, Double.valueOf(ppRecvYard));

        
        leagueScoring.setScoring(Position.WIDE_RECEIVER, points_per_category);
        
        System.out.println("Widereceiver scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = leagueScoring.getScoring(Position.WIDE_RECEIVER, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();
        
        
    }

    private void setTightendScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();
        
        String ppRecvTD = points_per_receiving_td_text_box.getText();
        points_per_category.put(ScoringCategory.RECEIVING_TDS, Double.valueOf(ppRecvTD));

        String ppRecvYard = points_per_receiving_yard_text_box.getText();
        points_per_category.put(ScoringCategory.RECEIVING_YARDS, Double.valueOf(ppRecvYard));

        
        leagueScoring.setScoring(Position.TIGHT_END, points_per_category);
        
        System.out.println("Tightend scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = leagueScoring.getScoring(Position.TIGHT_END, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();
        
        
    }

    private void setKickerScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();
        
        String ppFieldGoal = points_per_field_goal_text_box.getText();
        points_per_category.put(ScoringCategory.FIELD_GOALS, Double.valueOf(ppFieldGoal));

        String ppExtraPoint = points_per_extra_point_text_box.getText();
        points_per_category.put(ScoringCategory.EXTRA_POINTS, Double.valueOf(ppExtraPoint));

        
        leagueScoring.setScoring(Position.KICKER, points_per_category);
        
        System.out.println("Kicker scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = leagueScoring.getScoring(Position.KICKER, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();
        
        
    }

}
