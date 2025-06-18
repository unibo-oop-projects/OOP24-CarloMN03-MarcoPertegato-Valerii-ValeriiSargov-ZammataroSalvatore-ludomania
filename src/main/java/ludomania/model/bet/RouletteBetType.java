package ludomania.model.bet;

import ludomania.model.bet.api.BetType;

public class RouletteBetType {

    public static final BetType PLEIN = new BetType() {
        @Override
        public String getTypeName() {
            return "PLEIN";
        }

        @Override
        public Double getPayout() {
            return 35.0;
        }
    };

    public static final BetType CHEVAL = new BetType() {
        @Override
        public String getTypeName() {
            return "CHEVAL";
        }

        @Override
        public Double getPayout() {
            return 17.0;
        }
    };

    public static final BetType CARRE = new BetType() {
        @Override
        public String getTypeName() {
            return "CARRE";
        }

        @Override
        public Double getPayout() {
            return 8.0;
        }
    };

    public static final BetType DOUZAINE = new BetType() {
        @Override
        public String getTypeName() {
            return "DOUZAINE";
        }

        @Override
        public Double getPayout() {
            return 2.0;
        }
    };

    public static final BetType COLONNE = new BetType() {
        @Override
        public String getTypeName() {
            return "COLONNE";
        }

        @Override
        public Double getPayout() {
            return 2.0;
        }
    };

    public static final BetType PAIR = new BetType() {
        @Override
        public String getTypeName() {
            return "PAIR";
        }

        @Override
        public Double getPayout() {
            return 1.0;
        }
    };

    public static final BetType IMPAIR = new BetType() {
        @Override
        public String getTypeName() {
            return "IMPAIR";
        }

        @Override
        public Double getPayout() {
            return 1.0;
        }
    };

    public static final BetType MANQUE = new BetType() {
        @Override
        public String getTypeName() {
            return "MANQUE";
        }

        @Override
        public Double getPayout() {
            return 1.0;
        }
    };

    public static final BetType PASSE = new BetType() {
        @Override
        public String getTypeName() {
            return "PASSE";
        }

        @Override
        public Double getPayout() {
            return 1.0;
        }
    };

    public static final BetType ROUGE = new BetType() {
        @Override
        public String getTypeName() {
            return "ROUGE";
        }

        @Override
        public Double getPayout() {
            return 1.0;
        }
    };

    public static final BetType NOIR = new BetType() {
        @Override
        public String getTypeName() {
            return "NOIR";
        }

        @Override
        public Double getPayout() {
            return 1.0;
        }
    };
}
