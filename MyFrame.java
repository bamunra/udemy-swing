import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame {

    private JButton button1;
    private MyTextPanel textArea;
    private MyToolBar toolBar;
    private FormPanel formPanel;


    public MyFrame() throws HeadlessException {

        super("Hello world");

        setLayout(new BorderLayout());

        textArea = new MyTextPanel();
        add(textArea, BorderLayout.CENTER);

        button1 = new JButton("Click me");
        add(button1, BorderLayout.SOUTH);

        formPanel = new FormPanel();
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
                String name = event.getName();
                String occupation = event.getOccupation();
                int ageCat = event.getAgeCategory();
                String empCat = event.getEmpCategory();
                String gender = event.getGender();

                textArea.appendTxt(name + ": " + occupation + ": " + ageCat + ": " + empCat + ": " + gender + "\n");

            }
        });


        setSize(700, 800);
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
        JMenuItem showFormItem = new JMenuItem("Person Form");
        showMenu.add(showFormItem);
        windowMenu.add(showMenu);
        
        menuBar.add(fileMenu);
        menuBar.add(windowMenu);

        return menuBar;
    }
}
