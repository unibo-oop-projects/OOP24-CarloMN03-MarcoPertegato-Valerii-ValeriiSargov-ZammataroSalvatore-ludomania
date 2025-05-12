package ludomania.model.croupier;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.lyuda.jcards.DeckFactory;
import io.lyuda.jcards.Hand;
import ludomania.model.bet.api.Bet;
import ludomania.model.bet.impl.BlackJackBet;
import ludomania.model.bet.impl.BlackJackBetType;
import ludomania.model.croupier.impl.BlackJackDealer;
import ludomania.model.game.impl.BlackJackResult;
import ludomania.model.player.api.Player;
import ludomania.model.player.impl.BlackJackPlayer;
import ludomania.model.wallet.impl.WalletImpl;

public class BlackJackDealerTest {

    private static final double INITIAL_MONEY = 100.0;
    private static final double BET_AMOUNT = 10.0;

    private BlackJackDealer dealer;
    private WalletImpl wallet;
    private BlackJackPlayer player;
    private BlackJackBet bet;
    private Map<Player, Bet> roundBet;
    private DeckFactory deckFactory;

    @BeforeEach
    void setUp() {
        wallet = new WalletImpl(INITIAL_MONEY);
        player = new BlackJackPlayer(wallet);
        bet = new BlackJackBet(BET_AMOUNT, BlackJackBetType.BASE);
        roundBet = new HashMap<>();
        roundBet.put(player, bet);
        deckFactory = new DeckFactory();
        dealer = new BlackJackDealer(roundBet, deckFactory);
    }

    @Test
    void testDealInitialCards() {
        Map<Player, Hand> playerHands = new HashMap<>();
        dealer.dealInitialCards(playerHands);
        assertEquals(2, playerHands.get(player).getCards().size(), "Player should have 2 cards initially");
        assertEquals(2, dealer.getDealerHand().getCards().size(), "Dealer should have 2 cards initially");
    }

    @Test
    void testReset() {
        dealer.dealInitialCards(new HashMap<>());
        dealer.reset();
        assertEquals(0, dealer.getDealerHand().getCards().size(), "Dealer hand should be empty after reset");
        assertEquals(0, dealer.getDealerTotal(), "Dealer total should be 0 after reset");
    }

    @Test
    void testCheckBets_PlayerBust() {
        Map<Player, Hand> hands = new HashMap<>();
        Hand hand = new Hand();
        hand.addCard(new io.lyuda.jcards.Card(io.lyuda.jcards.Rank.KING, io.lyuda.jcards.Suit.HEARTS));
        hand.addCard(new io.lyuda.jcards.Card(io.lyuda.jcards.Rank.QUEEN, io.lyuda.jcards.Suit.HEARTS));
        hand.addCard(new io.lyuda.jcards.Card(io.lyuda.jcards.Rank.JACK, io.lyuda.jcards.Suit.HEARTS));
        hands.put(player, hand);

        dealer.dealerPlay();
        BlackJackResult result = dealer.declareResult(hands);
        Map<Player, Double> payouts = dealer.checkBets(result);

        assertEquals(0.0, payouts.get(player), "Player bust should result in 0 payout");
    }
}
