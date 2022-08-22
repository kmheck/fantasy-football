package model;

import java.util.EnumMap;

public class LeagueScoring
{
    private static LeagueScoring the_instance = new LeagueScoring();

    static public LeagueScoring Instance() 
    {
        if (the_instance == null)
        {
            the_instance = new LeagueScoring();
        }
        return the_instance;
    }  
    
    private EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
    
    private EnumMap<Position, EnumMap<ScoringCategory, Double>> position_scoring = new EnumMap<>(Position.class);
    
    private LeagueScoringParameters scoringParameters = null;

    private LeagueScoring()
    {
        super();
        this.points_per_category.clear();
        this.position_scoring.clear();
    }

    private LeagueScoring(EnumMap<ScoringCategory, Double> category_value,
            EnumMap<Position, EnumMap<ScoringCategory, Double>> position_scoring)
    {
        super();
        this.points_per_category = category_value;
        this.position_scoring = position_scoring;
    }

    public void setScoring(Position position, EnumMap<ScoringCategory, Double> categories_scoring)
    {
        points_per_category = categories_scoring;
        //TODO: How to get all categories set up before putting in position_scoring map
        
        this.position_scoring.put(position, points_per_category);
        
    }
    
    public double getScoring(Position position, ScoringCategory category)
    {
        double scoringValue = 0.0;
        if (this.position_scoring.containsKey(position))
        {
            EnumMap<ScoringCategory, Double> scoringMap =  this.position_scoring.get(position);
            if (scoringMap.containsKey(category))
            {
                scoringValue = scoringMap.get(category).doubleValue();
            }
          
        }
        return scoringValue;
    }

    public LeagueScoringParameters getScoringParameters()
    {
        return scoringParameters;
    }

    public void setScoringParameters(LeagueScoringParameters scoringParameters)
    {
        this.scoringParameters = scoringParameters;
    }
    
    private void setRunningbackScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();
        
        points_per_category.put(ScoringCategory.RUSHING_YARDS, this.scoringParameters.getOffensePointsPerRushingYard());

        points_per_category.put(ScoringCategory.RUSHING_TDS, this.scoringParameters.getOffensePointsPerRushingTD());

        points_per_category.put(ScoringCategory.RECEIVING_TDS, this.scoringParameters.getOffensePointsPerReceivingTD());

        points_per_category.put(ScoringCategory.RECEIVING_YARDS, this.scoringParameters.getOffensePointsPerReceivingYard());

        points_per_category.put(ScoringCategory.RECEPTIONS, this.scoringParameters.getOffensePointsPerReception());

        LeagueScoring.Instance().setScoring(Position.RUNNING_BACK, points_per_category);
        
        System.out.println("Runningback scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = LeagueScoring.Instance().getScoring(Position.RUNNING_BACK, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();
        
        
    }

    private void setQuarterbackScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();

        points_per_category.put(ScoringCategory.RUSHING_YARDS, this.scoringParameters.getOffensePointsPerRushingYard());

        points_per_category.put(ScoringCategory.RUSHING_TDS, this.scoringParameters.getOffensePointsPerRushingTD());

        points_per_category.put(ScoringCategory.PASSING_TDS, this.scoringParameters.getOffensePointsPerPassingTD());

        points_per_category.put(ScoringCategory.PASSING_YARDS, this.scoringParameters.getOffensePointsPerPassingYard());

        points_per_category.put(ScoringCategory.INTERCEPTIONS, this.scoringParameters.getOffensePointsPerInterception());

        points_per_category.put(ScoringCategory.FUMBLES, this.scoringParameters.getOffensePointsPerFumble());

        LeagueScoring.Instance().setScoring(Position.QUARTER_BACK, points_per_category);
        
        System.out.println("Quarterback scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = LeagueScoring.Instance().getScoring(Position.QUARTER_BACK, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();
        
        
    }
    
    private void setWidereceiverScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();
        
        points_per_category.put(ScoringCategory.RUSHING_YARDS, this.scoringParameters.getOffensePointsPerRushingYard());

        points_per_category.put(ScoringCategory.RUSHING_TDS, this.scoringParameters.getOffensePointsPerRushingTD());

        points_per_category.put(ScoringCategory.RECEIVING_TDS, this.scoringParameters.getOffensePointsPerReceivingTD());

        points_per_category.put(ScoringCategory.RECEIVING_YARDS, this.scoringParameters.getOffensePointsPerReceivingYard());

        points_per_category.put(ScoringCategory.RECEPTIONS, this.scoringParameters.getOffensePointsPerReception());
       
        LeagueScoring.Instance().setScoring(Position.WIDE_RECEIVER, points_per_category);
        
