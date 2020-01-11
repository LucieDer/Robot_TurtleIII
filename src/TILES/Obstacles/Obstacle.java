package TILES.Obstacles;

import GAME.Player;
import TILES.*;

/*
Classe Obstacle, abstraite car il y a différents obstacles possibles
Cette classe hérite de la classe Tuile
 */

public abstract class Obstacle extends Tile {
    protected Material m_material;
    protected boolean m_canMelt;
    private final Type m_type = Type.OBSTACLE;
    private Player m_player;

    //CONSTRUCTEURS


    /*
    m_material -> matériel de l'obstacle, défini à partir de la classe enum Material : bois, pierre, ou glace
    m_canMelt -> boolean si l'obstacle peut fondre sous l'effet d'un laser
    m_type -> Type de la tuile défini à OBSTACLE
    m_player -> Joueur associé à l'obstacle
     */

    // Même constructeur que la classe tuile
    public Obstacle(boolean isOnBoard, int x, int y, Player player){
        super(isOnBoard, Type.OBSTACLE, x, y );
        this.m_player = player;
        //this.material = material;
    }


    // Si l'obstacle est posé sur le plateau
    public Obstacle(boolean isOnBoard,  int x, int y){
        super(isOnBoard, Type.OBSTACLE, x, y );
    }

    // Si l'obstacle n'est pas posé sur le plateau et qu'il est dans la main d'un joueur
    public Obstacle(boolean isOnBoard,  Player player){
        super(isOnBoard, Type.OBSTACLE);
        this.m_player = player;
    }

    // Si l'obstacle est encore dans la pioche, donc qu'il n'est ni sur le plateau ni dans une main
    public Obstacle(boolean isOnBoard){
        super(isOnBoard, Type.OBSTACLE);
    }




    //METHODES

    // Accéder au matériel de l'obstacle
    public String getM_material(){
        return m_material.getMaterialValue();
    }

    // Afficher le matériel dans le terminal
    public String printMaterial(){
        String str = "";
        str += m_material.getMaterialValue();
        return str;
    }

    //Fonction qui renvoie la valeur de canMelt
    public boolean canMelt(){
        return m_canMelt;
    };

    @Override
    public boolean canMove(){
        return m_canMove;
    }


    // Fonction pour savoir si on peut placer l'obstacle
    @Override
    public boolean canPut(int x, int y) {
        return false;
    }
}
