package de.parcit.didemo.core;

import de.parcit.didemo.core.Person;

public interface DIDemoCore {

    Person newPerson(String name, String lastName, String address);

    Person getPersonWithID(String personID);

    void setPersonFields(String personID, String name, String lastName, String address);

    void removePerson(String personID);



    Iterable<Person> getAllPersons();

}
