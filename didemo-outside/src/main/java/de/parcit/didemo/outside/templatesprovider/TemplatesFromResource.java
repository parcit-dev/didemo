package de.parcit.didemo.outside.templatesprovider;

import de.parcit.didemo.app.TemplatesProvider;
import de.parcit.didemo.core.MailMergeTemplate;
import de.parcit.didemo.core.Person;
import de.parcit.didemo.util.InputStreamUtil;

import java.io.IOException;
import java.io.InputStream;

import static de.parcit.didemo.util.InputStreamUtil.toText;

public class TemplatesFromResource implements TemplatesProvider {

    private static final String TEMPLATES_DIR_IN_RESOURCES = "templates/";

    @Override
    public Iterable<String> getAllTemplateNames() {
        try {
            // the template names are stored as lines in a file
            return InputStreamUtil.toLines(inputStreamOfResource("templates/"+".template-names"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream inputStreamOfResource(String resourceName) throws IOException {
        return TemplatesFromResource.class.getResource(resourceName).openStream();
    }

    @Override
    public MailMergeTemplate getTemplateNamed(String templateName) {
        try {
            String text = toText(inputStreamOfResource(TEMPLATES_DIR_IN_RESOURCES +templateName));
            return new MailMergeTemplate() {
                @Override
                public String getName() {
                    return templateName;
                }

                @Override
                public String mailMergedText(Person receiver) {
                    return text.replaceAll("\\{firstName}",receiver.getFirstName())
                            .replaceAll("\\{lastName}",receiver.getLastName())
                            .replaceAll("\\{address}",receiver.getAddress())
                            .replaceAll("\\{salutation}",receiver.getSalutation());
                }
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
