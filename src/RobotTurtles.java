import Engine.GAME.Board;
import GUI.Table;
import Engine.CARDS.*;
import Engine.GAME.Color;

public class RobotTurtles {


    public static void main(String[] args) {
        Card c1, c2;
        c1 = new Card(Color.VIOLET);
        c2 = new Card(Color.ROUGE);


    /*
        //créer hand :
        handcards h1 = new handcards();
        program p = new program();
        h1.add(c1);
        h1.add(c2);
        system.out.println(h1.showhand());
        h1.addinprogram(c1, p);
        system.out.println("p : " + p.showprogram());

        //créer deck:
        deckcards d1 = new deckcards();
        d1.populate();
        d1.shuffle();
        system.out.println("deck " + d1.showhand());
        system.out.println("nb de cartes : " + d1.getamount());

        d1.deal(h1, 5);
        system.out.println("h1 : " + h1.showhand());
        system.out.println("d1: " + d1.showhand());




        //créer main d'obstacles :
        handobstacles h2 = new handobstacles();

        //créer deck:
        deckobstacles d2 = new deckobstacles();
        d2.populate();
        d2.shuffle();
        system.out.println("deck " + d2.showhand());
        system.out.println("nb de cartes : " + d2.getamount());

        d2.dealspecific(h2, "pierre", 2);
        system.out.println("h2 : " + h2.showhand());
        system.out.println("d2: " + d2.showhand());
        system.out.println("nb de cartes : " + d2.getamount());


     */

        //Test du plateau
        //blabla
/*
        Board board = Board.createStandardBoard(2);

        System.out.println(board.printBoard());




        DeckCards deck1 = new DeckCards();
        deck1.add(c1);
        deck1.add(c2);

        DeckCards discarding = new DeckCards();
        System.out.println("h2 : " + deck1.showHand());
        deck1.discard(2);
        System.out.println("h2 : " + deck1.showHand());
        deck1.repopulateFromDiscarding();


        System.out.println("h2 : " + deck1.showHand());


 */


        Table table = new Table();




    }
}
