package rvt.studentu_registracija;

import java.util.regex.Pattern;

public class Validator {
    static boolean validateNameSurname(String name) {
        String regexp = "^[A-Za-z]{3,}$"
        return name.matches(regexp);
    }

    static boolean validateEmail(String email) {
        String regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(regexp);
    }

    static boolean validatePerscode(String pers_code) {
        String regexp = "^\\d{6}-\\d{5}$";
        return pers_code.matches(regepx);
    }
}
