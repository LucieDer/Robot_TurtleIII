package PLAYERS;


import GAME.Board;
import GAME.Color;
import TILES.Turtle;

//Sous-classe pour le joueur Bleu (Joueur 4)
public final class BluePlayer extends Player{

    public BluePlayer(final Board board) {
        super(board);
        this.m_turtle = setM_turtle();
    }

    @Override
    public Color getColor() {
        return Color.BLEU;
    }

    @Override
    public Turtle setM_turtle(){
        return (Turtle)m_board.getBlueTurtle();
    }

    @Override
    public RedPlayer getNextPlayer() {
        return this.m_board.getRedPlayer();
    }
}
