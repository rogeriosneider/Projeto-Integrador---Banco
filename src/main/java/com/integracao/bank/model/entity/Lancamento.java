package com.integracao.bank.model.entity;

import com.integracao.bank.model.enums.TipoLancamentoEnum;
import com.integracao.bank.model.enums.TipoOperacaoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lancamento")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "protocolo", nullable = false, unique = true)
    private Long protocolo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tp_operacao", nullable = false)
    private TipoOperacaoEnum tipoOperacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tp_lancamento", nullable = false)
    private TipoLancamentoEnum tipoLancamento;

    @PositiveOrZero
    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nr_conta", nullable = false)
    private Conta conta;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;
}