        System.out.println("Widereceiver scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = LeagueScoring.Instance().getScoring(Position.WIDE_RECEIVER, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();
        
        
    }

    private void setTightendScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();

        points_per_category.put(ScoringCategory.RECEIVING_TDS, this.scoringParameters.getOffensePointsPerReceivingTD());

        points_per_category.put(ScoringCategory.RECEIVING_YARDS, this.scoringParameters.getOffensePointsPerReceivingYard());

        points_per_category.put(ScoringCategory.RECEPTIONS, this.scoringParameters.getOffensePointsPerReception());
        
        LeagueScoring.Instance().setScoring(Position.TIGHT_END, points_per_category);
        
        System.out.println("Tightend scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = LeagueScoring.Instance().getScoring(Position.TIGHT_END, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();
        
        
    }

    private void setKickerScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();

        points_per_category.put(ScoringCategory.FIELD_GOALS, this.scoringParameters.getOffensePointsPerFieldGoal());

        points_per_category.put(ScoringCategory.EXTRA_POINTS, this.scoringParameters.getOffensePointsPerExtraPoint());
        
        LeagueScoring.Instance().setScoring(Position.KICKER, points_per_category);
        
        System.out.println("Kicker scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = LeagueScoring.Instance().getScoring(Position.KICKER, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();
        
        
    }

    private void setTeamDefenseScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();
        
        points_per_category.put(ScoringCategory.SACKS, this.scoringParameters.getTeamDefensePointsPerSack());
        points_per_category.put(ScoringCategory.INTERCEPTIONS, this.scoringParameters.getTeamDefensePointsPerInterception());
        points_per_category.put(ScoringCategory.FUMBLES, this.scoringParameters.getTeamDefensePointsPerFumbleRecovery());
        points_per_category.put(ScoringCategory.RETURNS_FOR_TD, this.scoringParameters.getTeamDefensePointsPerTD());
        points_per_category.put(ScoringCategory.YARDS_ALLOWED_CUTOFF, this.scoringParameters.getTeamDefenseYardsAllowedCutoff());
        points_per_category.put(ScoringCategory.POINTS_PER_YARDS_ALLOWED, this.scoringParameters.getTeamDefensePointsPerYardsAllowed());
        points_per_category.put(ScoringCategory.POINTS_ALLOWED_CUTOFF, this.scoringParameters.getTeamDefensePointsAllowedCutoff());
        points_per_category.put(ScoringCategory.POINTS_PER_POINTS_ALLOWED, this.scoringParameters.getTeamDefensePointsPerPointsAllowed());

        LeagueScoring.Instance().setScoring(Position.TEAM_DEFENSE, points_per_category);
        
        System.out.println("Team Defense scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = LeagueScoring.Instance().getScoring(Position.TEAM_DEFENSE, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();
        
        
    }
    
    private void setIndividualDefensivePlayerScoring()
    {
        EnumMap<ScoringCategory, Double> points_per_category = new EnumMap<>(ScoringCategory.class);
        points_per_category.clear();

        points_per_category.put(ScoringCategory.SACKS, this.scoringParameters.getIdpPointsPerSack());
        points_per_category.put(ScoringCategory.INTERCEPTIONS, this.scoringParameters.getIdpPointsPerInterception());
        points_per_category.put(ScoringCategory.FUMBLES, this.scoringParameters.getIdpPointsPerFumbleRecovery());
        points_per_category.put(ScoringCategory.RETURNS_FOR_TD, this.scoringParameters.getIdpPointsPerTD());
        points_per_category.put(ScoringCategory.TACKLES, this.scoringParameters.getIdpPointsPerTackle());
        points_per_category.put(ScoringCategory.ASSISTS, this.scoringParameters.getIdpPointsPerAssist());

        LeagueScoring.Instance().setScoring(Position.INDIVIDUAL_DEFENSIVE_PLAYER, points_per_category);
        
        System.out.println("Individual Defensive Player scoring:");
        System.out.println();
        for (ScoringCategory category : points_per_category.keySet())
        {
            double scoringValue = LeagueScoring.Instance().getScoring(Position.INDIVIDUAL_DEFENSIVE_PLAYER, category);
            System.out.println("Scoring for category: " + category.toString() + " = " + scoringValue);
        }
        System.out.println();

    }

    public void setPositionScoringFromParameters()
    {
        if (this.scoringParameters != null)
        {
            setRunningbackScoring();
            setQuarterbackScoring();
            setWidereceiverScoring();
            setTightendScoring();
            setKickerScoring();
            setTeamDefenseScoring();
            setIndividualDefensivePlayerScoring();
        }

    }
}
