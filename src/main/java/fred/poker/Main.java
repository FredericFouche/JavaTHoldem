package fred.poker;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Game game = new Game(4);
        game.dealCards();

        for (Player player : game.getPlayers()) {
            System.out.println(player.getName() + "'s Hand: " + player.getHand().getHand());
        }
    }
}
