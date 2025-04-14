package ludomania.model.game.impl;

//import ludomania.model.game.api.CounterResult;

public class BlackJackResult /*extends CounterResult<Integer, Integer>*/{

    private final int playerScore;
    private final int dealerScore;

    public BlackJackResult(int playerScore, int dealerScore) {
        this.playerScore = playerScore;
        this.dealerScore = dealerScore;
    }
    
    public int getPlayerScore() {
        return playerScore;
    }

    public int getDealerScore() {
        return dealerScore;
    }

    public Integer getResult() {
        return getPlayerScore();
    }

    //boolean playerWin()

    //boolean dealerWin()
}
