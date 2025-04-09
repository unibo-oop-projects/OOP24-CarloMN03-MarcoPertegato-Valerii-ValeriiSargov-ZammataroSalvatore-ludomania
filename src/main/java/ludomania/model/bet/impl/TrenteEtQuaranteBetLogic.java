package ludomania.model.bet.impl;

public class TrenteEtQuaranteBetLogic {
    private final String displayName;
    private final double payout;

    public TrenteEtQuaranteBetLogic(String displayName, double payout) {
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
