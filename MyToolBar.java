import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyToolBar extends JPanel implements ActionListener {

    private JButton button1;
    private JButton button2;
    private StreemListner textListener;

    public MyToolBar() {


        super();

        setLayout(new FlowLayout(FlowLayout.LEFT));

        button1 = new JButton("Hello");
        button1.addActionListener(this);
        add(button1);

        button2 = new JButton("GoodBye");
        button2.addActionListener(this);
        add(button2);

    }

    public void setTextListener(StreemListner textListener) {
        this.textListener = textListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton clicked = (JButton) e.getSource();

        if (clicked == button1) {
           if (textListener != null) {
            textListener.textEmmited("Hello");}
        } else if (clicked == button2) {
           if (textListener != null) {
            textListener.textEmmited("GoodBye");}
        }

    }
}
