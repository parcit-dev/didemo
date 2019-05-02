package de.parcit.didemo.app;

import de.parcit.didemo.core.MailMergeTemplate;

public interface TemplatesProvider {

    Iterable<String> getAllTemplateNames();

    MailMergeTemplate getTemplateNamed(String templateName);
}
