package Engine.GAME;

/*
Classe pour rendre le programme plus propre de manière générale pour utiliser des constantes dans les autres classes
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BoardUtils {

    public static final int NUM_SQUARE = 64;
    public static final int NUM_SQUARE_PER_COLUMN = 8;
    public static final int NUM_SQUARE_PER_LIGN = 8;
    public static final int MAX_INDEX = 7;

    public static boolean isValidCoordinate(final int x, final int y){
        return (x >=0 && x < NUM_SQUARE_PER_COLUMN) && (y >=0 && y < NUM_SQUARE_PER_COLUMN);
    }

    //Orientations
    public static final int TURNED_TO_RIGHT = 0;
    public static final int TURNED_TO_LEFT = 180;
    public static final int TURNED_UP = 90;
    public static final int TURNED_DOWN = -90;


    public static int turnaboutOrientation(int orientation){
        switch(orientation){
            case BoardUtils.TURNED_UP: //x-1
                return TURNED_DOWN;
            case BoardUtils.TURNED_DOWN: //x+1
                return TURNED_UP;
            case BoardUtils.TURNED_TO_LEFT: //y-1
                return TURNED_TO_RIGHT;
            case BoardUtils.TURNED_TO_RIGHT: //y+1
                return TURNED_TO_LEFT;
        }
        return 0;
    }

    public static final int[] directions =  {-1, 1};

    public static int getRightOrientation(int currentOrientation){
        switch (currentOrientation){
            case BoardUtils.TURNED_UP: //x-1
                return TURNED_TO_RIGHT;
            case BoardUtils.TURNED_DOWN: //x+1
                return TURNED_TO_LEFT;
            case BoardUtils.TURNED_TO_LEFT: //y-1
                return TURNED_UP;
            case BoardUtils.TURNED_TO_RIGHT: //y+1
                return TURNED_DOWN;
        }
        return 0;
    }

    public static int getLeftOrientation(int currentOrientation){
        switch (currentOrientation){
            case BoardUtils.TURNED_UP: //x-1
                return TURNED_TO_LEFT;
            case BoardUtils.TURNED_DOWN: //x+1
                return TURNED_TO_RIGHT;
            case BoardUtils.TURNED_TO_LEFT: //y-1
                return TURNED_DOWN;
            case BoardUtils.TURNED_TO_RIGHT: //y+1
                return TURNED_UP;
        }
        return 0;
    }

    //Convertir position numéro d'un carré en position X, Y
    public static List<Integer> convertIntoXYPosition(final int iD){
        List<Integer> position = new ArrayList<>();
        if(iD%8 == 0){
            position.add(iD/8 - 1 );
            position.add(7);
            return position;
        }else{
            position.add((int) iD/ 8);
            position.add(iD%8 - 1 );
            return position;
        }

    }


    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }




}
