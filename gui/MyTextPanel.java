package gui;

import javax.swing.*;
import java.awt.*;

public class MyTextPanel extends JTextArea{

    private JTextArea textArea;

    public MyTextPanel() {

        textArea = new JTextArea();

        setLayout(new BorderLayout());

        add(new JScrollPane(textArea), BorderLayout.CENTER);

    }

    public void appendTxt(String s) {

        textArea.append(s);

    }
}
