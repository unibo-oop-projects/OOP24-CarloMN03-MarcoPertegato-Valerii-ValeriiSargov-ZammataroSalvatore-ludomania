package ludomania.model.bet.api;

public enum TrenteEtQuaranteBetColor {
    ROUGE("Rouge", 1.0),
    NOIR("Noir", 1.0);

    private final String displayName;
    private final double payout;

    TrenteEtQuaranteBetColor(String displayName, double payout) {
        this.displayName = displayName;
        this.payout = payout;
    }

    public String getDisplayName(){
        return displayName;
    }

    public double getPayout(){
        return payout;
    }    
}
