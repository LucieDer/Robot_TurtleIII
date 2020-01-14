package Engine.PLAYERS;

import Engine.GAME.Board;
import Engine.GAME.Color;
import Engine.TILES.Turtle;

//Sous-classe pour joueur rouge (Joueur 1)
public final class RedPlayer extends Player{

    public RedPlayer(final Board board) {
        super(board);
        this.m_turtle = setM_turtle();
        this.m_deckCards = board.m_RedPlayerDeckCards;
        this.m_handCards = board.m_RedPlayerHandCards;
        this.m_handObstacles = board.m_RedPlayerHandObstacles;
        this.m_program = board.m_RedPlayerProgram;
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