/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.api.apibackend.modules.Product.Application.useCase.ProductDeactivate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.apibackend.modules.Product.Application.DTOs.ResponseMessageDTO;
import com.api.apibackend.modules.Product.Domain.service.ProductDeactivateService;

@Service
public class ProductDeactivateUseCase {
    private ProductDeactivateService productDeactivateService;

    @Autowired
    public ProductDeactivateUseCase(ProductDeactivateService productDeactivateService) {
        this.productDeactivateService = productDeactivateService;
    }
    

    public ResponseEntity<ResponseMessageDTO> execute(Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageDTO("id do produto inválido", this.getClass().getName(), null));
        }
        
        return productDeactivateService.deactivate(id);
    }
}
