package model;

import java.io.Serializable;

public class IDPStats implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String displayName;
    private String year;
    private double games;
    private double tackles;
    private double assists;
    private double sacks;
    private double interceptions;
    private double fumbleRecoveries;
    private double TDs;
    private double fantasyPoints;

    public IDPStats()
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

    public double getTackles()
    {
        return tackles;
    }

    public void setTackles(double tackles)
    {
        this.tackles = tackles;
    }

    public double getAssists()
    {
        return assists;
    }

    public void setAssists(double assists)
    {
        this.assists = assists;
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
        this.fantasyPoints = 17.0/this.games*
                (this.tackles*LeagueScoring.Instance().getScoring(Position.INDIVIDUAL_DEFENSIVE_PLAYER, ScoringCategory.TACKLES) +
                 this.assists*LeagueScoring.Instance().getScoring(Position.INDIVIDUAL_DEFENSIVE_PLAYER, ScoringCategory.ASSISTS) +
                 this.sacks*LeagueScoring.Instance().getScoring(Position.INDIVIDUAL_DEFENSIVE_PLAYER, ScoringCategory.SACKS) +
                 this.interceptions*LeagueScoring.Instance().getScoring(Position.INDIVIDUAL_DEFENSIVE_PLAYER, ScoringCategory.INTERCEPTIONS) +
                 this.fumbleRecoveries*LeagueScoring.Instance().getScoring(Position.INDIVIDUAL_DEFENSIVE_PLAYER, ScoringCategory.FUMBLES) +
                 this.TDs*LeagueScoring.Instance().getScoring(Position.INDIVIDUAL_DEFENSIVE_PLAYER, ScoringCategory.RETURNS_FOR_TD));

    }
    public double getFantasyPoints()
    {
        return fantasyPoints;
    }

    public void setFantasyPoints(double fantasyPoints)
    {
        this.fantasyPoints = fantasyPoints;
    }

    @Override
    public String toString() {
        return "IDPStats [displayName=" + displayName + ", games="
                + games + ", tackles=" + tackles + ", assists="
                + assists + ", sacks=" + sacks
                + ", interceptions=" + interceptions + ", fumble recoveries="
                + fumbleRecoveries + ", TDs=" + TDs + "]";
    }


}
