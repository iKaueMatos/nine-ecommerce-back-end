/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */

package com.api.apibackend.modules.Customer.Domain.exception;

public class ErrorEmptyCustomer extends Exception {
    public ErrorEmptyCustomer(String message) {
        super(message);
    }
}
