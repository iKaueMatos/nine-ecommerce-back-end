package com.api.apibackend.modules.Auth.Domain.exception;

/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */

public class InvalidGenerateTokenException extends Exception {
    public InvalidGenerateTokenException(String menssage) {
        super(menssage);
    }
}
