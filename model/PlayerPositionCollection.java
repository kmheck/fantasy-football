package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
public class PlayerPositionCollection
{
    private static PlayerPositionCollection the_instance = new PlayerPositionCollection();
    private Map< Position, ArrayList<Player> > positionCollection = new TreeMap< Position, ArrayList<Player> >();
    // This is the mean value (based on number of players to be drafted for a starting lineup
    // in this league) of the fantasy points at a position.
    private final Map<Position, Double> positionMean = new TreeMap<Position, Double>();

    public double getPositionMean(Position position)
    {
        return this.positionMean.get(position);
    }

    static public PlayerPositionCollection Instance() {
        if (the_instance == null) {
            the_instance = new PlayerPositionCollection();
        }
        return the_instance;
    }
    
    private PlayerPositionCollection() {
        System.out.println("PlayerPositionCollection() - private constructor");

    }

    public void calculateDiffValues(Position position)
    {
        ArrayList<Player> players = this.positionCollection.get(position);
        Iterator<Player> i = players.iterator();
        double diffValue;
        while (i.hasNext())
        {
            Player player = (Player)i.next();
            double mean = getPositionMean(position);
            if (mean > 0.0)
            {
                diffValue = player.getFantasyPoints() - mean;
            }
            else
            {
                diffValue = 0.0;
            }
            player.setDiffValue(diffValue);
        }

        
    }
    
    public void calculateMean(Position position)
    {
        ArrayList<Player> players = this.positionCollection.get(position);
        Iterator<Player> i = players.iterator();
        Double sum = 0.0;
        int playerCount = 0;
        
        int playersToUse = Roster.Instance().getNumberOfTeamsInLeague() * Roster.Instance().getNumberOfStarters(position);
        int flexPlayersToUse = Roster.Instance().getNumberOfStarters(Position.FLEX);
        System.out.println("calculateMean - number of flex players in calculation: " + flexPlayersToUse);
        if (flexPlayersToUse > 0)
        {
            
            //TODO: Think about this code.  Thinking is that if position is RB, then
            // assume everyone will try and get a flex RB so use the total number of
            // teams in league to add to the mean calculation, however, if the 
            // position is anything else, only use a fraction 
            // (equal to 1/number of flex positions) for extra bodies in the mean.
            if (Roster.Instance().isAnEligibleFlexPosition(position))
            {
                if (position == Position.RUNNING_BACK || position == Position.WIDE_RECEIVER)
                {
                	System.out.println("Adding flex players to mean calculation for position:" + position.toString());
                    playersToUse = playersToUse + (Roster.Instance().getNumberOfTeamsInLeague() * 
                            flexPlayersToUse)/2 + 1;
                }
                else
                {
//                    playersToUse = playersToUse + Roster.Instance().getNumberOfTeamsInLeague() * 
//                            Roster.Instance().getNumberOfStarters(Position.FLEX) / 
//                            Roster.Instance().getNumberOfFlexPositions();
                }
            }
            else
            {
            	System.out.println("Position: " + position.toString() + " is not an eligible flex position");
            }
        }
        
        while (i.hasNext() && playerCount < playersToUse)
        {
            Player player = i.next();
            sum = sum + player.getFantasyPoints();
            System.out.println("Adding " + Double.toString(player.getFantasyPoints()) + " fantasy points to sum...\n");
            playerCount = playerCount + 1;
        }
        
        double mean = 0.0;
        if (playersToUse > 0)
        {
            mean = sum/(double)playersToUse;
        }
        this.positionMean.put(position, mean);
        String output_string = "mean for position: " + position.toString() + " is " + Double.toString(mean) + "\n";
        output_string = output_string + "using " + Integer.toString(playersToUse) + " players\n";
        System.out.println(output_string);
        
    }
    
    public void addPlayerToPosition(Player player, Position position)
    {
//    	System.out.println("PlayerPositionCollection.addPlayerToPosition() - player: " + 
//             player.getDisplayName() + " position: " + position);
    	
        ArrayList<Player> players = this.positionCollection.get(position);
        if (players == null)
        {
            players = new ArrayList<Player>();
        }
        players.add(player);
        this.positionCollection.put(position, players);

    }
    
    public void removePlayerFromPosition(Player player, Position position)
    {
    	ArrayList<Player> players = this.positionCollection.get(position);
    	players.remove(player);
    	
    	this.positionCollection.put(position, players);

    }

    public ArrayList<Player> getPositionCollection(Position position) {
        if (this.positionCollection.containsKey(position))
            
        {
            return this.positionCollection.get(position);
        }
        else
        {
            return null;
        }
    }

    public Map< Position, ArrayList<Player> > getEntirePositionCollection() {
        return this.positionCollection;
    }

    public void setEntirePositionCollection(Map< Position, ArrayList<Player> > map)
    {
        this.positionCollection = map;
    }
    
    public Player getTopUndraftedPlayerAtPosition(Position position)
    {
        Player returnedPlayer = null;
        ArrayList<Player> players = getPositionCollection(position);
        
        Iterator<Player> playerIter = players.iterator();
        while (playerIter.hasNext())
        {
            Player player = playerIter.next();
            if (!player.isDrafted())
            {
                returnedPlayer = player;
                break;
            }
        }
        return returnedPlayer;
    }
    
    public Player getSecondBestUndraftedPlayerAtPosition(Position position)
    {
        Player returnedPlayer = null;
        boolean topPlayerAlreadyFound = false;
        
        ArrayList<Player> players = getPositionCollection(position);
        
        Iterator<Player> playerIter = players.iterator();
        while (playerIter.hasNext())
        {
            Player player = playerIter.next();
            if (!player.isDrafted())
            {
                if (!topPlayerAlreadyFound)
                {
                    topPlayerAlreadyFound = true;
                }
                else
                {
                    returnedPlayer = player;
                    break;
                }
            }
        }
        return returnedPlayer;
    }
    
    public Player findMatchingPlayer(Player playerToBeMatched, Position position)
    {
        Player returnedPlayer = null;
        ArrayList<Player> players = getPositionCollection(position);
        
        Iterator<Player> playerIter = players.iterator();
        while (playerIter.hasNext())
        {
            Player player = playerIter.next();
            if (player.matchesName(playerToBeMatched))
            {
                returnedPlayer = player;
            }
        }
        return returnedPlayer;
    }
    
    public void sort(Position position)
    {
    	System.out.println("PlayerPositionCollection.sort() - sorting position: " + position.toString());
        Comparator<Player> playerComparator = new PlayerPositionComparator();

        ArrayList<Player> players = getPositionCollection(position);
        Collections.sort(players, playerComparator);
        this.positionCollection.put(position, players);
    }
}
