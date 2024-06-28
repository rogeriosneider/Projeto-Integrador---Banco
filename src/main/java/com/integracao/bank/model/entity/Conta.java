package com.integracao.bank.model.entity;

import com.integracao.bank.model.enums.SituacaoContaEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "conta")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero", nullable = false, unique = true)
    private Long numero;

    @PositiveOrZero
    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo;

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false)
    private SituacaoContaEnum situacao;

}
