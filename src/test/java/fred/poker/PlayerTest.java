package fred.poker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @BeforeEach
    void setUp() {
        Lookup.getInstance();
    }

    @Test
    void monteCarloSimulation() {
        Lookup.getInstance();

        int nbOftrials = 200;
        int handValue = 3326;

        double winrate = Player.monteCarloSimulation(nbOftrials, handValue);
        assertTrue(winrate >= 0 && winrate <= 1);


        System.out.println("-------------------");
        System.out.println("Winrate: " + winrate);
        System.out.println("-------------------");
    }

}