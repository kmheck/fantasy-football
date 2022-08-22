package model;

public class IndividualDefensivePlayer extends Player
{

    private String lastName;
    private String firstName;
    private String middleInitial = "";
    private IDPStats previousYearStats;
    private IDPStats projectedStats;

    public IDPStats getPreviousYearStats()
    {
        return previousYearStats;
    }
    public void setPreviousYearStats(IDPStats previousYearStats)
    {
        this.previousYearStats = previousYearStats;
    }
    public IDPStats getProjectedStats()
    {
        return projectedStats;
    }
    public void setProjectedStats(IDPStats projectedStats)
    {
        this.projectedStats = projectedStats;
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
    public String toString() 
    {
        String imageString = "";
        
        imageString = imageString + this.firstName + " " + this.middleInitial + " " + this.lastName + "\n";
        imageString = imageString + super.toString();
        
        return imageString;
    }

}
