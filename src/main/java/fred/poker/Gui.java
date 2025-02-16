package fred.poker;

import java.util.Arrays;
import java.util.Scanner;

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

    public String playerTurnInput() {
        Scanner scanner = new Scanner(System.in);
        String[] options = {"CALL", "RAISE", "FOLD", "ALL IN"};
        System.out.println("Choose an action: CALL, RAISE, FOLD, ALL IN");
        String input = scanner.nextLine().toUpperCase();
        if (Arrays.asList(options).contains(input)) {
            return input;
        } else {
            System.out.println("Invalid input, please try again");
            return playerTurnInput();
        }
    }

    public String startingMenu() {
        Scanner scanner = new Scanner(System.in);
        String[] options = {"START", "EXIT"};
        System.out.println("Choose an action: START, EXIT");
        String input = scanner.nextLine().toUpperCase();
        if (Arrays.asList(options).contains(input)) {
            return input;
        } else {
            System.out.println("Invalid input, please try again");
            return startingMenu();
        }
    }

    public String gameOptionsMenu() {
        Scanner scanner = new Scanner(System.in);
        String[] options = {"AI NUMBER", "BACK"};
        System.out.println("Choose an action: AI NUMBER, BACK");
        String input = scanner.nextLine().toUpperCase();
        if (Arrays.asList(options).contains(input)) {
            return input;
        } else {
            System.out.println("Invalid input, please try again");
            return gameOptionsMenu();
        }
    }

}
