package PLAYERS;

import GAME.Board;
import GAME.Color;
import TILES.Turtle;

//Sous-classe pour joueur Violet (Joueur 3)
public final class PurplePlayer extends Player{

    public PurplePlayer(final Board board) {
        super(board);
        this.m_turtle = setM_turtle();
    }

    @Override
    public Color getColor() {
        return Color.VIOLET;
    }

    @Override
    public Turtle setM_turtle(){
        return (Turtle)m_board.getPurpleTurtle();
    }

    @Override
    public Player getNextPlayer() {
        if(this.m_board.getM_nbOfPlayers() == 3){
            return (RedPlayer)this.m_board.getRedPlayer();
        }else return (BluePlayer)this.m_board.getBluePlayer();
    }
}
