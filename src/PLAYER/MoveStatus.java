package PLAYER;

public enum MoveStatus {
    DONE {
        @Override
        boolean isDone() {
            return false;
        }
    };
    abstract boolean isDone();
}
