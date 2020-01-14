package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static java.awt.FlowLayout.CENTER;

public class ActionPanel extends JPanel {

    ActionPanel(){
        super(new FlowLayout());

        JLabel jlabel = new JLabel("This is a label");
        jlabel.setFont(new Font("Verdana",1,20));
        this.add(jlabel);
        this.setBorder(new LineBorder(Color.BLACK));
        this.add(new JButton("Build Wall"));
        this.add(new JButton("Add Card To Program"));
        this.add(new JButton("Execute Program"));

        validate();
    }



}
