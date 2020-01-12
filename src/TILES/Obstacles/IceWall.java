package TILES.Obstacles;

import PLAYER.Player;

/*
Classe pour les murs de glace
Cette classe hérite de la classe obstacle
 */


public class IceWall extends Obstacle {

    //CONSTRUCTEURS

    /*
    Pour un mur de glace
    m_material -> le mur est en GLACE
    m_canMove -> le mur ne peut pas bouger
    m_canMelt -> le mur peut fondre
     */



    // Si le mur est sur le plateau
    public IceWall(boolean isOnBoard, int x, int y){
        super(isOnBoard, x, y);
        m_material = Material.GLACE;
        m_canMove = false;
        m_canMelt = true;
    }



    // Si le mur n'est pas sur le plateau mais qu'il est dans la main d'un joueur
    public IceWall(boolean isOnBoard, Player player){
        super(isOnBoard, player);
        m_material = Material.GLACE;
        m_canMove = false;
        m_canMelt = true;
    }

    // Si le mur est dans la pioche : il n'est ni sur le plateau si dans la main d'un joueur
    public IceWall(boolean isOnBoard){
        super(isOnBoard);
        m_material = Material.GLACE;
        m_canMove = false;
        m_canMelt = true;
    }


    //METHODES



    @Override
    public void updatePosition() {

    }
}
