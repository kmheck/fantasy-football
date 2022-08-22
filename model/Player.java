package model;

import java.io.Serializable;

public abstract class Player implements Comparable<Player>, Serializable
{
    private static final long serialVersionUID = 1L;
    protected Position playerPosition;
    protected String displayName;
    protected String displayPosition;
    private boolean drafted = false;
    private boolean onMyTeam = false;

    protected NFLTeam nflTeam;
    protected String nflTeamString;
    protected double fantasyPoints = 0.0;
    protected double diffValue;
    protected double relativeDiffValue;

    public double getRelativeDiffValue()
    {
        return relativeDiffValue;
    }

    public void setRelativeDiffValue(double relativeDiffValue)
    {
        this.relativeDiffValue = relativeDiffValue;
    }

    protected StatisticalProjections projections;

    public String getNflTeamString()
    {
        return nflTeamString;
    }

    public void setNflTeamString(String nflTeamString)
    {
        this.nflTeamString = nflTeamString;
    }

    public void calculateFantasyPoints()
    {
        double calculatedFantasyPoints = 0.0;
        for (ScoringCategory category : this.getProjections().getCategoryProjections().keySet())
        {
            double pointsPerCategoryScoring = LeagueScoring.Instance().getScoring(this.playerPosition, category);
            try
            {
                calculatedFantasyPoints = calculatedFantasyPoints + pointsPerCategoryScoring * this.getProjections().getCategoryProjection(category);
            }
            catch (NoSuchFieldException e)
            {
                System.out.println("Caught an exception attempting to get values for category: " + category.toString());
            }
        }
        this.fantasyPoints = calculatedFantasyPoints;
    }
    
    public double getFantasyPoints()
    {
        return fantasyPoints;
    }
    
    
    public Player()
    {
        super();
        this.displayName = "";
        this.displayPosition = "";
        this.drafted = false;
        this.onMyTeam = false;
        this.nflTeam = NFLTeam.Unknown;
        this.nflTeamString = "";
        this.diffValue = 0.0;
        this.fantasyPoints = 0.0;
        this.playerPosition = Position.UNKNOWN;
        this.projections = new StatisticalProjections();
    }
    
    public Player(Position position)
    {
        super();
        this.displayName = "";
        this.displayPosition = "";
        this.drafted = false;
        this.onMyTeam = false;
        this.nflTeam = NFLTeam.Unknown;
        this.nflTeamString = "";
        this.diffValue = 0.0;
        this.fantasyPoints = 0.0;
        this.projections = new StatisticalProjections();
        setPlayerPosition(position);

    }

    public String getDisplayName()
    {
        return displayName;
    }


    public void setDisplayPosition(String displayPosition)
    {
        this.displayPosition = displayPosition;
    }

    public String getDisplayPosition()
    {
        return displayPosition;
    }


    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public NFLTeam getNflTeam()
    {
        return nflTeam;
    }


    public double getDiffValue()
    {
        return diffValue;
    }

    public void setFantasyPoints(double fantasyPoints)
    {
        this.fantasyPoints = fantasyPoints;
    }

    public void setDiffValue(double diffValue)
    {
        this.diffValue = diffValue;
    }

    public Position getPlayerPosition()
    {
        return playerPosition;
    }

    public void setPlayerPosition(Position playerPosition)
    {
        this.playerPosition = playerPosition;
        this.displayPosition = this.playerPosition.toString();
    }

    public boolean isDrafted()
    {
        return drafted;
    }

    public void setDrafted(boolean drafted)
    {
        this.drafted = drafted;
    }

    public boolean isOnMyTeam()
    {
        return onMyTeam;
    }

    public void setOnMyTeam(boolean onMyTeam)
    {
        this.onMyTeam = onMyTeam;
    }

    public NFLTeam getNFLTeam()
    {
        return nflTeam;
    }
    
    public void setNFLTeam(NFLTeam nflTeam)
    {
        this.nflTeam = nflTeam;
        this.nflTeamString = this.nflTeam.toString();
    }

    public StatisticalProjections getProjections()
    {
        return projections;
    }

    public void setProjections(StatisticalProjections projections)
    {
        this.projections = projections;
        
        calculateFantasyPoints();
    }
    
    public void createDisplayName()
    {
        this.displayName = "";
    }

    public boolean matchesName(Player player)
    {
        boolean matches = false;

        if (this.displayName.equals(player.displayName))
        {
            matches = true;
        }
        return matches;
    }

    @Override
    public boolean equals(Object player) {
        boolean matches = true;
        
        if (player == null) return false;
        if (player == this) return true;
        if (!(player instanceof Player)) return false;
        Player o = (Player) player;
        if (o.displayName != this.displayName)
        {
            matches = false;
        }
        else if (o.playerPosition != this.playerPosition)
        {
            matches = false;
        }
        
        return matches;
    }
    
    public int compareTo(Player player)
    {
        final int EQUAL = 0;
        //this optimization is usually worthwhile, and can
        //always be added
        if (this == player) return EQUAL;

        if (this.equals(player)) return EQUAL;
        
        int comparison;
        comparison = this.displayName.compareTo(player.displayName);
        if (comparison != EQUAL) return comparison;

        return EQUAL;

        }
    @Override
    public String toString() 
    {
        String imageString = "";
        imageString = imageString + "displayName:    " + this.displayName + "\n";
        imageString = imageString + "displayPosition: " + this.displayPosition + "\n";
        imageString = imageString + "drafted:        " + this.drafted + "\n";
        imageString = imageString + "onMyTeam:       " + this.onMyTeam + "\n";
        imageString = imageString + "nflTeam:        " + this.nflTeam.toString() + "\n";
        imageString = imageString + "projections:    " + "\n";
        imageString = imageString + this.projections.toString();
        imageString = imageString + "\n";
        imageString = imageString + "fantasy points: " + Double.toString(this.fantasyPoints) + "\n";
        imageString = imageString + "diff value:     " + Double.toString(this.diffValue) + "\n";
        
        return imageString;
    }
}
