package com.emazon.msvc_user.domain.api.usecase;

import com.emazon.msvc_user.domain.model.User;
import com.emazon.msvc_user.domain.spi.IRolePersistencePort;

import java.util.Calendar;
import java.util.Date;

public class ValidateUser {
    private final IRolePersistencePort rolePersistencePort;

    public ValidateUser(IRolePersistencePort rolePersistencePort) {
        this.rolePersistencePort = rolePersistencePort;
    }

    public void validateUserData(User user) {
        // Validate name and lastName
        if (isBlank(user.getName())) {
            throw new IllegalArgumentException("Name is mandatory");
        }
        if (isBlank(user.getLastName())) {
            throw new IllegalArgumentException("Last name is mandatory");
        }

        // Validate document ID (only numeric)
        if (user.getDocumentId() == null || !String.valueOf(user.getDocumentId()).matches("\\d+")) {
            throw new IllegalArgumentException("Document must be numeric");
        }

        // Validate mobile number (up to 13 characters, may start with +)
        if (user.getMobileNumber() == null || !user.getMobileNumber().matches("^\\+?\\d{1,13}$")) {
            throw new IllegalArgumentException("Mobile number must be up to 13 characters: 12 digits and additionally can start with '+'");
        }

        // Validate email structure
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email should be valid");
        }

        // Validate birthdate (user must be at least 18 years old)
        if (!isAdult(user.getBirthdate())) {
            throw new IllegalArgumentException("User must be an adult (18+ years)");
        }

        if (rolePersistencePort.findRoleByName(user.getRole().getName()) == null){
            throw new IllegalArgumentException("Role must be valid.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && email.matches(emailRegex);
    }

    private boolean isAdult(Date birthdate) {
        if (birthdate == null) {
            return false;
        }

        Date minimumAdultDate = getMinimunAdultBirthdate();
        return birthdate.before(minimumAdultDate);
    }

    private Date getMinimunAdultBirthdate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18);
        return calendar.getTime();
    }
}
