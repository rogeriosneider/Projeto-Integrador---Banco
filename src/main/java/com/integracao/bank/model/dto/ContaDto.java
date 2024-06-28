package com.integracao.bank.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.integracao.bank.model.entity.Conta;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ContaDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Número da conta", accessMode = Schema.AccessMode.READ_ONLY)
    private Long numero;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Saldo da conta", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal saldo;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Data de abertura conta", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate dataCriacao;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Situação da conta", accessMode = Schema.AccessMode.READ_ONLY)
    private String situacao;

    public static ContaDto fromConta(Conta conta) {
        return ContaDto.builder()
                .numero(conta.getNumero())
                .saldo(conta.getSaldo())
                .dataCriacao(conta.getDataCriacao())
                .situacao(conta.getSituacao().name())
                .build();
    }
}