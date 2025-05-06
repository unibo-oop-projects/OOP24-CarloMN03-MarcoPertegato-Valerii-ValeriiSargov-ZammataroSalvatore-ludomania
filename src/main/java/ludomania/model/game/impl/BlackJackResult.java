package ludomania.model.game.impl;

import java.util.Map;
import ludomania.model.game.api.CounterResult;
import ludomania.model.bet.impl.BlackJackBetType;
import ludomania.model.player.api.Player;

public class BlackJackResult extends CounterResult<BlackJackBetType> {

    private final Map<Player, Integer> playerScores;
    private final int dealerScore;

    public BlackJackResult(Map<Player, Integer> playerScores, int dealerScore) {
        super(null);
        this.playerScores = playerScores;
        this.dealerScore = dealerScore;
    }

    public Map<Player, Integer> getPlayerScores() {
        return playerScores;
    }

    public int getDealerScore() {
        return dealerScore;
    }

}
