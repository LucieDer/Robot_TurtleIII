package TILES.Obstacles;

import PLAYERS.Player;

/*
Classe mur de pierre qui hérite de la classe obstacle
 */


public class StoneWall extends Obstacle {


    //CONSTRUCTEURS
    /*
    Pour un mur de pierre
    m_material -> le mur est en PIERRE
    m_canMove -> le mur ne peut pas bouger
    m_canMelt -> le mur ne peut pas fondre
     */

    // Si le mur est sur le plateau
    public StoneWall(boolean isOnBoard, int x, int y){
        super(isOnBoard, x, y);
        m_material = Material.PIERRE;
        m_canMove = false;
        m_canMelt = false;
    }



    // Si le mur n'est pas sur le plateau mais qu'il est dans la main d'un joueur
    public StoneWall(boolean isOnBoard, Player player){
        super(isOnBoard, player);
        m_material = Material.PIERRE;
        m_canMove = false;
        m_canMelt = false;
    }

    // Si le mur est dans la pioche : il n'est ni sur le plateau si dans la main d'un joueur
    public StoneWall(boolean isOnBoard){
        super(isOnBoard);
        m_material = Material.PIERRE;
        m_canMove = false;
        m_canMelt = false;
    }



    //METHODES

    @Override
    public void updatePosition() {

    }
}
