package de.parcit.didemo.outside.printer;


import de.parcit.didemo.app.Printer;

public class PrinterToSystemOut implements Printer {

    @Override
    public void print(String textToPrint) {
        System.out.println(textToPrint);
    }
}
