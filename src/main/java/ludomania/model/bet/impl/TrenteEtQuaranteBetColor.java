package ludomania.model.bet.impl;

public enum TrenteEtQuaranteBetColor {
    ROUGE("Rouge", 1.0),
    NOIR("Noir", 1.0);

    private final TrenteEtQuaranteBetLogic logic;

    TrenteEtQuaranteBetColor(String displayName, double payout) {
        this.logic = new TrenteEtQuaranteBetLogic(displayName, payout);
    }

    public String getDisplayName(){
        return logic.getDisplayName();
    }

    public double getPayout(){
        return logic.getPayout();
    }    
}
