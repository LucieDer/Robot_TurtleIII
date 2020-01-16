package GUI;

import Engine.GAME.Board;
import Engine.GAME.BoardUtils;
import Engine.GAME.Move;
import Engine.PLAYERS.MoveTransition;

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
    //private final String currentSatus;

    ActionPanel(Table table){
        super(new FlowLayout(CENTER, 13,12));

        this.nbStoneWallCard = table.getRTBoard().getCurrentPlayer().getM_HandObstacles().getStoneAmount();
        this.nbIceWallCard = table.getRTBoard().getCurrentPlayer().getM_HandObstacles().getIceAmount();
        this.currentPlayer = "Joueur " + table.getRTBoard().getCurrentPlayer().getColor().getColor();


        JLabel jlabel = new JLabel(currentPlayer);
        jlabel.setFont(new Font("Verdana",1,20));
        this.add(jlabel);
        this.setBorder(new LineBorder(Color.BLACK));

        JButton programButton = new JButton("Ajouter une carte au programme");
        this.add(programButton);

        JButton executeButton = new JButton("Exécuter le programme");
        this.add(executeButton);

        JButton stoneWallButton = new JButton("Ajouter un mur de pierre (" + nbStoneWallCard + ")");
        this.add(stoneWallButton);

        JButton iceWallButton = new JButton("Ajouter un mur de glace (" + nbIceWallCard + ")");
        this.add(iceWallButton);

        JButton passButton = new JButton("Passer le tour");
        this.add(passButton);




        //Bouton ajouter une carte au programme
        programButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //POSER OBSTACLE
                if(isRightMouseButton(e)){
                    table.getRTBoard().setAddingCard(false);
                } else if(isLeftMouseButton(e)){
                    if(!table.getRTBoard().isAddingCard()){
                        table.getRTBoard().setAddingCard(true);

                    }

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




        //Bouton ajouter un mur de Glace :
        iceWallButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {

                //POSER OBSTACLE
                if(isRightMouseButton(e)){
                    table.getRTBoard().setMovedObstacle(null);
                } else if(isLeftMouseButton(e)){
                    if(table.getRTBoard().getMovedObstacle() == null){
                        table.getRTBoard().setMovedObstacle(table.getRTBoard().getCurrentPlayer().getM_HandObstacles().getIceWall());

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


        //Bouton ajouter un mur de Pierre :
        stoneWallButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {

                //POSER OBSTACLE
                if(isRightMouseButton(e)){
                    table.getRTBoard().setMovedObstacle(null);
                } else if(isLeftMouseButton(e)){
                    if(table.getRTBoard().getMovedObstacle() == null){
                        table.getRTBoard().setMovedObstacle(table.getRTBoard().getCurrentPlayer().getM_HandObstacles().getStoneWall());

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



        setPreferredSize(BOARD_PANEL_DIMENSION);


        validate();
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

        JButton programButton = new JButton("Ajouter une carte au programme");
        this.add(programButton);

        JButton executeButton = new JButton("Exécuter le programme");
        this.add(executeButton);

        JButton stoneWallButton = new JButton("Ajouter un mur de pierre (" + nbStoneWallCard + ")");
        this.add(stoneWallButton);

        JButton iceWallButton = new JButton("Ajouter un mur de glace (" + nbIceWallCard + ")");
        this.add(iceWallButton);

        JButton passButton = new JButton("Passer le tour");
        this.add(passButton);




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


        //Bouton ajouter un obstacle :
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







        setPreferredSize(BOARD_PANEL_DIMENSION);


        validate();
    }
}
