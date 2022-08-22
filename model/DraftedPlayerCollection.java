package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
public class DraftedPlayerCollection
{
    private static DraftedPlayerCollection the_instance = new DraftedPlayerCollection();
    private final Map< Position, ArrayList<Player> > positionCollection = new TreeMap< Position, ArrayList<Player> >();
    // This is the mean value (based on number of players to be drafted for a starting lineup
    // in this league) of the fantasy points at a position.

    static public DraftedPlayerCollection Instance() {
        if (the_instance == null) {
            the_instance = new DraftedPlayerCollection();
        }
        return the_instance;
    }
    
    private DraftedPlayerCollection() {
        System.out.println("DraftedPlayerCollection() - private constructor");

    }

    public void addPlayerToPosition(Player player, Position position)
    {
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
        
        ArrayList<Player> players = this.getPositionCollection(position);
        if (players != null)
        {
            System.out.println("Removing player: " + player.getDisplayName() + 
                    " at position: " + position.toString() + " from drafted players");
            players.remove(player);
            
            this.positionCollection.put(position, players);

        }
        else
        {
            System.out.println("No drafted players found for position " + position.toString());
        }
        
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
    
    public int getNumberOfPlayersAtPosition(Position position)
    {
        int number_of_players = 0;
        
        ArrayList<Player> players = this.positionCollection.get(position);
        if (players != null)
        {
            number_of_players = players.size();
        }
        return number_of_players;
    }

    public void sort(Position position)
    {
        Comparator<Player> playerComparator = new PlayerPositionComparator();

        ArrayList<Player> players = getPositionCollection(position);
        Collections.sort(players, playerComparator);
        this.positionCollection.put(position, players);
    }
}
