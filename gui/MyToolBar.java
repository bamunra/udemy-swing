package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyToolBar extends JPanel implements ActionListener {

    private JButton saveButton;
    private JButton refreshButton;
    private ToolBarListner toolBarListner;

    public MyToolBar() {


        super();

        setLayout(new FlowLayout(FlowLayout.LEFT));

        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        add(saveButton);

        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(this);
        add(refreshButton);

    }

    public void setToolBarListner(ToolBarListner toolBarListner) {
        this.toolBarListner = toolBarListner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton clicked = (JButton) e.getSource();

        if (clicked == saveButton) {
           if (toolBarListner != null) {
            toolBarListner.saveEventOccured();}
        } else if (clicked == refreshButton) {
           if (toolBarListner != null) {
            toolBarListner.refreshEventOccured();}
        }

    }
}
