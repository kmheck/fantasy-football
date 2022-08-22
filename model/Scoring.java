package model;

import java.util.EnumMap;

public class Scoring
{

    private EnumMap<ScoringCategory, Double> per_category_points = new EnumMap<>(ScoringCategory.class);
    
    private EnumMap<Position, EnumMap<ScoringCategory, Double>> position_scoring = new EnumMap<>(Position.class);

    public Scoring()
    {
        super();
        this.per_category_points.clear();
        this.position_scoring.clear();
    }

    public Scoring(EnumMap<ScoringCategory, Double> per_category_points,
            EnumMap<Position, EnumMap<ScoringCategory, Double>> position_scoring)
    {
        super();
        this.per_category_points = per_category_points;
        this.position_scoring = position_scoring;
    }

    public void setScoring(Position position, EnumMap<ScoringCategory, Double> categories_scoring)
    {
        per_category_points = categories_scoring;
        //TODO: How to get all categories set up before putting in position_scoring map
        
        position_scoring.put(position, per_category_points);
        
    }
    
    public double getScoring(Position position, ScoringCategory category)
    {
        double scoringValue = 0.0;
        if (position_scoring.containsKey(position))
        {
            EnumMap<ScoringCategory, Double> scoringMap =  position_scoring.get(position);
            scoringValue = scoringMap.get(category).doubleValue();
        }
        return scoringValue;
    }
}
