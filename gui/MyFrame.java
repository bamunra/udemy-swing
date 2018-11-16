package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class MyFrame extends JFrame {

    private JButton button1;
    private MyTextPanel textArea;
    private MyToolBar toolBar;
    private FormPanel formPanel;
    private JFileChooser fileChooser;

    private Controller controller;
    private TablePannel tablePannel;

    public MyFrame() throws HeadlessException {

        super("Hello world");

        setLayout(new BorderLayout());

        textArea = new MyTextPanel();
        button1 = new JButton("Click me");
        formPanel = new FormPanel();
        tablePannel = new TablePannel();

        controller = new Controller();

        tablePannel.setData(controller.getPerson());
        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new PersonFileFilter());

        add(tablePannel, BorderLayout.CENTER);
        //add(textArea, BorderLayout.CENTER);
        add(button1, BorderLayout.SOUTH);
        add(formPanel, BorderLayout.WEST);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.appendTxt("Click\n");
            }
        });

        toolBar = new MyToolBar();
        add(toolBar, BorderLayout.NORTH);
        toolBar.setTextListener(new StreemListner() {
            @Override
            public void textEmmited(String text) {
                System.out.println(text + "wer");
            }
        });

        setJMenuBar(createMenuBar());

        formPanel.setFormListener(new FormListener() {
            public void formEventOccured(FormEvent event) {
                controller.addPerson(event);
                tablePannel.refresh();
            }
        });


        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
        showFormItem.setSelected(true);

        showMenu.add(showFormItem);
        windowMenu.add(showMenu);

        menuBar.add(fileMenu);
        menuBar.add(windowMenu);

        showFormItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) event.getSource();
                formPanel.setVisible(menuItem.isSelected());

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
                        JOptionPane.showMessageDialog(MyFrame.this,"Load data from file failed");
                    }
                }
            }
        });

        exportData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showSaveDialog(MyFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        controller.saveToFile(fileChooser.getSelectedFile());
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(MyFrame.this,"Save data to file failed");
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
                    System.exit(0);
                }
            }
        });

        return menuBar;
    }
}
