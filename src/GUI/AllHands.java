package GUI;

import Engine.CARDS.Card;
import Engine.CARDS.HandCards;
import Engine.GAME.Board;
import Engine.GAME.BoardUtils;
import Engine.GAME.Move;

import Engine.PLAYERS.MoveTransition;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;


public class AllHands extends JPanel{
    //private final JPanel allHands;
    private final HandCardsPanel handCardPanel;
    private final ProgramPanel programPanel;
    //private Board RTBoard;
    private Table table;

    private static final Dimension CARDS_PANEL_DIMENSION = new Dimension(30,130) ;
    public static final Dimension SINGLE_CARD_PANEL_DIMENSION = new Dimension(260, 570);
    public static String defaultPieceImagesPath = "ArtTiles/";

    public AllHands(Table table){
        super(new BorderLayout(3,2));
        this.handCardPanel = new HandCardsPanel(table,false, table.getRTBoard().getCurrentPlayer().getM_handCards());
        this.handCardPanel.setBackground(Color.decode("#7c00e7"));
        this.programPanel = new ProgramPanel(table);

        this.table = table;



        add(this.handCardPanel, BorderLayout.PAGE_START);
        add(this.programPanel, BorderLayout.PAGE_END);

        validate();


    }



    public AllHands.HandCardsPanel getHandCardPanel() {
        return handCardPanel;
    }

    public ProgramPanel getProgramPanel() {
        return programPanel;
    }

    //Les cartes
    public class HandCardsPanel extends JPanel {
        List<SingleCardPanel> handCards;
        HandCards m_hand;
        int amountInHand;
        boolean isProgram;

        HandCardsPanel(Table table, boolean isProgram, HandCards hand){
            super(new GridLayout(1,hand.getAmount()));
            this.isProgram = isProgram;
            this.m_hand = hand;
            this.amountInHand = hand.getAmount();
            this.handCards = new ArrayList<>();
            for(int i = 0; i< amountInHand; i++){
                //final SquarePanel squarePanel = new SquarePanel(this, i+1);
                final SingleCardPanel cardPanel = new SingleCardPanel(table, this.isProgram, m_hand, this, i);
                this.handCards.add(cardPanel);
                add(cardPanel);
            }



            setPreferredSize(AllHands.CARDS_PANEL_DIMENSION);
            validate();
        }

        HandCardsPanel(Table table){
            super();
            this.m_hand = table.getRTBoard().getCurrentPlayer().getM_handCards();
            this.isProgram = true;
            this.handCards = new ArrayList<>();
            this.amountInHand = 0;
        }

        public void drawCardPanel(Board rtBoard) {
            removeAll();
            for(final SingleCardPanel cardPanel : handCards){
                cardPanel.drawSquare(rtBoard);
                add(cardPanel);
            }
            validate();
            repaint();
        }

        public void redo(Board rtBoard) {
            removeAll();

            this.isProgram = isProgram;
            if(this.isProgram){
                this.m_hand = rtBoard.getCurrentPlayer().getM_program();
            }else{
                this.m_hand = rtBoard.getCurrentPlayer().getM_handCards();
            }
            this.amountInHand = this.m_hand.getAmount();
            this.handCards = new ArrayList<>();
            if(this.amountInHand == 0){
                add(new JPanel());
            }else{
                for(int i = 0; i< amountInHand; i++){
                    //final SquarePanel squarePanel = new SquarePanel(this, i+1);
                    final SingleCardPanel cardPanel = new SingleCardPanel(table, this.isProgram, m_hand, this, i);
                    this.handCards.add(cardPanel);
                    add(cardPanel);
                }
            }


            setPreferredSize(AllHands.CARDS_PANEL_DIMENSION);
            validate();


        }
    }



    public class ProgramPanel extends JPanel{
        List<Card> handCards = new ArrayList<>();
        private JLabel cardList;

        ProgramPanel(Table table){
            super();
            this.handCards = table.getRTBoard().getCurrentPlayer().getM_program().getCards();
            this.cardList = new JLabel("Programme :\n" + table.getRTBoard().getCurrentPlayer().getM_program().showProgram() );

            this.cardList.setFont(new Font("Verdana",1,30));
            add(this.cardList);
            validate();
        }

