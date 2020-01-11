package TILES;

import GAME.Color;

import static TILES.Type.JOYAU;

public class Jewel extends Tile {

    private final Color m_color;
    private final Type m_type = JOYAU;

    public Jewel(boolean isOnBoard, Color color, int x, int y ){
        super(isOnBoard, JOYAU);
        this.m_color = color;
        this.m_x = x;
        this.m_y = y;
    }

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
