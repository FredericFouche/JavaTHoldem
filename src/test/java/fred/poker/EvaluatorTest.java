package fred.poker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvaluatorTest {

    @BeforeEach
    void setUp() {
        Table table = new Table(new Deck(), new EventManager());
        Deck deck = new Deck();
        Hand hand = new Hand(deck);
        Evaluator evaluator = new Evaluator();
        Lookup lookup = Lookup.getInstance();
    }

}