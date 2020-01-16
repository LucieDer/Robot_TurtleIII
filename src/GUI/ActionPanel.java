package GUI;

import Engine.GAME.Board;
import Engine.GAME.BoardUtils;
import Engine.GAME.Move;
import Engine.PLAYERS.MoveTransition;
import Engine.TILES.Obstacles.IceWall;

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

        JButton executeButton = new JButton("Exécuter le programme");
        this.add(executeButton);

        this.add(new StoneWallButton(table.getRTBoard()));

        this.add(new IceWallButton(table.getRTBoard()));

        this.add(new FinishButton(table.getRTBoard()));


        setPreferredSize(BOARD_PANEL_DIMENSION);


        validate();
    }



    //SOUS CLASSES POUR LES BOUTONS

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
                    //POSER OBSTACLE
                    if(isRightMouseButton(e)){
                        board.setFinished(false);
                    } else if (isLeftMouseButton(e)) {
                        if (!board.isFinished()) {
                            board.setFinished(true);

                        } else if (board.isFinished()) {
                            //Le joueur pioche
                            while(board.getCurrentPlayer().getM_handCards().getAmount()<5){
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
            JButton programButton = new JButton("Ajouter une carte au programme");
            this.add(programButton);
            //Bouton ajouter une carte au programme
            programButton.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //AJOUTER AU PROGRAMME
                    if(isRightMouseButton(e)){
                        board.setAddingCard(false);
                    } else if(isLeftMouseButton(e)){
                        if(!board.isAddingCard()){
                            board.setAddingCard(true);
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                table.getActionPanel().redo(board);
                                table.getAllHandsPanel().getProgramPanel().redo(board);
                                table.getAllHandsPanel().getHandCardPanel().redo(board);
                                table.getOrientationPanel().reDraw(board);
                                table.getBoardPanel().drawBoard(board);
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
                        if(board.getMovedObstacle() == null){
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
                        if(board.getMovedObstacle() == null){
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

        JButton executeButton = new JButton("Exécuter le programme");
        this.add(executeButton);

        this.add(new StoneWallButton(board));

        this.add(new IceWallButton(board));

        this.add(new FinishButton(board));



        setPreferredSize(BOARD_PANEL_DIMENSION);


        validate();
    }
}
