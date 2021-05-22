package com.shoppingMall.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {

    @Test
    void testEmail(){
        assertTrue(Validation.validateEmail("ade@gmail.com"));
        assertFalse(Validation.validateEmail("ade@gmailcom"));
        assertFalse(Validation.validateEmail("adgmail"));
        assertFalse(Validation.validateEmail("@gmail.com"));
    }

    @Test
    void testPassword(){
        assertTrue(Validation.validatePassword("ade1234"));
        assertFalse(Validation.validatePassword("ade1"));
        assertFalse(Validation.validatePassword("ade12"));
        assertFalse(Validation.validatePassword("ade"));
    }

    @Test
    void testPhoneNumber(){
        assertTrue(Validation.validatePhoneNumber("08100897169"));
        assertTrue(Validation.validatePhoneNumber("2348100897169"));
        assertTrue(Validation.validatePhoneNumber("+2348100897169"));
        assertFalse(Validation.validatePhoneNumber("0810089716"));
        assertFalse(Validation.validatePhoneNumber("18100897169"));
    }

    @Test
    void testFullName(){
        assertTrue(Validation.validateFullName("Ade damilola"));
        assertTrue(Validation.validateFullName("ayo adewale seyi"));
        assertFalse(Validation.validateFullName("Ade"));
        assertFalse(Validation.validateFullName("ayo"));
        assertFalse(Validation.validateFullName("adewale"));
        assertFalse(Validation.validateFullName(""));
    }

    @Test
    void testGender(){
        assertTrue(Validation.validateGender("MALE"));
        assertTrue(Validation.validateGender("FEMALE"));
        assertTrue(Validation.validateGender("male"));
        assertTrue(Validation.validateGender("female"));
        assertFalse(Validation.validateGender("custom"));
        assertFalse(Validation.validateGender("ayo"));
        assertFalse(Validation.validateGender("adewale"));
        assertFalse(Validation.validateGender(""));
    }

}