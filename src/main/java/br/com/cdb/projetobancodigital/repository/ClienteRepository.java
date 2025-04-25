package br.com.cdb.projetobancodigital.repository;

import br.com.cdb.projetobancodigital.entity.Cliente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	Optional<Cliente> findByCpf(String cpf);
	Optional<Cliente> findByEmail(String email);
}
