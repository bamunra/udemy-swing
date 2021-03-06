package gui;

import javax.swing.*;
import java.awt.*;

public class ProgressDialog extends JDialog {

    private JButton cancelButton;
    private JProgressBar progressBar;

    public ProgressDialog(Window parent) {
        super(parent, "Message downloaded.... ", ModalityType.APPLICATION_MODAL);

        cancelButton = new JButton("Cancel");
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setString("Retriving messages...");

        progressBar.setMaximum(10);

        Dimension size = cancelButton.getPreferredSize();
        size.width = 400;
        progressBar.setPreferredSize(size);

        setLayout(new FlowLayout());

        add(progressBar);
        add(cancelButton);

        pack();

        setLocationRelativeTo(parent);


    }

    public void setMaximum(int count){
        progressBar.setMaximum(count);
    }

    public void setValue(int value){
        int progress = value/progressBar.getMaximum() * 100;
        progressBar.setString(String.format("%d%% complete",progress));
        progressBar.setValue(value);
    }
}
