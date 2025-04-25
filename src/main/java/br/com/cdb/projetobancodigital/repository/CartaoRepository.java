package br.com.cdb.projetobancodigital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cdb.projetobancodigital.entity.Cartao;
import br.com.cdb.projetobancodigital.entity.Conta;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    List<Cartao> findByContaNumeroConta(String numeroConta);

    Optional<Cartao> findByNumero(String numero);
    
    List<Cartao> findByConta(Conta conta); 

    boolean existsByNumero(String numero);
}
