import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormPanel extends JPanel {

    private JLabel nameLabel;
    private JLabel occupationLabel;
    private JTextField nameField;
    private JTextField occupationField;
    private JButton okBtn;
    private FormListener formListener;
    private JList ageList;
    private JComboBox comboBox;


    public FormPanel() {

        Dimension dim = getPreferredSize();
        dim.width = 250;
        setPreferredSize(dim);

        nameLabel = new JLabel("Name: ");
        occupationLabel = new JLabel("Occupation: ");
        nameField = new JTextField(10);
        occupationField = new JTextField(10);
        ageList = new JList();
        comboBox = new JComboBox();

        // setup List box

        DefaultListModel ageModel = new DefaultListModel();
        ageModel.addElement(new AgeCategory(0,"Under 18"));
        ageModel.addElement(new AgeCategory(1,"18 to 65"));
        ageModel.addElement(new AgeCategory(2,"65 over"));
        ageList.setModel(ageModel);
        ageList.setPreferredSize(new Dimension(110, 66));
        ageList.setBorder(BorderFactory.createEtchedBorder());
        ageList.setSelectedIndex(2);

        // setup ComboBox
        DefaultComboBoxModel empModel = new DefaultComboBoxModel();
        empModel.addElement("employed");
        empModel.addElement("self-employed");
        empModel.addElement("unemployed");
        comboBox.setModel(empModel);
        comboBox.setEditable(true);

        okBtn = new JButton("OK");

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String occupation = occupationField.getText();
                AgeCategory ageCat = (AgeCategory) ageList.getSelectedValue();
                String empCat = String.valueOf(comboBox.getSelectedIndex());

                FormEvent event = new FormEvent(this, name, occupation,ageCat.getId(),empCat);
                if (formListener != null) {
                    formListener.formEventOccured(event);
                }
            }
        });

        layoutComponent();

    }

    public void setFormListener(FormListener formListener) {
        this.formListener = formListener;
    }

    public void layoutComponent(){
        //setBorder(BorderFactory.createTitledBorder("Add Prefix"));
        Border innerBorder = BorderFactory.createTitledBorder("Add Person");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);

        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();


        gc.fill = GridBagConstraints.NONE;
        gc.gridy = 0;

        // first row
        gc.weightx = 1;
        gc.weighty = 0;
        gc.anchor = GridBagConstraints.LAST_LINE_END;
        gc.gridx = 0;
        add(nameLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        add(nameField, gc);

        // next row

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LAST_LINE_END;
        gc.gridx = 0;
        gc.insets = new Insets(0, 0, 0, 0);
        add(occupationLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        add(occupationField, gc);


        //next row

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.gridx = 0;
        gc.insets = new Insets(0, 0, 0, 0);
        add(new JLabel("Age: "), gc);


        gc.weightx = 1;
        gc.weighty = 0.5;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(ageList, gc);

        //foth row


        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.8;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.gridx = 0;
        gc.insets = new Insets(0, 0, 0, 0);
        add(new JLabel("employment: "), gc);

        gc.weightx = 1;
        gc.weighty = 1.1;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(comboBox, gc);

        //foth row

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1.1;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(okBtn, gc);
    }

}
class AgeCategory{
    private int id;
    private String text;

    public AgeCategory(int id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override
    public String toString() {
        return  text ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}