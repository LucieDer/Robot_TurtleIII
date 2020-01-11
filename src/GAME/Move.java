package GAME;

import TILES.Tile;

/*
Classe pour les mouvements effectués
Classe abstraites car trois types de mouvement : Avancer(bleu), tourner à 90° vers la droite(jaune), tourner à 90° vers la gauche(violet), lancer un laser(Rouge)
 */

public abstract class Move {
    final Board m_board;
    final Tile m_movedTile;

    /*
    CONSTRUCTEURS
    m_board -> le plateau
    m_tile -> la tuile qui fait le mouvement
    m_xDestination, m_yDestination -> les coordonnées d'arrivée de la tuile
    m_orientation -> orientation (pour une tortue) : 0, -90, 180, 90 ??
     */

    //Constructeur complet
    public Move(final Board board, final Tile tile){
        this.m_board = board;
        this.m_movedTile = tile;
    }







    // Classe fille mouvement Avancer
    public static final class GoForward extends Move{
        final int m_xDestination;
        final int m_yDestination;
        public GoForward(final Board board, final Tile tile, final int xDestination, final int yDestination){
            super(board, tile);
            this.m_xDestination = xDestination;
            this.m_yDestination = yDestination;
        }
    }

    //Classe fille mouvement Tourner à droite
    public static final class TurnRight extends Move{
        final int m_orientation;
        public TurnRight(final Board board, final Tile tile, final int orientation){
            super(board, tile);
            this.m_orientation = orientation;
        }
    }

    //Classe fille mouvement Tourner à gauche
    public static final class TurnLeft extends Move{
        final int m_orientation;
        public TurnLeft(final Board board, final Tile tile, final int orientation){
            super(board, tile);
            this.m_orientation = orientation;
        }
    }

    //Classe fille mouvement Laser
    //Classe fille mouvement Tourner à droite
    public static final class Laser extends Move{

        public Laser(final Board board, final Tile tile){
            super(board, tile);
        }
    }




}
