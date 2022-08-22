package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;

public class AllPlayerCollection
{
    private static AllPlayerCollection the_instance = new AllPlayerCollection();

    private ArrayList<Player> players = new ArrayList<Player>();
    
    private ArrayList<Player> relativeDiffPlayers = new ArrayList<Player>();

    
    private AllPlayerCollection()
    {
        System.out.println("AllPlayerCollection() - private constructor");
    }

    static public AllPlayerCollection Instance() {
        if (the_instance == null) {
            the_instance = new AllPlayerCollection();
        }
        return the_instance;
    }

    public ArrayList<Player> getCollection() 
    {
        return this.players;
    }
    
    public void diffSort()
    {
        System.out.println("AllPlayerCollection.diffSort()");
        
        Comparator<Player> playerComparator = new PlayerComparator();
        
        Collections.sort(this.players, playerComparator);

    }

    public void assemble()
    {
        System.out.println("AllPlayerCollection.assemble()");
        
        this.players.clear();
        
        ArrayList<Player> playersAtPosition;
        for (Position position : EnumSet.range(Position.QUARTER_BACK, Position.INDIVIDUAL_DEFENSIVE_PLAYER))
        {
            if (Roster.Instance().getNumberOfStarters(position) > 0)
            {
                playersAtPosition = PlayerPositionCollection.Instance().getPositionCollection(position);
                if (playersAtPosition != null)
                {
                    System.out.println(playersAtPosition.size() + " players at position " + position.toString());
                    Iterator<Player> playerIter = playersAtPosition.iterator();
                    while (playerIter.hasNext())
                    {
                        Player player = playerIter.next();
                        if (!player.isDrafted() && !this.players.contains(player))
                        {
                            this.players.add(player);
                        }
                    }
                }
                else
                {
                    System.out.println("No players at position: " + position.toString());
                }
            }
        }
        diffSort();
    }
    
    
    public void calculateRelativeDiffValues()
    {
        this.relativeDiffPlayers.clear();
        for (Position position : EnumSet.range(Position.QUARTER_BACK, Position.INDIVIDUAL_DEFENSIVE_PLAYER))
        {
            Player topUndraftedPlayerAtPosition = PlayerPositionCollection.Instance().getTopUndraftedPlayerAtPosition(position);
            Player secondBestUndraftedPlayerAtPosition = PlayerPositionCollection.Instance().getSecondBestUndraftedPlayerAtPosition(position);
            if (topUndraftedPlayerAtPosition != null && secondBestUndraftedPlayerAtPosition != null)
            {
                double fantasyPointsAtTop = topUndraftedPlayerAtPosition.getFantasyPoints();
                double fantasyPointsOfNext = secondBestUndraftedPlayerAtPosition.getFantasyPoints();
                double relativeDiffValue = fantasyPointsAtTop - fantasyPointsOfNext;
                topUndraftedPlayerAtPosition.setRelativeDiffValue(relativeDiffValue);
                this.relativeDiffPlayers.add(topUndraftedPlayerAtPosition);
            }
        }
        Comparator<Player> playerRelativeDiffComparator = new PlayerRelativeDiffComparator();
        Collections.sort(this.relativeDiffPlayers, playerRelativeDiffComparator);

    }

    public ArrayList<Player> getRelativeDiffPlayers()
    {
        return relativeDiffPlayers;
    }
}
