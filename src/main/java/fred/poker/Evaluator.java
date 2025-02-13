package fred.poker;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Evaluator {
    int[] values = new int[5];
    int[] suits = new int[5];



    public Evaluator() {
    }

    /**
     * Encode une main à partir des valeurs et des couleurs des cartes
     * @param hand : tableau de 5 entiers représentant les cartes
     * @return : main encodée au format Cactus Kev
     */
    public static int[] encodeHandToCactusKev(List<Card> hand) {
        int[] encodedHand = new int[5];
        for (int i = 0; i < hand.size(); i++) {
            encodedHand[i] = Card.convertToCactusKev(hand.get(i));
        }
        return encodedHand;
    }

    public static int evaluateHand(int[] encodedHand) {
        // Vérifie si toutes les cartes ont la même couleur (flush)
        if ((encodedHand[0] & encodedHand[1] & encodedHand[2] & encodedHand[3] & encodedHand[4] & 0xF000) != 0) {
            System.out.println(Arrays.toString(encodedHand) + " is a test to check if all cards have the same color");

            // Combine les valeurs des cartes en utilisant l'opération OR
            int handOr = (encodedHand[0] | encodedHand[1] | encodedHand[2] | encodedHand[3] | encodedHand[4]) >> 16;
            System.out.println("handOr: " + handOr);

            // Calcule le produit des nombres premiers pour les bits de rang
            long prime = Card.primeProductFromRankBits(handOr);

            System.out.println("prime: " + prime);
            System.out.println(Lookup.getFlushLookup().get(prime));

            // Recherche le rang dans la table de lookup des flush
            Map<Long, Integer> flushLookup = Lookup.getFlushLookup();
            Integer rank = flushLookup.get(prime);

            // Vérifie si le rang est trouvé
            if (rank == null) {
                throw new IllegalArgumentException("Rank is null");
            }

            return rank;
        }
        return -1;
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
