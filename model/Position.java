package model;

public enum Position
{
    QUARTER_BACK
    {
        @Override
        public String toString()
        {
            return "QB";
        }
    },
    RUNNING_BACK
    {
        @Override
        public String toString()
        {
            return "RB";
        }
    },
    WIDE_RECEIVER
    {
        @Override
        public String toString()
        {
            return "WR";
        }
    },
    TIGHT_END
    {
        @Override
        public String toString()
        {
            return "TE";
        }
        
    },
    KICKER
    {
        @Override
        public String toString()
        {
            return "K";
        }
    },
    TEAM_DEFENSE
    {
        @Override
        public String toString()
        {
            return "DEF";
        }
    },
    INDIVIDUAL_DEFENSIVE_PLAYER
    {
        @Override
        public String toString()
        {
            return "IDP";
        }
    },
    FLEX
    {
        @Override
        public String toString()
        {
            return "FLEX";
        }
    },
    UNKNOWN
    {
        @Override
        public String toString()
        {
            return "UNK";
        }
    }
}
