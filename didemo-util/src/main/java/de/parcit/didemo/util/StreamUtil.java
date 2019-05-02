package de.parcit.didemo.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class StreamUtil {
    public static <T> List<T> toList(Stream<T> stream) {
        return stream.collect(Collectors.toList());
    }
}
