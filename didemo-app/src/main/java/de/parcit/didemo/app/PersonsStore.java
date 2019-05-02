package de.parcit.didemo.app;

import de.parcit.didemo.core.Person;

public interface PersonsStore {

    Iterable<Person> loadAllPersons();

    void savePerson(Person person);

    void deletePerson(Person person);
}
