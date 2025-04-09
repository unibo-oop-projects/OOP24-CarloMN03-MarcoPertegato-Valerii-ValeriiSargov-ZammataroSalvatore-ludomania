package ludomania.model.bet.impl;

public enum TrenteEtQuaranteBetType {
    COULEUR("Couleur", 1.0),
    ENVERSE("Enverse", 1.0);

    private final TrenteEtQuaranteBetLogic logic;

    TrenteEtQuaranteBetType(String displayName, double payout) {
        this.logic = new TrenteEtQuaranteBetLogic(displayName, payout);
    }

    public String getDisplayName(){
        return logic.getDisplayName();
    }

    public double getPayout(){
        return logic.getPayout();
    }
}
