package Engine.CARDS;
import Engine.GAME.Color;
import Engine.TILES.Obstacles.DeckObstacles;
import Engine.TILES.Obstacles.IceWall;
import Engine.TILES.Obstacles.Obstacle;
import Engine.TILES.Obstacles.StoneWall;

import java.util.Random;

public class DeckCards extends HandCards {
    private final int amount = 37; //37*4
    private final int red = 3;
    private final int green = 8;
    private final int purple = 8;
    private final int blue = 18;
    public HandCards m_discarding;
    Random rand = new Random();

    public DeckCards(){
        super();
        this.m_discarding = new HandCards();
    }

    public void populate() {
        for (Color color : Color.values()) {
            // 3 cartes rouges (Laser)
            if (color.getColor() == "Rouge") {
                for (int j = 0; j < red; j++) {
                    Card card = new Card(Color.ROUGE);
                    this.add(card);
                }
            }

            // 8 cartes vertes
            if (color.getColor() == "Vert") {
                for (int j = 0; j < green; j++) {
                    Card card = new Card(Color.VERT);
                    this.add(card);
                }
            }

            // 8 cartes violettes
            if (color.getColor() == "Violet") {
                for (int j = 0; j < purple; j++) {
                    Card card = new Card(Color.VIOLET);
                    this.add(card);
                }
            }

            // 18 cartes bleues
            if (color.getColor() == "Bleu") {
                for (int j = 0; j < blue; j++) {
                    Card card = new Card(Color.BLEU);
                    this.add(card);
                }
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

    //Distribuer assez de cartes pour en avoir 5 dans la main
    public void dealUntilFive(HandCards h){
        int amountInHand = h.getAmount();

        for (int i=0; i< (5 - amountInHand ); i++){
            h.add(cards.remove(0));
        }
    }

    //Savoir si le deck est vide
    public boolean isEmpty(){
        return (this.getAmount() == 0);
    }

    public void discardOne(){
        this.m_discarding.add(cards.remove(0));
    }

    public void discard(int amount){
        for(int i=0; i< amount ;i++){
            this.m_discarding.add(cards.remove(0));
        }

    }


    //Fonction si le deck est vide, reprendre depuis la défausse
    public void repopulateFromDiscarding(){
        if(this.isEmpty()){
            int amount = this.m_discarding.getAmount();
            for(int i=0; i<amount; i++){
                cards.add(this.m_discarding.getCards().remove(0));
            }
        }
    }


    public void addToDiscard(Program program) {
        for(int i=0; i<program.getAmount(); i++){
            this.m_discarding.add(program.getCards().remove(0));
        }
    }
}
