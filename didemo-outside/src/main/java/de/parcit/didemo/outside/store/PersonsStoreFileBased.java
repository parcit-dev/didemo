package de.parcit.didemo.outside.store;

import de.parcit.didemo.app.PersonsStore;
import de.parcit.didemo.core.Person;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Collectors;

public class PersonsStoreFileBased implements PersonsStore {
    private static final Path DEFAULT_STORAGE_PATH = Paths
            .get(System.getProperty("user.home"),".didemo", "store", "persons");
    private final Path storagePath = DEFAULT_STORAGE_PATH;



    @Override
    public Iterable<Person> loadAllPersons() {
        try {
            Files.createDirectories(storagePath);

            return Files.list(storagePath)
                    .filter(p-> p.toString().endsWith(".txt"))
                    .map(this::loadPersonFromFile)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void savePerson(Person person) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void deletePerson(Person person) {
        throw new IllegalStateException("Not implemented");
    }

    private Person loadPersonFromFile(Path path) {
        File file = path.toFile();

        try {
            Properties props = new Properties();
            props.load(new FileInputStream(file));
            return new Person() {
                @Override
                public String getFirstName() {
                    return props.getProperty("firstName");
                }

                @Override
                public String getLastName() {
                    return props.getProperty("lastName");
                }

                @Override
                public String getSalutation() {
                    return props.getProperty("salutation");
                }

                @Override
                public String getAddress() {
                    return props.getProperty("address");
                }

                @Override
                public String getID() {
                    return file.getName();
                }
            };

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
