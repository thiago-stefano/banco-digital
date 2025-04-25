package br.com.cdb.projetobancodigital.entity;

import br.com.cdb.projetobancodigital.validation.CPFValido;
import br.com.cdb.projetobancodigital.validation.TelefoneFormatoValido;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String nome;

	@CPFValido
	@Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Formato do CPF deve ser xxx.xxx.xxx-xx")
	private String cpf;

	@Email
	private String email;

	@TelefoneFormatoValido
	private String telefone;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dataNascimento;

	@Embedded
	private Endereco endereco;
	@Column(nullable = false)
	private boolean emailVerificado = false;

	private String codigoVerificacao;
	
	public boolean isEmailVerificado() {
		return emailVerificado;
	}

	public void setEmailVerificado(boolean emailVerificado) {
		this.emailVerificado = emailVerificado;
	}

	public String getCodigoVerificacao() {
		return codigoVerificacao;
	}

	public void setCodigoVerificacao(String codigoVerificacao) {
		this.codigoVerificacao = codigoVerificacao;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

}
