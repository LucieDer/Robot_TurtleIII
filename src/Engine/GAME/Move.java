package Engine.GAME;

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
    List<Integer> finalCoordinate;
    int finalOrientation;
    int destinationOrientation;

    protected boolean isMoving = false;
    protected boolean isPutting = false;
    protected boolean isTurning = false;
    protected boolean isLaser = false;
    protected boolean moveIllegal = true;

    //protected static final Move NULL_MOVE = new NullMove();

    /*
    CONSTRUCTEURS
    m_board -> le plateau
    m_tile -> la tuile qui fait le mouvement
    m_xDestination, m_yDestination -> les coordonnées d'arrivée de la tuile
    m_orientation -> orientation (pour une tortue) : 0, -90, 180, 90 ??
     */

    //Constructeur complet
    public Move(final Board board, final Tile tile){
        if(tile!=null){
            this.finalCoordinate = tile.getTilePosition(); //Par défaut la pièce ne bouge pas
            this.finalOrientation = tile.getM_currentOrientation();
            this.destinationCoordinate = tile.getTilePosition(); //Par défaut la pièce ne bouge pas
            this.destinationOrientation = tile.getM_currentOrientation();
        }

        this.m_board = board;
        this.m_movedTile = tile;

    }

    /*
    public static Move getNullMove() {
        return NULL_MOVE;
    }

     */

    public boolean isLaser() {
        return isLaser;
    }

    public boolean isMoveIllegal() {
        return moveIllegal;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public boolean isPutting() {
        return isPutting;
    }

    public boolean isTurning() {
        return isTurning;
    }

    public Tile getAttackedTile(){
        return null;
    }

    public List<Integer> getFinalCoordinate() {
        return finalCoordinate;
    }

    public int getFinalOrientation() {
        return finalOrientation;
    }

    public List<Integer> getDestinationCoordinate(){
        return this.destinationCoordinate;
    }

    public int getDestinationOrientation() {
        return destinationOrientation;
    }

    public List<Integer> getCurrentCoordinate(){

        return this.getM_movedTile().getTilePosition();
    }

    public int getCurrentOrientation(){
        return this.getM_movedTile().getM_currentOrientation();
    }

    public Board getM_board() {
        return m_board;
    }

    public List<Integer> getInitialCoordinate() {
        if(this.m_movedTile.getType() == "Tortue"){
            return this.m_movedTile.getM_initialPosition();
        }else return null;
    }

    public Board execute() {
        final Board.Builder builder = new Board.Builder(this.m_board.getM_nbOfPlayers());


        if(this.m_movedTile != null){

            for(final Tile tile : this.m_board.getActiveTiles()){
                //Vérifier que la pièce est sur le plateau : refait le plateau précédent avec toutes les pièces non bougees
                if(!this.m_movedTile.equals(tile) && tile.isM_isOnBoard()){
                    builder.setTile(tile);
                }
            }
            //Représenter la pièce déplacée : crée une nouvelle pièce sur plateau avec de nouveaux paramètres
            builder.setTile(this.m_movedTile.moveTile(this));
            //Passer au joueur suivant
            builder.setMoveMaker(this.m_board.getCurrentPlayer().getNextPlayer().getColor());

            builder.setRedPlayerHands(this.m_board.m_RedPlayerDeckCards, this.m_board.m_RedPlayerHandCards, this.m_board.m_RedPlayerProgram, this.m_board.m_RedPlayerHandObstacles);
            builder.setGreenPlayerHands(this.m_board.m_GreenPlayerDeckCards, this.m_board.m_GreenPlayerHandCards, this.m_board.m_GreenPlayerProgram, this.m_board.m_GreenPlayerHandObstacles);
            builder.setPurplePlayerHands(this.m_board.m_PurplePlayerDeckCards, this.m_board.m_PurplePlayerHandCards, this.m_board.m_PurplePlayerProgram, this.m_board.m_PurplePlayerHandObstacles);
            builder.setBluePlayerHands(this.m_board.m_BluePlayerDeckCards, this.m_board.m_BluePlayerHandCards, this.m_board.m_BluePlayerProgram, this.m_board.m_BluePlayerHandObstacles);
        }else{
            for(final Tile tile : this.m_board.getActiveTiles()){
                    builder.setTile(tile);
                }
            }

        return builder.build();
    }

    public Board executeProgram() { //Différence avec celui-au dessus : le joueur le change pas
        final Board.Builder builder = new Board.Builder(this.m_board.getM_nbOfPlayers());
        for(final Tile tile : this.m_board.getActiveTiles()){
            //Vérifier que la pièce est sur le plateau
            if(!this.m_movedTile.equals(tile) && tile.isM_isOnBoard()){
                builder.setTile(tile);
            }
        }

        //Représenter la pièce déplacée : crée une nouvelle pièce sur plateau avec de nouveaux paramètres
        builder.setTile(this.m_movedTile.moveTile(this));

        return builder.build();
    }


    public Tile getM_movedTile(){
        return this.m_movedTile;
    }



    //Quand le mouvement est terminé
    public static final class NullMove extends Move{
        public NullMove(Board board, Tile tile){
            super(board, tile);
        }
    }






    // Classe fille mouvement Avancer
    public static final class TurtleGoForward extends Move{

        int destX = this.m_movedTile.getTilePosition().get(0);
        int destY = this.m_movedTile.getTilePosition().get(1);
        List<Integer> currPosition;
        List<Integer> secondTurtlePosition;
        boolean anotherTurtle = false;
        boolean Jewel = false;


        public TurtleGoForward(Board board, Tile movedTile) {
            super(board, movedTile);
            this.isMoving = true;
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
                this.finalCoordinate = movedTile.getM_initialPosition();
                this.finalOrientation = BoardUtils.TURNED_DOWN;
            }
            else if(board.getSquare(this.destinationCoordinate).getTile().getType() == "Obstacle"){ //Demi-tour devant obstacle
                this.finalOrientation = BoardUtils.turnaboutOrientation(this.getCurrentOrientation());
            }
            else if(board.getSquare(this.destinationCoordinate).getTile().getType() == "Tortue"){
                //TODO
                this.anotherTurtle = true;
                this.finalCoordinate = movedTile.getM_initialPosition();
                this.finalOrientation = BoardUtils.TURNED_DOWN;

                this.setAnotherTurtle(true);
            }



        }

        public boolean isAnotherTurtle() {
            return anotherTurtle;
        }

        public void setAnotherTurtle(boolean anotherTurtle) {
            this.anotherTurtle = anotherTurtle;
        }
    }


    // Classe fille revenir à la position initiale
    public static final class TurtleGoToInitialPosition extends Move{

        public TurtleGoToInitialPosition(Board board, Tile movedTile) {
            super(board, movedTile);
            this.isMoving = true;
            this.finalCoordinate = movedTile.getTilePosition();
            this.finalOrientation = BoardUtils.TURNED_DOWN;


        }

    }

    //Classe fille mouvement Tourner à droite
    public static final class TurtleTurnRight extends Move {
        public TurtleTurnRight(final Board board, final Tile tile) {
            super(board, tile);
            this.isTurning = true;
            switch (tile.getM_currentOrientation()) {
                case BoardUtils.TURNED_UP: //x-1
                    this.finalOrientation = BoardUtils.TURNED_TO_RIGHT;
                    break;
                case BoardUtils.TURNED_DOWN: //x+1
                    this.finalOrientation = BoardUtils.TURNED_TO_LEFT;
                    break;
                case BoardUtils.TURNED_TO_LEFT: //y-1
                    this.finalOrientation = BoardUtils.TURNED_UP;
                    break;
                case BoardUtils.TURNED_TO_RIGHT: //y+1
                    this.finalOrientation = BoardUtils.TURNED_DOWN;
                    break;
            }
        }
    }



    //Classe fille mouvement Tourner à gauche
    public static final class TurtleTurnLeft extends Move{
        public TurtleTurnLeft(final Board board, final Tile tile) {
            super(board, tile);
            this.isTurning = true;
            switch (tile.getM_currentOrientation()) {
                case BoardUtils.TURNED_UP: //x-1
                    this.finalOrientation = BoardUtils.TURNED_TO_LEFT;
                    break;
                case BoardUtils.TURNED_DOWN: //x+1
                    this.finalOrientation = BoardUtils.TURNED_TO_RIGHT;
                    break;
                case BoardUtils.TURNED_TO_LEFT: //y-1
                    this.finalOrientation = BoardUtils.TURNED_DOWN;
                    break;
                case BoardUtils.TURNED_TO_RIGHT: //y+1
                    this.finalOrientation = BoardUtils.TURNED_UP;
                    break;
            }
        }


    }

    //Classe fille mouvement Laser
    public static final class TurtleLaser extends Move{

        int destX = this.destinationCoordinate.get(0);
        int destY = this.destinationCoordinate.get(1);
        List<Integer> currPosition = this.destinationCoordinate;
        boolean jewel = false;
        boolean turtle = false;
        boolean iceWall = false;

        public TurtleLaser(final Board board, final Tile tile){
            super(board, tile);
            this.isLaser = true;
            switch(tile.getM_currentOrientation()){
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

            if(board.getSquare(this.destinationCoordinate).getTile() == null){
                this.finalCoordinate = currPosition;
            }

            else if(board.getSquare(this.destinationCoordinate).getTile().getType() == "Joyau"){
                this.jewel = true;
                if(board.getM_nbOfPlayers() == 2){ // 1/2 tour
                    this.finalCoordinate = currPosition;
                    this.finalOrientation = BoardUtils.turnaboutOrientation(this.getCurrentOrientation());
                }else{  //case départ
                    this.finalCoordinate = tile.getTilePosition();
                    this.finalOrientation = BoardUtils.TURNED_DOWN;
                }
            }

            else if(board.getSquare(this.destinationCoordinate).getTile().getType() == "Tortue"){
                this.turtle = true;
            }

            else if(board.getSquare(this.destinationCoordinate).getTile().getM_material() == "Glace"){
                this.iceWall = true;
            }

        }


    }


    //Classe fille mouvement Tourner à gauche
    public static final class TurtleTurnabout extends Move{

        public TurtleTurnabout(final Board board, final Tile tile){
            super(board, tile);
            this.isTurning = true;
            this.finalOrientation = BoardUtils.turnaboutOrientation(tile.getM_currentOrientation());
        }


    }




    public static class PutObstacle extends Move{

        public PutObstacle(Board board, Tile tile, List<Integer> destinationCoordinate) {
            super(board, tile);
            this.isPutting = true;
            this.destinationCoordinate = destinationCoordinate;
            this.moveIllegal = ((this.isNextToForbiddenPiece()) && (tile.getM_material() == "Pierre"));
        }

        public List<Square> getAdjacentSquares(){
            int x = this.destinationCoordinate.get(0);
            int y = this.destinationCoordinate.get(1);
            List<Integer> position = new ArrayList<>();
            int[] xdirections = {0 , 0 , 1 , -1};
            int[] ydirections = {-1 , 1 , 0 , 0};
            List<Square> adjacentSquares = new ArrayList<>();

            for(int i=0; i<4; i++){
                position.clear();
                position.add(x + xdirections[i]);
                position.add(y + ydirections[i]);
                if(BoardUtils.isValidCoordinate(position.get(0), position.get(1))){
                    adjacentSquares.add(this.m_board.getSquare(position));
                }

            }

            return adjacentSquares;
        }

        public boolean isNextToForbiddenPiece(){
            List<Square> adjacentSquare = this.getAdjacentSquares();
            for(Square square : adjacentSquare){
                if(square.isSquareOccupied()){
                    if( (square.getTile().getType() == "Joyau") || (square.getTile().getType() == "Tortue") ){
                        return true;
                    }
                }
            }
            return false;
        }

        public void canPutHere(){
            if(this.m_movedTile.getM_material() == "Pierre"){
                if(this.isNextToForbiddenPiece()){
                    this.moveIllegal = true;
                }
            }else if(this.m_movedTile.getM_material() == "Glace"){
                this.moveIllegal = false;
            }
            this.moveIllegal = true;
        }


    }



}
