package TILES.Obstacles;

import TILES.Turtle;
import TILES.Type;

/*
Classe pour les murs de glace
Cette classe hérite de la classe obstacle
 */


public class IceWall extends Obstacle {

    //CONSTRUCTEURS

    /*
    Pour un mur de pierre
    m_material -> le mur est en GLACE
    m_canMove -> le mur ne peut pas bouger
    m_canMelt -> le mur peut fondre
     */



    // Même constructeur que pour obstacle
    public IceWall(boolean isOnBoard, Type type, int x, int y, Turtle turtle){
        super(isOnBoard, type, x, y , turtle);
        m_material = Material.GLACE;
        m_canMove = false;
        m_canMelt = true;
    }

    //Si le mur est sur le plateau
    public IceWall(boolean isOnBoard, Type type, int x, int y){
        super(isOnBoard, type, x, y);
        m_material = Material.GLACE;
        m_canMove = false;
        m_canMelt = true;
    }

    // Si le mur n'est pas sur le plateau mais qu'il est dans la main d'un joueur
    public IceWall(boolean isOnBoard, Type type, Turtle turtle){
        super(isOnBoard, type, turtle);
        m_material = Material.GLACE;
        m_canMove = false;
        m_canMelt = true;
    }


    // Si le mur est dans la pioche : il n'est ni sur le plateau ni dans une main
    public IceWall(boolean isOnBoard, Type type){
        super(isOnBoard, type);
        m_material = Material.GLACE;
        m_canMove = false;
        m_canMelt = true;
    }


    //METHODES

    // Retourne si le mur peut fondre
    @Override
    public boolean canMelt() {
        return m_canMelt;
    }

    // Retourne si le mur peut bouger
    @Override
    public boolean canMove() {
        return m_canMove;
    }
}
