package Engine.GAME;

import Engine.TILES.Obstacles.Obstacle;
import Engine.TILES.Tile;

import java.util.ArrayList;
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

    public static final PutObstacle NULL_MOVE = new NullMove();

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
        this.destinationCoordinate = tile.getM_currentPosition(); //Par défaut la pièce ne bouge pas
        this.destinationOrientation = tile.getM_currentOrientation();
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
            //Vérifier que la pièce est sur le plateau
            if(!this.m_movedTile.equals(tile) && tile.isM_isOnBoard()){
                builder.setTile(tile);
            }
        }

        //Représenter la pièce déplacée : crée une nouvelle pièce sur plateau avec de nouveaux paramètres
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

        int destX = this.m_movedTile.getM_currentPosition().get(0);
        int destY = this.m_movedTile.getM_currentPosition().get(1);
        List<Integer> currPosition;

        public TurtleGoForward(Board board, Tile movedTile) {
            super(board, movedTile);
            currPosition = this.destinationCoordinate;
            switch(movedTile.getM_currentOrientation()){
                case BoardUtils.TURNED_UP: //x-1
                    this.destinationCoordinate.set(0, destX-1);
                    break;
                case BoardUtils.TURNED_DOWN: //x+1
                    this.destinationCoordinate.set(0, destX+1);
                    break;
                case BoardUtils.TURNED_TO_LEFT: //y-1
                    this.destinationCoordinate.set(1, destY-1);
                    break;
                case BoardUtils.TURNED_TO_RIGHT: //y+1
                    this.destinationCoordinate.set(1, destY+1);
                    break;
            }

            if(this.destinationCoordinate.get(0) > 7 || this.destinationCoordinate.get(1) > 7 ||
                    this.destinationCoordinate.get(0) < 0 || this.destinationCoordinate.get(1) < 0){  //Destination en dehors du plateau : retour à case départ
                this.destinationCoordinate = this.initialCoordinate;
                this.destinationOrientation = BoardUtils.TURNED_DOWN;
            }
            else if(board.getSquare(this.destinationCoordinate).getTile().getType() == "Obstacle"){ //Demi-tour devant obstacle
                this.destinationOrientation = BoardUtils.turnaboutOrientation(this.getCurrentOrientation());
            }
            else if(board.getSquare(this.destinationCoordinate).getTile().getType() == "Tortue"){
                //TODO
            }

        }

    }


    // Classe fille revenir à la position initiale
    public static final class TurtleGoToInitialPosition extends Move{

        public TurtleGoToInitialPosition(Board board, Tile movedTile, final List<Integer> destinationCoordinate) {
            super(board, movedTile);
            this.destinationCoordinate = movedTile.getM_currentPosition();


        }

    }

    //Classe fille mouvement Tourner à droite
    public static final class TurtleTurnRight extends Move {
        public TurtleTurnRight(final Board board, final Tile tile, final int orientation) {
            super(board, tile);
            switch (tile.getM_currentOrientation()) {
                case BoardUtils.TURNED_UP: //x-1
                    this.destinationOrientation = BoardUtils.TURNED_TO_RIGHT;
                    break;
                case BoardUtils.TURNED_DOWN: //x+1
                    this.destinationOrientation = BoardUtils.TURNED_TO_LEFT;
                    break;
                case BoardUtils.TURNED_TO_LEFT: //y-1
                    this.destinationOrientation = BoardUtils.TURNED_UP;
                    break;
                case BoardUtils.TURNED_TO_RIGHT: //y+1
                    this.destinationOrientation = BoardUtils.TURNED_DOWN;
                    break;
            }
        }
    }



    //Classe fille mouvement Tourner à gauche
    public static final class TurtleTurnLeft extends Move{
        public TurtleTurnLeft(final Board board, final Tile tile, final int orientation) {
            super(board, tile);
            switch (tile.getM_currentOrientation()) {
                case BoardUtils.TURNED_UP: //x-1
                    this.destinationOrientation = BoardUtils.TURNED_TO_LEFT;
                    break;
                case BoardUtils.TURNED_DOWN: //x+1
                    this.destinationOrientation = BoardUtils.TURNED_TO_RIGHT;
                    break;
                case BoardUtils.TURNED_TO_LEFT: //y-1
                    this.destinationOrientation = BoardUtils.TURNED_DOWN;
                    break;
                case BoardUtils.TURNED_TO_RIGHT: //y+1
                    this.destinationOrientation = BoardUtils.TURNED_UP;
                    break;
            }
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


    }




    public static class PutObstacle extends Move{

        public PutObstacle(Board board, Tile tile, List<Integer> destinationCoordinate) {
            super(board, tile);
            this.destinationCoordinate = destinationCoordinate;
        }

        public List<Square> getAdjacentSquares(){
            int x = this.destinationCoordinate.get(0);
            int y = this.destinationCoordinate.get(1);
            List<Integer> position = new ArrayList<>();
            int[] xdirections = {0 , 0 , 1 , -1};
            int[] ydirections = {-1 , 1 , 0 , 0};
            List<Square> adjacentSquares = new ArrayList<>();

            for(int i=0; i<4; i++){
                try{
                    position.add(x + xdirections[i]);
                    position.add(y + ydirections[i]);
                    adjacentSquares.add(this.m_board.getSquare(position));
                }catch (IndexOutOfBoundsException e){
                    continue;
                }
            }
            return adjacentSquares;
        }

        public boolean isNextToJewel(){
            List<Square> adjacentSquare = this.getAdjacentSquares();
            for(Square square : adjacentSquare){
                if(square.getTile().getType() == "Joyau"){
                    return true;
                }
            }
            return false;
        }

        public boolean canPutHere(){
            if(this.m_movedTile.getM_material() == "Pierre"){
                if(this.isNextToJewel()){
                    return false;
                }
            }else if(this.m_movedTile.getM_material() == "Glace"){
                return true;
            }
            return false;
        }


    }

    //Pour mouvement invalide
    public static final class NullMove extends PutObstacle{

        public NullMove() {
            super(null, null, null);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("Impossible d'executer le NullMove.");
        }
    }






}
