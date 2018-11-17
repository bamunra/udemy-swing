package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

public class PrefsDialog extends JDialog{

    private JButton okButton;
    private JButton cancelButton;
    private JSpinner portSpinner;
    private SpinnerNumberModel spinnerNumberModel;
    private JPasswordField passwordField;
    private JTextField userField;
    private PrefsListener prefsListener;

    public PrefsDialog(JFrame parent) {

        super(parent,"Prefetences", true);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        userField = new JTextField(10);
        passwordField = new JPasswordField(10);
        passwordField.setEchoChar('*');

        spinnerNumberModel = new SpinnerNumberModel(3306,0,9999,1);
        portSpinner = new JSpinner(spinnerNumberModel);

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;


        gc.gridx = 0;
        add(new JLabel("User: "),gc);


        gc.gridx++;
        add(userField,gc);

        //+++++++++++++++++++++
        gc.gridy++;
        gc.gridx = 0;
        add(new JLabel("Password: "),gc);


        gc.gridx++;
        add(passwordField,gc);

        //+++++++++++++++++++++


        gc.gridy++;
        gc.gridx = 0;
        add(new JLabel("Port: "),gc);


        gc.gridx++;
        add(portSpinner,gc);

        //+++++++++++++++++++++
        gc.gridy++;
        gc.gridx = 0;

        add(okButton,gc);

        gc.gridx++;
        add(cancelButton,gc);


        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer val = (Integer) portSpinner.getValue();
                String user = userField.getText();
                char[] password = passwordField.getPassword();

                if (prefsListener != null){
                    prefsListener.preferencesSet(user, String.valueOf(passwordField.getPassword()),val);
                }

                System.out.println(val + user + String.valueOf(password));
                setVisible(false);

            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        setSize(new Dimension(300,400));
        setLocationRelativeTo(parent);


    }

    public void setDefoults(String user, String password, int port){
        userField.setText(user);
        passwordField.setText(password);
        portSpinner.setValue(port);
    }

    public void setPrefsListener(PrefsListener prefsListener) {

        this.prefsListener = prefsListener;
    }
}
