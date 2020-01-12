package CARDS;

import GAME.Board;
import GAME.Move;
import PLAYER.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
Classe pour la pile du programme
 */

public class Program {
    private Player m_player;
    private ArrayList<Card> cards;
    //constructeur
    public Program(){
        cards = new ArrayList<Card>();
    }

    // Ajouter une carte dans le programme
    public void add(Card card){
        cards.add(card);
    }

    // Vider le programme
    public void clear(){
        cards.clear();
    }

    // Afficher le programme dans le terminal
    public String showProgram(){
        String str = "";
        for (Card c: cards){
            str += c.printColor() + " ";
        }
        return str;
    }




    //Accéder au joueur associé au programme
    public Player getM_player(){
        return m_player;
    }



    //Va créer la liste de mouvements à partir des cartes présentes dans le programme
    public Collection<Move> calculateMoves(){
        final List<Move> moves = new ArrayList<>();
        final Board board = this.m_player.getM_Board();
        for (Card c: cards){
            if (c.getColor() == "Bleu"){
                //TODO ICI
                moves.add(new Move.GoForward(this.m_player.getM_Board(), this.m_player.getM_turtle()));
            }
            else if (c.getColor() == "Vert"){
                //TODO ICI
                moves.add(new Move.TurnLeft(this.m_player.getM_Board(), this.m_player.getM_turtle(), this.m_player.getM_turtle().getM_orientation()));
            }
            else if (c.getColor() == "Violet"){
                //TODO ICI
                moves.add(new Move.TurnRight(this.m_player.getM_Board(), this.m_player.getM_turtle(), this.m_player.getM_turtle().getM_orientation() ));
            }
            else if (c.getColor() == "Bleu"){
                //TODO ICI
                moves.add(new Move.Laser(this.m_player.getM_Board(), this.m_player.getM_turtle()));
            }
        }

        return moves;
    }

    // Exécuter le programme A COMPLETER
    public void executeProgram(){

    }

}
