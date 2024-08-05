/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */

package com.api.apibackend.modules.Auth.Domain.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DataBindingViolationException extends DataIntegrityViolationException {
    public DataBindingViolationException(String message) {
        super(message);
    }
}
