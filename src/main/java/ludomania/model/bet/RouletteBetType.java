package ludomania.model.bet;

import ludomania.model.bet.api.BetType;

public class RouletteBetType {
    private static final Double PleinPayout = 35.0;
    private static final Double ChevalPayout = 17.0;
    private static final Double CarrePayout = 8.0;
    private static final Double DouzainePayout = 2.0;
    private static final Double ColonnePayout = 2.0;
    private static final Double PairPayout = 1.0;
    private static final Double ImpairPayout = 1.0;
    private static final Double ManquePayout = 1.0;
    private static final Double PassePayout = 1.0;
    private static final Double RougePayout = 1.0;
    private static final Double NoirPayout = 1.0;

    private RouletteBetType() { }

    public static final BetType PLEIN = new BetType() {
        @Override
        public String getTypeName() {
            return "PLEIN";
        }

        @Override
        public Double getPayout() {
            return PleinPayout;
        }
    };

    public static final BetType CHEVAL = new BetType() {
        @Override
        public String getTypeName() {
            return "CHEVAL";
        }

        @Override
        public Double getPayout() {
            return ChevalPayout;
        }
    };

    public static final BetType CARRE = new BetType() {
        @Override
        public String getTypeName() {
            return "CARRE";
        }

        @Override
        public Double getPayout() {
            return CarrePayout;
        }
    };

    public static final BetType DOUZAINE = new BetType() {
        @Override
        public String getTypeName() {
            return "DOUZAINE";
        }

        @Override
        public Double getPayout() {
            return DouzainePayout;
        }
    };

    public static final BetType COLONNE = new BetType() {
        @Override
        public String getTypeName() {
            return "COLONNE";
        }

        @Override
        public Double getPayout() {
            return ColonnePayout;
        }
    };

    public static final BetType PAIR = new BetType() {
        @Override
        public String getTypeName() {
            return "PAIR";
        }

        @Override
        public Double getPayout() {
            return PairPayout;
        }
    };

    public static final BetType IMPAIR = new BetType() {
        @Override
        public String getTypeName() {
            return "IMPAIR";
        }

        @Override
        public Double getPayout() {
            return ImpairPayout;
        }
    };

    public static final BetType MANQUE = new BetType() {
        @Override
        public String getTypeName() {
            return "MANQUE";
        }

        @Override
        public Double getPayout() {
            return ManquePayout;
        }
    };

    public static final BetType PASSE = new BetType() {
        @Override
        public String getTypeName() {
            return "PASSE";
        }

        @Override
        public Double getPayout() {
            return PassePayout;
        }
    };

    public static final BetType ROUGE = new BetType() {
        @Override
        public String getTypeName() {
            return "ROUGE";
        }

        @Override
        public Double getPayout() {
            return RougePayout;
        }
    };

    public static final BetType NOIR = new BetType() {
        @Override
        public String getTypeName() {
            return "NOIR";
        }

        @Override
        public Double getPayout() {
            return NoirPayout;
        }
    };
}
