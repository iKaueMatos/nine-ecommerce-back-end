package com.api.apibackend.PriceUnitary.Infra.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.api.apibackend.Product.Infra.entity.ProductEntity;
import com.api.apibackend.Unity.infra.entity.UnityEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "preco_unitario")
@EqualsAndHashCode(of = "id")
public class PriceUnitaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idproduto", nullable = false)
    private ProductEntity productId;

    @ManyToOne
    @JoinColumn(name = "idunidade", nullable = false)
    private UnityEntity unitId;

    @Column(name = "preco", nullable = false)
    private BigDecimal price;

    @Column(name = "data_inicial", nullable = false)
    private LocalDate startDate;

    @Column(name = "data_final")
    private LocalDate endDate;
}
