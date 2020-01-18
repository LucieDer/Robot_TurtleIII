package GUI;

import Engine.GAME.Board;
import Engine.GAME.BoardUtils;
import Engine.GAME.Move;
import Engine.GAME.Square;
import Engine.PLAYERS.MoveTransition;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
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
    private BoardPanel boardPanel;
    private AllHands allHandsPanel;
    private OrientationPanel orientationPanel;
    private ActionPanel actionPanel;

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




    public ActionPanel getActionPanel(){
        return this.actionPanel;
    }

    public AllHands getAllHandsPanel() {
        return allHandsPanel;
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public OrientationPanel getOrientationPanel() {
        return orientationPanel;
    }

    public Board getRTBoard() {
        return RTBoard;
    }

    public void newGame(int nbOfPlayer){
        RTBoard = Board.createStandardBoard(nbOfPlayer);
        this.actionPanel.redo(RTBoard);
        this.allHandsPanel.getProgramPanel().redo(RTBoard);
        this.allHandsPanel.getHandCardPanel().redo(RTBoard);
        this.orientationPanel.reDraw(RTBoard);
        this.boardPanel.drawBoard(RTBoard);
    }

    private JMenuBar createMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu optionMenu = new JMenu("Options");

        final JMenuItem resetMenuItem = new JMenuItem("Nouvelle Partie", KeyEvent.VK_P);
        resetMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                newGame(RTBoard.getM_nbOfPlayers());
            }

        });
        optionMenu.add(resetMenuItem);



        final ButtonGroup nbOfPlayerButtons = new ButtonGroup();

        final JRadioButtonMenuItem twoPlayersRadioButton = new JRadioButtonMenuItem("2 Joueurs");
        twoPlayersRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        optionMenu.add(twoPlayersRadioButton);

        final JRadioButtonMenuItem threePlayersRadioButton = new JRadioButtonMenuItem("3 Joueurs");
        threePlayersRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame(3);
            }
        });
        optionMenu.add(threePlayersRadioButton);

        final JRadioButtonMenuItem fourPlayersRadioButton = new JRadioButtonMenuItem("4 Joueurs");
        fourPlayersRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame(4);
            }
        });
        optionMenu.add(fourPlayersRadioButton);


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

    public void setRTBoard(Board transitionBoard) {
        this.RTBoard = transitionBoard;
    }


    //Le Plateau
    public class BoardPanel extends JPanel{
        final List<SquarePanel> boardSquares;

        BoardPanel(){
            super(new GridLayout(8,8));
            this.boardSquares = new ArrayList<>();
            for(int i = 0; i< BoardUtils.NUM_SQUARE; i++){
                final SquarePanel squarePanel = new SquarePanel(this, i+1);
                this.boardSquares.add(squarePanel);
                add(squarePanel);
            }

            this.setBorder(new LineBorder(Color.BLACK));

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

    public class OrientationPanel extends JPanel {
        private int currentOrientation;
        private int nbInDeck;
        private int nbInDiscarding;
        private JLabel label;
        private JLabel deck;
        private JLabel discarding;
        private final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

        OrientationPanel(){
            super(new BorderLayout());
            setBackground(Color.decode("#05ffa1"));
            setBorder(PANEL_BORDER);
            this.currentOrientation = RTBoard.getCurrentPlayer().getM_turtle().getM_currentOrientation();
            this.nbInDeck = RTBoard.getCurrentPlayer().getM_deckCards().getAmount();
            this.nbInDiscarding = RTBoard.getCurrentPlayer().getM_deckCards().m_discarding.getAmount();

            this.label = new JLabel("Orientation :\n");
            this.label.setFont(new Font("Verdana",1,10));
            add(this.label, BorderLayout.NORTH);

            this.label = new JLabel( orientationToString());
            this.label.setFont(new Font("Verdana",1,18));
            add(this.label, BorderLayout.PAGE_START);

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

        public void reDraw(Board board){
            removeAll();
            setBackground(Color.decode("#05ffa1"));
            setBorder(PANEL_BORDER);
            this.currentOrientation = board.getCurrentPlayer().getM_turtle().getM_currentOrientation();
            this.nbInDeck = board.getCurrentPlayer().getM_deckCards().getAmount();
            this.nbInDiscarding = board.getCurrentPlayer().getM_deckCards().m_discarding.getAmount();

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
                    //Clic droit pour annuler
                    if(isRightMouseButton(e)){
                        destinationSquare = null;
                        RTBoard.setMovedObstacle(null);

                    } else if(isLeftMouseButton(e)){
                        if(RTBoard.getMovedObstacle() != null){
                            destinationSquare = RTBoard.getSquare(BoardUtils.convertIntoXYPosition(squareId));
                            //Si on clique sur un carré occupé par une pièce, on annule le clic
                            if(destinationSquare.isSquareOccupied()){
                                destinationSquare = null;
                            } else {

                                final Move.PutObstacle movePutObstacle = new Move.PutObstacle(RTBoard, RTBoard.getMovedObstacle(), destinationSquare.getPosition());
                                if(!movePutObstacle.isMoveIllegal()){
                                    final MoveTransition transition = RTBoard.getCurrentPlayer().makeMove(movePutObstacle);
                                    if(transition.getMoveStatus().isDone()){
                                        RTBoard = transition.getTransitionBoard();
                                        RTBoard.setTurnFinished(true);
                                    }
                                }
                                destinationSquare = null;
                                RTBoard.setMovedObstacle(null);
                            }
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    actionPanel.redo(RTBoard);
                                    allHandsPanel.getProgramPanel().redo(RTBoard);
                                    allHandsPanel.getHandCardPanel().redo(RTBoard);
                                    orientationPanel.reDraw(RTBoard);
                                    boardPanel.drawBoard(RTBoard);
                                    RTBoard.setTurnFinished(true);
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
            boolean isLight = false;
            if(BoardUtils.convertIntoXYPosition(squareId).get(0) == RTBoard.getCurrentPlayer().getM_turtle().getTilePosition().get(0) &&
                    BoardUtils.convertIntoXYPosition(squareId).get(1) == RTBoard.getCurrentPlayer().getM_turtle().getTilePosition().get(1)){
                setBackground(Color.decode("#ff0045"));
                return;
            }
            else if(squareId%8 == 0) {
                int columnId = BoardUtils.convertIntoXYPosition(squareId).get(0);
                isLight = (columnId%2 == 0);
            } else  isLight = ((squareId + squareId / 8) % 2 == 0);
            setBackground(isLight ? lightTileColor : darkTileColor);
        }
    }






    }









