package ludomania.model.game.impl;

import java.util.Map;

import ludomania.model.game.api.CounterResult;
import ludomania.model.player.api.Player;

public class BlackJackResult extends CounterResult<Map<Player, BlackJackOutcomeResult>> {

    public BlackJackResult(Map<Player, BlackJackOutcomeResult> results) {
        super(results);
    }

    @Override
    public Map<Player, BlackJackOutcomeResult> getResult() {
        return result;
    }
}


