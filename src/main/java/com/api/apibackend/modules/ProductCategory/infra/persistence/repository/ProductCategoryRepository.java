/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.api.apibackend.modules.ProductCategory.infra.persistence.repository;

import java.util.List;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.apibackend.modules.Product.Infra.persistence.entity.ProductEntity;
import com.api.apibackend.modules.ProductCategory.infra.persistence.entity.ProductCategoryEntity;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {
    @Query("SELECT p FROM ProductEntity p JOIN p.category c WHERE c.name = :categoryName")
    List<ProductEntity> findByCategoryName(@Param("categoryName") String categoryName);
    ProductCategoryEntity findCategoryByName(String categoryName);
    List<ProductCategoryEntity> findAllByOrderByName();
}
