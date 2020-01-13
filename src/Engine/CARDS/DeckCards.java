package Engine.CARDS;
import Engine.GAME.Color;

import java.util.Random;

public class DeckCards extends HandCards {
    private final int amount = 44; //44*4
    Random rand = new Random();

    public void populate(){
        for (Color color: Color.values()){
            for(int i = 0; i< amount; i++){
                Card card = new Card(color);
                this.add(card);
            }
        }
    }

    public void shuffle(){
        for (int i = cards.size() -1 ; i>0; i--) {
            //échanger une carte aléatoire entre première et dernière carte
            int pick = rand.nextInt(i);
            Card randCard = cards.get(pick);
            Card lastCard = cards.get(i);
            cards.set(i, randCard);
            cards.set(pick, lastCard);
        }
    }

    //Distribuer UNE carte à une main
    public void deal(HandCards h, int amount){
        for (int i=0; i< amount; i++){
            h.add(cards.remove(0));
        }
    }



}
