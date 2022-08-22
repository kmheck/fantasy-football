package model;

public class OffensivePlayer extends Player
{

    public OffensivePlayer()
    {
        super();
    }
    
    public OffensivePlayer(Position position)
    {
        super(position);
    }

    private String lastName;
    private String firstName;
    private String middleInitial = "";
    private OffensiveStats previousYearStats;
    private OffensiveStats projectedStats;
    
    public OffensiveStats getProjectedStats() {
        return projectedStats;
    }

    public void setProjectedStats(OffensiveStats projectedStats) {
        this.projectedStats = projectedStats;
    }

    public OffensiveStats getPreviousYearStats() {
        return previousYearStats;
    }

    public void setPreviousYearStats(OffensiveStats previousYearStats) {
        this.previousYearStats = previousYearStats;
    }

    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public String getMiddleInitial()
    {
        return middleInitial;
    }
    public void setMiddleInitial(String middleInitial)
    {
        this.middleInitial = middleInitial;
    }
    
    @Override
    public void createDisplayName()
    {
        if (this.middleInitial != "")
        {
          this.displayName = this.firstName + " " + this.middleInitial + " " + this.lastName;
        }
        else
        {
            this.displayName = this.firstName + " " + this.lastName;
        }
    }

    @Override
    public boolean equals(Object player) {
        boolean matches = true;
        
        if (player == null) return false;
        if (player == this) return true;
        if (!(player instanceof OffensivePlayer)) return false;
        OffensivePlayer o = (OffensivePlayer) player;
        if (o.firstName != this.firstName)
        {
            matches = false;
        }
        else if (o.lastName != this.lastName)
        {
            matches = false;
        }
        
        return matches;
    }

    @Override
    public String toString() 
    {
        String imageString = "";
        
        imageString = imageString + super.toString();
        
        return imageString;
    }

//    @Override
//    public int compareTo(OffensivePlayer offensivePlayer)
//    {
//        final int BEFORE = -1;
//        final int EQUAL = 0;
//        final int AFTER = 1;
//
//        //this optimization is usually worthwhile, and can
//        //always be added
//        if (this == offensivePlayer) return EQUAL;
//
//        if (this.equals(offensivePlayer)) return EQUAL;
//        
//        int comparison;
//        comparison = this.lastName.compareTo(offensivePlayer.lastName);
//        if (comparison != EQUAL) return comparison;
//
//        comparison = this.firstName.compareTo(offensivePlayer.firstName);
//        if (comparison != EQUAL) return comparison;
//        
//        return EQUAL;
//
//    }
}
