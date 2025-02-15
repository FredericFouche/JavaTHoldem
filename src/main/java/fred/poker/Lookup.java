package fred.poker;

import java.util.*;

public class Lookup {
    private static Map<Long, Integer> flushLookup = new HashMap<>();
    private static Map<Long, Integer> unsuitedLookup = new HashMap<>();

    public static final int MAX_STRAIGHT_FLUSH = 10;
    public static final int MAX_FOUR_OF_A_KIND   = 166;
    public static final int MAX_FULL_HOUSE       = 322;
    public static final int MAX_FLUSH            = 1599;
    public static final int MAX_STRAIGHT         = 1609;
    public static final int MAX_THREE_OF_A_KIND  = 2467;
    public static final int MAX_TWO_PAIR         = 3325;
    public static final int MAX_PAIR             = 6185;

    private Lookup() {
        lookUpTable();
    }

    public static synchronized Lookup getInstance() {
         return new Lookup();
    }

    public void lookUpTable() {
        buildFlushes();
        buildMultiples();
    }

    // ----------------------------------------------------------------------
    // 1. Construction des tables de lookup pour les flush
    // ----------------------------------------------------------------------
    private void buildFlushes() {

        // cet array contient les 10 combinaisons de cartes qui forment un flush straight sous forme de binaire
        // 1 = carte présente, 0 = carte absente
        int[] straightFlushes = {
                0b1111100000000, // AKJQT
                0b0111110000000, // KJQT9
                0b0011111000000, // QJT98
                0b0001111100000,
                0b0000111110000,
                0b0000011111000,
                0b0000001111100,
                0b0000000111110,
                0b0000000011111,
                0b1000000001111
        };

        int rank = 1;

        /*
          On ajoute les combinaisons de straight flush au début de la table de lookup
         */
        for (int mask: straightFlushes) {
            long product = primeProductFromRankBits(mask);
            flushLookup.put(product, rank);
            rank++;
        }

        /*
          On génère toutes les combinaisons possibles de flush
         */
        List<Integer> allCombinationsFlush = generateAllCombinations(13, 5);

        /*
            On ajoute les combinaisons de flush qui ne sont pas des straight flush à un set
         */
        Set<Integer> straightMasks = new HashSet<>();
        for (int mask: straightFlushes) {
            straightMasks.add(mask);
        }

        /*
            On crée une liste pour les combinaisons de flush qui ne sont pas des straight flush
            Pour déduire les combinaisons de flush qui ne sont pas des straight flush, on utilise le set précédent
            Ensuite on trie la liste par ordre décroissant car les combinaisons les plus fortes sont les plus hautes
         */
        List<Integer> flushCombinations = new ArrayList<>();
        for (int combination: allCombinationsFlush) {
            if (!straightMasks.contains(combination)) {
                flushCombinations.add(combination);
            }
        }
        flushCombinations.sort(Collections.reverseOrder());

        /*
          On ajoute les combinaisons de flush qui ne sont pas des straight flush à la table de lookup
          On commece à placer après le rang des full house car on a déjà ajouté les straight flush
          Les full houses sont les combinaisons les plus fortes après les straight flush
         */
        rank = MAX_FULL_HOUSE + 1;
        for (int combination: flushCombinations) {
            long product = primeProductFromRankBits(combination);
            flushLookup.put(product, rank);
            rank++;
        }
        addStraightHighCards(straightFlushes, flushCombinations);
    }

    // ----------------------------------------------------------------------
    // A ce Stade on a ajouté les combinaisons de straight flush dans les rangs 1 à 10
    // On a ajouté les combinaisons de flush qui ne sont pas des straight flush dans les rangs 323 à 1599
    // ----------------------------------------------------------------------

    /**
     *
     * @param straightMasks : combinaisons de straight flush
     * @param flushCombinations : combinaisons de flush
     */
    private void addStraightHighCards(int[] straightMasks, List<Integer> flushCombinations) {

        /*
        Insère les masques de quinte (straights non-suited) dans unsuitedLookup
        en leur attribuant un rang juste après les flushes
        */
        int rank = MAX_FLUSH + 1;
        for (Integer straight : straightMasks) {
            long primeProduct = primeProductFromRankBits(straight);
            unsuitedLookup.put(primeProduct, rank);
            rank++;
        }

        /*
        Réutilise les combinaisons de rangs (ex-flush) pour les classer
        en High Card dans unsuitedLookup après les paires
        */
        rank = MAX_PAIR + 1;
        for (Integer flush : flushCombinations) {
            long primeProduct = primeProductFromRankBits(flush);
            unsuitedLookup.put(primeProduct, rank);
            rank++;
        }

    }

