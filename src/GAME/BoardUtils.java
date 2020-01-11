package GAME;

/*
Classe pour rendre le programme plus propre de manière générale pour utiliser des constantes dans les autres classes
 */

public class BoardUtils {

    public static final int NUM_SQUARE = 64;
    public static final int NUM_SQUARE_PER_COLUMN = 8;
    public static final int NUM_SQUARE_PER_LIGN = 8;
    public static final int MAX_INDEX = 7;

    public static boolean isValidCoordinate(final int x, final int y){
        return (x >=0 && x < NUM_SQUARE_PER_COLUMN) && (y >=0 && y < NUM_SQUARE_PER_COLUMN);
    }
}
