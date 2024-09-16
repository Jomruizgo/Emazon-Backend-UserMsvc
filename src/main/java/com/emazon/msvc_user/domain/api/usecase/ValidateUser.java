package com.emazon.msvc_user.domain.api.usecase;

import com.emazon.msvc_user.domain.exceptions.DuplicatedObjectException;
import com.emazon.msvc_user.domain.model.User;
import com.emazon.msvc_user.domain.spi.IRolePersistencePort;
import com.emazon.msvc_user.domain.spi.IUserPersistencePort;
import com.emazon.msvc_user.domain.util.ValidationMessages;

import java.util.Calendar;
import java.util.Date;


public class ValidateUser {
    private final IUserPersistencePort userPersistencePort;

    public ValidateUser(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    public void validateUserData(User user) {
        if (isBlank(user.getName())) {
            throw new IllegalArgumentException(ValidationMessages.NAME_MANDATORY);
        }
        if (isBlank(user.getLastName())) {
            throw new IllegalArgumentException(ValidationMessages.LAST_NAME_MANDATORY);
        }

        if (user.getDocumentId() == null) {
            throw new IllegalArgumentException(ValidationMessages.DOCUMENT_MANDATORY);
        }
        if(userPersistencePort.findUserByDocumentId(user.getDocumentId()) != null) {
            throw new DuplicatedObjectException(ValidationMessages.DUPLICATE_DOCUMENT);
        }

        if (user.getMobileNumber() == null || !user.getMobileNumber().matches("^\\+?\\d{1,13}$")) {
            throw new IllegalArgumentException(ValidationMessages.MOBILE_NUMBER_INVALID);
        }

        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException(ValidationMessages.EMAIL_INVALID);
        }
        if (userPersistencePort.findUserByEmail(user.getEmail()) != null){
            throw new DuplicatedObjectException(ValidationMessages.DUPLICATE_EMAIL);
        }

        if (!isAdult(user.getBirthdate())) {
            throw new IllegalArgumentException(ValidationMessages.USER_NOT_ADULT);
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
