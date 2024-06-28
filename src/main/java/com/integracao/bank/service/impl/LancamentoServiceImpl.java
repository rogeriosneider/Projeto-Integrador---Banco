package com.integracao.bank.service.impl;

import com.integracao.bank.exceptions.SaldoException;
import com.integracao.bank.model.dto.LancamentoDto;
import com.integracao.bank.model.entity.Conta;
import com.integracao.bank.model.entity.Lancamento;
import com.integracao.bank.model.enums.TipoLancamentoEnum;
import com.integracao.bank.model.enums.TipoOperacaoEnum;
import com.integracao.bank.repository.LancamentoRepository;
import com.integracao.bank.service.ContaService;
import com.integracao.bank.service.LancamentoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private ContaService contaService;

    @Transactional
    @Override
    public Lancamento deposito(LancamentoDto dto) {
        Conta conta = contaService.getConta(dto.getContaNumero());
        atualizarSaldo(conta, dto.getValor());
        return criarLancamento(conta, dto.getValor(), TipoOperacaoEnum.CREDITO, TipoLancamentoEnum.DEPOSITO, generateProtocolo());
    }

    @Transactional
    @Override
    public Lancamento saque(LancamentoDto dto) {
        Conta conta = contaService.getConta(dto.getContaNumero());
        validaSaldo(conta.getSaldo(), dto.getValor());
        atualizarSaldo(conta, dto.getValor().negate());
        return criarLancamento(conta, dto.getValor(), TipoOperacaoEnum.DEBITO, TipoLancamentoEnum.SAQUE, generateProtocolo());
    }

    @Transactional
    @Override
    public List<Lancamento> transferir(Long contaOrigemId, Long contaDestinoId, BigDecimal valor) {
        Conta contaOrigem = contaService.getConta(contaOrigemId);
        Conta contaDestino = contaService.getConta(contaDestinoId);

        validaSaldo(contaOrigem.getSaldo(), valor);

        long protocolo = generateProtocolo();
        List<Lancamento> lancamentos = new ArrayList<>();

        atualizarSaldo(contaOrigem, valor.negate());
        lancamentos.add(criarLancamento(contaOrigem, valor, TipoOperacaoEnum.DEBITO, TipoLancamentoEnum.TRANSFERENCIA, protocolo));

        atualizarSaldo(contaDestino, valor);
        lancamentos.add(criarLancamento(contaDestino, valor, TipoOperacaoEnum.CREDITO, TipoLancamentoEnum.TRANSFERENCIA, protocolo));

        return lancamentos;
    }

    @Override
    public List<LancamentoDto> listaLancamentosConta(Long contaNumero) {
        return lancamentoRepository.findAllByContaNumero(contaNumero)
                .stream().map(LancamentoDto::fromLancamento)
                .collect(Collectors.toList());
    }

    @Override
    public List<LancamentoDto> listaLancamentosPeloProtocolo(Long protocolo) {
        return lancamentoRepository.findAllByProtocolo(protocolo)
                .stream().map(LancamentoDto::fromLancamento)
                .collect(Collectors.toList());
    }

    private void atualizarSaldo(Conta conta, BigDecimal valor) {
        conta.setSaldo(conta.getSaldo().add(valor));
        contaService.atualizaConta(conta);
    }

    private Lancamento criarLancamento(Conta conta, BigDecimal valor, TipoOperacaoEnum tipoOperacao, TipoLancamentoEnum tipoLancamento, Long protocolo) {
        Lancamento lancamento = Lancamento.builder()
                .protocolo(protocolo)
                .conta(conta)
                .tipoOperacao(tipoOperacao)
                .tipoLancamento(tipoLancamento)
                .valor(valor)
                .dataHora(LocalDateTime.now())
                .build();
        return lancamentoRepository.save(lancamento);
    }

    private void validaSaldo(BigDecimal saldo, BigDecimal valor) {
        if (saldo.compareTo(valor) < 0) {
            throw new SaldoException("Saldo da conta insuficiente.");
        }
    }

    private Long generateProtocolo() {
        long timestamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
        long randomValue = ThreadLocalRandom.current().nextLong(1000);
        return timestamp * 1000 + randomValue;
    }
}