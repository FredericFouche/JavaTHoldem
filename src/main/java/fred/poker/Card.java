package fred.poker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Card {
    private static final Set<String> VALID_TYPES = new HashSet<>(Arrays.asList("DIAMONDS", "SPADES", "CLUBS", "HEARTS"));

    private byte cardValue; // Utiliser des valeurs de 0 à 12
    private String cardType;

    public Card(byte cardValue, String cardType) {
        setCardValue(cardValue);
        setCardType(cardType);
    }

    public byte getCardValue() {
        return cardValue;
    }

    public void setCardValue(byte cardValue) {
        if (cardValue < 0 || cardValue > 12) {
            throw new IllegalArgumentException("Value must be between 0 and 12");
        }
        this.cardValue = cardValue;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        if (!VALID_TYPES.contains(cardType)) {
            throw new IllegalArgumentException("Card type must be \"DIAMONDS\", \"SPADES\", \"CLUBS\", \"HEARTS\". Case sensitive.");
        }
        this.cardType = cardType;
    }

    public String getCard() {
        return cardValue + " " + cardType;
    }

    /**
     * Encode card to a 32-bit integer.
     * Permet de représenter une carte par un entier
     *
     * @return int representation de la carte
     */
    public int encodeCardValue() {
        int value = 0;
        switch (cardType) {
            case "DIAMONDS":
                break;
            case "SPADES":
                value = 1;
                break;
            case "CLUBS":
                value = 2;
                break;
            case "HEARTS":
                value = 3;
                break;
            default:
                throw new IllegalArgumentException("Invalid card type");
        }
        return (cardValue + (13 * value));
    }
}
