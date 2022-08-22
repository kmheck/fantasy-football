package model;

import java.io.Serializable;

import org.apache.commons.math3.distribution.NormalDistribution;

public class TeamDefenseStats implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String displayName;
    private String year;
    private double games;
    private double sacks;
    private double interceptions;
    private double fumbleRecoveries;
    private double TDs;
    private double pointsAllowed;
    private double yardsAllowed;
    private double safeties;
    private double fantasyPoints;
    
    public TeamDefenseStats()
    {
        super();
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public double getGames()
    {
        return games;
    }

    public void setGames(double games)
    {
        this.games = games;
    }

    public double getSacks()
    {
        return sacks;
    }

    public void setSacks(double sacks)
    {
        this.sacks = sacks;
    }

    public double getInterceptions()
    {
        return interceptions;
    }

    public void setInterceptions(double interceptions)
    {
        this.interceptions = interceptions;
    }

    public double getFumbleRecoveries()
    {
        return fumbleRecoveries;
    }

    public void setFumbleRecoveries(double fumbleRecoveries)
    {
        this.fumbleRecoveries = fumbleRecoveries;
    }

    public double getTDs()
    {
        return TDs;
    }

    public void setTDs(double tDs)
    {
        TDs = tDs;
    }

    public void calculateFantasyPoints()
    {
        this.fantasyPoints = 16.0/this.games*
                (this.sacks*LeagueScoring.Instance().getScoring(Position.TEAM_DEFENSE, ScoringCategory.SACKS) +
                 this.interceptions*LeagueScoring.Instance().getScoring(Position.TEAM_DEFENSE, ScoringCategory.INTERCEPTIONS) +
                 this.fumbleRecoveries*LeagueScoring.Instance().getScoring(Position.TEAM_DEFENSE, ScoringCategory.FUMBLES) +
                 this.TDs*LeagueScoring.Instance().getScoring(Position.TEAM_DEFENSE, ScoringCategory.RETURNS_FOR_TD));

        double yardsAllowedPerGame = this.yardsAllowed/this.games;
        double yardsCutoff = LeagueScoring.Instance().getScoring(Position.TEAM_DEFENSE, ScoringCategory.YARDS_ALLOWED_CUTOFF);
        double pointsPerYardsAllowed = LeagueScoring.Instance().getScoring(Position.TEAM_DEFENSE, ScoringCategory.POINTS_PER_YARDS_ALLOWED);
//        System.out.printf("Team Defense - yards allowed per game: %.0f\n", yardsAllowedPerGame);
        NormalDistribution defenseDistribution = new NormalDistribution(yardsAllowedPerGame, 80.0);
        double probabilityOfLessThan310YyardsAllowed = defenseDistribution.cumulativeProbability(yardsCutoff);
//        System.out.printf("Team Defense - probability of less than 310 yards allowed: %.3f\n", probabilityOfLessThan310YyardsAllowed);
        double valueInMiddleOfLessThan310YardsProbabilityCurve = defenseDistribution.inverseCumulativeProbability(0.5*probabilityOfLessThan310YyardsAllowed);
//        System.out.printf("Team Defense - yardage allowed value to use: %.0f\n", valueInMiddleOfLessThan310YardsProbabilityCurve);
        double yardageFantasyPoints = 16.0*(yardsCutoff - valueInMiddleOfLessThan310YardsProbabilityCurve)*probabilityOfLessThan310YyardsAllowed*pointsPerYardsAllowed;
//        System.out.printf("Team Defense - yardageFantasyPoints: %.0f\n", yardageFantasyPoints);
        
        this.fantasyPoints = this.fantasyPoints + yardageFantasyPoints;
        
        //TODO: Do something similar for points allowed  
        // Excel function:
        // 16*(10-NORMINV(0.5*NORMDIST(10,E2/16,9.5,TRUE),E2/16,9.5))*NORMDIST(10,E2/16,9.5,TRUE)
        double pointsAllowedPerGame = this.pointsAllowed/this.games;
        double pointsCutoff = LeagueScoring.Instance().getScoring(Position.TEAM_DEFENSE, ScoringCategory.POINTS_ALLOWED_CUTOFF);
        double pointsPerPointsAllowed = LeagueScoring.Instance().getScoring(Position.TEAM_DEFENSE, ScoringCategory.POINTS_PER_POINTS_ALLOWED);
//        System.out.printf("Team Defense - points allowed per game: %.0f\n", pointsAllowedPerGame);
        // Note - hardcoded 4.7 is the standard deviation of points per game
        NormalDistribution defensePointsDistribution = new NormalDistribution(pointsAllowedPerGame, 4.7);
        double probabilityOfLessThan10PointsAllowed = defensePointsDistribution.cumulativeProbability(pointsCutoff);
//        System.out.printf("Team Defense - probability of less than 10 points allowed: %.3f\n", probabilityOfLessThan10PointsAllowed);
        double valueInMiddleOfLessThan10PointsProbabilityCurve = defensePointsDistribution.inverseCumulativeProbability(0.5*probabilityOfLessThan10PointsAllowed);
//        System.out.printf("Team Defense - points allowed value to use: %.0f\n", valueInMiddleOfLessThan10PointsProbabilityCurve);
        double pointsFantasyPoints = 16.0*(pointsCutoff - valueInMiddleOfLessThan10PointsProbabilityCurve)*probabilityOfLessThan10PointsAllowed*pointsPerPointsAllowed;
//        System.out.printf("Team Defense - pointsFantasyPoints: %.0f\n", pointsFantasyPoints);
        
        this.fantasyPoints = this.fantasyPoints + pointsFantasyPoints;
    }
    
    public double getPointsAllowed()
    {
        return pointsAllowed;
    }

    public void setPointsAllowed(double pointsAllowed)
    {
        this.pointsAllowed = pointsAllowed;
    }

    public double getYardsAllowed()
    {
        return yardsAllowed;
    }

    public void setYardsAllowed(double yardsAllowed)
    {
        this.yardsAllowed = yardsAllowed;
    }

    public double getSafeties()
    {
        return safeties;
    }

    public void setSafeties(double safeties)
    {
        this.safeties = safeties;
    }

    public double getFantasyPoints()
    {
        return fantasyPoints;
    }

    public void setFantasyPoints(double fantasyPoints)
    {
        this.fantasyPoints = fantasyPoints;
    }
    
    
}
