package Engine.PLAYERS;


import Engine.GAME.Board;
import Engine.GAME.Color;
import Engine.TILES.Turtle;

//Sous-classe pour le joueur Bleu (Joueur 4)
public final class BluePlayer extends Player{

    public BluePlayer(final Board board) {
        super(board);
        this.m_turtle = setM_turtle();
        this.m_deckCards = board.m_BluePlayerDeckCards;
        this.m_handCards = board.m_BluePlayerHandCards;
        this.m_handObstacles = board.m_BluePlayerHandObstacles;
        this.m_program = board.m_BluePlayerProgram;
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
