package com.ps.mymvvm.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
public class FormValidatorTest {

    /**
     * Validates inputs based on empty characters and length
     */
    @Test
    public void validateFieldsWithLength() {
        assertFalse(FormValidator.validateFieldsWithLength("p", 3));
        assertFalse(FormValidator.validateFieldsWithLength("prokash-sarkar", 16));
        assertTrue(FormValidator.validateFieldsWithLength("prokash-sarkar", 3));
    }
}