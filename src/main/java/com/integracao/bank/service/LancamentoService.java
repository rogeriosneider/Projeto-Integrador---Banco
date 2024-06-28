package com.integracao.bank.service;

import com.integracao.bank.model.dto.LancamentoDto;
import com.integracao.bank.model.entity.Lancamento;

import java.math.BigDecimal;
import java.util.List;

public interface LancamentoService {

    Lancamento deposito(LancamentoDto dto);

    Lancamento saque(LancamentoDto dto);

    List<Lancamento> transferir(Long contaOrigemId, Long contaDestinoId, BigDecimal valor);

    List<LancamentoDto> listaLancamentosConta(Long contaNumero);

    List<LancamentoDto> listaLancamentosPeloProtocolo(Long protocolo);
}
