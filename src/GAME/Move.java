package GAME;

import TILES.Tile;
import GAME.*;
import java.util.List;

/*
Classe pour les mouvements effectués
Classe abstraites car trois types de mouvement : Avancer(bleu), tourner à 90° vers la droite(jaune), tourner à 90° vers la gauche(violet), lancer un laser(Rouge)
 */

public abstract class Move {
    final Board m_board;
    final Tile m_movedTile;
    List<Integer> destinationCoordinate;
    int destinationOrientation;

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

    public List<Integer> getDestinationCoordinate(){
        return this.getDestinationCoordinate();
    }

    public abstract Board execute();


    // Classe fille mouvement Avancer
    public static final class GoForward extends Move{

        public GoForward(Board board, Tile tile) {
            super(board, tile);
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder(this.m_board.getM_nbOfPlayers());
            for(final Tile tile : this.m_board.getActiveTiles()){
                if(!this.m_movedTile.equals(tile)){
                    builder.setTile(tile);
                }
            }

            //Représenter la pièce déplacée
            builder.setTile(null);
            //Passer au joueur suivant
            builder.setMoveMaker(this.m_board.getCurrentPlayer().getNextPlayer().getColor());


            return builder.build();
        }
    }

    //Classe fille mouvement Tourner à droite
    public static final class TurnRight extends Move{
        final int m_orientation;
        public TurnRight(final Board board, final Tile tile, final int orientation){
            super(board, tile);
            this.m_orientation = orientation;
        }

        @Override
        public Board execute() {
            return null;
        }
    }

    //Classe fille mouvement Tourner à gauche
    public static final class TurnLeft extends Move{
        final int m_orientation;
        public TurnLeft(final Board board, final Tile tile, final int orientation){
            super(board, tile);
            this.m_orientation = orientation;
        }

        @Override
        public Board execute() {
            return null;
        }
    }

    //Classe fille mouvement Laser
    //Classe fille mouvement Tourner à droite
    public static final class Laser extends Move{

        public Laser(final Board board, final Tile tile){
            super(board, tile);
        }

        @Override
        public Board execute() {
            return null;
        }
    }


    public static final class PutObstacle extends Move{

        public PutObstacle(Board board, Tile tile, List<Integer> destinationCoordinate) {
            super(board, tile);
            this.destinationCoordinate = destinationCoordinate;
        }

        @Override
        public Board execute() {
            return null;
        }
    }




}
