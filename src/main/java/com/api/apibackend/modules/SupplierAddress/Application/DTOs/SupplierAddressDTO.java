/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */

package com.api.apibackend.modules.SupplierAddress.Application.DTOs;

import com.api.apibackend.modules.Supplier.Application.DTOs.SupplierDTO;

import lombok.Data;

@Data
public class SupplierAddressDTO {
    private Long idSupplierAddress;
    private String road;
    private String neighborhood;
    private String numberHouseOrCompany;
    private String cep;
    private SupplierDTO supplierDTO;
}
