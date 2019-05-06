package de.parcit.didemo.core;

public interface MailMergeTemplate {

    String getName();

    String mailMergedText(Person receiver);
}
