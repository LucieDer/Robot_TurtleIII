package Engine.CARDS;

import java.util.ArrayList;
import java.util.Collection;

/*
Classe pour créer une main de cartes, peut importe le nombre de carte dans la main
Classe mère du deck
 */

public class HandCards {
    protected ArrayList<Card> cards;
    //constructeur
    public HandCards(){
        cards = new ArrayList<Card>();
    }

    //Accéder aux cartes


    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getCard(Card card){
        return cards.contains(card)? card : null;
    }

    //Avoir le nombre de cartes dans la main
    public int getAmount(){
        return cards.size();
    }

    // Ajouter une carte à la main
    public void add(Card card){
        cards.add(card);
    }


    // Vider la main
    public void clear(){
        cards.clear();
    }

    // Retirer une carte dans la main
    public void throwOne(Card card){
        cards.remove(card);
    }

    // Retirer plusieurs cartes dans la main (A TESTER)



    // Afficher le contenu de la main dans le terminal
    public String showHand(){
        String str = "";
        for (Card c: cards){
            str += c.printColor() + "\n";
        }
        return str;
    }

    /* Ajouter une carte dans un programme
    Vérifie si la carte que l'on veut déplacer se trouve dans la main en renvoyant true ou false
     */
    public boolean addInProgram(Card card, Program program){
        if (!cards.contains(card)){
            return false;
        }
        else{
            //get index position of card
            cards.remove(card);
            program.add(card);
            return true;
        }
    }


}
