package PLAYER;

import CARDS.DeckCards;
import CARDS.Program;
import GAME.Board;
import GAME.Move;
import TILES.Obstacles.DeckObstacles;
import TILES.Turtle;

public class Player {
    private final Board m_board;
    private final Turtle m_turtle;
    private Program m_program;
    private DeckCards m_deckCards;
    private DeckObstacles m_deckObstacles;
    private boolean m_isWinner = false;


    //CONSTRUCTEUR
    /*
    m_turtle -> tortue associée au joueur
    m_program -> programme associé au joueur
    m_deckCards -> deck de cartes associé au joueur
    m_deckObstacles -> deck d'Obstacles associé au joueur
     */
    //TODO
    public Player(Board board, final Turtle turtle){
        this.m_board = board;
        this.m_turtle = turtle;
    }

    //Obtenir la tortue associée au joueur
    public Turtle getM_turtle(){
        return m_turtle;
    }

    //Obtenir le plateau
    public Board getM_Board() {
        return this.m_board;
    }

    //Obtenir le programme associé au joueur
    public Program getM_program(){
        return m_program;
    }

    //Obtenir le deck de cartesassocié au joueur
    public DeckCards getM_deckCards(){
        return m_deckCards;
    }

    //Obtenir le deck d'obstacles associé au joueur
    public DeckObstacles getM_deckObstacles() {
        return m_deckObstacles;
    }

    //Savoir si le joueur a gagné
    public boolean isWinner(){
        this.m_isWinner = m_turtle.isM_isOnJewel();
        return m_isWinner;
    }


    public MoveTransition makeMove(final Move move) {
        return null;
    }


}
