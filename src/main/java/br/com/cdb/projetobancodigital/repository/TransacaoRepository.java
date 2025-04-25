package br.com.cdb.projetobancodigital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cdb.projetobancodigital.entity.Transacao;
import br.com.cdb.projetobancodigital.enums.TipoTransacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByNumeroConta(String numeroConta);
    List<Transacao> findByNumeroContaAndTipo(String numeroConta, TipoTransacao tipo);
    List<Transacao> findByNumeroCartaoAndTipoAndQuitadaFalse(String numeroCartao, TipoTransacao tipo);


}