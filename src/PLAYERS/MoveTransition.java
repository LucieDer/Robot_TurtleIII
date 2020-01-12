package PLAYERS;

/* Classe pour faire la transition entre les plateaux
 *
 */

import GAME.Board;
import GAME.Move;

public class MoveTransition {

    private final Board transitionBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    public MoveTransition(Board transitionBoard, Move move, MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public MoveStatus getMoveStatus() {
        return moveStatus;
    }
}
