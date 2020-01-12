package PLAYERS;

import GAME.Board;
import GAME.Color;
import TILES.Turtle;

//Sous-classe pour joueur rouge (Joueur 1)
public final class RedPlayer extends Player{

    public RedPlayer(final Board board) {
        super(board);
        this.m_turtle = setM_turtle();
    }

    @Override
    public Color getColor() {
        return Color.ROUGE;
    }

    @Override
    public Turtle setM_turtle(){
        return (Turtle)this.m_board.getRedTurtle();
    }

    @Override
    public GreenPlayer getNextPlayer() {
        return this.m_board.getGreenPlayer();
    }
}