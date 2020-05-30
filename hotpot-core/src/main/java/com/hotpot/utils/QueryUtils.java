package com.hotpot.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryUtils {

    public static Map<String, String> parseQuery(String q) {
        return Arrays.stream(q.split(","))
            .collect(Collectors.toMap(
                s -> s.split("=")[0],
                s -> s.split("=")[1]
            ));
    }

}
