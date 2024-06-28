package com.integracao.bank.controller;

import com.integracao.bank.model.dto.ContaDto;
import com.integracao.bank.model.entity.Conta;
import com.integracao.bank.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conta")
@Slf4j
public class ContaController {

    @Autowired
    private ContaService contaService;

    @Operation(summary = "Abrir uma nova conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    public ResponseEntity<ContaDto> criarConta() {
        log.info("Iniciando criação de conta.");
        Conta conta = contaService.abreConta();
        log.info("Conta {} criada com sucesso!", conta.getNumero());
        return ResponseEntity.status(HttpStatus.CREATED).body(ContaDto.fromConta(conta));
    }

    @Operation(summary = "Retornar uma lista com todas as contas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contas encontradas"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Não foram encontradas contas")
    })
    @GetMapping
    public ResponseEntity<List<ContaDto>> listaContas() {
        List<ContaDto> contas = contaService.listaContas();
        if (contas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(contas);
    }

    @Operation(summary = "Retornar dados de uma conta pelo número")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta encontrada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    @GetMapping("/{contaNr}")
    public ResponseEntity<ContaDto> getConta(@PathVariable Long contaNr){
        return contaService.buscaConta(contaNr)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Encerrar uma conta pelo número")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conta encerrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    @DeleteMapping("/{contaNr}")
    public ResponseEntity<ContaDto> encerraConta(@PathVariable Long contaNr) {
        ContaDto contaDto = contaService.encerraConta(contaNr);
        return ResponseEntity.ok(contaDto);
    }
}