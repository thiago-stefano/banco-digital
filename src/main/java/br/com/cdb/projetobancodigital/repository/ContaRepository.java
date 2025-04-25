package br.com.cdb.projetobancodigital.repository;

import br.com.cdb.projetobancodigital.entity.Conta;
import br.com.cdb.projetobancodigital.enums.TipoConta;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
	Optional<Conta> findByChavePix(String chavePix);
	
	Optional<Conta> findByClienteIdAndTipo(Long clienteId, TipoConta tipo);
	
	Optional<Conta> findByNumeroConta(String numeroConta);
	
	List<Conta> findAllByClienteId(Long clienteId);
	
	boolean existsByNumeroConta(String numeroConta);


}