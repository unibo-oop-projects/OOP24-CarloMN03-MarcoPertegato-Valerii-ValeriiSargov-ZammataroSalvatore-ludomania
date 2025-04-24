package ludomania.model.bet.api;

public enum TrenteEtQuaranteBetType implements BetType{
    ROUGE("Rouge", 1.0),
    NOIR("Noir", 1.0),
    COULEUR("Couleur", 1.0),
    ENVERSE("Enverse", 1.0);

    private final String typeName;
    private final double payout;

    TrenteEtQuaranteBetType(String displayName, double payout) {
        this.typeName = displayName;
        this.payout = payout;
    }

    public String getTypeName(){
        return typeName;
    }

    public Double getPayout(){
        return payout;
    }
}
