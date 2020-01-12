package GAME;

import PLAYER.Player;
import TILES.Jewel;
import TILES.Obstacles.StoneWall;
import TILES.Tile;
import TILES.Turtle;

import java.util.*;

public class Board {



    private final Map<List<Integer>, Square> gameBoard;
    private int m_nbOfPlayers;



    private Board(Builder builder){
        this.gameBoard = createGameBoard(builder);
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
        final Builder builder = new Builder();

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
        newBoard.setM_nbOfPlayers(nbOfPlayers);

        return newBoard;
    }


    public static class Builder{

        Map<List<Integer>, Tile> boardConfig;
        Player nextMoveMaker;

        public Builder(){
            this.boardConfig = new HashMap<>();
        }

        public Builder setTile(final Tile tile){
            this.boardConfig.put(tile.getTilePosition(), tile);
            return this;
        }

        public Builder setMoveMaker(final Player nextMoveMaker){
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build(){
            return new Board(this);
        }
    }
}
