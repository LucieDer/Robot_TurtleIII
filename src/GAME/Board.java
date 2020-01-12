package GAME;

import PLAYERS.*;

import TILES.Jewel;
import TILES.Obstacles.StoneWall;
import TILES.Tile;
import TILES.Turtle;

import java.util.*;

public class Board {
    public final Collection<Tile> activeTurtles;
    public final Collection<Tile> activeTiles;
    public Turtle redTurtle;
    public Turtle greenTurtle;
    public Turtle purpleTurtle;
    public Turtle blueTurtle;

    //Joueurs
    private RedPlayer redPlayer = null;
    private GreenPlayer greenPlayer = null;
    private PurplePlayer purplePlayer = null;
    private BluePlayer bluePlayer = null;

    private final Map<List<Integer>, Square> gameBoard;
    private int m_nbOfPlayers;

    private final Player currentPlayer;



    private Board(Builder builder){

        this.gameBoard = createGameBoard(builder);
        //this.m_nbOfPlayers = builder.m_nbOfPlayers;
        this.activeTiles = calculateActiveTiles(this.gameBoard);
        this.activeTurtles = calculateActiveTurtles(this.gameBoard);
        this.m_nbOfPlayers = activeTurtles.size();
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.m_nbOfPlayers, this.redPlayer, this.greenPlayer, this.purplePlayer, this.bluePlayer);

