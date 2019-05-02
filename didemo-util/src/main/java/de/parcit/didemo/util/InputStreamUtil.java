package de.parcit.didemo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static de.parcit.didemo.util.StreamUtil.toList;

public class InputStreamUtil {
    public static String toText(InputStream input) throws IOException {
        return String.join("\n", toLines(input));
    }

    public static List<String> toLines(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return toList(buffer.lines());
        }
    }
}
