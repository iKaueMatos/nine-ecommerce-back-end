package com.api.apibackend.ContactNewsletter.Domain.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.apibackend.Auth.Validation.AutheticationValidationServiceHandler;
import com.api.apibackend.ContactNewsletter.Application.DTOs.ContactRequest;

@Service
public class ValidateContactClient {
    
    @Autowired
    private AutheticationValidationServiceHandler clientValidationServiceHandler;

    public boolean validateContactHandler(ContactRequest contactRequest) {
        if (contactRequest != null) {
            boolean isValidEmail = clientValidationServiceHandler.isValidEmail(contactRequest.getEmail()) != null;

            if (isValidEmail) {
                return true; 
            }
        }
        return false;
    }
}
