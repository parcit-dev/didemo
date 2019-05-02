package de.parcit.didemo.outside.printer;

import de.parcit.didemo.app.Printer;

import javax.swing.*;
import java.awt.*;

public class PrinterToSwingDialog implements Printer {

    private static final int DIALOG_WIDTH = 600;
    private static final int DIALOG_HEIGHT = 800;

    @Override
    public void print(String textToPrint) {
        JDialog dlg = new JDialog();
        dlg.setTitle("Printer Output");
        dlg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dlg.getContentPane().setLayout(new BorderLayout());


        JTextArea textArea = new JTextArea();
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(l -> dlg.dispose());

        dlg.add(new JScrollPane(textArea));
        dlg.add(closeBtn, BorderLayout.SOUTH);

        textArea.setText(textToPrint);

        dlg.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        dlg.setLocationRelativeTo(null);

        dlg.setModal(true);
        dlg.setVisible(true);

    }
}
