package GUI;

import Engine.CARDS.Card;
import Engine.GAME.Board;
import Engine.GAME.BoardUtils;
import Engine.GAME.Move;
import Engine.PLAYERS.MoveTransition;
import Engine.TILES.Obstacles.IceWall;
import Engine.TILES.Tile;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.awt.FlowLayout.CENTER;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class ActionPanel extends JPanel {

    private final Dimension BOARD_PANEL_DIMENSION = new Dimension(300, 400);
    private int nbStoneWallCard;
    private int nbIceWallCard;
    private String currentPlayer;
    private Board newBoard;
    private Table table;
    //private final String currentSatus;

    ActionPanel(Table table){
        super(new FlowLayout(CENTER, 13,12));
        this.table = table;
        this.nbStoneWallCard = table.getRTBoard().getCurrentPlayer().getM_HandObstacles().getStoneAmount();
        this.nbIceWallCard = table.getRTBoard().getCurrentPlayer().getM_HandObstacles().getIceAmount();
        this.currentPlayer = "Joueur " + table.getRTBoard().getCurrentPlayer().getColor().getColor();


        JLabel jlabel = new JLabel(currentPlayer);
        jlabel.setFont(new Font("Verdana",1,20));
        this.add(jlabel);
        this.setBorder(new LineBorder(Color.BLACK));

        this.add(new AddToProgramButton(table.getRTBoard(), table));

        this.add(new ExecuteButton(table.getRTBoard()));

        this.add(new StoneWallButton(table.getRTBoard()));

        this.add(new IceWallButton(table.getRTBoard()));

        this.add(new FinishButton(table.getRTBoard()));

        this.add(new TurnStatus(table.getRTBoard()));


        setPreferredSize(BOARD_PANEL_DIMENSION);


        validate();
    }







    //SOUS CLASSES POUR LES BOUTONS


    //Bouton pour exécuter le programme
    private class ExecuteButton extends JPanel {
        ExecuteButton(Board board) {
            super();
            JButton executeButton = new JButton("Executer le programme");
            this.add(executeButton);

            executeButton.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (isLeftMouseButton(e)) {
                        if (!board.isFinished() && !board.isTurnFinished()) {
                        }

                        executeProgram(table.getRTBoard());
                        table.getRTBoard().setTurnFinished(true);
                    }
                }


                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

        }

        public void executeProgram(Board board) {
            //Vérifier que le joueur n'a pas appuyé sur le bouton pour finir le tour

            if (board.getCurrentPlayer().getM_program().getCards().size() > 0) {
                //Prendre la 1ere carte dans le programme
                Card card = board.getCurrentPlayer().getM_program().getCards().remove(0);
                board.getCurrentPlayer().getM_deckCards().addToDiscard(card);

                table.getAllHandsPanel().getProgramPanel().redo(table.getRTBoard());
                table.getOrientationPanel().reDraw(table.getRTBoard());

                //Carte bleue = avancer
                if (card.getColor() == "Bleu") {
                    final Move.TurtleGoForward goForward = new Move.TurtleGoForward(board, board.getCurrentPlayer().getM_turtle());

                    final MoveTransition transition = board.getCurrentPlayer().makeMove(goForward);
                    //execute le 1er mouvement
                    table.setRTBoard(transition.getTransitionBoard());


                    //Si tortue rencontre une autre tortue : créer un deuxième mouvment pour la 2eme tortue
                    if(goForward.isAnotherTurtle()) {
                        //Récupère 2eme tortue
                        final Tile otherTurtle = transition.getTransitionBoard().getSquare(goForward.getDestinationCoordinate()).getTile();
                        //Crée mouvement de retour à la case départ
                        final Move.TurtleGoToInitialPosition otherTurtleGoToInitialPosition = new Move.TurtleGoToInitialPosition(transition.getTransitionBoard(), otherTurtle);
                        //Bouge la deuxième tortue
                        final MoveTransition secondTransition = transition.getTransitionBoard().getCurrentPlayer().makeMove(otherTurtleGoToInitialPosition);
                        //execute le mouvement de la deuxième tortue sur le plateau
                        table.setRTBoard(secondTransition.getTransitionBoard());
                        //Redessine le plateau, les cartes et le panel d'info
                        table.getAllHandsPanel().getProgramPanel().redo(table.getRTBoard());
                        table.getAllHandsPanel().getHandCardPanel().redo(table.getRTBoard());
                        table.getOrientationPanel().reDraw(table.getRTBoard());
                        table.getBoardPanel().drawBoard(table.getRTBoard());

                    }else{
                        table.getAllHandsPanel().getProgramPanel().redo(table.getRTBoard());
                        table.getAllHandsPanel().getHandCardPanel().redo(table.getRTBoard());
                        table.getOrientationPanel().reDraw(table.getRTBoard());
                        table.getBoardPanel().drawBoard(table.getRTBoard());
                    }


                    //Carte Verte = tourner à gauche
                } else if (card.getColor() == "Vert") {
                    final Move.TurtleTurnLeft turnLeft = new Move.TurtleTurnLeft(board, board.getCurrentPlayer().getM_turtle());
                    final MoveTransition transition = board.getCurrentPlayer().makeMove(turnLeft);
                    table.setRTBoard(transition.getTransitionBoard());
                    //Tout redessiner
                    table.getAllHandsPanel().getProgramPanel().redo(table.getRTBoard());
                    table.getAllHandsPanel().getHandCardPanel().redo(table.getRTBoard());
                    table.getOrientationPanel().reDraw(table.getRTBoard());
                    table.getBoardPanel().drawBoard(table.getRTBoard());

                    //Carte Violette = tourner à droite
                } else if (card.getColor() == "Violet") {
                    final Move.TurtleTurnRight turnRight = new Move.TurtleTurnRight(board, board.getCurrentPlayer().getM_turtle());
                    final MoveTransition transition = board.getCurrentPlayer().makeMove(turnRight);
                    table.setRTBoard(transition.getTransitionBoard());
                    //Tout redessiner
                    table.getAllHandsPanel().getProgramPanel().redo(table.getRTBoard());
                    table.getAllHandsPanel().getHandCardPanel().redo(table.getRTBoard());
                    table.getOrientationPanel().reDraw(table.getRTBoard());
                    table.getBoardPanel().drawBoard(table.getRTBoard());
                }
                //Carte Rouge = Laser
                else if(card.getColor() == "Rouge"){
                    final Move.TurtleLaser laser = new Move.TurtleLaser(board, board.getCurrentPlayer().getM_turtle());

                    if(laser.isTurtle()){

                        if(table.getRTBoard().getM_nbOfPlayers() == 2){
                            //1er mouvement 1/2 tour pour la 1ere tortue
                            final Move.TurtleTurnabout firstTurtleTurnabout = new Move.TurtleTurnabout(board, board.getCurrentPlayer().getM_turtle());

                            //on récupère la 2eme tortue et on crée son mouvement
                            final Tile otherTurtle = board.getSquare(laser.getDestinationCoordinate()).getTile();
                            final Move.TurtleTurnabout secondTurtleTurnabout = new Move.TurtleTurnabout(board, otherTurtle);

                            //On effectue les 2 mouvements
                            final MoveTransition firstTransition = board.getCurrentPlayer().makeMove(firstTurtleTurnabout);
                            final MoveTransition secondTransition = firstTransition.getTransitionBoard().getCurrentPlayer().makeMove(secondTurtleTurnabout);

                            //On redéfinit le plateau
                            table.setRTBoard(secondTransition.getTransitionBoard());
                            //Tout redessiner

                        }else{
                            //1er mouvement 1/2 tour pour la 1ere tortue
                            final Move.TurtleGoToInitialPosition firstTurtleGoBack = new Move.TurtleGoToInitialPosition(board, board.getCurrentPlayer().getM_turtle());

                            //on récupère la 2eme tortue et on crée son mouvement
                            final Tile otherTurtle = board.getSquare(laser.getDestinationCoordinate()).getTile();
                            final Move.TurtleGoToInitialPosition secondTurtleGoBack = new Move.TurtleGoToInitialPosition(board, otherTurtle);

                            //On effectue les 2 mouvements
                            final MoveTransition firstTransition = board.getCurrentPlayer().makeMove(firstTurtleGoBack);
                            final MoveTransition secondTransition = firstTransition.getTransitionBoard().getCurrentPlayer().makeMove(secondTurtleGoBack);

                            //On redéfinit le plateau
                            table.setRTBoard(secondTransition.getTransitionBoard());
                        }
                    }else if(laser.isJewel()){
                        if(table.getRTBoard().getM_nbOfPlayers() == 2){
                            //1er mouvement 1/2 tour pour la 1ere tortue
                            final Move.TurtleTurnabout turnabout = new Move.TurtleTurnabout(board, board.getCurrentPlayer().getM_turtle());

                            final MoveTransition firstTransition = board.getCurrentPlayer().makeMove(turnabout);

                            //On redéfinit le plateau
                            table.setRTBoard(firstTransition.getTransitionBoard());
                        }else{
                            final Move.TurtleTurnabout goBack = new Move.TurtleTurnabout(board, board.getCurrentPlayer().getM_turtle());
                            final MoveTransition firstTransition = board.getCurrentPlayer().makeMove(goBack);

                            //On redéfinit le plateau
                            table.setRTBoard(firstTransition.getTransitionBoard());
                        }
                    }else if(laser.isIceWall()){
                        //On accède au mur et dit qu'il n'est plus sur le plateau
                        IceWall newIceWall = (IceWall)board.getSquare(laser.getDestinationCoordinate()).getTile();
                        newIceWall.setM_isOnBoard(false);
                        //on l'ajoute à la pioche des obstacles
                        //board.getDeckObstacles().add(newIceWall);
                        //On supprime le mur de glace sur le plateau
                        table.getRTBoard().getActiveTiles().remove(board.getSquare(laser.getDestinationCoordinate()).getTile());

                    }
                    table.getAllHandsPanel().getProgramPanel().redo(table.getRTBoard());
                    table.getAllHandsPanel().getHandCardPanel().redo(table.getRTBoard());
                    table.getOrientationPanel().reDraw(table.getRTBoard());
                    table.getBoardPanel().drawBoard(table.getRTBoard());

                }

                executeProgram(table.getRTBoard());
                table.getAllHandsPanel().getProgramPanel().redo(table.getRTBoard());
                table.getAllHandsPanel().getHandCardPanel().redo(table.getRTBoard());
                table.getOrientationPanel().reDraw(table.getRTBoard());
                table.getBoardPanel().drawBoard(table.getRTBoard());

            }
        }

    }

    //Bouton pour finir le tour
    private class FinishButton extends JPanel{
        FinishButton(Board board){
            super();
            JButton passButton = new JButton("Finir le tour");
            this.add(passButton);

            //Bouton Finir le tour :
            passButton.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //FINIR LE TOUR
                    if(isRightMouseButton(e)){
                        board.setFinished(false);
                    } else if (isLeftMouseButton(e)) {
                        if (!board.isFinished()) {
                            board.setFinished(true);

                        } else if (board.isFinished()) {
                            //Le joueur pioche
                            while(board.getCurrentPlayer().getM_handCards().getAmount()<5){
                                //Vérifie si la pioche a moins de 5 cartes, si oui elle redistribue depuis la défausse
                                if(board.getCurrentPlayer().getM_deckCards().getAmount() < 5){
                                    board.getCurrentPlayer().getM_deckCards().repopulateFromDiscarding();
                                }
                                board.getCurrentPlayer().getM_deckCards().deal(board.getCurrentPlayer().getM_handCards(), 1);
                            }

                            final Move.NullMove nullMove = new Move.NullMove(board, board.getCurrentPlayer().getM_turtle());
                            final MoveTransition transition = board.getCurrentPlayer().makeMove(nullMove);
                            table.setRTBoard(transition.getTransitionBoard());

                            if (transition.getMoveStatus().isDone()) {
                                //redo l'action panel

                                //redo le AllHands
                                //redo le log
                            }
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                table.getActionPanel().redo(table.getRTBoard());
                                table.getAllHandsPanel().getProgramPanel().redo(table.getRTBoard());
                                table.getAllHandsPanel().getHandCardPanel().redo(table.getRTBoard());
                                table.getOrientationPanel().reDraw(table.getRTBoard());
                                table.getBoardPanel().drawBoard(table.getRTBoard());

                            }
                        });

                    }

                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {

                }

            });
        }
    }




    //Bouton pour ajouter une carte au programme
    private class AddToProgramButton extends JPanel{
        AddToProgramButton(Board board, Table table){
            super();
            JButton programButton = new JButton("Ajouter des cartes au programme");
            this.add(programButton);
            //Bouton ajouter une carte au programme
            programButton.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //AJOUTER AU PROGRAMME
                    if(isRightMouseButton(e)){
                        board.setAddingCard(false);
                    } else if(isLeftMouseButton(e)){
                        if(!board.isAddingCard() && !board.isTurnFinished() && !board.isFinished()){
                            if(!board.isAddingCard()){
                                board.setAddingCard(true);
                            }else{
                                board.setAddingCard(false);
                            }

                        }

                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                table.getActionPanel().redo(board);
                                table.getAllHandsPanel().getProgramPanel().redo(board);
                                table.getAllHandsPanel().getHandCardPanel().redo(board);
                                table.getOrientationPanel().reDraw(board);
                                table.getBoardPanel().drawBoard(board);
                                table.getRTBoard().setTurnFinished(true);
                            }
                        });

                    }

                }


                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
    }



    //Bouton pour ajouter un mur de pierre
    private class StoneWallButton extends JPanel{
        int nbOfStoneWall;
        StoneWallButton(Board board){
            super();
            this.nbOfStoneWall = board.getCurrentPlayer().getM_HandObstacles().getStoneAmount();
            JButton stoneWallButton = new JButton("Ajouter un mur de pierre (" + nbOfStoneWall + ")");
            this.add(stoneWallButton);
            //Bouton ajouter un mur de Pierre :
            stoneWallButton.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {

                    //POSER OBSTACLE
                    if(isRightMouseButton(e)){
                        board.setMovedObstacle(null);
                    } else if(isLeftMouseButton(e)){
                        if(board.getMovedObstacle() == null && !board.isTurnFinished() && !board.isFinished()){
                            board.setMovedObstacle(board.getCurrentPlayer().getM_HandObstacles().getStoneWall());

                        }

                    }

                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });

        }
    }



    //Bouton pour ajouter un mur de glace
    private class IceWallButton extends JPanel{
        int nbOfIceWall;
        IceWallButton(Board board){
            super();
            this.nbOfIceWall = board.getCurrentPlayer().getM_HandObstacles().getIceAmount();
            JButton iceWallButton = new JButton("Ajouter un mur de glace (" + nbIceWallCard + ")");
            this.add(iceWallButton);
            //Bouton ajouter un mur de Glace :
            iceWallButton.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {

                    //POSER OBSTACLE
                    if(isRightMouseButton(e)){
                        board.setMovedObstacle(null);
                    } else if(isLeftMouseButton(e)){
                        if(board.getMovedObstacle() == null && !board.isTurnFinished() && !board.isFinished()){
                            board.setMovedObstacle(board.getCurrentPlayer().getM_HandObstacles().getIceWall());

                        }

                    }

                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });
        }
    }


    private class TurnStatus extends JPanel{
        private String currentStatus;

        TurnStatus(Board board){
            if(board.isFinished()){
                currentStatus = "         Cliquez sur les cartes à défausser";
            }else if(!board.isTurnFinished()){
                currentStatus = "    Choisissez une action";
            }else if(board.isTurnFinished()){
                currentStatus = "Tour terminé. Cliquez sur le bouton 'Finir' ";
            }

            JLabel jlabel = new JLabel(currentStatus);
            jlabel.setFont(new Font("Verdana",1,11));
            this.add(jlabel);
        }


    }















































    public void redo(Board board) {
        removeAll();

        this.nbStoneWallCard = board.getCurrentPlayer().getM_HandObstacles().getStoneAmount();
        this.nbIceWallCard = board.getCurrentPlayer().getM_HandObstacles().getIceAmount();
        this.currentPlayer = "Joueur " + board.getCurrentPlayer().getColor().getColor();


        JLabel jlabel = new JLabel(currentPlayer);
        jlabel.setFont(new Font("Verdana",1,20));
        this.add(jlabel);
        this.setBorder(new LineBorder(Color.BLACK));

        this.add(new AddToProgramButton(board, this.table));

        this.add(new ExecuteButton(board));

        this.add(new StoneWallButton(board));

        this.add(new IceWallButton(board));

        this.add(new FinishButton(board));

        this.add(new TurnStatus(board));




        setPreferredSize(BOARD_PANEL_DIMENSION);


        validate();
    }




}
