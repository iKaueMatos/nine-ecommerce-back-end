/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */

package com.api.apibackend.modules.OrderItem.Domain.exception;

public class NonExistentesItemsException extends Exception {
    public NonExistentesItemsException(String message) {
        super(message);
    }
}
