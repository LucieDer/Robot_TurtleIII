package TILES;

import GAME.Color;

import static TILES.Type.TORTUE;

public class Turtle extends Tile {
    private int m_orientation;
    private int m_xInitial;
    private final int m_yInitial;
    private final int m_orientationInitial;
    private Color m_color;
    private boolean m_isOnJewel;
    private final Type m_type = TORTUE;


    public Turtle(boolean isOnBoard, Color color, int xInitial, int yInitial, int orientationInitial){
        super(isOnBoard, TORTUE);
        this.m_color = color;
        this.m_x = xInitial;
        this.m_y = yInitial;
        this.m_orientation = orientationInitial;
        this.m_xInitial = xInitial;
        this.m_yInitial = yInitial;
        this.m_orientationInitial = orientationInitial;
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
