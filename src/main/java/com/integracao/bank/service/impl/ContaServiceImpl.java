package com.integracao.bank.service.impl;

import com.integracao.bank.exceptions.NotFoundException;
import com.integracao.bank.exceptions.SaldoException;
import com.integracao.bank.model.dto.ContaDto;
import com.integracao.bank.model.enums.SituacaoContaEnum;
import com.integracao.bank.model.entity.Conta;
import com.integracao.bank.repository.ContaRepository;
import com.integracao.bank.service.ContaService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContaServiceImpl implements ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Transactional
    @Override
    public Conta abreConta() {
        Conta novaConta = Conta.builder()
                .saldo(BigDecimal.ZERO)
                .dataCriacao(LocalDate.now())
                .situacao(SituacaoContaEnum.ATIVA)
                .build();
        return contaRepository.save(novaConta);
    }

    @Override
    public Optional<ContaDto> buscaConta(Long contaId) {
        return contaRepository.findById(contaId).map(ContaDto::fromConta);
    }

    @Override
    public List<ContaDto> listaContas() {
        return contaRepository.findAll().stream().map(ContaDto::fromConta).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ContaDto encerraConta(Long contaNr) {
        Conta conta = getConta(contaNr);
        if (conta.getSaldo().compareTo(BigDecimal.ZERO) > 0) {
            throw new SaldoException("Conta com saldo positivo não pode ser encerrada.");
        }
        conta.setSituacao(SituacaoContaEnum.ENCERRADA);
        return ContaDto.fromConta(contaRepository.save(conta));
    }

    @Override
    public Conta getConta(Long contaId) {
        return contaRepository.findById(contaId)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada."));
    }

    @Transactional
    @Override
    public void atualizaConta(Conta conta) {
        contaRepository.save(conta);
    }
}
