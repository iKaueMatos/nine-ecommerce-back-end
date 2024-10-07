/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.api.apibackend.modules.Product.Application.DTOs;

import com.api.apibackend.modules.Midia.Application.DTOs.MidiaDTO;
import com.api.apibackend.modules.Price.Application.DTOs.PriceDTO;
import com.api.apibackend.modules.ProductCategory.Application.DTOs.ProductCategoryDTO;
import com.api.apibackend.modules.Stock.Application.DTOs.StockDTO;
import com.api.apibackend.modules.Supplier.Application.DTOs.SupplierDTO;
import com.api.apibackend.modules.Unity.Application.DTOs.UnityDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class ProductDTO {
    
    @NotNull(message = "O campo 'idProduct' não pode ser nulo.")
    private Long idProduct;

    @NotBlank(message = "O nome do produto não pode estar em branco.")
    @Size(min = 2, max = 100, message = "O nome do produto deve ter entre 2 e 100 caracteres.")
    private String name;

    @Size(max = 500, message = "A descrição não pode exceder 500 caracteres.")
    private String description;

    @Min(value = 0, message = "O status deve ser um valor positivo.")
    private int status;

    @NotBlank(message = "O SKU não pode estar em branco.")
    @Size(max = 50, message = "O SKU não pode exceder 50 caracteres.")
    private String sku;

    @JsonProperty("midia")
    private MidiaDTO midia;

    @JsonProperty("category")
    @NotNull(message = "A categoria não pode ser nula.")
    private ProductCategoryDTO category;

    @JsonProperty("supplier")
    @NotNull(message = "O fornecedor não pode ser nulo.")
    private SupplierDTO supplierEntity;

    @JsonProperty("price")
    @NotNull(message = "O preço não pode ser nulo.")
    private PriceDTO priceEntity;

    @JsonProperty("stock")
    @NotNull(message = "O estoque não pode ser nulo.")
    private StockDTO stockEntity;

    @JsonProperty("unity")
    @NotNull(message = "A unidade não pode ser nula.")
    private UnityDTO unityEntity;
}
