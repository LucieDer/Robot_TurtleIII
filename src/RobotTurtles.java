import CARDS.*;
import GAME.Color;
import TILES.Obstacles.DeckObstacles;
import TILES.Obstacles.HandObstacles;

public class RobotTurtles {
    public static final int Tile_Size = 80;
    public static final int height = 8;
    public static final int width = 8;

    public static final char VIDE = 'o';
    public static final char TORTUE = 't';
    public static final char PIERRE = 'p';
    public static final char GLACE = 'g';
    public static final char CAISSE = 'c';

    public static char[][] plateau = new char[height][width];

    public static void main(String[] args) {
        Card c1, c2;
        c1 = new Card(Color.BLEU);
        c2 = new Card(Color.JAUNE);
    /*
        //Créer hand :
        HandCards h1 = new HandCards();
        Program p = new Program();
        h1.add(c1);
        h1.add(c2);
        System.out.println(h1.showHand());
        h1.addInProgram(c1, p);
        System.out.println("p : " + p.showProgram());

        //Créer deck:
        DeckCards d1 = new DeckCards();
        d1.populate();
        d1.shuffle();
        System.out.println("deck " + d1.showHand());
        System.out.println("nb de cartes : " + d1.getAmount());

        d1.deal(h1, 5);
        System.out.println("h1 : " + h1.showHand());
        System.out.println("d1: " + d1.showHand());

    */


        //Créer main d'obstacles :
        HandObstacles h2 = new HandObstacles();

        //Créer deck:
        DeckObstacles d2 = new DeckObstacles();
        d2.populate();
        d2.shuffle();
        System.out.println("deck " + d2.showHand());
        System.out.println("nb de cartes : " + d2.getAmount());

        d2.dealSpecific(h2, "Pierre", 2);
        System.out.println("h2 : " + h2.showHand());
        System.out.println("d2: " + d2.showHand());
        System.out.println("nb de cartes : " + d2.getAmount());






    }
}