        switch(this.m_nbOfPlayers){
            case 2:
                this.redPlayer = new RedPlayer(this);
                this.greenPlayer = new GreenPlayer(this);
                break;
            case 3:
                this.redPlayer = new RedPlayer(this);
                this.greenPlayer = new GreenPlayer(this);
                this.purplePlayer = new PurplePlayer(this);
                break;
            case 4:
                this.redPlayer = new RedPlayer(this);
                this.greenPlayer = new GreenPlayer(this);
                this.purplePlayer = new PurplePlayer(this);
                this.bluePlayer = new BluePlayer(this);
                break;


        }


    }

    public Player getCurrentPlayer(){
        return this.currentPlayer;
    }

    public Collection<Tile> calculateActiveTiles(Map<List<Integer>, Square> gameBoard) {
        final List<Tile> activeTiles = new ArrayList<>();
        for (final Square square : gameBoard.values()){
            if(square.isSquareOccupied()){
                final Tile tile = square.getTile();
                activeTiles.add(tile);
            }
        }
        return activeTiles;
    }

    //Va stocker toutes les tortues sur le plateau dans une liste
    private Collection<Tile> calculateActiveTurtles(Map<List<Integer>, Square> gameBoard) {
        final List<Tile> activeTurtles = new ArrayList<>();
        for (final Square square : gameBoard.values()){
            if(square.isSquareOccupied()){
                if(square.getTile().getType() == "Tortue"){
                    final Tile tile = square.getTile();
                    activeTurtles.add(tile);
                }

            }
        }
        return activeTurtles;
    }

    //Obtenir le nombre de joueurs
    public int getM_nbOfPlayers() {
        return m_nbOfPlayers;
    }


    public void setM_nbOfPlayers(int m_nbOfPlayers) {
        this.m_nbOfPlayers = m_nbOfPlayers;
    }

    //Afficher le plateau dans le terminal
    public String printBoard(){
        final StringBuilder builder = new StringBuilder();
        List<Integer> position = new ArrayList<>();

        for (int i=0; i<BoardUtils.NUM_SQUARE_PER_COLUMN; i++){
            for(int j=0; j<BoardUtils.NUM_SQUARE_PER_LIGN; j++){
                //final String tileText = prettyPrint(this.gameBoard[i][j]);
                position.add(i);
                position.add(j);
                final String tileText = this.gameBoard.get(position).getSquareValue();
                builder.append(String.format("%3s", tileText));
                position.clear();

            }
            builder.append("\n");
        }
        return builder.toString();
    }



    public Square getSquare(final List<Integer> position){
        return gameBoard.get(position);
    }

    //Obtenir la liste de tortues sur le plateau
    public Collection<Tile> getActiveTurtles(){
        return this.activeTurtles;
    }

    //Accéder à la tortue rouge
    public Tile getRedTurtle(){
        for(Tile turtle : this.activeTurtles){
            if(turtle.getType() == "Tortue"){
                if(turtle.getM_color() == Color.ROUGE){
                    return turtle;
                }else return null;
            }else return null;
        }return null;
    }


    //Accéder à la tortue verte
    public Tile getGreenTurtle(){
        for(Tile turtle : this.activeTurtles){
            if(turtle.getType() == "Tortue"){
                if(turtle.getM_color() == Color.VERT){
                    return turtle;
                }else return null;
            }else return null;
        }return null;
    }

    //Accéder à la tortue violette
    public Tile getPurpleTurtle(){
        for(Tile turtle : this.activeTurtles){
            if(turtle.getType() == "Tortue"){
                if(turtle.getM_color() == Color.VIOLET){
                    return turtle;
                }else return null;
            }else return null;
        }return null;
    }

    //Accéder à la tortue bleue
    public Tile getBlueTurtle(){
        for(Tile turtle : this.activeTurtles){
            if(turtle.getType() == "Tortue"){
                if(turtle.getM_color() == Color.BLEU){
                    return turtle;
                }else return null;
            }else return null;
        }return null;
    }


    //Accéder aux joueurs :


    public RedPlayer getRedPlayer() {
        return redPlayer;
    }

    public GreenPlayer getGreenPlayer() {
        return greenPlayer;
    }

    public PurplePlayer getPurplePlayer() {
        return purplePlayer;
    }

    public BluePlayer getBluePlayer() {
        return bluePlayer;
    }

    /*Va créer une plateau qui représenté 64 cases (Empy ou Occupied) et de leur position
                    Le plateau va être crée à partir du Builder qui contenait la liste des pièces et de leur position
                     */
    private static Map<List<Integer>, Square> createGameBoard(final Builder builder){
        final Map<List<Integer>, Square> squares = new HashMap<>();
        int x, y;


        for (int i=0; i< BoardUtils.NUM_SQUARE_PER_LIGN; i++){
            for (int j=0; j< BoardUtils.NUM_SQUARE_PER_COLUMN; j++){
                List<Integer> position = new ArrayList<>(); //stocker les index
                position.add(i);
                position.add(j);
                Square put = squares.put(position, Square.createSquare(i, j, builder.boardConfig.get(position))); //Collections.unmodifiableList va copier la liste

            }
        }

        return squares;
    }


    //Va placer les tuiles en fonction du nombre de joueurs dans le builder
    public static Board createStandardBoard(int nbOfPlayers){
        final Builder builder = new Builder(nbOfPlayers);

        switch (nbOfPlayers){
            case 2:
                // 2 tortues
                builder.setTile(new Turtle(true, Color.ROUGE, 0, 1, -90));
                builder.setTile(new Turtle(true, Color.VERT, 0, 5, -90));

                // 1 joyau vert
                builder.setTile(new Jewel(true, Color.VERT, 7, 3));

                // Colonne de droite avec que des murs en pierre
                for (int i=0; i < BoardUtils.NUM_SQUARE_PER_LIGN; i++){
                    builder.setTile(new StoneWall(true, i, BoardUtils.MAX_INDEX));
                }

                //builder.setMoveMaker();

                break;
            case 3:
                // 3 tortues
                builder.setTile(new Turtle(true, Color.ROUGE, 0, 0, -90));
                builder.setTile(new Turtle(true, Color.VERT, 0, 3, -90));
                builder.setTile(new Turtle(true, Color.VIOLET, 0, 6, -90));

                // 3 Joyaux : violet, vert, bleu
                builder.setTile(new Jewel(true, Color.VIOLET, 7, 0));
                builder.setTile(new Jewel(true, Color.VERT, 7, 3));
                builder.setTile(new Jewel(true, Color.BLEU, 7, 6));

                // Colonne de droite avec que des murs en pierre
                for (int i=0; i < BoardUtils.NUM_SQUARE_PER_LIGN; i++){
                    builder.setTile(new StoneWall(true, i, BoardUtils.MAX_INDEX));
                }

                //builder.setMoveMaker();
                break;
            case 4:
                // 4 tortues
                builder.setTile(new Turtle(true, Color.ROUGE, 0, 0, -90));
                builder.setTile(new Turtle(true, Color.VERT, 0, 2, -90));
                builder.setTile(new Turtle(true, Color.VIOLET, 0, 5, -90));
                builder.setTile(new Turtle(true, Color.BLEU, 0, 7, -90));

                // 2 joyaux : violet, bleu
                builder.setTile(new Jewel(true, Color.VIOLET, 7, 1));
                builder.setTile(new Jewel(true, Color.BLEU, 7, 6));

                //builder.setMoveMaker();
                break;

                default:
                    System.out.println("Impossible de créer le plateau.");
                    break;

        }

        Board newBoard = builder.build();

        return newBoard;
    }

    public Collection<Tile> getActiveTiles() {
        return calculateActiveTiles(this.gameBoard);
    }


    public static class Builder{

        Map<List<Integer>, Tile> boardConfig;
        final int m_nbOfPlayers;
        Color nextMoveMaker;

        public Builder(final int nbOfPlayers){
            this.m_nbOfPlayers = nbOfPlayers;
            this.boardConfig = new HashMap<>();
        }

        public Builder setTile(final Tile tile){
            this.boardConfig.put(tile.getTilePosition(), tile);
            return this;
        }

        public Builder setMoveMaker(final Color nextMoveMaker){
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build(){
            return new Board(this);
        }
    }
}
