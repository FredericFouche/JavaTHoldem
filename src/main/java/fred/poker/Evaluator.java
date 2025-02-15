package fred.poker;

import java.util.Map;

public class Evaluator {

    /**
     * Evalue en utilisant les tables de lookup la force d'une main de 5 cartes.
     *
     * @param encodedFinalHand : la main de 5 cartes encodée sur 32 bits
     * @return le rang de la main
     */
    public static int evaluateHand(int[] encodedFinalHand) {

        // cette expression permet de vérifier si toutes les cartes sont de la même couleur
        boolean isFlush =
                ((encodedFinalHand[0] & encodedFinalHand[1] & encodedFinalHand[2] & encodedFinalHand[3] & encodedFinalHand[4]) & 0xF000)
                        != 0;

        if (isFlush) {
            // CAS FLUSH
            // -----------------------------------------------
            // Pas besoin de faire la multiplication rang par rang car on sait que chaque rang est différent

            int handOr = (encodedFinalHand[0] | encodedFinalHand[1] | encodedFinalHand[2] | encodedFinalHand[3] | encodedFinalHand[4]) >> 16;
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
                int primeOfCard = (encodedFinalHand[i] & 0xFF);
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
}
