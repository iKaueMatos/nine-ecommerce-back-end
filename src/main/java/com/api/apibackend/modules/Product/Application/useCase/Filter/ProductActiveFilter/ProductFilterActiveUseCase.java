/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.api.apibackend.modules.Product.Application.useCase.Filter.ProductActiveFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.apibackend.modules.Product.Application.DTOs.ResponseMessageDTO;
import com.api.apibackend.modules.Product.Domain.service.ProductFilterService;

@Service
public class ProductFilterActiveUseCase {
    private ProductFilterService productFilterService;

    @Autowired
    public ProductFilterActiveUseCase(ProductFilterService productFilterService) {
        this.productFilterService = productFilterService;
    }

    public ResponseEntity<ResponseMessageDTO> execute(int status) {
        return productFilterService.processProductFiltering(status);
    }
}
