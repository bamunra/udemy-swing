package gui;

import model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        JMenuItem removeItem = new JMenuItem("Delete row");
        popupMenu.add(removeItem);

        table.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                table.getSelectionModel().setSelectionInterval(row,row);
                if (e.getButton() == MouseEvent.BUTTON3){
                    popupMenu.show(table,e.getX(),e.getY());
                }
            }
        });

        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                
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
