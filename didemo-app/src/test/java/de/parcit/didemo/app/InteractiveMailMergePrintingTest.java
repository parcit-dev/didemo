package de.parcit.didemo.app;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import de.parcit.didemo.app.SelectPersonsAndTemplateUI.SelectionResult;
import de.parcit.didemo.core.MailMergeTemplate;
import de.parcit.didemo.core.Person;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.parcit.didemo.util.ListUtil.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InteractiveMailMergePrintingTest {


    // Test data
    private static final Person person1 = new SamplePerson("p1", "F1", "L1", "Mr.", "ADR1");
    private static final Person person2 = new SamplePerson("p2", "F2", "L2", "Mrs.", "ADR2");
    private static final Person person3 = new SamplePerson("p3", "F3", "L3", "Mr.", "ADR3");
    private static final List<Person> allPersons = toList(person1, person2, person3);
    private static final MailMergeTemplate template1 = new SampleTemplate("temp1");
    private static final MailMergeTemplate template2 = new SampleTemplate("temp2");
    private static final List<String> allTemplateNames = toList("temp1", "temp2");
    private static final Injector injector = Guice.createInjector(new GuiceModuleForAppTest());
    // Test Input
    private static List<Person> personsPassedToUI;
    private static List<String> templateNamesPassedToUI;
    // Test Output
    private static SelectionResult selectionToReturnFromUI;
    private static StringBuilder printerOutput;
    // System under test
    private final InteractiveMailMergePrinting sut =
            injector.getInstance(InteractiveMailMergePrinting.class);

    @Test
    void printMailMergeWithUI_singlePersonSelected() {

        // Arrange
        printerOutput = new StringBuilder();
        selectionToReturnFromUI = new SampleSelectionResult("temp1", person1);

        // Act
        sut.printMailMergeWithUI();

        // Assert
        assertEquals(allPersons, personsPassedToUI);
        assertEquals(allTemplateNames, templateNamesPassedToUI);
        assertEquals("template temp1 with Mr. F1 L1 ADR1\n", printerOutput.toString());
    }

    @Test
    void printMailMergeWithUI_multiplePersonsSelected() {

        // Arrange
        printerOutput = new StringBuilder();
        selectionToReturnFromUI = new SampleSelectionResult("temp2",
                person1, person2, person3);

        // Act
        sut.printMailMergeWithUI();

        // Assert
        assertEquals(allPersons, personsPassedToUI);
        assertEquals(allTemplateNames, templateNamesPassedToUI);
        assertEquals(
                "template temp2 with Mr. F1 L1 ADR1\n" +
                        "template temp2 with Mrs. F2 L2 ADR2\n" +
                        "template temp2 with Mr. F3 L3 ADR3\n", printerOutput.toString());
    }

    @Test
    void printMailMergeWithUI_userCanceled() {

        // Arrange
        printerOutput = new StringBuilder();
        selectionToReturnFromUI = null;

        // Act
        sut.printMailMergeWithUI();

        // Assert
        assertEquals(allPersons, personsPassedToUI);
        assertEquals(allTemplateNames, templateNamesPassedToUI);
        assertEquals("", printerOutput.toString());
    }

    private static class GuiceModuleForAppTest extends AbstractModule {
        @Override
        protected void configure() {

            bind(MailMergeUI.class).to(MailMergeUIForTest.class);
            bind(PersonsStore.class).to(PersonsStoreForTest.class);
            bind(TemplatesProvider.class).to(TemplatesForTest.class);
            bind(Printer.class).to(PrinterForTest.class);
        }
    }

    private static class SamplePerson implements Person {

        private final String firstName;
        private final String lastName;
        private final String salutation;
        private final String address;
        private final String id;

        private SamplePerson(String id, String firstName, String lastName, String salutation, String address) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.salutation = salutation;
            this.address = address;
            this.id = id;
        }

        @Override
        public String getFirstName() {
            return firstName;
        }

        @Override
        public String getLastName() {
            return lastName;
        }

        @Override
        public String getSalutation() {
            return salutation;
        }

        @Override
        public String getAddress() {
            return address;
        }

        @Override
        public String getID() {
            return id;
        }
    }

    private static class SampleTemplate implements MailMergeTemplate {

        private final String name;

        private SampleTemplate(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String mailMergedText(Person receiver) {
            return String.format("template %s with %s %s %s %s\n", name,
                    receiver.getSalutation(), receiver.getFirstName(), receiver.getLastName(), receiver.getAddress());
        }
    }


    private static class SampleSelectionResult implements SelectionResult {
        private final String templateName;
        private final List<Person> persons;

        private SampleSelectionResult(String templateName, Person... persons) {
            this.templateName = templateName;
            this.persons = toList(persons);
        }

        @Override
        public Iterable<Person> getPersons() {
            return persons;
        }

        @Override
        public String getTemplateName() {
            return templateName;
        }
    }

    private static class MailMergeUIForTest implements MailMergeUI {
        @Override
        public SelectPersonsAndTemplateUI newSelectPersonsAndTemplateUI() {
            return new SelectPersonsAndTemplateUI() {
                @Override
                public void setPersons(Iterable<Person> persons) {
                    personsPassedToUI = toList(persons);
                }

                @Override
                public void setTemplateNames(Iterable<String> templateNames) {
                    templateNamesPassedToUI = toList(templateNames);
                }

                @Override
                public SelectionResult askForSelection() {
                    return selectionToReturnFromUI;
                }
            };
        }
    }

    private static class PersonsStoreForTest implements PersonsStore {

        @Override
        public Iterable<Person> loadAllPersons() {
            return allPersons;
        }

        @Override
        public void savePerson(Person person) {
            throw new IllegalStateException("unexpected call");
        }

        @Override
        public void deletePerson(Person person) {
            throw new IllegalStateException("unexpected call");
        }
    }

    private static class TemplatesForTest implements TemplatesProvider {
        @Override
        public Iterable<String> getAllTemplateNames() {
            return allTemplateNames;
        }

        @Override
        public MailMergeTemplate getTemplateNamed(String templateName) {
            if (templateName.equals("temp1")) return template1;
            if (templateName.equals("temp2")) return template2;
            throw new IllegalArgumentException("Unexpected template " + templateName);
        }
    }

    private static class PrinterForTest implements Printer {

        @Override
        public void print(String textToPrint) {
            printerOutput.append(textToPrint);
        }
    }
}