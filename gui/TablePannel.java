package gui;

import model.Person;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TablePannel extends JTable {
    private JTable table;
    private PersonTableModel personTableModel;


    public TablePannel() {


        personTableModel = new PersonTableModel();
        table = new JTable(personTableModel);

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
