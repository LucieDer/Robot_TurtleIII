package Engine.PLAYERS;

public enum MoveStatus {
    DONE {
        @Override
        public boolean isDone() {
            return true;
        }
    },
    ILLEGAL_MOVE {
        @Override
        public boolean isDone() {
            return false;
        }
    },
    IS_IN_PROGRESS{
        @Override
        public boolean isDone() {
            return false;
        }
    };


    public abstract boolean isDone();
}
