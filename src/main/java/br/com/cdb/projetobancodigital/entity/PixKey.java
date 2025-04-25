package br.com.cdb.projetobancodigital.entity;

import br.com.cdb.projetobancodigital.enums.TipoChavePix;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class PixKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoChavePix tipo;

    private String valor;

    @ManyToOne
    private Cliente cliente;

    public PixKey() {}

    public PixKey(TipoChavePix tipo, String valor, Cliente cliente) {
        this.tipo = tipo;
        this.valor = valor;
        this.cliente = cliente;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoChavePix getTipo() {
		return tipo;
	}

	public void setTipo(TipoChavePix tipo) {
		this.tipo = tipo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}

