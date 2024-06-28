package com.integracao.bank.repository;

import com.integracao.bank.model.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    List<Lancamento> findAllByContaNumero(Long contaNumero);

    List<Lancamento> findAllByProtocolo(Long protocolo);
}
