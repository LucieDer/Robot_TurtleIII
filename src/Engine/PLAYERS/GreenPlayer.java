package Engine.PLAYERS;


import Engine.GAME.Board;
import Engine.GAME.Color;
import Engine.TILES.Turtle;

//Sous-classe pour joueur Vert (Joueur 2)
public final class GreenPlayer extends Player{

    public GreenPlayer(final Board board) {
        super(board);
        this.m_turtle = setM_turtle();
        this.m_deckCards = board.m_GreenPlayerDeckCards;
        this.m_handCards = board.m_GreenPlayerHandCards;
        this.m_handObstacles = board.m_GreenPlayerHandObstacles;
        this.m_program = board.m_GreenPlayerProgram;
    }

    @Override
    public Color getColor() {
        return Color.VERT;
    }

    @Override
    public Turtle setM_turtle(){
        return (Turtle)m_board.getGreenTurtle();
    }

    @Override
    public Player getNextPlayer() {
        if(this.m_board.getM_nbOfPlayers() == 2){
            return (RedPlayer)this.m_board.getRedPlayer();
        }else return (PurplePlayer)this.m_board.getPurplePlayer();
    }
}
