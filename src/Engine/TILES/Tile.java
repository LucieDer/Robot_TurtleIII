package Engine.TILES;

/*
Classe mère pour les tuiles
 */


import Engine.GAME.Color;
import Engine.GAME.Move;

import java.util.ArrayList;
import java.util.List;

public abstract class Tile {


    protected int m_x, m_y;
    protected boolean m_isOnBoard = false;
    protected final Type m_type;
    protected boolean m_canMove;
    
    protected List<Integer> m_currentPosition = new ArrayList<>();
    protected int m_currentOrientation;


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
            this.m_currentPosition.add(x);
            this.m_currentPosition.add(y);
        }
    }


    //Si la tuile n'est pas sur le plateau mais qu'elle est dans une main d'un joueur
    public Tile(boolean isOnBoard, Type type){
        m_type = type;
        m_isOnBoard = isOnBoard;
        if (!isOnBoard){
            m_x = 999;
            m_y = 999;
        }
    }





    //METHODES


    public List<Integer> getM_currentPosition() {
        return m_currentPosition;
    }

    public int getM_currentOrientation() {
        return m_currentOrientation;
    }

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

    public int getM_x() {
        return m_x;
    }

    public int getM_y() {
        return m_y;
    }

    public boolean isM_canMove() {
        return m_canMove;
    }

    public boolean isM_isOnBoard() {
        return m_isOnBoard;
    }

    public Type getM_type() {
        return m_type;
    }



    //Changer valeur de isOnBoard
    public void setM_isOnBoard(boolean isOnBoard){
        this.m_isOnBoard = isOnBoard;
    }

    //Fonction pour placer une tuile si canPut est vrai
    public void putOnBoard(int x, int y){

    }


    //METHODES ABSTRAITES

    public abstract Tile moveTile(Move move);

    //Vérifier si deux Tuiles sont égales pour ne pas comparer à la référence
    @Override
    public boolean equals(final Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof Tile)){
            return false;
        }
        final Tile otherTile = (Tile) other;
        return m_currentPosition == otherTile.getM_currentPosition() && m_type.getTypeValue().equals(otherTile.getM_type()) &&
                m_isOnBoard == otherTile.isM_isOnBoard() && m_canMove == otherTile.isM_canMove() && m_x == otherTile.getM_x() && m_y == otherTile.getM_y();
    }

    //???
    @Override
    public int hashCode(){
        int result = m_type.hashCode();
        result = 31* result + m_currentPosition.hashCode();
        result = 31* result + (m_canMove ? 1 : 0);
        result = 31* result + m_x;
        result = 31* result + m_y;
        result = 31* result + (m_isOnBoard ? 1 : 0);
        return result;
    }


    //Changer les coordonnées de la tuile
    public abstract void updatePosition();

    //Fonction pour déterminer si la tuile peut être placée à un endroit
    public abstract boolean canPut(int x, int y);

    //Fonction pour déterminer si la tuile peut se déplacer
    public abstract boolean canMove();

    //Fonction pour déterminer la couleur si la tuile en a une
    public abstract Color getM_color();

    public abstract List<Integer> getM_initialPosition();

    protected abstract int getM_initialOrientation();

    public abstract String getM_material();
}
