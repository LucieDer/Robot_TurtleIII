package Engine.GAME;

import Engine.TILES.Tile;

import java.util.List;

/*
Classe pour les mouvements effectués
Classe abstraites car trois types de mouvement : Avancer(bleu), tourner à 90° vers la droite(jaune), tourner à 90° vers la gauche(violet), lancer un laser(Rouge)
 */

public abstract class Move {
    final Board m_board;
    final Tile m_movedTile;
    List<Integer> destinationCoordinate;
    List<Integer> initialCoordinate;
    int destinationOrientation;

    public static final Move NULL_MOVE = new NullMove();

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

    public Tile getAttackedTile(){
        return null;
    }

    public List<Integer> getDestinationCoordinate(){
        return this.getDestinationCoordinate();
    }

    public int getDestinationOrientation() {
        return destinationOrientation;
    }

    public List<Integer> getCurrentCoordinate(){

        return this.getM_movedTile().getM_currentPosition();
    }

    public int getCurrentOrientation(){
        return this.getM_movedTile().getM_currentOrientation();
    }


    public List<Integer> getInitialCoordinate() {
        if(this.m_movedTile.getType() == "Tortue"){
            return this.m_movedTile.getM_initialPosition();
        }else return null;
    }

    public Board execute() {
        final Board.Builder builder = new Board.Builder(this.m_board.getM_nbOfPlayers());
        for(final Tile tile : this.m_board.getActiveTiles()){
            if(!this.m_movedTile.equals(tile)){
                builder.setTile(tile);
            }
        }

        //Représenter la pièce déplacée
        builder.setTile(this.m_movedTile.moveTile(this));
        //Passer au joueur suivant
        builder.setMoveMaker(this.m_board.getCurrentPlayer().getNextPlayer().getColor());


        return builder.build();
    }

    public Tile getM_movedTile(){
        return this.m_movedTile;
    }






    // Classe fille mouvement Avancer
    public static final class TurtleGoForward extends Move{

        public TurtleGoForward(Board board, Tile movedTile, final List<Integer> destinationCoordinate) {
            super(board, movedTile);
            this.destinationCoordinate = destinationCoordinate;
        }

    }


    // Classe fille revenir à la position initiale
    public static final class TurtleGoToInitialPosition extends Move{

        public TurtleGoToInitialPosition(Board board, Tile movedTile, final List<Integer> destinationCoordinate) {
            super(board, movedTile);
            this.destinationCoordinate = destinationCoordinate;
        }

    }

    //Classe fille mouvement Tourner à droite
    public static final class TurtleTurnRight extends Move{
        final int m_orientation;
        public TurtleTurnRight(final Board board, final Tile tile, final int orientation){
            super(board, tile);
            this.m_orientation = orientation;
        }

        @Override
        public Board execute() {
            return null;
        }
    }

    //Classe fille mouvement Tourner à gauche
    public static final class TurtleTurnLeft extends Move{
        final int m_orientation;
        public TurtleTurnLeft(final Board board, final Tile tile, final int orientation){
            super(board, tile);
            this.m_orientation = orientation;
        }

        @Override
        public Board execute() {
            return null;
        }
    }

    //Classe fille mouvement Laser
    public static final class TurtleLaser extends Move{

        final Tile attackedTile;

        public TurtleLaser(final Board board, final Tile tile, final Tile attackedTile){
            super(board, tile);
            this.attackedTile = attackedTile;
        }

        @Override
        public Board execute() {
            return null;
        }

        @Override
        public Tile getAttackedTile(){
            return this.attackedTile;
        }
    }


    //Classe fille mouvement Tourner à gauche
    public static final class TurtleTurnabout extends Move{
        final int m_orientation;
        public TurtleTurnabout(final Board board, final Tile tile, final int orientation){
            super(board, tile);
            this.m_orientation = orientation;
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

    //Pour mouvement invalide
    public static final class NullMove extends Move{

        public NullMove() {
            super(null, null);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("Impossible d'executer le NullMove.");
        }
    }


}
