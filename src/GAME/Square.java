package GAME;

import TILES.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Classe case du plateau
 */

public abstract class Square {
    protected int m_x;
    protected int m_y;


    private static final Map<List<Integer>, EmptySquare> EMPTY_SQUARE = createAllPossibleEmptySquare();

    //Créer plateau vide
    private static  Map<List<Integer>, EmptySquare> createAllPossibleEmptySquare() {
        final Map<List<Integer>, EmptySquare> emptySquareMap = new HashMap<>();
        List<Integer> position = new ArrayList<>();
        for (int i = 0; i<BoardUtils.NUM_SQUARE_PER_COLUMN; i++){
            for(int j=0; j<BoardUtils.NUM_SQUARE_PER_COLUMN; j++){
                position.add(i);
                position.add(j);
                emptySquareMap.put(position, new EmptySquare(i,j));
                position.clear();
            }

        }
        return emptySquareMap;
    }


    public static Square createSquare(final int x, final int y, final Tile tile){
        List<Integer> position = new ArrayList<>(){{
            add(x);
            add(y);
        }};
        //return tile != null ? new OccupiedSquare(x, y, tile) : EMPTY_SQUARE.get(position);
        return tile != null ? new OccupiedSquare(x, y, tile) : new EmptySquare( x, y);
    }


    //CONSTRUCTEURS
    private Square(int x, int y){
        this.m_x = x;
        this.m_y = y;
    }

    public abstract boolean isSquareOccupied();

    public abstract Tile getTile();

    //Pour avoir la valeur du carré (vide ou non)
    public abstract String getSquareValue();









    //Classe fille pour emplacement vide
    public static final class EmptySquare extends Square{
        EmptySquare(final int x, final int y){
            super( x, y);
        }

        @Override
        public String getSquareValue() {
            return "-";
        }

        @Override
        public boolean isSquareOccupied() {
            return false;
        }

        @Override
        public Tile getTile() {
            return null;
        }

    }

    //Classe fille pour emplacement occupé
    public static final class OccupiedSquare extends Square{

        private final Tile tileOnSquare;

        OccupiedSquare(final int x, final int y, Tile tileOnSquare){
            super(x,y);
            this.tileOnSquare = tileOnSquare;
        }

        @Override
        public String getSquareValue(){
            return this.tileOnSquare.getType();
        }

        @Override
        public boolean isSquareOccupied() {
            return true;
        }

        @Override
        public Tile getTile() {
            return this.tileOnSquare;
        }
    }

}
