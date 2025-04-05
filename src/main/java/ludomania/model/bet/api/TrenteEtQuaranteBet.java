package ludomania.model.bet.api;

public abstract class TrenteEtQuaranteBet implements Bet{
    private final int value;
    
    public TrenteEtQuaranteBet(int value){
        this.value=value;
    }

    public int getValue(){
        return value;
    }
}