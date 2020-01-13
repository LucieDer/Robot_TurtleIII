package Engine.CARDS;

/*
Classe pour créer une carte. Une carte possède un enum color (voir classe color)
 */




import Engine.GAME.Color;

public class Card {
    protected final Color color;

    public Card(final Color color){

        this.color = color;
    }

    // Accéder à la couleur de la carte
    public String getColor(){
        return color.getColor();
    }

    // Voir quelle est la couleur de la carte dans l'IU
    public String printColor(){
        String str = "";
        str += color.getColor();
        return str;
    }

}
