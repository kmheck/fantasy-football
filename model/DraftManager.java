package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

public class DraftManager {

    private static DraftManager the_instance =null;

    static public DraftManager Instance() {
        if (the_instance == null) {
            the_instance = new DraftManager();
        }
        return the_instance;
    }
    
    private DraftManager() {
        System.out.println("DraftManager() - private constructor");
        this.pickCount = 0;

    }

    /**
     * Records how many players have been drafted at any point in the draft.
     */
	int pickCount;
	

    Stack<Player> picksStack = new Stack<Player>();
	
	public void incrementPickCount()
	{
		this.pickCount++;
	}

    public void setPickCount(int pickCount)
    {
        this.pickCount = pickCount;
    }

	public int getPickCount()
	{
		return pickCount;
	}
	
	/**
	 * Performs all the book keeping that is necessary when a pick is made
	 * @param player - Used to add to the picksStack in case an "undo" takes place.
	 */
	public void pickBookKeeping(Player player)
	{
		System.out.println("DraftManager.pickBookKeeping()");
		
		incrementPickCount();
		picksStack.push(player);
		if (picksStack.size() > 10)
		{
			this.picksStack.removeElementAt(0);
		}
		System.out.println("picksStack size: " + this.picksStack.size());
		
	}
	
	public void undoPick()
	{
		System.out.println("DraftManager.undoPick()");
		Player playerToUndo = this.picksStack.pop();
		System.out.println("Attempting to restore player: " + playerToUndo.getDisplayName() + " back into draft");
		playerToUndo.setDrafted(false);
		playerToUndo.setOnMyTeam(false);
		PlayerPositionCollection.Instance().addPlayerToPosition(playerToUndo, playerToUndo.getPlayerPosition());
		PlayerPositionCollection.Instance().sort(playerToUndo.getPlayerPosition());
		AllPlayerCollection.Instance().assemble();
		AllPlayerCollection.Instance().calculateRelativeDiffValues();
		DraftedPlayerCollection.Instance().removePlayerFromPosition(playerToUndo, playerToUndo.getPlayerPosition());
	}
	
	public void saveDraft(boolean activatedFromUI)
	{
	    try
	    {
	        
            System.out.println("saveDraft() - Enter");
	        FileOutputStream playerFileOutputStream = new FileOutputStream(ConfigurationData.Instance().getSavedDraftPlayerFile());
	        ObjectOutputStream draftOutputStream = new ObjectOutputStream(playerFileOutputStream);
	        //TODO: add method to return entire PlayerPositionCollection and use that in writeObject
	        Map< Position, ArrayList<Player> > entirePositionCollection = PlayerPositionCollection.Instance().getEntirePositionCollection();
	        draftOutputStream.writeObject(entirePositionCollection);
	        draftOutputStream.close();
	        
            if (activatedFromUI)  
            {
                
                FileOutputStream rosterFileOutputStream = new FileOutputStream(ConfigurationData.Instance().getSavedDraftRosterFile());
                ObjectOutputStream rosterObjectOutputStream = new ObjectOutputStream(rosterFileOutputStream);
    	        Roster rosterInformation = Roster.Instance();
    	        System.out.println("rosterInformation before serialization: " + rosterInformation.toString());
    	        System.out.println("Saving roster file to: " + ConfigurationData.Instance().getSavedDraftRosterFile());
    	        rosterObjectOutputStream.writeObject(rosterInformation);
    	        rosterObjectOutputStream.close();
            }
            else
            {
                System.out.println("Roster file already saved - not saving again");
            }
	 
	    }
	    catch (FileNotFoundException e)
	    {
	        System.out.println("Could not find file: " + ConfigurationData.Instance().getSavedDraftPlayerFile());
	        e.printStackTrace();
	    }
	    catch(IOException ex)
	    {
	           ex.printStackTrace();
	    } 

	}
	
	private void restorePicks()
	{
	    //TODO: Iterate through players to see who is on my team and
	    // add to DraftedPlayerCollection
	    System.out.println("restorePicks() - Enter");
	    int pickCount = 0;
	    
        ArrayList<Player> playersAtPosition;
        for (Position position : EnumSet.range(Position.QUARTER_BACK, Position.INDIVIDUAL_DEFENSIVE_PLAYER))
        {
            playersAtPosition = PlayerPositionCollection.Instance().getPositionCollection(position);
            if (playersAtPosition != null)
            {
                System.out.println(playersAtPosition.size() + " players at position " + position.toString());
                Iterator<Player> playerIter = playersAtPosition.iterator();
                while (playerIter.hasNext())
                {
                    Player player = playerIter.next();
                    if (player.isDrafted())
                    {
                        pickCount++;
                        if (player.isOnMyTeam())
                        {
                            DraftedPlayerCollection.Instance().addPlayerToPosition(player, position);
                        }
                    }
                }
            }
            else
            {
                System.out.println("No players at position: " + position.toString());
            }
        }
        setPickCount(pickCount);
	}
	
	
	@SuppressWarnings({ "unchecked", "unused" })
    public void restoreDraft()
	{
	    //TODO: Implement this method
	    // Read in serialized object from file
	    // Do stuff that is done after a player is drafted
	    TreeMap< Position, ArrayList<Player> > map = null;
	    //Roster rosterInstance = Roster.Instance();
        try
        {
            FileInputStream playerFileInputStream = new FileInputStream(ConfigurationData.Instance().getSavedDraftPlayerFile());
            ObjectInputStream playerObjectInputStream = new ObjectInputStream(playerFileInputStream);

            FileInputStream rosterFileInputStream = new FileInputStream(ConfigurationData.Instance().getSavedDraftRosterFile());
            ObjectInputStream rosterObjectInputStream = new ObjectInputStream(rosterFileInputStream);
            
            System.out.println("Reading in roster object from file: " + ConfigurationData.Instance().getSavedDraftRosterFile());
            Roster rosterInstance = (Roster)rosterObjectInputStream.readObject();
            System.out.println("rosterInstance after draft restore: " + rosterInstance.toString());
            Roster.setSerializedInstance(rosterInstance);
            System.out.println("Roster singleton after draft restore: " + Roster.Instance().toString());

            map = (TreeMap< Position, ArrayList<Player> >)playerObjectInputStream.readObject();
            PlayerPositionCollection.Instance().setEntirePositionCollection(map);
            AllPlayerCollection.Instance().assemble();
            AllPlayerCollection.Instance().calculateRelativeDiffValues();
            restorePicks();
            //TODO: Somehow need to iterate over the various positions and figure
            //out if player was drafted on my team and add to DraftedPlayerCollection
            playerObjectInputStream.close();
            playerFileInputStream.close();
            rosterObjectInputStream.close();
            rosterFileInputStream.close();
        }
        catch (IOException ioe)
        {
            System.out.println("Caught IOException");
            ioe.printStackTrace();
            return;
        }
        catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
	}
}
