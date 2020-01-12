package PLAYERS;


import GAME.Board;
import GAME.Color;
import TILES.Turtle;

//Sous-classe pour joueur Vert (Joueur 2)
public final class GreenPlayer extends Player{

    public GreenPlayer(final Board board) {
        super(board);
        this.m_turtle = setM_turtle();
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
