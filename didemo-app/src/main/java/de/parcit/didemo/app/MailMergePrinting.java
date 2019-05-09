package de.parcit.didemo.app;

import com.google.inject.Inject;
import de.parcit.didemo.app.SelectPersonsAndTemplateUI.SelectionResult;
import de.parcit.didemo.core.MailMergeTemplate;
import de.parcit.didemo.core.Person;

public class MailMergePrinting {

    private final PersonsStore store;
    private final TemplatesProvider templatesProvider;
    private final MailMergeUI ui;
    private final Printer printer;

    @Inject
    public MailMergePrinting(
            PersonsStore store,
            TemplatesProvider templatesProvider,
            MailMergeUI ui,
            Printer printer) {

        this.store = store;
        this.templatesProvider = templatesProvider;
        this.ui = ui;
        this.printer = printer;
    }

    public void printMailMergeWithUI() {

        Iterable<Person> allPersons = store.loadAllPersons();
        Iterable<String> allTemplateNames = templatesProvider.getAllTemplateNames();

        SelectPersonsAndTemplateUI selectUI = ui.newSelectPersonsAndTemplateUI();
        selectUI.setPersons(allPersons);
        selectUI.setTemplateNames(allTemplateNames);

        SelectionResult selection = selectUI.askForSelection();

        if (selection != null) {
            printMailMerge(
                    selection.getPersons(), selection.getTemplateName());
        }
    }

    private void printMailMerge(
            Iterable<Person> persons, String templateName) {

        MailMergeTemplate template = templatesProvider.getTemplateNamed(templateName);
        for (Person person : persons) {
            printer.print(template.mailMergedText(person));
        }
    }
}
