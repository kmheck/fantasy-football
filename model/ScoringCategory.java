package model;

import java.util.EnumSet;

public enum ScoringCategory
{
        PASSING_TDS
        {
            @Override
            public String toString()
            {
                return "Passing TDs";
            }

        },

        PASSING_YARDS
        {
            @Override
            public String toString()
            {
                return "Passing Yards";
            }

        },
        RUSHING_TDS
        {
            @Override
            public String toString()
            {
                return "Rushing TDs";
            }

        },
        RUSHING_YARDS
        {
            @Override
            public String toString()
            {
                return "Rushing Yards";
            }

        },
        RECEIVING_TDS
        {
            @Override
            public String toString()
            {
                return "Receiving TDs";
            }

        },
        RECEIVING_YARDS
        {
            @Override
            public String toString()
            {
                return "Receiving Yards";
            }

        },
        RECEPTIONS
        {
            @Override
            public String toString()
            {
                return "Receptions";
            }

        },
        FIELD_GOALS
        {
            @Override
            public String toString()
            {
                return "Field Goals";
            }

        },
        EXTRA_POINTS
        {
            @Override
            public String toString()
            {
                return "Extra Points";
            }

        },
        TACKLES
        {
            @Override
            public String toString()
            {
                return "Tackles";
            }

        },
        ASSISTS
        {
            @Override
            public String toString()
            {
                return "Assists";
            }

        },
        SACKS
        {
            @Override
            public String toString()
            {
                return "Sacks";
            }

        },
        INTERCEPTIONS
        {
            @Override
            public String toString()
            {
                return "Interceptions";
            }

        },
        FUMBLES
        {
            @Override
            public String toString()
            {
                return "Fumbles";
            }

        },
        RETURNS_FOR_TD
        {
            @Override
            public String toString()
            {
                return "Returns for TD";
            }

        },
        YARDS_ALLOWED
        {
            @Override
            public String toString()
            {
                return "Yards Allowed";
            }

        },
        POINTS_ALLOWED
        {
            @Override
            public String toString()
            {
                return "Points Allowed";
            }

        },
        YARDS_ALLOWED_CUTOFF
        {
            @Override
            public String toString()
            {
                return "Yards Allowed Cutoff";
            }

        },
        POINTS_ALLOWED_CUTOFF
        {
            @Override
            public String toString()
            {
                return "Points Allowed Cutoff";
            }

        },
        POINTS_PER_YARDS_ALLOWED
        {
            @Override
            public String toString()
            {
                return "Points Per Yards Allowed";
            }
        },
        POINTS_PER_POINTS_ALLOWED
        {
            @Override
            public String toString()
            {
                return "Points Per Points Allowed";
            }
        };
        
        public static final EnumSet<ScoringCategory> TeamDefenseNormalCategory = EnumSet.range(SACKS, RETURNS_FOR_TD);
        public static final EnumSet<ScoringCategory> TeamDefenseAlternateCategory = EnumSet.range(YARDS_ALLOWED, POINTS_ALLOWED_CUTOFF);
}
