package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class FormPanel extends JPanel {

    private JLabel nameLabel;
    private JLabel occupationLabel;
    private JTextField nameField;
    private JTextField occupationField;
    private JButton okBtn;
    private FormListener formListener;
    private JList ageList;
    private JComboBox comboBox;
    private JCheckBox citisenCheckBox;
    private JTextField taxField;
    private JLabel taxLable;
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
    private ButtonGroup genderGroup;


    public FormPanel() {

        Dimension dim = getPreferredSize();
        dim.width = 250;
        setPreferredSize(dim);
        setMinimumSize(dim);

        nameLabel = new JLabel("Name: ");
        nameField = new JTextField(10);
        occupationLabel = new JLabel("Occupation: ");
        occupationField = new JTextField(10);
        ageList = new JList();
        comboBox = new JComboBox();
        citisenCheckBox = new JCheckBox();
        taxField = new JTextField(10);
        taxLable = new JLabel("Tax ID:");


        nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
        nameLabel.setLabelFor(nameField);


        // setup Radio

        maleRadio = new JRadioButton("male");
        femaleRadio = new JRadioButton("female");

        maleRadio.setActionCommand("male");
        femaleRadio.setActionCommand("female");

        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        maleRadio.setSelected(true);


        // setup tax

        taxLable.setEnabled(false);
        taxField.setEnabled(false);

        citisenCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isTicked = citisenCheckBox.isSelected();
                taxLable.setEnabled(isTicked);
                taxField.setEnabled(isTicked);
            }
        });


        // setup List box

        DefaultListModel ageModel = new DefaultListModel();
        ageModel.addElement(new AgeCategory(0, "Under 18"));
        ageModel.addElement(new AgeCategory(1, "18 to 65"));
        ageModel.addElement(new AgeCategory(2, "65 over"));
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
        okBtn.setMnemonic(KeyEvent.VK_O);

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String occupation = occupationField.getText();
                AgeCategory ageCat = (AgeCategory) ageList.getSelectedValue();
                String empCat = String.valueOf(comboBox.getSelectedIndex());
                String taxId = taxField.getText();
                boolean usCitizen = citisenCheckBox.isSelected();
                String gender = genderGroup.getSelection().getActionCommand();

                System.out.println(gender);

                FormEvent event = new FormEvent(this, name, occupation, ageCat.getId(), empCat, taxId, usCitizen, gender);
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

    public void layoutComponent() {
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
        gc.weighty = 0.1;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(ageList, gc);

        //foth row


        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.gridx = 0;
        gc.insets = new Insets(0, 0, 0, 0);
        add(new JLabel("employment: "), gc);

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(comboBox, gc);

        //foth row

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.gridx = 0;
        gc.insets = new Insets(0, 0, 0, 0);
        add(new JLabel("Belarus citizen: "), gc);

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(citisenCheckBox, gc);
        //foth row

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.gridx = 0;
        gc.insets = new Insets(0, 0, 0, 0);
        add(taxLable, gc);

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(taxField, gc);

        //foth row

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.gridx = 0;
        gc.insets = new Insets(0, 0, 0, 0);
        add(new JLabel("Gender: "), gc);

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(maleRadio, gc);

        gc.gridy++;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(femaleRadio, gc);
        //foth row

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 5.1;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(okBtn, gc);
    }

}

class AgeCategory {
    private int id;
    private String text;

    public AgeCategory(int id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
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