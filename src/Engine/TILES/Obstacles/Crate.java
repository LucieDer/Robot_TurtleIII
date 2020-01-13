package Engine.TILES.Obstacles;

import Engine.GAME.Move;
import Engine.PLAYERS.Player;

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

    // Si le mur est sur le plateau
    public Crate(boolean isOnBoard, int x, int y){
        super(isOnBoard, x, y);
        m_material = Material.BOIS;
        m_canMove = true;
        m_canMelt = false;
    }



    // Si le mur n'est pas sur le plateau mais qu'il est dans la main d'un joueur
    public Crate(boolean isOnBoard, Player player){
        super(isOnBoard, player);
        m_material = Material.BOIS;
        m_canMove = true;
        m_canMelt = false;
    }

    // Si le mur est dans la pioche : il n'est ni sur le plateau si dans la main d'un joueur
    public Crate(boolean isOnBoard){
        super(isOnBoard);
        m_material = Material.BOIS;
        m_canMove = true;
        m_canMelt = false;
    }

    @Override
    public Crate moveTile(Move move) {
        return new Crate(move.getM_movedTile().isM_isOnBoard(), move.getDestinationCoordinate().get(0), move.getDestinationCoordinate().get(1));
    }

    @Override
    public void updatePosition() {

    }


    //METHODES

}
