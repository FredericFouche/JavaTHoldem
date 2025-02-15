package fred.poker;

import java.util.Arrays;

public class Gui {
    // Cette classe sert à générer l'interface graphique du jeu en réaction des evenements

    public Gui() {
        // Constructeur de la classe
    }

    public void generateCard(Card card) {
        int cardValue = card.getCardValue();
        String cardSuit = card.getCardSuit();
        String cardSuitSymbol = "";

        switch (cardSuit) {
            case "HEARTS":
                cardSuitSymbol = "♥";
                break;
            case "DIAMONDS":
                cardSuitSymbol = "♦";
                break;
            case "CLUBS":
                cardSuitSymbol = "♣";
                break;
            case "SPADES":
                cardSuitSymbol = "♠";
                break;
        }


        String template =
                "╭─────────╮\n"
                        + "│%d        │\n"
                        + "│         │\n"
                        + "│    %s    │\n"
                        + "│         │\n"
                        + "│        %d│\n"
                        + "╰─────────╯";

        String cardFormatted = String.format(template, cardValue, cardSuitSymbol, cardValue);
        System.out.println(cardFormatted);

    }

}
