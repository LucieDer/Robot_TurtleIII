package Engine.PLAYERS;

import Engine.CARDS.DeckCards;
import Engine.CARDS.HandCards;
import Engine.CARDS.Program;
import Engine.GAME.Board;
import Engine.GAME.Color;
import Engine.GAME.Move;
import Engine.TILES.Obstacles.DeckObstacles;
import Engine.TILES.Obstacles.HandObstacles;
import Engine.TILES.Turtle;

public abstract class Player {
    protected final Board m_board;
    protected Turtle m_turtle;

    //Decks :
    protected Program m_program;
    protected DeckCards m_deckCards;
    protected HandCards m_handCards;
    protected HandObstacles m_handObstacles;
    protected boolean m_isWinner = false;



    //CONSTRUCTEUR
    /*
    m_turtle -> tortue associée au joueur
    m_program -> programme associé au joueur
    m_deckCards -> deck de cartes associé au joueur
    m_deckObstacles -> deck d'Obstacles associé au joueur
     */
    //TODO
    public Player(Board board){
        this.m_board = board;
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

    //Obtenir le deck de cartes associé au joueur
    public DeckCards getM_deckCards(){
        return m_deckCards;
    }

    //Obtenir la main de cartes du joueur
    public HandCards getM_handCards() {
        return m_handCards;
    }

    //Obtenir le deck d'obstacles associé au joueur
    public HandObstacles getM_HandObstacles() {
        return m_handObstacles;
    }



    //Savoir si le joueur a gagné
    public boolean isWinner(){
        this.m_isWinner = m_turtle.isM_isOnJewel();
        return m_isWinner;
    }


    //TODO
    //Savoir si le joueur peut faire une action
    public boolean isMoveLegal(final Move move){
        return false;
    }


    //Faire le mouvement
    public MoveTransition makeMove(final Move move) {
        //Si le mouvement est illegal, renvoie le plateau inchangé
        /*
        if(!isMoveLegal(move)){
            return new MoveTransition(this.m_board, move, MoveStatus.ILLEGAL_MOVE);
        }

         */
        final Board transitionBoard = move.execute();
        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }



    //Obtenir la couleur du joueur (celle de la tortue)
    public abstract Color getColor();

    //Définir la tortue associée au joueur
    public abstract Turtle setM_turtle();

    //Obtenir le joueur suivant
    public abstract Player getNextPlayer();

    public void setM_program(Program m_program) {
        this.m_program = m_program;
    }

    public void setM_deckCards(DeckCards deckCards){
        this.m_deckCards = deckCards;
    }

    public void setM_handCards(HandCards m_handCards) {
        this.m_handCards = m_handCards;
    }

    public void setM_handObstacles(HandObstacles m_handObstacles){
        this.m_handObstacles = m_handObstacles;
    }
}
