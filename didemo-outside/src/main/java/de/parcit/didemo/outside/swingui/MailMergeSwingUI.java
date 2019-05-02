package de.parcit.didemo.outside.swingui;

import de.parcit.didemo.app.MailMergeUI;
import de.parcit.didemo.app.SelectPersonsAndTemplateUI;
import de.parcit.didemo.core.Person;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MailMergeSwingUI implements MailMergeUI {

    private static final int DIALOG_WIDTH = 600;
    private static final int DIALOG_HEIGHT = 400;
    private static final int PERSON_TEMPLATE_DIVIDER_LOCATION = DIALOG_WIDTH / 2;

    @Override
    public SelectPersonsAndTemplateUI newSelectPersonsAndTemplateUI() {

        JDialog dlg = new JDialog();
        dlg.setTitle("Mail Merge");
        dlg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dlg.getContentPane().setLayout(new BorderLayout());
        MailMergePanel panel = new MailMergePanel(PERSON_TEMPLATE_DIVIDER_LOCATION, dlg);
        dlg.getContentPane().add(panel);

        return new SelectPersonsAndTemplateUI() {
            @Override
            public void setTemplateNames(Iterable<String> templateNames) {
                panel.setTemplates(templateNames);
            }            @Override
            public void setPersons(Iterable<Person> persons) {
                panel.setPersons(persons);
            }

            @Override
            public SelectionResult askForSelection() {

                dlg.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
                dlg.setLocationRelativeTo(null); // center

                // open as modal dialog
                dlg.setModal(true);
                dlg.setVisible(true);

                List<Person> persons = panel.getSelectedPersons();
                String template = panel.getSelectedTemplate();

                return !panel.isInputConfirmed() ? null : new SelectionResult() {
                    @Override
                    public Iterable<Person> getPersons() {
                        return persons;
                    }

                    @Override
                    public String getTemplateName() {
                        return template;
                    }
                };
            }


        };
    }

}
