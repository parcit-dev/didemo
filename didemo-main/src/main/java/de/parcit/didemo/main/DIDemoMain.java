package de.parcit.didemo.main;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import de.parcit.didemo.app.MailMergePrinting;
import de.parcit.didemo.app.MailMergeUI;
import de.parcit.didemo.app.PersonsStore;
import de.parcit.didemo.app.Printer;
import de.parcit.didemo.app.TemplatesProvider;
import de.parcit.didemo.outside.printer.PrinterToSwingDialog;
import de.parcit.didemo.outside.store.PersonsStoreFileBased;
import de.parcit.didemo.outside.swingui.MailMergeSwingUI;
import de.parcit.didemo.outside.templatesprovider.TemplatesFromResource;

@SuppressWarnings("WeakerAccess")
public class DIDemoMain {

    private static final Injector injector = Guice.createInjector(new GuiceModuleForDIDemoMain());

    public static void main(String[] args) {

        MailMergePrinting app =
                injector.getInstance(MailMergePrinting.class);

        app.printMailMergeWithUI();
    }

    private static class GuiceModuleForDIDemoMain extends AbstractModule {
        @Override
        protected void configure() {

            bind(MailMergeUI.class).to(MailMergeSwingUI.class);
            bind(PersonsStore.class).to(PersonsStoreFileBased.class);
            bind(TemplatesProvider.class).to(TemplatesFromResource.class);
            bind(Printer.class).to(PrinterToSwingDialog.class);
        }
    }
}
