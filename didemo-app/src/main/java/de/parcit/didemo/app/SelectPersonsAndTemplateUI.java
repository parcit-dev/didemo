package de.parcit.didemo.app;

import de.parcit.didemo.core.Person;

public interface SelectPersonsAndTemplateUI {

     interface SelectionResult {
        Iterable<Person> getPersons();
        String getTemplateName();
    }

    /**
     * The persons the user can select from.
     */
    void setPersons(Iterable<Person> persons);

    /**
     * The names of the templates the user can select from.
     */
    void setTemplateNames(Iterable<String> templateNames);

    /**
     * Let the user select one or more persons and a template,
     * and return the user's selection, or {@code null} when the user canceled.
     */
    SelectionResult askForSelection();

}
