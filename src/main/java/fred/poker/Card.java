package fred.poker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Card {
    private static final Set<String> VALID_SuitS = new HashSet<>(Arrays.asList("DIAMONDS", "SPADES", "CLUBS", "HEARTS"));

    private int cardValue;
    private String cardSuit;

    static int[] VALUES = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    static String[] SUITS = {"DIAMONDS", "SPADES", "CLUBS", "HEARTS"};
    static int[] PRIMES = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41};

    public Card(int cardValue, String cardSuit) {
        setCardValue(cardValue);
        setCardSuit(cardSuit);
    }

    public int getCardValue() {
        return cardValue;
    }

    public void setCardValue(int cardValue) {
        if (cardValue < 0 || cardValue > 12) {
            throw new IllegalArgumentException("Value must be between 0 and 12");
        }
        this.cardValue = cardValue;
    }

    public String getCardSuit() {
        return cardSuit;
    }

    public void setCardSuit(String cardSuit) {
        if (!VALID_SuitS.contains(cardSuit)) {
            throw new IllegalArgumentException("Card Suit must be \"DIAMONDS\", \"SPADES\", \"CLUBS\", \"HEARTS\". Case sensitive.");
        }
        this.cardSuit = cardSuit;
    }

    public Card getCard() {
        return this;
    }

    /**
     * Retourne le produit des nombres premiers en utilisant les bits de rang
     * Chaque 1 de la séquence est converti en un nombre premier et multiplié
     * Permet d'opti les calculs des suites et des couleurs
     */
    public static long primeProductFromRankBits(int rankBits) {
        long product = 1;
        for (int i = 0; i < 13; i++) {
            if ((rankBits & (1 << i)) != 0) {
                product *= PRIMES[i];
            }
        }
        return product;
    }

    /**
     * convertisseur pour une Card en entier "Cactus Kev" compatible
     * @Param Card : carte à convertir
     * @return int : entier représentant la carte
     */
    public static int convertToCactusKev(Card c) {
        int suitVal;
        switch (c.getCardSuit()) {
            case "DIAMONDS":
                suitVal = 0;
                break;
            case "SPADES":
                suitVal = 1;
                break;
            case "CLUBS":
                suitVal = 2;
                break;
            case "HEARTS":
                suitVal = 3;
                break;
            default:
                throw new IllegalArgumentException("Invalid card Suit");
        }

        int rankVal = c.getCardValue();

        int primeCard = PRIMES[rankVal];

        int rankBit = 1 << (16 + rankVal);

        // Magie noire qui permet de construire un entier à partir des valeurs de la carte
        // Bits 0-7 : prime de la carte
        // Bits 8-11 : rank de la carte
        // Bits 12-15 : suit de la carte
        // Bits 16-rank = 1
        System.out.println(rankBit | primeCard | (rankVal << 8) | (suitVal << 12));
        return rankBit | primeCard | (rankVal << 8) | (suitVal << 12);
    }
}
