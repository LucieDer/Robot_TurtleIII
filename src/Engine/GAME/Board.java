package Engine.GAME;

import Engine.CARDS.DeckCards;
import Engine.CARDS.HandCards;
import Engine.CARDS.Program;
import Engine.PLAYERS.*;

import Engine.TILES.Jewel;
import Engine.TILES.Obstacles.DeckObstacles;
import Engine.TILES.Obstacles.HandObstacles;
import Engine.TILES.Obstacles.StoneWall;
import Engine.TILES.Tile;
import Engine.TILES.Turtle;

import java.util.*;

public class Board {
    public final Collection<Tile> activeTurtles;
    public final Collection<Tile> activeTiles;
    public final Move transitionMove;
    public Turtle redTurtle;
    public Turtle greenTurtle;
    public Turtle purpleTurtle;
    public Turtle blueTurtle;

    //Joueurs
    private RedPlayer redPlayer = null;
    private GreenPlayer greenPlayer = null;
    private PurplePlayer purplePlayer = null;
    private BluePlayer bluePlayer = null;

    //Deck obstacle
    private DeckObstacles deckObstacles = new DeckObstacles();

    //Joueur Rouge
    public DeckCards m_RedPlayerDeckCards = null;
    public Program m_RedPlayerProgram = null;
    public HandCards m_RedPlayerHandCards = null;
    public HandObstacles m_RedPlayerHandObstacles = null;

    //Joueur Vert
    public DeckCards m_GreenPlayerDeckCards = null;
    public Program m_GreenPlayerProgram = null;
    public HandCards m_GreenPlayerHandCards = null;
    public HandObstacles m_GreenPlayerHandObstacles = null;

    //Joueur Violet
    public DeckCards m_PurplePlayerDeckCards = null;
    public Program m_PurplePlayerProgram = null;
    public HandCards m_PurplePlayerHandCards = null;
    public HandObstacles m_PurplePlayerHandObstacles = null;

    //Joueur Bleu
    public DeckCards m_BluePlayerDeckCards = null;
    public Program m_BluePlayerProgram = null;
    public HandCards m_BluePlayerHandCards = null;
    public HandObstacles m_BluePlayerHandObstacles = null;


    private final Map<List<Integer>, Square> gameBoard;
    private int m_nbOfPlayers;

    private final Player currentPlayer;



