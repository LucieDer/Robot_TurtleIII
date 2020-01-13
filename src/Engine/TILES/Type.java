package Engine.TILES;

/*
Classe pour définir le type d'une tuile
 */

public enum Type {
    TORTUE("Tortue"),
    OBSTACLE("Obstacle"),
    JOYAU("Joyau");

    private final String typeValue;

    // Constructeur :
    Type(final String typeValue){
        this.typeValue = typeValue;
    }

    // Méthode Publique
    public String getTypeValue(){
        return typeValue;
    }
}
