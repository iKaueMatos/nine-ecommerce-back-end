/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */

package com.api.apibackend.modules.Stock.Domain.exception;

public class HasEnoughStockProductException extends Exception {
    public HasEnoughStockProductException(String message) {
        super(message);
    }
}