    // ----------------------------------------------------------------------
    // 2. Construction des mains non-flush
    // ----------------------------------------------------------------------
    private void buildMultiples() {

        /*
        Ajoute les combinaisons de 4 cartes de même rang (quads) dans unsuitedLookup
         */
        int rank = MAX_STRAIGHT_FLUSH + 1;
        for (int i = 12; i >= 0; i--) {
            for (int kicker = 12; kicker >= 0; kicker--) {
                if (kicker == i) continue;
                long product = pow(Card.PRIMES[i], 4) * Card.PRIMES[kicker];
                unsuitedLookup.put(product, rank);
                rank++;
            }
        }

        /*
        Les full houses
         */
        rank = MAX_FOUR_OF_A_KIND + 1;
        for(int brelan = 12; brelan >= 0; brelan--) {
            for(int pair = 12; pair >= 0; pair--) {
                if (pair == brelan) continue;
                long product = pow(Card.PRIMES[brelan], 3) * pow(Card.PRIMES[pair], 2);
                unsuitedLookup.put(product, rank);
                rank++;
            }
        }

        /*
        Les Brelans
         */
        rank = MAX_STRAIGHT + 1;
        for (int i = 12; i >= 0; i--) {
            for (int kicker1 = 12; kicker1 >= 0; kicker1--) {
                if (kicker1 == i) continue;
                for (int kicker2 = kicker1 - 1; kicker2 >= 0; kicker2--) {
                    if (kicker2 == i) continue;
                    long product = pow(Card.PRIMES[i], 3) * Card.PRIMES[kicker1] * Card.PRIMES[kicker2];
                    unsuitedLookup.put(product, rank);
                    rank++;
                }
            }
        }

        /*
        Les double paires
         */
        rank = MAX_THREE_OF_A_KIND + 1;
        for (int highPair = 12; highPair >= 0; highPair--) {
            for (int lowPair = highPair - 1; lowPair >= 0; lowPair--) {
                for (int kicker = 12; kicker >= 0; kicker--) {
                    if (kicker == highPair || kicker == lowPair) continue;
                    long product = pow(Card.PRIMES[highPair], 2) * pow(Card.PRIMES[lowPair], 2) * Card.PRIMES[kicker];
                    unsuitedLookup.put(product, rank);
                    rank++;
                }
            }
        }

        /*
        Les paires
         */
        rank = MAX_TWO_PAIR + 1;
        for (int pair = 12; pair >= 0; pair--) {
            for (int kicker1 = 12; kicker1 >= 0; kicker1--) {
                if (kicker1 == pair) continue;
                for (int kicker2 = kicker1 - 1; kicker2 >= 0; kicker2--) {
                    if (kicker2 == pair) continue;
                    for (int kicker3 = kicker2 - 1; kicker3 >= 0; kicker3--) {
                        if (kicker3 == pair) continue;
                        long product = pow(Card.PRIMES[pair], 2) * Card.PRIMES[kicker1] * Card.PRIMES[kicker2] * Card.PRIMES[kicker3];
                        unsuitedLookup.put(product, rank);
                        rank++;
                    }
                }
            }
        }

    }

    // ----------------------------------------------------------------------
    // Fonctions utilitaires
    // ----------------------------------------------------------------------

    /**
     * Puissance d'un nombre
     * @param base : base
     * @param exponent : exposant
     * @return : base exposant exponent
     */
    private long pow(long base, int exponent) {
        long result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }

    /**
     * Produit des nombres premiers associés aux rangs
     * @param rankBits : bits représentant les rangs
     * @return : produit des nombres premiers associés aux rangs
     */
    long primeProductFromRankBits(int rankBits) {
        long product = 1;
        for (int i = 0; i < 13; i++) {
            if ((rankBits & (1 << i)) != 0) {
                product *= Card.PRIMES[i];
            }
        }
        return product;
    }

    // ----------------------------------------------------------------------
    // Générer les combinaisons de n éléments parmi k
    // Chaque combinaison est représentée par un entier dont les bits à 1 indiquent les rangs valides
    // Globalement de la magie noire.
    // ----------------------------------------------------------------------
    public static List<Integer> generateAllCombinations(int n, int k) {
        List<Integer> combinations = new ArrayList<>();
        int combination = (1 << k) - 1;
        while (combination < (1 << n)) {
            combinations.add(combination);
            int x = combination & -combination;
            int y = combination + x;
            combination = ((combination & ~y) / x >> 1) | y;
        }
        return combinations;
    }

    // ----------------------------------------------------------------------
    // Getters
    // ----------------------------------------------------------------------

    /**
     * Getter lookup flush
     * @return : table de lookup pour les flush
     */
    public static Map<Long, Integer> getFlushLookup() {
        return flushLookup;
    }

    /**
     * Getter lookup unsuited
     * @return : table de lookup pour les mains non-flush
     */
    public static Map<Long, Integer> getUnsuitedLookup() {
        return unsuitedLookup;
    }

}
