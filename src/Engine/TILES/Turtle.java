package Engine.TILES;

import Engine.GAME.Color;
import Engine.GAME.Move;

import java.util.ArrayList;
import java.util.List;

import static Engine.TILES.Type.TORTUE;

public class Turtle extends Tile {
    private int m_orientation;
    private int m_initialX;
    private final int m_initialY;
    private final int m_initialOrientation;
    private Color m_color;
    private boolean m_isOnJewel;
    private final Type m_type = TORTUE;

    private List<Integer> m_initialPosition;
    private List<Integer> m_currentPosition;


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

    public Turtle(boolean isOnBoard, Color color, List<Integer> initialPosition, int initialOrientation, List<Integer> currentPosition, int orientation){
        super(isOnBoard, TORTUE);
        this.m_color = color;
        this.m_x = currentPosition.get(0);
        this.m_y = currentPosition.get(1);
        this.m_initialPosition = initialPosition;
        this.m_orientation = orientation;
        this.m_initialX = initialPosition.get(0);;
        this.m_initialY = initialPosition.get(1);
        this.m_initialOrientation = initialOrientation;
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



    //Accéder à l'orientation initiale
    @Override
    public int getM_initialOrientation(){
        return this.m_initialOrientation;
    }

    @Override
    public String getM_material() {
        return null;
    }

    public Type getM_type() {
        return m_type;
    }





    //Accéder à la couleur de la tortue
    @Override
    public Color getM_color() {
        return m_color;
    }

    @Override
    public List<Integer> getM_initialPosition() {
        return this.m_initialPosition;
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


    //Va créer une nouvelle tortue, exactement comme la précédente mais avec de nuovelles coordonnées
    @Override
    public Turtle moveTile(final Move move) {
        return new Turtle(move.getM_movedTile().isM_isOnBoard(), move.getM_movedTile().getM_color(), move.getM_movedTile().getM_initialPosition(), move.getM_movedTile().getM_initialOrientation(), move.getFinalCoordinate(), move.getFinalOrientation());
    }

    @Override
    public void updatePosition() {

    }
}
