package gui;

import model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TablePannel extends JTable {

    private JTable table;
    private PersonTableModel personTableModel;
    private JPopupMenu popupMenu;

    public TablePannel() {

        personTableModel = new PersonTableModel();
        table = new JTable(personTableModel);
        popupMenu = new JPopupMenu();
        JMenuItem remuve = new JMenuItem("Delete row");
        popupMenu.add(remuve);

        table.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3){
                    popupMenu.show(table,e.getX(),e.getY());
                }
            }
        });

        setLayout(new BorderLayout());

        add(new JScrollPane(table),BorderLayout.CENTER);

    }

    public void setData(List<Person> db){
        personTableModel.setData(db);
    }

    public void refresh() {
        personTableModel.fireTableDataChanged();
    }
}
