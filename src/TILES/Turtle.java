package TILES;

import GAME.Color;

import java.util.ArrayList;
import java.util.List;

import static TILES.Type.TORTUE;

public class Turtle extends Tile {
    private int m_orientation;
    private int m_initialX;
    private final int m_initialY;
    private final int m_initialOrientation;
    private Color m_color;
    private boolean m_isOnJewel;
    private final Type m_type = TORTUE;


    public Turtle(boolean isOnBoard, Color color, int xInitial, int yInitial, int orientationInitial){
        super(isOnBoard, TORTUE);
        this.m_color = color;
        this.m_x = xInitial;
        this.m_y = yInitial;
        this.m_orientation = orientationInitial;
        this.m_initialX = xInitial;
        this.m_initialY = yInitial;
        this.m_initialOrientation = orientationInitial;
    }


    //Savoir si la tortue est sur un joyau
    public boolean isM_isOnJewel() {
        return m_isOnJewel;
    }

    //Accéder à la position actuelle
    public List<Integer> getPosition(){
        List<Integer> position = new ArrayList<>();
        position.add(m_x);
        position.add(m_y);
        return position;
    }

    //Accéder à l'orientation actuelle
    public int getM_orientation(){
        return m_orientation;
    }


    //Accéder à la position initiale
    public List<Integer> getInitialPosition(){
        List<Integer> initialPosition = new ArrayList<>();
        initialPosition.add(m_initialX);
        initialPosition.add(m_initialY);
        return initialPosition;
    }

    //Accéder à l'orientation initiale
    public int getM_initialOrientation(){
        return this.m_initialOrientation;
    }

    //Accéder à la couleur de la tortue
    public Color getM_color() {
        return m_color;
    }


    //TODO
    @Override
    public boolean canPut(int x, int y) {
        return false;
    }

    @Override
    public boolean canMove() {
        return false;
    }

    @Override
    public void updatePosition() {

    }
}
