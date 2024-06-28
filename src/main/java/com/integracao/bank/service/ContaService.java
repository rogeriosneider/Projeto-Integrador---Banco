package com.integracao.bank.service;

import com.integracao.bank.model.dto.ContaDto;
import com.integracao.bank.model.entity.Conta;

import java.util.List;
import java.util.Optional;

public interface ContaService {

    Conta abreConta();

    Optional<ContaDto> buscaConta(Long contaId);

    List<ContaDto> listaContas();

    ContaDto encerraConta(Long contaNr);

    Conta getConta(Long contaId);

    void atualizaConta(Conta conta);
}
