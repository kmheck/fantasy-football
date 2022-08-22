package model;

public class DraftUtility
{

    public static void draftOrStrikeoutAction(Player player, boolean draftedOnMyTeam, Position position)
    {
      Player playerInCollection = PlayerPositionCollection.Instance().findMatchingPlayer(player, position);

      playerInCollection.setDrafted(true);
      System.out.println("Set Drafted flag for player: " + playerInCollection.getDisplayName());
      
      if (draftedOnMyTeam)
      {
    	  playerInCollection.setOnMyTeam(true);
          DraftedPlayerCollection.Instance().addPlayerToPosition(playerInCollection, playerInCollection.getPlayerPosition());
      }

      DraftManager.Instance().pickBookKeeping(playerInCollection);
//      PlayerPositionCollection.Instance().removePlayerFromPosition(playerInCollection, position);
      AllPlayerCollection.Instance().assemble();
      AllPlayerCollection.Instance().calculateRelativeDiffValues();
      DraftManager.Instance().saveDraft(false);
    }
}
