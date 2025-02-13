package fred.poker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvaluatorTest {

    @BeforeEach
    void setUp() {
        Table table = new Table(new Deck(), new EventManager());
        Deck deck = new Deck();
        Hand hand = new Hand(deck, table);
        Evaluator evaluator = new Evaluator();
        Lookup lookup = Lookup.getInstance();
    }

    @Test
    void getPrimeProduct() {
        int[] values = {0, 1, 2, 3, 4};
        Evaluator evaluator = new Evaluator();
        evaluator.values = values;
        assertEquals(2310, evaluator.getPrimeProduct());
    }

    @Test
    void evaluateHand() {
        int[] hand = {0, 1, 2, 3, 4};
    }
}