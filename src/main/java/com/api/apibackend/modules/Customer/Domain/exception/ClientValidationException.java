/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */

package com.api.apibackend.modules.Customer.Domain.exception;

public class ClientValidationException extends RuntimeException {
    public ClientValidationException(String message) {
        super(message);
    }
}
