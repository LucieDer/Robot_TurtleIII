package Engine.TILES;

import Engine.GAME.Color;
import Engine.GAME.Move;

import java.util.List;

import static Engine.TILES.Type.JOYAU;

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
    public Color getM_color() {
        return m_color;
    }

    @Override
    public List<Integer> getM_initialPosition() {
        return null;
    }

    @Override
    protected int getM_initialOrientation() {
        return 0;
    }

    @Override
    public String getM_material() {
        return null;
    }

    @Override
    public Tile moveTile(Move move) {
        return null;
    }

    @Override
    public void updatePosition() {
    }
}
