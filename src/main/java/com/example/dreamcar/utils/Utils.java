package com.example.dreamcar.utils;

public class Utils {

    private Utils() {}
    public static String composeJSONMessage(String message) {
        return "{\"message\": \"" + message + "\"}";
    }
}
