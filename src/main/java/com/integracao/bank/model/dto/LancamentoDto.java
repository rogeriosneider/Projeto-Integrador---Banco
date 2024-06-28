package com.integracao.bank.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.integracao.bank.model.entity.Lancamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class LancamentoDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Identificador do lançamento", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Protocolo do lançamento", example = "1234567890", accessMode = Schema.AccessMode.READ_ONLY)
    private Long protocolo;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Tipo da operação do lançamento", accessMode = Schema.AccessMode.READ_ONLY)
    private String tipoOperacao;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Tipo do lançamento", accessMode = Schema.AccessMode.READ_ONLY)
    private String tipoLancamento;

    @PositiveOrZero(message = "O valor deve ser igual ou maior que zero.")
    @Schema(description = "Valor do lançamento", example = "150.00", required = true)
    private BigDecimal valor;

    @NotNull(message = "Número da conta não pode ser nulo.")
    @Schema(description = "Número da conta", example = "12345", required = true)
    private Long contaNumero;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Data e hora do lançamento", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dataHora;

    public static LancamentoDto fromLancamento(Lancamento lancamento) {
        return LancamentoDto.builder()
                .id(lancamento.getId())
                .protocolo(lancamento.getProtocolo())
                .tipoOperacao(lancamento.getTipoOperacao().name())
                .tipoLancamento(lancamento.getTipoLancamento().name())
                .valor(lancamento.getValor())
                .contaNumero(lancamento.getConta().getNumero())
                .dataHora(lancamento.getDataHora())
                .build();
    }
}
