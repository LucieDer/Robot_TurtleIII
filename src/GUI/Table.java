package GUI;

import Engine.GAME.Board;
import Engine.GAME.BoardUtils;
import Engine.GAME.Move;
import Engine.GAME.Square;
import Engine.PLAYERS.MoveTransition;
import Engine.TILES.Obstacles.Obstacle;
import Engine.TILES.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {

    private Square sourceSquare;
    private Square destinationSquare;
    private Obstacle movedObstacle;

    private final Color lightTileColor = Color.decode("#b0e0e6");
    private final Color darkTileColor = Color.decode("#9ec9cf");
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(800,600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400,400) ;
    private static final Dimension SQUARE_PANEL_DIMENSION = new Dimension(10,10) ;
    private static String defaultPieceImagesPath = "ArtTiles/";

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final ActionPanel actionPanel;
    private Board RTBoard;

    public Table(){
        this.gameFrame = new JFrame("Robot Turtles");
        this.gameFrame.setLayout(new BorderLayout());

        //Créer la bande à dérouler en haut
        final JMenuBar tableMenuBar = createMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);

        //Crée un plateau
        this.RTBoard = Board.createStandardBoard(3);
        this.boardPanel = new BoardPanel();
        //this.boardPanel.setBounds(10, 10, 300, 300);


        //Crée partie avec les cartes
        this.actionPanel = new ActionPanel();
        //this.actionPanel.setBounds(500, 1, 10, 10);



        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.actionPanel, BorderLayout.EAST);
        //this.gameFrame.add(debugPanel, BorderLayout.SOUTH);

        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("open up that pgn file!");
            }
        });
        fileMenu.add(openPGN);

        //Créer option pour quitter
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }


    //Le Plateau
    private class BoardPanel extends JPanel{
        final List<SquarePanel> boardSquares;

        BoardPanel(){
            super(new GridLayout(8,8));
            this.boardSquares = new ArrayList<>();
            for(int i = 0; i< BoardUtils.NUM_SQUARE; i++){
                final SquarePanel squarePanel = new SquarePanel(this, i+1);
                this.boardSquares.add(squarePanel);
                add(squarePanel);
            }

            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard(Board rtBoard) {
            removeAll();
            for(final SquarePanel squarePanel : boardSquares){
                squarePanel.drawSquare(rtBoard);
                add(squarePanel);
            }
            validate();
            repaint();
        }
    }

    //Un carré individuel sur le plateau
    private class SquarePanel extends JPanel{

        private final int squareId;
        SquarePanel(final BoardPanel boardPanel, final int squareId){
            super(new GridBagLayout());
            this.squareId = squareId;
            setPreferredSize(SQUARE_PANEL_DIMENSION);
            assignSquareColor();
            assignSquareTileIcon(RTBoard);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {

                    //POSER OBSTACLE
                    if(isRightMouseButton(e)){
                        destinationSquare = null;
                        movedObstacle = null;

                    } else if(isLeftMouseButton(e)){
                        if(movedObstacle != null){
                            destinationSquare = RTBoard.getSquare(BoardUtils.convertIntoXYPosition(squareId));
                            //Si on clique sur un carré occupé par une pièce, on annule le clic
                            if(destinationSquare.isSquareOccupied()){
                                destinationSquare = null;
                            } else {
                                final Move.PutObstacle movePutObstacle = new Move.PutObstacle(RTBoard, movedObstacle, destinationSquare.getTile().getTilePosition());
                                if(movePutObstacle.canPutHere()){
                                    final MoveTransition transition = RTBoard.getCurrentPlayer().makeMove(movePutObstacle);
                                    if(transition.getMoveStatus().isDone()){
                                        RTBoard = transition.getTransitionBoard();
                                    }
                                }
                                destinationSquare = null;
                                movedObstacle = null;
                            }
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    boardPanel.drawBoard(RTBoard);
                                }
                            });
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

            validate();
        }


        public void drawSquare(final Board board){
            assignSquareColor();
            assignSquareTileIcon(board);
            validate();
            repaint();
        }


        private void assignSquareTileIcon(final Board board){
            this.removeAll();;
            List<Integer> position = BoardUtils.convertIntoXYPosition(this.squareId);
            if(board.getSquare(position).isSquareOccupied()){


                if(board.getSquare(position).getTile().getType() == "Tortue"){
                    try {
                        String pathname = defaultPieceImagesPath + board.getSquare(position).getTile().getM_color().getColor() +
                                board.getSquare(position).getTile().getType() + ".png";
                        //Exemple de nom d'image : "RougeTortue.png"
                        BufferedImage image =
                                ImageIO.read(new File(defaultPieceImagesPath + board.getSquare(position).getTile().getM_color().getColor() +
                                        board.getSquare(position).getTile().getType() + ".png"));
                        image = BoardUtils.resize(image, 60, 50);
                        add(new JLabel(new ImageIcon(image)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }



                else if(board.getSquare(position).getTile().getType() == "Obstacle"){
                    try {
                        String pathname = defaultPieceImagesPath + board.getSquare(position).getTile().getM_material() + ".png";
                        //Exemple de nom d'image : "Glace.png"
                        BufferedImage image =
                                ImageIO.read(new File(defaultPieceImagesPath + board.getSquare(position).getTile().getM_material() + ".png"));
                        image = BoardUtils.resize(image, 60, 50);
                        add(new JLabel(new ImageIcon(image)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                else if (board.getSquare(position).getTile().getType() == "Joyau"){
                    try {
                        String pathname = defaultPieceImagesPath + board.getSquare(position).getTile().getType() + ".png";
                        //Exemple de nom d'image : "Joyau .png"
                        BufferedImage image =
                                ImageIO.read(new File(defaultPieceImagesPath + board.getSquare(position).getTile().getType() + ".png"));
                        image = BoardUtils.resize(image, 60, 50);
                        add(new JLabel(new ImageIcon(image)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }

        }

        private void assignSquareColor() {
            boolean isLight;
            if(squareId%8 == 0) {
                int columnId = BoardUtils.convertIntoXYPosition(squareId).get(0);
                isLight = (columnId%2 == 0);
            } else  isLight = ((squareId + squareId / 8) % 2 == 0);
            setBackground(isLight ? lightTileColor : darkTileColor);
        }
    }





}
