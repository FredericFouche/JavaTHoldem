package fred.poker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Card {
    // Une carte a une valeur
    // Une carte a une couleur
    // Une carte est soit un carreau/spades/etc
    private byte cardValue;
    private String cardColor;
    private String cardType;


    public Card(byte cardValue, String cardColor, String cardType) {
        this.cardValue = cardValue;
        this.cardColor = cardColor;
        this.cardType = cardType;
    }

    public byte getCardValue() {
        if (cardValue < 0) {
            throw new IllegalArgumentException("Value must be > 0");
        }

        if (cardValue > 14) {
            throw new IllegalArgumentException("Value must be < 14");
        }
        return cardValue;
    }

    public void setCardValue(byte cardValue) {
        this.cardValue = cardValue;
    }

    public String getCardColor() {
        if(cardColor.equals("RED") || cardColor.equals("BLACK")) {
            return cardColor;
        } else if (cardColor.equalsIgnoreCase("RED") || cardColor.equalsIgnoreCase("BLACK")) {
            throw new IllegalArgumentException("Case Sensitive.");
        }
        else {
            throw new IllegalArgumentException("Color must be BLACK or RED.");
        }
    }

    public void setCardColor(String cardColor) {
        this.cardColor = cardColor;
    }

    public String getCardType() {
        Set<String> validValues = new HashSet<>(Arrays.asList("DIAMONDS", "SPADES", "CLUBS", "HEARTS"));

        if(validValues.contains(cardType)) {
            return cardType;
        } else {
            throw new IllegalArgumentException("fred.poker.Card type must be \"DIAMONDS\", \"SPADES\", \"CLUBS\", \"HEARTS\". Case sensitive.");
        }
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCard() {
        return cardValue + " " + cardColor + " " + cardType;
    }
}