    private Board(Builder builder){

        this.gameBoard = createGameBoard(builder);
        //this.m_nbOfPlayers = builder.m_nbOfPlayers;
        this.activeTiles = calculateActiveTiles(this.gameBoard);
        this.activeTurtles = calculateActiveTurtles(this.gameBoard);
        this.m_nbOfPlayers = activeTurtles.size();

        //Attribution des tortues
        this.redTurtle = (Turtle)this.getRedTurtle();
        this.greenTurtle = (Turtle)this.getGreenTurtle();
        this.purpleTurtle = (Turtle)this.getPurpleTurtle();
        this.blueTurtle = (Turtle)this.getBlueTurtle();

        //Attribution des paquets/mains de cartes
        this.m_RedPlayerDeckCards = builder.m_RedPlayerDeckCards;
        this.m_RedPlayerHandCards = builder.m_RedPlayerHandCards;
        this.m_RedPlayerHandObstacles = builder.m_RedPlayerHandObstacles;
        this.m_RedPlayerProgram = builder.m_RedPlayerProgram;

        this.m_GreenPlayerDeckCards = builder.m_GreenPlayerDeckCards;
        this.m_GreenPlayerHandCards = builder.m_GreenPlayerHandCards;
        this.m_GreenPlayerHandObstacles = builder.m_GreenPlayerHandObstacles;
        this.m_GreenPlayerProgram = builder.m_GreenPlayerProgram;

        this.m_PurplePlayerDeckCards = builder.m_PurplePlayerDeckCards;
        this.m_PurplePlayerHandCards = builder.m_PurplePlayerHandCards;
        this.m_PurplePlayerHandObstacles = builder.m_PurplePlayerHandObstacles;
        this.m_PurplePlayerProgram = builder.m_PurplePlayerProgram;

        this.m_BluePlayerDeckCards = builder.m_BluePlayerDeckCards;
        this.m_BluePlayerHandCards = builder.m_BluePlayerHandCards;
        this.m_BluePlayerHandObstacles = builder.m_BluePlayerHandObstacles;
        this.m_BluePlayerProgram = builder.m_BluePlayerProgram;


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

        this.deckObstacles = builder.m_deckObstacles;
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.m_nbOfPlayers, this.redPlayer, this.greenPlayer, this.purplePlayer, this.bluePlayer);
        this.transitionMove = builder.transitionMove != null ? builder.transitionMove : null;

    }

    public DeckObstacles getDeckObstacles() {
        return deckObstacles;
    }

    public Move getTransitionMove() {
        return transitionMove;
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
                }else continue;
            }else return null;
        }return null;
    }


    //Accéder à la tortue verte
    public Tile getGreenTurtle(){
        for(Tile turtle : this.activeTurtles){
            if(turtle.getType() == "Tortue"){
                if(turtle.getM_color() == Color.VERT){
                    return turtle;
                }else continue;
            }else return  null;
        }return null;
    }

    //Accéder à la tortue violette
    public Tile getPurpleTurtle(){
        for(Tile turtle : this.activeTurtles){
            if(turtle.getType() == "Tortue"){
                if(turtle.getM_color() == Color.VIOLET){
                    return turtle;
                }else continue;
            }else return null;
        }return null;
    }

    //Accéder à la tortue bleue
    public Tile getBlueTurtle(){
        for(Tile turtle : this.activeTurtles){
            if(turtle.getType() == "Tortue"){
                if(turtle.getM_color() == Color.BLEU){
                    return turtle;
                }else continue;
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


    //Plateau pour début du jeu : va placer les tuiles en fonction du nombre de joueurs dans le builder
    public static Board createStandardBoard(int nbOfPlayers){
        final Builder builder = new Builder(nbOfPlayers);

        //Placement des tuiles
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

                builder.setMoveMaker(Color.ROUGE);

                //Initialisation des paquets de carte :
                builder.setDeckObstacles(new DeckObstacles());
                builder.m_deckObstacles.populate();

                //Joueur Rouge
                builder.setRedPlayerHands(new DeckCards(),new HandCards(), new Program(), builder.m_deckObstacles.createBasicHand());
                builder.m_RedPlayerDeckCards.populate();
                builder.m_RedPlayerDeckCards.shuffle();
                builder.m_RedPlayerDeckCards.deal(builder.m_RedPlayerHandCards, 5);

                //Joueur Vert
                builder.setGreenPlayerHands(new DeckCards(),new HandCards(), new Program(), builder.m_deckObstacles.createBasicHand());
                builder.m_GreenPlayerDeckCards.populate();
                builder.m_GreenPlayerDeckCards.shuffle();
                builder.m_GreenPlayerDeckCards.deal(builder.m_GreenPlayerHandCards, 5);


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

                builder.setMoveMaker(Color.ROUGE);

                //Initialisation des paquets de carte :
                builder.setDeckObstacles(new DeckObstacles());
                builder.m_deckObstacles.populate();

                //Joueur Rouge
                builder.setRedPlayerHands(new DeckCards(),new HandCards(), new Program(), builder.m_deckObstacles.createBasicHand());
                builder.m_RedPlayerDeckCards.populate();
                builder.m_RedPlayerDeckCards.shuffle();
                builder.m_RedPlayerDeckCards.deal(builder.m_RedPlayerHandCards, 5);

                //Joueur Vert
                builder.setGreenPlayerHands(new DeckCards(),new HandCards(), new Program(), builder.m_deckObstacles.createBasicHand());
                builder.m_GreenPlayerDeckCards.populate();
                builder.m_GreenPlayerDeckCards.shuffle();
                builder.m_GreenPlayerDeckCards.deal(builder.m_GreenPlayerHandCards, 5);

                //Joueur Violet
                builder.setPurplePlayerHands(new DeckCards(),new HandCards(), new Program(), builder.m_deckObstacles.createBasicHand());
                builder.m_PurplePlayerDeckCards.populate();
                builder.m_GreenPlayerDeckCards.shuffle();
                builder.m_PurplePlayerDeckCards.deal(builder.m_GreenPlayerHandCards, 5);


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

                builder.setMoveMaker(Color.ROUGE);

                //Initialisation des paquets de carte :
                builder.setDeckObstacles(new DeckObstacles());
                builder.m_deckObstacles.populate();

                //Joueur Rouge
                builder.setRedPlayerHands(new DeckCards(),new HandCards(), new Program(), builder.m_deckObstacles.createBasicHand());
                builder.m_RedPlayerDeckCards.populate();
                builder.m_RedPlayerDeckCards.shuffle();
                builder.m_RedPlayerDeckCards.deal(builder.m_RedPlayerHandCards, 5);

                //Joueur Vert
                builder.setGreenPlayerHands(new DeckCards(),new HandCards(), new Program(), builder.m_deckObstacles.createBasicHand());
                builder.m_GreenPlayerDeckCards.populate();
                builder.m_GreenPlayerDeckCards.shuffle();
                builder.m_GreenPlayerDeckCards.deal(builder.m_GreenPlayerHandCards, 5);

                //Joueur Violet
                builder.setPurplePlayerHands(new DeckCards(),new HandCards(), new Program(), builder.m_deckObstacles.createBasicHand());
                builder.m_PurplePlayerDeckCards.populate();
                builder.m_PurplePlayerDeckCards.shuffle();
                builder.m_PurplePlayerDeckCards.deal(builder.m_PurplePlayerHandCards, 5);

                //Joueur Bleu
                builder.setBluePlayerHands(new DeckCards(),new HandCards(), new Program(), builder.m_deckObstacles.createBasicHand());
                builder.m_BluePlayerDeckCards.populate();
                builder.m_BluePlayerDeckCards.shuffle();
                builder.m_BluePlayerDeckCards.deal(builder.m_BluePlayerHandCards, 5);


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
        Move transitionMove = null;

        //Decks et mains des joueurs
        DeckObstacles m_deckObstacles;

        //Joueur Rouge
        DeckCards m_RedPlayerDeckCards = null;
        Program m_RedPlayerProgram = null;
        HandCards m_RedPlayerHandCards = null;
        HandObstacles m_RedPlayerHandObstacles = null;

        //Joueur Vert
        DeckCards m_GreenPlayerDeckCards = null;
        Program m_GreenPlayerProgram = null;
        HandCards m_GreenPlayerHandCards = null;
        HandObstacles m_GreenPlayerHandObstacles = null;

        //Joueur Violet
        DeckCards m_PurplePlayerDeckCards = null;
        Program m_PurplePlayerProgram = null;
        HandCards m_PurplePlayerHandCards = null;
        HandObstacles m_PurplePlayerHandObstacles = null;

        //Joueur Bleu
        DeckCards m_BluePlayerDeckCards = null;
        Program m_BluePlayerProgram = null;
        HandCards m_BluePlayerHandCards = null;
        HandObstacles m_BluePlayerHandObstacles = null;




        public Builder(final int nbOfPlayers){
            this.m_nbOfPlayers = nbOfPlayers;
            this.boardConfig = new HashMap<>();
        }

        public Builder setTile(final Tile tile){
            this.boardConfig.put(tile.getTilePosition(), tile);
            return this;
        }

        public Builder setDeckObstacles(final DeckObstacles deckObstacles){
            this.m_deckObstacles = deckObstacles;
            return this;
        }

        public Builder setMoveMaker(final Color nextMoveMaker){
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Builder setRedPlayerHands(DeckCards deckCards, HandCards handCards, Program program, HandObstacles handObstacles){
            this.m_RedPlayerDeckCards = deckCards;
            this.m_RedPlayerHandCards = handCards;
            this.m_RedPlayerProgram = program;
            this.m_RedPlayerHandObstacles = handObstacles;
            return this;
        }

        public Builder setGreenPlayerHands(DeckCards deckCards, HandCards handCards, Program program, HandObstacles handObstacles){
            this.m_GreenPlayerDeckCards = deckCards;
            this.m_GreenPlayerHandCards = handCards;
            this.m_GreenPlayerProgram = program;
            this.m_GreenPlayerHandObstacles = handObstacles;
            return this;
        }

        public Builder setPurplePlayerHands(DeckCards deckCards, HandCards handCards, Program program, HandObstacles handObstacles){
            this.m_PurplePlayerDeckCards = deckCards;
            this.m_PurplePlayerHandCards = handCards;
            this.m_PurplePlayerProgram = program;
            this.m_PurplePlayerHandObstacles = handObstacles;
            return this;
        }

        public Builder setBluePlayerHands(DeckCards deckCards, HandCards handCards, Program program, HandObstacles handObstacles){
            this.m_BluePlayerDeckCards = deckCards;
            this.m_BluePlayerHandCards = handCards;
            this.m_BluePlayerProgram = program;
            this.m_BluePlayerHandObstacles = handObstacles;
            return this;
        }

        public Builder setMoveTransition(final Move transitionMove){
            this.transitionMove = transitionMove;
            return this;
        }


        public Board build(){
            return new Board(this);
        }
    }
}
