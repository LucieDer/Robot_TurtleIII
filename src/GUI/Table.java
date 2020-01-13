package GUI;

import Engine.GAME.Board;
import Engine.GAME.BoardUtils;
import Engine.TILES.Obstacles.Obstacle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Table {

    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(900,900);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400,350) ;
    private static final Dimension SQUARE_PANEL_DIMENSION = new Dimension(10,10) ;
    private static String defaultPieceImagesPath = "ArtTiles/";

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final Board RTBoard;

    public Table(){
        this.gameFrame = new JFrame("Robot Turtles");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.RTBoard = Board.createStandardBoard(2);
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
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
            validate();
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
                        final BufferedImage image =
                                ImageIO.read(new File(defaultPieceImagesPath + board.getSquare(position).getTile().getM_color().getColor() +
                                        board.getSquare(position).getTile().getType() + ".png"));
                        add(new JLabel(new ImageIcon(image)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                else if(board.getSquare(position).getTile().getType() == "Obstacle"){
                    try {
                        String pathname = defaultPieceImagesPath + board.getSquare(position).getTile().getM_material() + ".png";
                        //Exemple de nom d'image : "Glace.png"
                        final BufferedImage image =
                                ImageIO.read(new File(defaultPieceImagesPath + board.getSquare(position).getTile().getM_material() + ".png"));
                        add(new JLabel(new ImageIcon(image)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                else if (board.getSquare(position).getTile().getType() == "Jewel"){
                    try {
                        String pathname = defaultPieceImagesPath + board.getSquare(position).getTile().getType() + ".png";
                        //Exemple de nom d'image : "Joyau.png"
                        final BufferedImage image =
                                ImageIO.read(new File(defaultPieceImagesPath + board.getSquare(position).getTile().getType() + ".png"));
                        add(new JLabel(new ImageIcon(image)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }

        }

        private void assignSquareColor() {
            boolean isLight = ((squareId + squareId / 8) % 2 == 0);
            setBackground(isLight ? lightTileColor : darkTileColor);
        }
    }

}
