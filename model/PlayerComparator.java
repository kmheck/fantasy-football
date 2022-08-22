package model;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player>   
{
    public int compare(Player player1, Player player2)
    {
        int return_value;
        if (player1 == player2)
        {
            return_value = 0;
        }
        else if (player1.getDiffValue() == player2.getDiffValue())
        {
            return_value = 0;
        }
        else if (player1.getDiffValue() > player2.getDiffValue())
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
