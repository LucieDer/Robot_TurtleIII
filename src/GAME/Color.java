package GAME;

/*
    Classe pour définir les différents types de cartes

    Utile pour Tortues, cartes, joyaux
 */

public enum Color {
    BLEU("Bleu"),
    VERT("Vert"),
    VIOLET("Violet"),
    ROUGE("Rouge"); //Pour les cartes = Laser

    private final String colorValue;

    // Constructeur :
    Color(final String colorValue){
        this.colorValue = colorValue;
    }

    // Méthode Publique
    public String getColor(){
        return colorValue;
    }
}