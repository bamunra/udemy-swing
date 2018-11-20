package gui;

import controller.Controller;
import javafx.scene.control.SplitPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

public class MyFrame extends JFrame {

    private JButton button1;
    private MyTextPanel textArea;
    private MyToolBar toolBar;
    private FormPanel formPanel;
    private JFileChooser fileChooser;
    private PrefsDialog prefsDialog;
    private Preferences prefs;
    private JSplitPane splitPane;

    private Controller controller;
    private TablePannel tablePannel;

    public MyFrame() throws HeadlessException {

        super("Hello world");

        setLayout(new BorderLayout());

        textArea = new MyTextPanel();
        button1 = new JButton("Click me");
        formPanel = new FormPanel();
        tablePannel = new TablePannel();
        prefsDialog = new PrefsDialog(this);
        prefs = Preferences.userRoot().node("db");
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tablePannel);

        splitPane.setOneTouchExpandable(true);

        controller = new Controller();

        tablePannel.setData(controller.getPerson());

        tablePannel.setPersonTableListener(new PersonTableListener() {
            public void rowDeleted(int row) {
                controller.removePerson(row);
                tablePannel.refresh();
            }

            ;
        });

        prefsDialog.setPrefsListener(new PrefsListener() {
            @Override
            public void preferencesSet(String user, String password, int port) {
                prefs.put("user", user);
                prefs.put("password", password);
                prefs.putInt("port", port);
            }
        });

        String user = prefs.get("user", "");
        String password = prefs.get("password", "");
        Integer port = prefs.getInt("port", 3306);
        prefsDialog.setDefoults(user, password, port);


        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new PersonFileFilter());


        add(splitPane,BorderLayout.CENTER);
        //add(tablePannel, BorderLayout.CENTER);
        //add(textArea, BorderLayout.CENTER);
        //add(button1, BorderLayout.SOUTH);
        //add(formPanel, BorderLayout.WEST);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.appendTxt("Click\n");
            }
        });

        toolBar = new MyToolBar();
        add(toolBar, BorderLayout.PAGE_START);
        toolBar.setToolBarListner(new ToolBarListner() {
            @Override
            public void saveEventOccured() {

                try {
                    controller.connect();
                    controller.save();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(MyFrame.this, "Save database problem");
                    e.printStackTrace();
                }
            }

            @Override
            public void refreshEventOccured() {
                try {
                    controller.connect();
                    controller.load();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(MyFrame.this, "Load database problem");
                    e.printStackTrace();
                }
                tablePannel.refresh();
            }
        });

        setJMenuBar(createMenuBar());

        formPanel.setFormListener(new FormListener() {
            public void formEventOccured(FormEvent event) {
                controller.addPerson(event);
                tablePannel.refresh();
            }
        });


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Window closing");
                dispose();
                System.gc();
                        }
        });

        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);

    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exportData = new JMenuItem("Export Data...");
        JMenuItem importData = new JMenuItem("Import Data...");
        JMenuItem exitItem = new JMenuItem("Exit...");
        fileMenu.add(exportData);
        fileMenu.add(importData);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu windowMenu = new JMenu("Window");
        JMenu showMenu = new JMenu("Show");
        JMenuItem prefsItem = new JMenuItem("Preferences.....");

        JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
        showFormItem.setSelected(true);

        showMenu.add(showFormItem);
        windowMenu.add(showMenu);
        windowMenu.add(prefsItem);

        menuBar.add(fileMenu);
        menuBar.add(windowMenu);

        prefsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prefsDialog.setVisible(true);
            }
        });


        showFormItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) event.getSource();
                formPanel.setVisible(menuItem.isSelected());

                if (menuItem.isSelected()){
                    splitPane.setDividerLocation(formPanel.getMinimumSize().getWidth());
                }

            }
        });

        fileMenu.setMnemonic(KeyEvent.VK_F);
        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        importData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showOpenDialog(MyFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        controller.loadFromFile(fileChooser.getSelectedFile());
                        tablePannel.refresh();
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(MyFrame.this, "Load data from file failed");
                    }
                }
            }
        });
        importData.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));


        exportData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showSaveDialog(MyFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        controller.saveToFile(fileChooser.getSelectedFile());
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(MyFrame.this, "Save data to file failed");
                    }
                }
            }
        });


        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = JOptionPane.showConfirmDialog(MyFrame.this, "Do you really want to liave application",
                        "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
                if (action == JOptionPane.OK_OPTION) {
                    WindowListener[] listeners = getWindowListeners();
                    for (WindowListener listener:listeners) {
                        listener.windowClosing(new WindowEvent(MyFrame.this ,0));
                    }
                    //System.exit(0);
                }
            }
        });

        return menuBar;
    }
}
