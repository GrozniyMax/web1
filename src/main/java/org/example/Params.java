package org.example;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

class Params {
    private final float x;
    private final float y;
    private final float r;

//    public Params(String query) throws ValidationException {
//
//        if (query == null || query.isEmpty()) {
//            throw new ValidationException("Missing query string");
//        }
//        var params = splitQuery(query);
//        validateParams(params);
//        this.x = Float.parseFloat(params.get("x"));
//        this.y = Float.parseFloat(params.get("y"));
//        this.r = Float.parseFloat(params.get("r"));
//    }

    public Params(float x, float y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    private static Map<String, String> splitQuery(String query) {
        var queryPairs = new HashMap<String, String>();
        var pairs = query.split("&");
        for (var pair : pairs) {
            var idx = pair.indexOf("=");
            queryPairs.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8), URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
        }
        return queryPairs;
    }


    private static void validateParams(Map<String, String> params) throws ValidationException {
        var x = params.get("x");
        if (x == null || x.isEmpty()) {
            throw new ValidationException("x is invalid");
        }

        try {
            var xx = Float.parseFloat(x);
        } catch (NumberFormatException e) {
            throw new ValidationException("x is not a number");
        }

        var y = params.get("y");
        if (y == null || y.isEmpty()) {
            throw new ValidationException("y is invalid");
        }

        try {
            var yy = Float.parseFloat(y);
        } catch (NumberFormatException e) {
            throw new ValidationException("y is not a number");
        }

        var r = params.get("r");
        if (r == null || r.isEmpty()) {
            throw new ValidationException("r is invalid");
        }

        try {
            var rr = Float.parseFloat(r);
        } catch (NumberFormatException e) {
            throw new ValidationException("r is not a number");
        }
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getR() {
        return r;
    }
}