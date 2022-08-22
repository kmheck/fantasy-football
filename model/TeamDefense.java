package model;

import org.apache.commons.math3.distribution.NormalDistribution;


public class TeamDefense extends Player
{

    private String teamDefenseName;
    private TeamDefenseStats previousYearStats;
    private TeamDefenseStats projectedStats;
    
    public TeamDefenseStats getPreviousYearStats()
    {
        return previousYearStats;
    }

    public void setPreviousYearStats(TeamDefenseStats previousYearStats)
    {
        this.previousYearStats = previousYearStats;
    }

    public TeamDefenseStats getProjectedStats()
    {
        return projectedStats;
    }

    public void setProjectedStats(TeamDefenseStats projectedStats)
    {
        this.projectedStats = projectedStats;
    }

    public TeamDefense()
    {
        super();
        this.teamDefenseName = this.nflTeam.toString();
    }
    
    public void setTeamDefenseName(NFLTeam nflTeam)
    {
        this.teamDefenseName = nflTeam.toString();
        this.displayName = this.teamDefenseName;
    }

    @Override
    public void calculateFantasyPoints()
    {
        double teamDefenseFantasyPoints = 0.0;
        for (final ScoringCategory category : ScoringCategory.TeamDefenseNormalCategory)
        {
            double pointsPerCategoryScoring = LeagueScoring.Instance().getScoring(Position.TEAM_DEFENSE, category);
            try
            {
                teamDefenseFantasyPoints = teamDefenseFantasyPoints + pointsPerCategoryScoring * this.getProjections().getCategoryProjection(category);
            }
            catch (NoSuchFieldException e)
            {
                System.out.println("Caught an exception attempting to get values for category: " + category.toString());
            }
        }

        //TODO: Handle alternate scoring for yardage allowed/points allowed
        // Excel function looks like:
        // =16*(310-NORMINV(0.5*NORMDIST(310,ya per game,80,TRUE),ya per game,80))*NORMDIST(310,ya per game,80,TRUE)/10
        // NORMDIST is defined as NORMDIST(x, mu, stddev, cumulative)
        // NORMINV is defined as NORMINV(probability, mean, stddev) and returns the "x" value
        // from NORMDIST that yields the given probability
        double yardsAllowedPerGame;
        try
        {
            yardsAllowedPerGame = this.getProjections().getCategoryProjection(ScoringCategory.YARDS_ALLOWED)/16.0;
        }
        catch (NoSuchFieldException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            yardsAllowedPerGame = 320.0;
        }
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
        
        teamDefenseFantasyPoints = teamDefenseFantasyPoints + yardageFantasyPoints;
        
        //TODO: Do something similar for points allowed  
        // Excel function:
        // 16*(10-NORMINV(0.5*NORMDIST(10,E2/16,9.5,TRUE),E2/16,9.5))*NORMDIST(10,E2/16,9.5,TRUE)
        double pointsAllowedPerGame;
        try
        {
            pointsAllowedPerGame = this.getProjections().getCategoryProjection(ScoringCategory.POINTS_ALLOWED)/16.0;
        }
        catch (NoSuchFieldException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            pointsAllowedPerGame = 30.0;
        }
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
        
        teamDefenseFantasyPoints = teamDefenseFantasyPoints + pointsFantasyPoints;
        
        this.fantasyPoints = teamDefenseFantasyPoints;
    }

    public String getTeamDefenseName()
    {
        return teamDefenseName;
    }

    @Override
    public String toString() 
    {
        String imageString = "";
        
        imageString = imageString + this.teamDefenseName + "\n";
        imageString = imageString + super.toString();
        
        return imageString;
    }

}
