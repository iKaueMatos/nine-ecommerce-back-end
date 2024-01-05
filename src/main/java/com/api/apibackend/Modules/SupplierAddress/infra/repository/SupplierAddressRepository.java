/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */

package com.api.apibackend.Modules.SupplierAddress.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.apibackend.Modules.SupplierAddress.infra.persistence.entity.SupplierAddressEntity;

@Repository
public interface SupplierAddressRepository extends JpaRepository<SupplierAddressEntity, Long> {
    Optional<SupplierAddressEntity> findByRoadAndNeighborhoodAndNumberCompanyAndCep(
            String road, String neighborhood, String numberHouseOrCompany, String cep);
}
