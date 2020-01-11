package TILES;

/*
Classe mère pour les tuiles
 */


import GAME.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class Tile {


    protected int m_x, m_y;
    protected boolean m_isOnBoard = false;
    protected final Type m_type;
    protected boolean m_canMove;


    /*
    * CONSTRUCTEURS
    * m_isOnBoard -> si la tuile est posée sur le plateau
    * m_x ->  location x de de la tuile                  si la tuile n'est pas posée, x et y définis à 999?.
    * m_y ->  location y de la tuile
    * m_player ->  à quelle tortue est associée la tuile : si la tuile est posée, elle n'est pas associée ??
    * m_type -> le type de la tuile : une tortue, un obstacle ou un joyau. Le Type est effectué à partir de la class Type
    * */

    public Tile(boolean isOnBoard, Type type, int x, int y){
        m_type = type;
        m_isOnBoard = isOnBoard;
        if (!isOnBoard){
            m_x = 999;
            m_y = 999;
        }

        else {
            m_x = x;
            m_y = y;
        }
    }
    /*

    //Si la tuile est sur le plateau : on initialise x et y
    public Tile(boolean isOnBoard, Type type, int x, int y, Color color){
        m_type = type;
        m_isOnBoard = isOnBoard;
        m_color = color;
        if (isOnBoard){
            m_x = x;
            m_y = y;
        }
    }

*/

    //Si la tuile n'est pas sur le plateau mais qu'elle est dans une main d'un joueur
    public Tile(boolean isOnBoard, Type type){
        m_type = type;
        m_isOnBoard = isOnBoard;
        if (!isOnBoard){
            m_x = 999;
            m_y = 999;
        }
    }



/*
    //Si la tuile n'est pas sur le plateau et qu'elle est dans la pioche
    public Tile(boolean isOnBoard, Type type){
        m_type = type;
        m_isOnBoard = isOnBoard;
        if (!isOnBoard){
            m_x = 999;
            m_y = 999;
        }
    }


 */




    //METHODES



    // Fonction pour obtenir le type de la tuile
    public String getType(){
        return m_type.getTypeValue();
    }

    //Fonction pour récupérer la position de la tuile
    public List<Integer> getTilePosition(){
        List<Integer> position = new ArrayList<Integer>();
        position.add(m_x);
        position.add(m_y);
        return position;
    };



    //Changer valeur de isOnBoard
    public void setM_isOnBoard(boolean isOnBoard){
        this.m_isOnBoard = isOnBoard;
    }

    //Fonction pour placer une tuile si canPut est vrai
    public void putOnBoard(int x, int y){

    }


    //METHODES ABSTRAITES

    //Changer les coordonnées de la tuile
    public abstract void updatePosition();

    //Fonction pour déterminer si la tuile peut être placée à un endroit
    public abstract boolean canPut(int x, int y);

    //Fonction pour déterminer si la tuile peut se déplacer
    public abstract boolean canMove();


}
