package GAME;

/*
    Classe pour définir les différents types de cartes

    Utile pour Tortues, cartes, joyaux
 */

import PLAYERS.*;

public enum Color {
    BLEU("Bleu"){
        @Override
        public boolean isBlue()  {
            return true;
        }

        @Override
        public boolean isGreen() {
            return false;
        }

        @Override
        public boolean isPurple() {
            return false;
        }

        @Override
        public boolean isRed() {
            return false;
        }

        @Override
        public boolean isLaser() {
            return false;
        }

        @Override
        public Player choosePlayer(int nbOfPlayer, RedPlayer redPlayer, GreenPlayer greenPlayer, PurplePlayer purplePlayer, BluePlayer bluePlayer) {
            return bluePlayer;
        }


    },

    VERT("Vert"){
        @Override
        public boolean isBlue()  {
            return false;
        }

        @Override
        public boolean isGreen() {
            return true;
        }

        @Override
        public boolean isPurple() {
            return false;
        }

        @Override
        public boolean isRed() {
            return false;
        }

        @Override
        public boolean isLaser() {
            return false;
        }

        @Override
        public Player choosePlayer(int nbOfPlayer, RedPlayer redPlayer, GreenPlayer greenPlayer, PurplePlayer purplePlayer, BluePlayer bluePlayer) {
            return greenPlayer;
        }

    },
    VIOLET("Violet") {
        @Override
        public boolean isBlue() {
            return false;
        }

        @Override
        public boolean isGreen() {
            return false;
        }

        @Override
        public boolean isPurple() {
            return true;
        }

        @Override
        public boolean isRed() {
            return false;
        }

        @Override
        public boolean isLaser() {
            return false;
        }

        @Override
        public Player choosePlayer(int nbOfPlayer, RedPlayer redPlayer, GreenPlayer greenPlayer, PurplePlayer purplePlayer, BluePlayer bluePlayer) {
            return purplePlayer;
        }


    },
    ROUGE("Rouge") {
        @Override
        public boolean isBlue() {
            return false;
        }

        @Override
        public boolean isGreen() {
            return false;
        }

        @Override
        public boolean isPurple() {
            return false;
        }

        @Override
        public boolean isRed() {
            return false;
        }

        @Override
        public boolean isLaser() {
            return false;
        }

        @Override
        public Player choosePlayer(int nbOfPlayer, RedPlayer redPlayer, GreenPlayer greenPlayer, PurplePlayer purplePlayer, BluePlayer bluePlayer) {
            return redPlayer;
        }


    }; //Pour les cartes = Laser

    private final String colorValue;

    // Constructeur :
    Color(final String colorValue){
        this.colorValue = colorValue;
    }

    // Méthode Publique
    public String getColor(){
        return colorValue;
    }

    public abstract boolean isBlue() ;

    public abstract boolean isGreen();

    public abstract boolean isPurple();

    public abstract boolean isRed();

    public abstract boolean isLaser();

    public abstract Player choosePlayer(int nbOfPlayer, RedPlayer redPlayer, GreenPlayer greenPlayer, PurplePlayer purplePlayer, BluePlayer bluePlayer);

}