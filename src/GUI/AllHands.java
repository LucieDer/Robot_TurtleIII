package GUI;

import Engine.CARDS.HandCards;
import Engine.GAME.Board;
import Engine.GAME.BoardUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AllHands extends JPanel{
    //private final JPanel allHands;
    private final HandCardsPanel handCardPanel;
    private final JPanel programPanel;
    private Board RTBoard;
    private static final Dimension CARDS_PANEL_DIMENSION = new Dimension(30,130) ;
    public static final Dimension SINGLE_CARD_PANEL_DIMENSION = new Dimension(300, 600);
    public static String defaultPieceImagesPath = "ArtTiles/";

    public AllHands(Table table){
        super(new BorderLayout(3,2));
        this.handCardPanel = new HandCardsPanel(table.getRTBoard().getCurrentPlayer().getM_handCards());
        this.handCardPanel.setBackground(Color.decode("#7c00e7"));

        if(table.getRTBoard().getCurrentPlayer().getM_program().getAmount() == 0){ //Programme vide
            this.programPanel = new JPanel();
            this.programPanel.setBackground(Color.green);
            this.programPanel.setPreferredSize(CARDS_PANEL_DIMENSION);

        }else{
            this.programPanel = new HandCardsPanel(table.getRTBoard().getCurrentPlayer().getM_program());
        }


        add(this.handCardPanel, BorderLayout.PAGE_START);
        add(this.programPanel, BorderLayout.PAGE_END);

        validate();


    }

    void redo(final Board board){

    }


    //Les cartes
    private class HandCardsPanel extends JPanel {
        final List<SingleCardPanel> handCards;
        final HandCards m_hand;
        final int amountInHand;

        HandCardsPanel(HandCards hand){
            super(new GridLayout(1,hand.getAmount()));
            this.m_hand = hand;
            this.amountInHand = hand.getAmount();
            this.handCards = new ArrayList<>();
            for(int i = 0; i< amountInHand; i++){
                //final SquarePanel squarePanel = new SquarePanel(this, i+1);
                final SingleCardPanel cardPanel = new SingleCardPanel(m_hand, this, i);
                this.handCards.add(cardPanel);
                add(cardPanel);
            }



            setPreferredSize(AllHands.CARDS_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard(Board rtBoard) {
            removeAll();
            for(final SingleCardPanel cardPanel : handCards){
                cardPanel.drawSquare(rtBoard);
                add(cardPanel);
            }
            validate();
            repaint();
        }
    }


    //Un carré individuel sur le plateau
    private class SingleCardPanel extends JPanel {

        private final int cardId;
        private final HandCards m_hand;

        SingleCardPanel(final HandCards hand, final HandCardsPanel handCardsPanel, final int cardId) {
            super(new GridBagLayout());
            this.m_hand = hand;
            this.cardId = cardId;
            setPreferredSize(AllHands.SINGLE_CARD_PANEL_DIMENSION);
            assignCardIcon(RTBoard);

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




