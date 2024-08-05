package com.api.apibackend.modules.Order.Domain.exception;

/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */

public class OrderCannotBeCreatedException extends Exception {
    public OrderCannotBeCreatedException(String message) {
        super(message);
    }
}
