package ru.otus;

import com.google.common.base.CharMatcher;

public class HelloOtus {
    private static final String PETER_TONGUE_TWISTER = "Peter Piper picked a peck of pickled peppers\n" +
            "A peck of pickled peppers Peter Piper picked\n" +
            "If Peter Piper picked a peck of pickled peppers\n" +
            "Where’s the peck of pickled peppers Peter Piper picked?";
    private static final String RESULT_TEMPLATE = "'p' mentioned %d times";

    public static void main(String... args) {
        System.out.println(PETER_TONGUE_TWISTER);

        CharMatcher matcher = CharMatcher.is('p');
        int matchesCount = matcher.countIn(PETER_TONGUE_TWISTER);
        System.out.println(String.format(RESULT_TEMPLATE, matchesCount));
    }
}
