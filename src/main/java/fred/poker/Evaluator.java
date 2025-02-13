package fred.poker;

import java.util.Arrays;
import java.util.Map;

public class Evaluator {
    int[] values = new int[5];
    int[] suits = new int[5];



    public Evaluator() {
    }

    public static int evaluateHand(int[] encodedHand) {
        // vérification flush
        boolean isFlush =
                ((encodedHand[0] & encodedHand[1] & encodedHand[2] & encodedHand[3] & encodedHand[4]) & 0xF000)
                        != 0;

        if (isFlush) {
            // CAS FLUSH
            // -----------------------------------------------
            // Pas besoin de faire la multiplication rang par rang car on sait que chaque rang est différent

            int handOr = (encodedHand[0] | encodedHand[1] | encodedHand[2] | encodedHand[3] | encodedHand[4]) >> 16;
            long prime = Card.primeProductFromRankBits(handOr);

            Map<Long, Integer> flushLookup = Lookup.getFlushLookup();
            Integer rank = flushLookup.get(prime);
            if (rank == null) {
                throw new IllegalArgumentException("Rank is null in flushLookup");
            }
            return rank;

        } else {
            // CAS NON-FLUSH (paires, brelans, full, carré, straights, high-card, etc.)
            // ---------------------------------------------------------
            // On calcule le produit des nombres premiers des cartes pour trouver le rang de la main

            long primeProduct = 1;
            for (int i = 0; i < 5; i++) {
                int primeOfCard = (encodedHand[i] & 0xFF);
                primeProduct *= primeOfCard;
            }

            Map<Long, Integer> unsuitedLookup = Lookup.getUnsuitedLookup();
            Integer rank = unsuitedLookup.get(primeProduct);
            if (rank == null) {
                throw new IllegalArgumentException(
                        "Rank is null in unsuitedLookup for primeProduct=" + primeProduct);
            }
            return rank;
        }
    }



    public int getPrimeProduct() {
        int[] primes = new int[5];
        System.out.println("values initial: " + Arrays.toString(values));
        System.out.println("values: ");
        for (int i = 0; i < values.length; i++) {
            System.out.println(values[i]);
            primes[i] = Card.PRIMES[values[i]];
        }
        System.out.println("primes: " + Arrays.toString(primes));
        return Arrays.stream(primes).reduce(1, (a, b) -> a * b);
    }
}
