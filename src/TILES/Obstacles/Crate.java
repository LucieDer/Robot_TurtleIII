package TILES.Obstacles;

import TILES.Turtle;
import TILES.Type;

/*
Classe pour la caisse en bois
Cette classe hérite de la classe obstacle
 */

public class Crate extends Obstacle {

    //CONSTRUCTEURS
    /*
    Pour une caisse en bois
    m_material -> le mur est en BOIS
    m_canMove -> le mur peut bouger
    m_canMelt -> le mur ne peut pas fondre
     */

    // Même constructeur que pour obstacle
    public Crate(boolean isOnBoard, Type type, int x, int y, Turtle turtle){
        super(isOnBoard, type, x, y , turtle);
        m_material = Material.BOIS;
        m_canMove = true;
        m_canMelt = false;
    }

    // Si la caisse est sur le plateau
    public Crate(boolean isOnBoard, Type type, int x, int y){
        super(isOnBoard, type, x, y);
        m_material = Material.BOIS;
        m_canMove = true;
        m_canMelt = false;
    }


    // Si la caisse n'est pas sur le plateau mais qu'elle est dans la main d'un joueur
    public Crate(boolean isOnBoard, Type type, Turtle turtle){
        super(isOnBoard, type, turtle);
        m_material = Material.BOIS;
        m_canMove = true;
        m_canMelt = false;
    }


    // Si la caisse est dans la pioche : elle n'est ni sur le plateau, ni dans la main d'un joueur
    public Crate(boolean isOnBoard, Type type){
        super(isOnBoard, type);
        m_material = Material.BOIS;
        m_canMove = true;
        m_canMelt = false;
    }



    //METHODES

    // Retourne si la caisse peut fondre
    @Override
    public boolean canMelt() {
        return m_canMelt;
    }


    // Retourne si la caisse peut bouger
    @Override
    public boolean canMove() {
        return m_canMove;
    }
}
