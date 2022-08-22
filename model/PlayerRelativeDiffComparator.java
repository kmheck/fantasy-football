package model;

import java.util.Comparator;

// This class is used to compare players within a given position.
// To compare players across positions, see PlayerComparator

public class PlayerRelativeDiffComparator implements Comparator<Player>
{
    public int compare(Player player1, Player player2) {
        int return_value = 0;
        if (player1 == player2)
        {
            return_value = 0;
        }
        else if (player1.getRelativeDiffValue() == player2.getRelativeDiffValue())
        {
            return_value = 0;
        }
        else if (player1.getRelativeDiffValue() > player2.getRelativeDiffValue())
        {
            return_value = -1;
        }
        else
        {
            return_value = 1;
        }
        return return_value;
    }

}
