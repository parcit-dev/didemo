package de.parcit.didemo.util;

import javax.swing.*;
import java.awt.*;

public class SwingUtil {

    public static JComponent withLabelAboveComponent(String labelText, JComponent component) {
        JPanel result = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        Font font = label.getFont();
        label.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize()));
        result.add(label, BorderLayout.NORTH);
        result.add(component, BorderLayout.CENTER);
        return result;
    }

    public static JComponent inFlowRightAligned(JComponent... components) {
        JPanel result = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        for (JComponent c : components) {
            result.add(c);
        }
        return result;
    }

    public static JComponent leftRightSplit(int dividerLocation, JComponent left, JComponent right) {
        JSplitPane result = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        result.setDividerLocation(dividerLocation);
        return result;
    }
}
