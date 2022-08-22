package model;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;

public class Roster implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static Roster the_instance = new Roster();
    private int rosterSize;
    private int numberOfTeamsInLeague;
    private LeagueRosterParameters rosterParameters = null;

//    private static class RosterHelper{
//        private static final Roster the_instance = new Roster();
//    }
    
//    public static Roster Instance(){
//        return RosterHelper.the_instance;
//    }

    static public Roster Instance() 
    {
//        if (the_instance == null)
//        {
//            System.out.println("Instance() - returning newly constructed Roster");
//            the_instance = new Roster();
//        }
        return the_instance;
    }  

//    protected Object readResolve() throws ObjectStreamException {
//        System.out.println("readResolve() - Enter");
//        return Instance();
//    }

    // Note - This method should be called only after deserializing a Roster
    public static void setSerializedInstance(Roster serializedInstance)
    {
        the_instance = serializedInstance;
    }
    @Override
    public String toString()
    {
        return "Roster [rosterSize=" + rosterSize + ", numberOfTeamsInLeague=" + numberOfTeamsInLeague
                + ", rosterPositionSizeMap=" + rosterPositionSizeMap + ", flexPositions=" + flexPositions + "]";
    }

    
    private EnumMap<Position, Integer> rosterPositionSizeMap = new EnumMap<>(Position.class);

    private ArrayList<Position> flexPositions = new ArrayList<Position>();
    
    public ArrayList<Position> getFlexPositions()
    {
        return flexPositions;
    }

    public void setFlexPositions(ArrayList<Position> flexPositions)
    {
        this.flexPositions = flexPositions;
    }

    public int getNumberOfFlexPositions()
    {
        return this.flexPositions.size();
    }
    public boolean isAnEligibleFlexPosition(Position position)
    {
        boolean isEligible = false;
        
        Iterator<Position> i = this.flexPositions.iterator();
        
        while (i.hasNext())
        {
            Position eligiblePosition = i.next();
            if (eligiblePosition == position)
            {
                isEligible = true;
                break;
            }
        }
        return isEligible;
    }
    
    private Roster()
    {
//        super();
        System.out.println("Roster default constructor - Enter");
//        this.rosterSize = 16;
//        this.numberOfTeamsInLeague = 12;
//        this.rosterPositionSizeMap.clear();
//        this.flexPositions.clear();
    }

    private Roster(int rosterSize, int numberOfTeamsInLeague)
    {
        super();
        this.rosterSize = rosterSize;
        this.numberOfTeamsInLeague = numberOfTeamsInLeague;
        this.rosterPositionSizeMap.clear();
        this.flexPositions.clear();
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
    
    public void setNumberOfStarters(Position position, int numberOfStarters)
    {
        this.rosterPositionSizeMap.put(position, numberOfStarters);
    }
    
    public int getNumberOfStarters(Position position)
    {
        if (this.rosterPositionSizeMap.containsKey(position))
        {
            return this.rosterPositionSizeMap.get(position);
        }
        else
        {
            System.out.println("No value found in map for position: " + position.toString());
            return 0;
        }
    }

    public LeagueRosterParameters getRosterParameters()
    {
        return rosterParameters;
    }

    public void setRosterParameters(LeagueRosterParameters rosterParameters)
    {
        this.rosterParameters = rosterParameters;
    }

    public void setLeagueRosterSettingsFromParameters()
    {
        System.out.println("setLeagueRosterSettingsFromParameters() - Enter");
        if (rosterParameters != null)
        {
            setNumberOfTeamsInLeague(this.rosterParameters.getNumberOfTeamsInLeague());
            System.out.println("Set number of teams in league to: " + getNumberOfTeamsInLeague());
    
            Roster.Instance().setRosterSize(this.rosterParameters.getRosterSize());
            System.out.println("Set roster size in league to: " + getRosterSize());
    
            Roster.Instance().setNumberOfStarters(Position.QUARTER_BACK, this.rosterParameters.getNumberOfQuarterbackStarters());
            System.out.println("Number of QB starters: " + getNumberOfStarters(Position.QUARTER_BACK));
    
            Roster.Instance().setNumberOfStarters(Position.RUNNING_BACK, this.rosterParameters.getNumberOfRunningbackStarters());
            System.out.println("Number of RB starters: " + getNumberOfStarters(Position.RUNNING_BACK));
    
            Roster.Instance().setNumberOfStarters(Position.WIDE_RECEIVER, this.rosterParameters.getNumberOfWideReceiverStarters());
            System.out.println("Number of WR starters: " + getNumberOfStarters(Position.WIDE_RECEIVER));
    
            Roster.Instance().setNumberOfStarters(Position.TIGHT_END, this.rosterParameters.getNumberOfTightEndStarters());
            System.out.println("Number of TE starters: " + getNumberOfStarters(Position.TIGHT_END));
    
            Roster.Instance().setNumberOfStarters(Position.TEAM_DEFENSE, this.rosterParameters.getNumberOfTeamDefenseStarters());
            System.out.println("Number of Team Defense starters: " + getNumberOfStarters(Position.TEAM_DEFENSE));
    
            Roster.Instance().setNumberOfStarters(Position.KICKER, this.rosterParameters.getNumberOfKickerStarters());
            System.out.println("Number of K starters: " + getNumberOfStarters(Position.KICKER));
    
            Roster.Instance().setNumberOfStarters(Position.INDIVIDUAL_DEFENSIVE_PLAYER, this.rosterParameters.getNumberOfIDPStarters());
            System.out.println("Number of IDP starters: " + getNumberOfStarters(Position.INDIVIDUAL_DEFENSIVE_PLAYER));
    
            //TODO: How to handle the "flex" positions?
            int number_of_flex_rb_wr_te = this.rosterParameters.getNumberOfFlexRBWRTEStarters();
            int number_of_flex_wr_te = this.rosterParameters.getNumberOfFlexWRTEStarters();
            int number_of_flex_positions = number_of_flex_rb_wr_te + number_of_flex_wr_te;
            System.out.println("Setting number of FLEX positions to: " + number_of_flex_positions);
            setNumberOfStarters(Position.FLEX, number_of_flex_positions);

            ArrayList<Position> eligibleFlexPositions = new ArrayList<Position>();
            eligibleFlexPositions.clear();
            if (number_of_flex_rb_wr_te > 0)
            {
                eligibleFlexPositions.add(Position.RUNNING_BACK);
                eligibleFlexPositions.add(Position.WIDE_RECEIVER);
                eligibleFlexPositions.add(Position.TIGHT_END);
            }
            else if (number_of_flex_wr_te > 0)
            {
                eligibleFlexPositions.add(Position.WIDE_RECEIVER);
                eligibleFlexPositions.add(Position.TIGHT_END);
            }
            Roster.Instance().setFlexPositions(eligibleFlexPositions);

        }
        else
        {
            System.out.println("Roster parameters is null - cannot use");
        }
    }
}
