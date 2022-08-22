package model;

import java.io.Serializable;
import java.util.EnumMap;

public class StatisticalProjections implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private EnumMap<ScoringCategory, Double> categoryProjections = new EnumMap<>(ScoringCategory.class);

    
    public StatisticalProjections()
    {
        super();
        for (ScoringCategory category : getCategoryProjections().keySet())
        {
            this.categoryProjections.put(category, 0.0);
        }
    }

    public EnumMap<ScoringCategory, Double> getCategoryProjections()
    {
        return categoryProjections;
    }

    public void setCategoryProjections(EnumMap<ScoringCategory, Double> categoryProjections)
    {
        this.categoryProjections = categoryProjections;
    }

    public void setCategoryProjection(ScoringCategory category, Double value)
    {
        this.categoryProjections.put(category, value);
    }
    
    public double getCategoryProjection(ScoringCategory category) throws NoSuchFieldException
    {
        if (this.categoryProjections.containsKey(category))
        {
            return this.categoryProjections.get(category).doubleValue();
        }
        else
        {
            throw new NoSuchFieldException();
        }
            
    }
    
    @Override
    public String toString()
    {
        String imageString = "";

        for (ScoringCategory category : getCategoryProjections().keySet())
        {
            try
            {
                imageString = imageString + category.toString() + ": " + getCategoryProjection(category) + "\n";
            }
            catch (NoSuchFieldException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return imageString;
    }
}
