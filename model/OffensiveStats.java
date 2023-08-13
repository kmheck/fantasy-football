package model;

import java.io.Serializable;

//Note - this class was primarily created for the purpose of populating an "Examine" TableView
// item - some attributes are added specifically for that TableView (such as displayName) even
// though they would normally not be in a class such as this.

public class OffensiveStats implements Serializable
{

    private static final long serialVersionUID = 1L;
    //Note - this attribute
	private String displayName;
	private String year;
	private double games;
	private double passingYards;
	private double passingTDs;
	private double rushingYards;
	private double rushingTDs;
	private double receivingYards;
	private double receivingTDs;
	private double receptions;

    private double interceptions;
	private double fumbles;
	private double fantasyPoints;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public double getGames() {
		return games;
	}

	public void setGames(double games) {
		this.games = games;
	}

    public double getReceptions()
    {
        return receptions;
    }

    public void setReceptions(double receptions)
    {
        this.receptions = receptions;
    }

	public double getInterceptions() {
		return interceptions;
	}

	public void setInterceptions(double interceptions) {
		this.interceptions = interceptions;
	}

	public double getFumbles() {
		return fumbles;
	}

	public void setFumbles(double fumbles) {
		this.fumbles = fumbles;
	}
	private double extraPoints;
	private double fieldGoals;


	public OffensiveStats() {
		super();
		this.passingYards = 0.0;
		this.passingTDs = 0.0;
		this.rushingYards = 0.0;
		this.rushingTDs = 0.0;
		this.receivingYards = 0.0;
		this.receivingTDs = 0.0;
		this.receptions = 0.0;
		this.extraPoints = 0.0;
		this.fieldGoals = 0.0;
		this.interceptions = 0.0;
		this.fumbles = 0.0;
		this.games = 17.0;
		this.fantasyPoints = 0.0;

	}

	public OffensiveStats(StatisticalProjections projections)
	{
        for (ScoringCategory category : projections.getCategoryProjections().keySet())
        {
        	switch(category)
        	{
        	case PASSING_YARDS:
        		try
        		{
					this.passingYards = projections.getCategoryProjection(category);
				}
        		catch (NoSuchFieldException e)
        		{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	case PASSING_TDS:
        		try
        		{
					this.passingTDs = projections.getCategoryProjection(category);
				}
        		catch (NoSuchFieldException e)
        		{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	case RUSHING_YARDS:
        		try
        		{
					this.rushingYards = projections.getCategoryProjection(category);
				}
        		catch (NoSuchFieldException e)
        		{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	case RUSHING_TDS:
        		try
        		{
					this.rushingTDs = projections.getCategoryProjection(category);
				}
        		catch (NoSuchFieldException e)
        		{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	case RECEIVING_YARDS:
        		try
        		{
					this.receivingYards = projections.getCategoryProjection(category);
				}
        		catch (NoSuchFieldException e)
        		{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            case RECEIVING_TDS:
                try
                {
                    this.receivingTDs = projections.getCategoryProjection(category);
                }
                catch (NoSuchFieldException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            case RECEPTIONS:
                try
                {
                    this.receptions = projections.getCategoryProjection(category);
                }
                catch (NoSuchFieldException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        	case FIELD_GOALS:
        		try
        		{
					this.fieldGoals = projections.getCategoryProjection(category);
				}
        		catch (NoSuchFieldException e)
        		{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	case EXTRA_POINTS:
        		try
        		{
					this.extraPoints = projections.getCategoryProjection(category);
				}
        		catch (NoSuchFieldException e)
        		{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	case INTERCEPTIONS:
        		try
        		{
					this.interceptions = projections.getCategoryProjection(category);
				}
        		catch (NoSuchFieldException e)
        		{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	case FUMBLES:
        		try
        		{
					this.fumbles = projections.getCategoryProjection(category);
				}
        		catch (NoSuchFieldException e)
        		{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			default:
				break;

        	}
        }

	}
	@Override
	public String toString() {
		return "OffensiveStats [displayName=" + displayName + ", games="
				+ games + ", passingYards=" + passingYards + ", passingTDs="
				+ passingTDs + ", rushingYards=" + rushingYards
				+ ", rushingTDs=" + rushingTDs + ", receivingYards="
				+ receivingYards + ", receivingTDs=" + receivingTDs
				+ ", receptions=" + receptions
				+ ", interceptions=" + interceptions + ", fumbles=" + fumbles
				+ ", fantasyPoints=" + fantasyPoints + ", extraPoints="
				+ extraPoints + ", fieldGoals=" + fieldGoals + "]";
	}
	public double getPassingYards() {
		return passingYards;
	}
	public void setPassingYards(double passingYards) {
		this.passingYards = passingYards;
	}
	public double getPassingTDs() {
		return passingTDs;
	}
	public void setPassingTDs(double passingTDs) {
		this.passingTDs = passingTDs;
	}
	public double getRushingYards() {
		return rushingYards;
	}
	public void setRushingYards(double rushingYards) {
		this.rushingYards = rushingYards;
	}
	public double getRushingTDs() {
		return rushingTDs;
	}
	public void setRushingTDs(double rushingTDs) {
		this.rushingTDs = rushingTDs;
	}
	public double getReceivingYards() {
		return receivingYards;
	}
	public void setReceivingYards(double receivingYards) {
		this.receivingYards = receivingYards;
	}
	public double getReceivingTDs() {
		return receivingTDs;
	}
	public void setReceivingTDs(double receivingTDs) {
		this.receivingTDs = receivingTDs;
	}
	public double getExtraPoints() {
		return extraPoints;
	}
	public void setExtraPoints(double extraPoints) {
		this.extraPoints = extraPoints;
	}
	public double getFieldGoals() {
		return fieldGoals;
	}
	public void setFieldGoals(double fieldGoals) {
		this.fieldGoals = fieldGoals;
	}

	public void calculateFantasyPoints(Position playerPosition)
	{
		this.fantasyPoints = 17.0/this.games*
				(this.passingYards*LeagueScoring.Instance().getScoring(playerPosition, ScoringCategory.PASSING_YARDS) +
				 this.passingTDs*LeagueScoring.Instance().getScoring(playerPosition, ScoringCategory.PASSING_TDS) +
				 this.rushingYards*LeagueScoring.Instance().getScoring(playerPosition, ScoringCategory.RUSHING_YARDS) +
				 this.rushingTDs*LeagueScoring.Instance().getScoring(playerPosition, ScoringCategory.RUSHING_TDS) +
				 this.receivingYards*LeagueScoring.Instance().getScoring(playerPosition, ScoringCategory.RECEIVING_YARDS) +
				 this.receivingTDs*LeagueScoring.Instance().getScoring(playerPosition, ScoringCategory.RECEIVING_TDS) +
				 this.receptions*LeagueScoring.Instance().getScoring(playerPosition, ScoringCategory.RECEPTIONS) +
				 this.fieldGoals*LeagueScoring.Instance().getScoring(playerPosition, ScoringCategory.FIELD_GOALS) +
				 this.extraPoints*LeagueScoring.Instance().getScoring(playerPosition, ScoringCategory.EXTRA_POINTS));

	}

	public double getFantasyPoints() {
		return fantasyPoints;
	}

	public void setFantasyPoints(double fantasyPoints) {
		this.fantasyPoints = fantasyPoints;
	}

}
