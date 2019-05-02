package de.parcit.didemo.outside.swingui;

import de.parcit.didemo.core.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import static de.parcit.didemo.util.SwingUtil.inFlowRightAligned;
import static de.parcit.didemo.util.SwingUtil.leftRightSplit;
import static de.parcit.didemo.util.SwingUtil.withLabelAboveComponent;

class MailMergePanel extends JPanel {
    private final Window window;
    private final DefaultListModel<Person> personsModel = new DefaultListModel<>();
    private final DefaultListModel<String> templatesModel = new DefaultListModel<>();
    private boolean inputConfirmed = false;
    private final JLabel titleLabel = new JLabel("For Mail merge please select one or more persons and a template:\n");
    private final JList<Person> personsList = new JList<>(personsModel);
    private final JList<String> templatesList = new JList<>(templatesModel);
    private final JButton cancelButton = new JButton("Cancel");
    private final JButton okButton = new JButton("OK");

    @SuppressWarnings("SameParameterValue")
    MailMergePanel(int dividerLocation, Window window) {
        this.window = window;

        initComponents();

        layoutComponents(dividerLocation);

        updateUIState();
    }

    private void initComponents() {
        personsList.addListSelectionListener(l -> updateUIState());
        personsList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Person) {
                    Person p = (Person) value;
                    value = String.format("%s %s %s", p.getSalutation(),p.getFirstName(),p.getLastName());
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        templatesList.addListSelectionListener(l -> updateUIState());
        templatesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cancelButton.addActionListener(this::onCancel);
        okButton.addActionListener(this::onOK);
    }

    List<Person> getSelectedPersons() {
        return personsList.getSelectedValuesList();
    }

    String getSelectedTemplate() {
        return templatesList.getSelectedValue();
    }

    boolean isInputConfirmed() {
        return isValidSelection() && inputConfirmed;
    }

    void setPersons(Iterable<Person> persons) {
        personsModel.clear();
        for (Person p:persons) {
            personsModel.addElement(p);
        }
    }

    void setTemplates(Iterable<String> templateNames) {
        templatesModel.clear();
        for (String s : templateNames) templatesModel.addElement(s);
    }

    private void layoutComponents(int dividerLocation) {
        setLayout(new BorderLayout());

        add(titleLabel, BorderLayout.NORTH);
        add(leftRightSplit(dividerLocation,
                withLabelAboveComponent("Persons", personsList),
                withLabelAboveComponent("Templates", templatesList)), BorderLayout.CENTER);
        add(inFlowRightAligned(cancelButton, okButton), BorderLayout.SOUTH);
    }

    private void updateUIState() {
        okButton.setEnabled(isValidSelection());
    }


    private boolean isValidSelection() {
        return !personsList.isSelectionEmpty() && !templatesList.isSelectionEmpty();
    }

    @SuppressWarnings("unused")
    private void onCancel(ActionEvent l) {
        closeWindow();
    }

    @SuppressWarnings("unused")
    private void onOK(ActionEvent l) {
        inputConfirmed = true;
        closeWindow();
    }

    private void closeWindow() {
        window.dispose();
    }

}
