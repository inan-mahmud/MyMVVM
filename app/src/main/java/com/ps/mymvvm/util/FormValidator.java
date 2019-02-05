package com.ps.mymvvm.util;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
public class FormValidator {

    /**
     * Verifies if input is correct based on empty and length checkup
     *
     * @param input      string to validate
     * @param min_length minimum length to be matched
     * @return true/false
     */
    public static boolean validateFieldsWithLength(String input, int min_length) {
        return !input.isEmpty() && input.length() >= min_length;
    }
}
