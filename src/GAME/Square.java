package GAME;

import TILES.Tile;

import java.util.HashMap;
import java.util.Map;

/*
Classe case du plateau
 */

public abstract class Square {
    protected int m_x;
    protected int m_y;


    private static final Map<int[], EmptySquare> EMPTY_SQUARE = createAllPossibleEmptySquare();

    //Créer plateau vide
    private static  Map<int[], EmptySquare> createAllPossibleEmptySquare() {
        final Map<int[], EmptySquare> emptySquareMap = new HashMap<>();

        for (int i = 0; i<BoardUtils.NUM_SQUARE_PER_COLUMN; i++){
            for(int j=0; j<BoardUtils.NUM_SQUARE_PER_COLUMN; j++){
                emptySquareMap.put(new int[] {i,j}, new EmptySquare(i,j));
            }

        }
        return emptySquareMap;
    }


    public static Square createSquare(final int x, final int y, final Tile tile){
        return tile != null ? new OccupiedSquare(x, y, tile) : EMPTY_SQUARE.get(new int[]{x,y});
    }


    //CONSTRUCTEURS
    private Square(int x, int y){
        this.m_x = x;
        this.m_y = y;
    }

    public abstract boolean isSquareOccupied();

    public abstract Tile getTile();

    //Classe fille pour emplacement vide
    public static final class EmptySquare extends Square{
        EmptySquare(final int x, final int y){
            super( x, y);
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
        public boolean isSquareOccupied() {
            return true;
        }

        @Override
        public Tile getTile() {
            return this.tileOnSquare;
        }
    }

}
