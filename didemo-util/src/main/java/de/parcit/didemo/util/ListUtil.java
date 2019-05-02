package de.parcit.didemo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUtil {
    public static <T> List<T> toList(Iterable<T> iterable) {
        List<T> result = new ArrayList<>();
        for (T e : iterable) {
            result.add(e);
        }
        return result;
    }

    @SafeVarargs
    public static <T> List<T> toList(T... items) {
        return Arrays.asList(items);
    }
}
