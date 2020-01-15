package GUI;

import Engine.CARDS.HandCards;
import Engine.GAME.Board;
import Engine.GAME.BoardUtils;
import Engine.GAME.Move;
import Engine.GAME.Square;
import Engine.PLAYERS.MoveTransition;
import Engine.TILES.Obstacles.Obstacle;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
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

    private int nbOfPlayer = 2;
    private final Color lightTileColor = Color.decode("#b0e0e6");
    private final Color darkTileColor = Color.decode("#9ec9cf");
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(950,800);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(300,300) ;
    private static final Dimension SQUARE_PANEL_DIMENSION = new Dimension(10,10) ;
    private static final Dimension CARDS_PANEL_DIMENSION = new Dimension(30,100) ;
    private static final Dimension SINGLE_CARD_PANEL_DIMENSION = new Dimension(400, 600);
    private static String defaultPieceImagesPath = "ArtTiles/";

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final AllHands allHandsPanel;
    private final OrientationPanel orientationPanel;
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
        this.RTBoard = Board.createStandardBoard(this.nbOfPlayer);
        this.boardPanel = new BoardPanel();
        //this.boardPanel.setBounds(10, 10, 300, 300);
        //Crée partie avec les cartes
        this.allHandsPanel = new AllHands(this);
        //Créer partie avec orientation :
        this.orientationPanel = new OrientationPanel();
        //Créer partie avec Actions
        this.actionPanel = new ActionPanel(this);

        //this.actionPanel.setBounds(500, 1, 10, 10);


        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.allHandsPanel, BorderLayout.SOUTH);
        this.gameFrame.add(this.orientationPanel, BorderLayout.WEST);
        this.gameFrame.add(this.actionPanel, BorderLayout.EAST);

        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameFrame.setVisible(true);
    }


    /*
//Constructeur pour changer le nb de joueurs
    public Table(final int nbOfPlayer){
        this.gameFrame = new JFrame("Robot Turtles");
        this.gameFrame.setLayout(new BorderLayout());

        this.nbOfPlayer = nbOfPlayer;

        //Créer la bande à dérouler en haut
        final JMenuBar tableMenuBar = createMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);

        //Crée un plateau
        this.RTBoard = Board.createStandardBoard(this.nbOfPlayer);
        this.boardPanel = new BoardPanel();
        //this.boardPanel.setBounds(10, 10, 300, 300);


        //Crée partie avec les cartes
        this.allHandsPanel = new AllHands(this);
        this.orientationPanel = new OrientationPanel();

        //this.actionPanel.setBounds(500, 1, 10, 10);


        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.allHandsPanel, BorderLayout.SOUTH);
        this.gameFrame.add(this.orientationPanel, BorderLayout.WEST);
        //this.gameFrame.add(debugPanel, BorderLayout.SOUTH);

        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameFrame.setVisible(true);
    }

     */


    public Board getRTBoard() {
        return RTBoard;
    }

    private JMenuBar createMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu optionMenu = new JMenu("Options");

        final JCheckBoxMenuItem nbOfPlayersCheckBox = new JCheckBoxMenuItem("Nombre de joueurs");
        nbOfPlayersCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        optionMenu.add(nbOfPlayersCheckBox);


        //Créer option pour quitter
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                System.exit(0);
            }
        });
        optionMenu.add(exitMenuItem);

        return optionMenu;
    }

    public void setMovedObstacle(Obstacle movedObstacle) {
        this.movedObstacle = movedObstacle;
    }


    public Obstacle getMovedObstacle() {
        return movedObstacle;
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

    private class OrientationPanel extends JPanel {
        private final int currentOrientation;
        private final int nbInDeck;
        private final int nbInDiscarding;
        private final JLabel label;
        private final JLabel deck;
        private final JLabel discarding;
        private final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

        OrientationPanel(){
            super(new BorderLayout());
            setBackground(Color.decode("#05ffa1"));
            setBorder(PANEL_BORDER);
            this.currentOrientation = RTBoard.getCurrentPlayer().getM_turtle().getM_orientation();
            this.nbInDeck = RTBoard.getCurrentPlayer().getM_deckCards().getAmount();
            this.nbInDiscarding = RTBoard.getCurrentPlayer().getM_deckCards().m_discarding.getAmount();

            this.label = new JLabel("Orientation :\n" + orientationToString());
            this.label.setFont(new Font("Verdana",1,10));
            add(this.label, BorderLayout.NORTH);

            this.deck = new JLabel("Deck :\n" + this.nbInDeck);
            this.deck.setFont(new Font("Verdana",1,10));
            add(this.deck, BorderLayout.CENTER);

            this.discarding = new JLabel("Défausse :\n" + this.nbInDiscarding);
            this.discarding.setFont(new Font("Verdana",1,10));
            add(this.discarding, BorderLayout.SOUTH);



            validate();
        }

        public int getCurrentOrientation() {
            return currentOrientation;
        }

        public String orientationToString(){
            switch(this.currentOrientation){
                case(BoardUtils.TURNED_TO_LEFT):
                    return "Gauche";
                case(BoardUtils.TURNED_TO_RIGHT):
                    return "Droite";
                case(BoardUtils.TURNED_UP):
                    return "Haut";
                case(BoardUtils.TURNED_DOWN):
                    return "Bas";
            }
            return null;
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

                                final Move.PutObstacle movePutObstacle = new Move.PutObstacle(RTBoard, movedObstacle, destinationSquare.getPosition());
                                if(movePutObstacle.isMovelegal()){
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
            this.removeAll();
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









