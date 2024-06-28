package com.integracao.bank.controller;

import com.integracao.bank.model.dto.LancamentoDto;
import com.integracao.bank.model.entity.Lancamento;
import com.integracao.bank.service.LancamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lancamento")
@Validated
public class LancamentoController {

    @Autowired
    private LancamentoService lancamentoService;

    @Operation(summary = "Criar um novo depósito na conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Depósito criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping("/deposito")
    public ResponseEntity<LancamentoDto> deposito(@RequestBody LancamentoDto dto) {
        Lancamento lancamento = lancamentoService.deposito(dto);
        LancamentoDto lancamentoCriado = LancamentoDto.fromLancamento(lancamento);
        return ResponseEntity.ok(lancamentoCriado);
    }

    @Operation(summary = "Realizar um novo saque na conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saque realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping("/saque")
    public ResponseEntity<LancamentoDto> criarLancamentoSaque(@RequestBody @Valid LancamentoDto lancamentoDto) {
        Lancamento lancamento = lancamentoService.saque(lancamentoDto);
        return ResponseEntity.ok(LancamentoDto.fromLancamento(lancamento));
    }

    @Operation(summary = "Realiza uma transferência entre contas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferéncia realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping("/transferencia/conta/{contaNr}")
    public ResponseEntity<List<LancamentoDto>> criarLancamentoTransferencia(@PathVariable Long contaNr, @RequestBody @Valid LancamentoDto lancamentoDto) {
        List<LancamentoDto> lancamentos = lancamentoService
                .transferir(contaNr, lancamentoDto.getContaNumero(), lancamentoDto.getValor())
                .stream().map(LancamentoDto::fromLancamento).collect(Collectors.toList());
        return ResponseEntity.ok(lancamentos);
    }

    @Operation(summary = "Lista os lançamentos da conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lançamentos encontrados"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Não encontrados lançamentos para a conta informada")
    })
    @GetMapping("/conta/{contaNr}")
    public ResponseEntity<List<LancamentoDto>> listaLancamentosConta(@PathVariable Long contaNr) {
        List<LancamentoDto> lancamentos = lancamentoService.listaLancamentosConta(contaNr);
        if (lancamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lancamentos);
    }

    @Operation(summary = "Lista os lançamentos pelo protocolo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lançamentos encontrados"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Não encontrados lançamentos para a conta informada")
    })
    @GetMapping("/protocolo/{protocolo}")
    public ResponseEntity<List<LancamentoDto>> listaLancamentosProtocolo(@PathVariable Long protocolo) {
        List<LancamentoDto> lancamentos = lancamentoService.listaLancamentosPeloProtocolo(protocolo);
        if (lancamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lancamentos);
    }


}
