package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;


public class MyToolBar extends JToolBar implements ActionListener {

    private JButton saveButton;
    private JButton refreshButton;
    private ToolBarListner toolBarListner;

    public MyToolBar() {


        super();

        //setLayout(new FlowLayout(FlowLayout.LEFT));

        saveButton = new JButton();
        saveButton.addActionListener(this);

        // Doesn't work
        saveButton.setIcon(Utils.createIcon("/icons/general/Save16.gif"));
        // This one works
        //saveButton.setIcon(createIcon("/icons/general/Save16.gif"));

        //saveButton.setIcon(Utils.createIcon("Raznoe/Java/swing_1/src/icons/general/SaveAs16.gif"));
        saveButton.setToolTipText("Save");
        add(saveButton);
        //addSeparator();

        refreshButton = new JButton();
        refreshButton.addActionListener(this);
        //refreshButton.setIcon(Utils.createIcon("Raznoe/Java/swing_1/src/icons/general/SaveAs16.gif"));
        refreshButton.setIcon(createIcon("/icons/general/refresh16.gif"));
        refreshButton.setToolTipText("Refresh");
        add(refreshButton);

    }

    public void setToolBarListner(ToolBarListner toolBarListner) {
        this.toolBarListner = toolBarListner;
    }

    private ImageIcon createIcon(String path){
        URL url =  getClass().getResource(path);
        if (url == null){
            System.err.println("Unable to load resourse " + path);
        }
        ImageIcon imageIcon = new ImageIcon(url);
        return imageIcon;
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
