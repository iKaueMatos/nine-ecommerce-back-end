/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.api.apibackend.modules.Product.Application.useCase.Filter.ProductActiveFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.apibackend.modules.Product.Application.DTOs.ResponseMessageDTO;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("v1/produto")
public class ProductFilterStatusController {
    private ProductFilterStatusUseCase productFilterActiveUseCase;

    @Autowired
    public ProductFilterStatusController(ProductFilterStatusUseCase productFilterActiveUseCase) {
        this.productFilterActiveUseCase = productFilterActiveUseCase;
    }

    @PostMapping(path = "/status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produtos filtrados com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Erro de validação", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    public ResponseEntity<ResponseMessageDTO> handle(
            @RequestParam @PositiveOrZero int status) {
        try {
            if (status != 0 && status != 1) {
                return ResponseEntity.badRequest()
                        .body(new ResponseMessageDTO("Erro de validação!", this.getClass().getName(), "O valor deve ser 0 ou 1"));
            }

            return productFilterActiveUseCase.execute(status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessageDTO("Ocorreu um erro ao processar a requisição!", this.getClass().getName(), e.getMessage()));
        }
    }
}