        public void redo(Board board){
            removeAll();

            this.handCards = board.getCurrentPlayer().getM_program().getCards();
            this.cardList = new JLabel("Programme :\n" );
            if(this.handCards.size() > 0){
                for(Card card : handCards){
                    this.cardList.setText(this.cardList.getText() + " " + card.printColor());
                }
            }
            this.cardList.setFont(new Font("Verdana",1,30));
            add(this.cardList);
            validate();
            repaint();
        }
    }


    //Un carré individuel sur le plateau
    private class SingleCardPanel extends JPanel {

        private final int cardId;
        private final HandCards m_hand;
        private final boolean isProgram;

        SingleCardPanel(Table table, boolean isProgram, final HandCards hand, final HandCardsPanel handCardsPanel, final int cardId) {
            super(new GridBagLayout());
            this.isProgram = isProgram;
            this.m_hand = hand;
            this.cardId = cardId;
            setPreferredSize(AllHands.SINGLE_CARD_PANEL_DIMENSION);
            assignCardIcon(table.getRTBoard());

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //AJOUTER AU PROGRAMME
                    //Clic droit pour annuler
                    if(isRightMouseButton(e)){
                        table.getRTBoard().setAddedCard(null);

                    }else if(isLeftMouseButton(e)){
                        //Si le joueur veut ajouter une carte au programme
                        if(table.getRTBoard().isAddingCard() && table.getRTBoard().getAddedCard() == null && !table.getRTBoard().isFinished()) {
                            //On ajoute la carte sur le carré au programme
                            table.getRTBoard().setAddedCard(table.getRTBoard().getCurrentPlayer().getM_handCards().getCards().remove(cardId));
                            table.getRTBoard().getCurrentPlayer().getM_program().add(table.getRTBoard().getAddedCard());

                            table.getRTBoard().setAddedCard(null);

                        }
                        //Si le joueur veut finir le tour
                        else if(table.getRTBoard().isFinished() && table.getRTBoard().getAddedCard() == null){
                            table.getRTBoard().setTurnFinished(true);
                            //On ajoute la carte sur le carré à la défausse
                            table.getRTBoard().setAddedCard(table.getRTBoard().getCurrentPlayer().getM_handCards().getCards().remove(cardId));
                            table.getRTBoard().getCurrentPlayer().getM_deckCards().addToDiscard(table.getRTBoard().getAddedCard());

                            //table.getRTBoard().setFinished(false);
                            table.getRTBoard().setAddedCard(null);



                        }

                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {


                                    //redo actionpanel
                                    table.getActionPanel().redo(table.getRTBoard());
                                    //redo card panel
                                    table.getAllHandsPanel().getProgramPanel().redo(table.getRTBoard());
                                    table.getAllHandsPanel().getHandCardPanel().redo(table.getRTBoard());
                                    //redo orientation panel
                                    table.getOrientationPanel().reDraw(table.getRTBoard());
                                    //table.getOrientationPanel().redo(table.getRTBoard());

                                    validate();
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

            validate();
        }


        public void drawSquare(final Board board) {
            assignCardIcon(board);
            validate();
            repaint();
        }


        private void assignCardIcon(final Board board) {
            this.removeAll();
            int position = this.cardId;
            if (m_hand.getCards().get(position).printColor().equals("Rouge")) {
                try {
                    String pathname = AllHands.defaultPieceImagesPath + "cards/RougeCard.png";

                    BufferedImage image =
                            ImageIO.read(new File(pathname));
                    image = BoardUtils.resize(image, 130, 145);
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (m_hand.getCards().get(position).printColor().equals("Vert")) {
                try {
                    String pathname = AllHands.defaultPieceImagesPath + "cards/VertCard.png";

                    BufferedImage image =
                            ImageIO.read(new File(pathname));
                    image = BoardUtils.resize(image, 130, 145);
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (m_hand.getCards().get(position).printColor().equals("Violet")) {
                try {
                    String pathname = AllHands.defaultPieceImagesPath + "cards/VioletCard.png";

                    BufferedImage image =
                            ImageIO.read(new File(pathname));
                    image = BoardUtils.resize(image, 130, 145);
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (m_hand.getCards().get(position).printColor().equals("Bleu")) {
                try {
                    String pathname = AllHands.defaultPieceImagesPath + "cards/BleuCard.png";

                    BufferedImage image =
                            ImageIO.read(new File(pathname));
                    image = BoardUtils.resize(image, 130, 145);
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }




}





