package model;

public class LeagueRosterParameters
{


    String leagueName;
    
    int rosterSize;
    int numberOfTeamsInLeague;

    int numberOfQuarterbackStarters;
    int numberOfRunningbackStarters;
    int numberOfWideReceiverStarters;
    int numberOfTightEndStarters;
    int numberOfKickerStarters;
    int numberOfTeamDefenseStarters;
    int numberOfIDPStarters;
    int numberOfFlexRBWRTEStarters;
    int numberOfFlexWRTEStarters;

    public LeagueRosterParameters()
    {
        super();
    }

    public String getLeagueName()
    {
        return leagueName;
    }
    public void setLeagueName(String leagueName)
    {
        this.leagueName = leagueName;
    }
    public int getRosterSize()
    {
        return rosterSize;
    }
    public void setRosterSize(int rosterSize)
    {
        this.rosterSize = rosterSize;
    }
    public int getNumberOfTeamsInLeague()
    {
        return numberOfTeamsInLeague;
    }
    public void setNumberOfTeamsInLeague(int numberOfTeamsInLeague)
    {
        this.numberOfTeamsInLeague = numberOfTeamsInLeague;
    }
    public int getNumberOfQuarterbackStarters()
    {
        return numberOfQuarterbackStarters;
    }
    public void setNumberOfQuarterbackStarters(int numberOfQuarterbackStarters)
    {
        this.numberOfQuarterbackStarters = numberOfQuarterbackStarters;
    }
    public int getNumberOfRunningbackStarters()
    {
        return numberOfRunningbackStarters;
    }
    public void setNumberOfRunningbackStarters(int numberOfRunningbackStarters)
    {
        this.numberOfRunningbackStarters = numberOfRunningbackStarters;
    }
    public int getNumberOfWideReceiverStarters()
    {
        return numberOfWideReceiverStarters;
    }
    public void setNumberOfWideReceiverStarters(int numberOfWideReceiverStarters)
    {
        this.numberOfWideReceiverStarters = numberOfWideReceiverStarters;
    }
    public int getNumberOfTightEndStarters()
    {
        return numberOfTightEndStarters;
    }
    public void setNumberOfTightEndStarters(int numberOfTightEndStarters)
    {
        this.numberOfTightEndStarters = numberOfTightEndStarters;
    }
    public int getNumberOfKickerStarters()
    {
        return numberOfKickerStarters;
    }
    public void setNumberOfKickerStarters(int numberOfKickerStarters)
    {
        this.numberOfKickerStarters = numberOfKickerStarters;
    }
    public int getNumberOfTeamDefenseStarters()
    {
        return numberOfTeamDefenseStarters;
    }
    public void setNumberOfTeamDefenseStarters(int numberOfTeamDefenseStarters)
    {
        this.numberOfTeamDefenseStarters = numberOfTeamDefenseStarters;
    }
    public int getNumberOfIDPStarters()
    {
        return numberOfIDPStarters;
    }
    public void setNumberOfIDPStarters(int numberOfIDPStarters)
    {
        this.numberOfIDPStarters = numberOfIDPStarters;
    }
    public int getNumberOfFlexRBWRTEStarters()
    {
        return numberOfFlexRBWRTEStarters;
    }
    public void setNumberOfFlexRBWRTEStarters(int numberOfFlexRBWRTEStarters)
    {
        this.numberOfFlexRBWRTEStarters = numberOfFlexRBWRTEStarters;
    }
    public int getNumberOfFlexWRTEStarters()
    {
        return numberOfFlexWRTEStarters;
    }
    public void setNumberOfFlexWRTEStarters(int numberOfFlexWRTEStarters)
    {
        this.numberOfFlexWRTEStarters = numberOfFlexWRTEStarters;
    }
    
    @Override
    public String toString()
    {
        return "LeagueRosterParameters [leagueName=" + leagueName + ", numberOfQuarterbackStarters="
                + numberOfQuarterbackStarters + ", numberOfRunningbackStarters=" + numberOfRunningbackStarters
                + ", numberOfWideReceiverStarters=" + numberOfWideReceiverStarters + ", numberOfTightEndStarters="
                + numberOfTightEndStarters + ", numberOfKickerStarters=" + numberOfKickerStarters
                + ", numberOfTeamDefenseStarters=" + numberOfTeamDefenseStarters + ", numberOfIDPStarters="
                + numberOfIDPStarters + ", numberOfFlexRBWRTEStarters=" + numberOfFlexRBWRTEStarters
                + ", numberOfFlexWRTEStarters=" + numberOfFlexWRTEStarters + "]";
    }
    
    





}
