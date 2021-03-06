package Engine.TILES.Obstacles;

/*
Classe enum pour créer les différents matériaux des obstacles
 */

public enum Material {
    PIERRE("Pierre"),
    GLACE("Glace");

    private final String materialValue;

    // Constructeur :
    Material(final String materialValue){
        this.materialValue = materialValue;
    }

    // Méthode Publique
    public String getMaterialValue(){
        return materialValue;
    }
}
