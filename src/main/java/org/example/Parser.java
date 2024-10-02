package org.example;

import java.util.regex.Pattern;

public class Parser {

    private static Pattern xPattern= Pattern.compile("\"x\".*:.*[0-9]+[.,]*,");

    private static Pattern yPattern = Pattern.compile("\"y\".*:.*[0-9]+[.,]*,");

    private static Pattern rPattern = Pattern.compile("\"r\".*:.*[0-9]+[.,]*,");

    public static Params parse(String input) throws ValidationException {
        float x = 0;
        float y = 0;
        float r = 0;
        try {
            x = Float.parseFloat(parseNumber(input, xPattern).replace("[^0-9]*", ""));
            y = Float.parseFloat(parseNumber(input, yPattern).replace("[^0-9]*", ""));
            r = Float.parseFloat(parseNumber(input, rPattern).replace("[^0-9]*", ""));
            return new Params(x, y, r);
        } catch (NumberFormatException e) {
            throw new ValidationException("Not a number in request");
        }

    }

    private static String parseNumber(String input, Pattern pattern) throws ValidationException {
        var matcher = pattern.matcher(input);
        if (matcher.find()) {
            return input.substring(matcher.start(), matcher.end());
        }
        throw new ValidationException("Invalid json");
    }
}