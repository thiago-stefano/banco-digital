package br.com.cdb.projetobancodigital.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Random;

import br.com.cdb.projetobancodigital.enums.TipoConta;

@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numeroConta;

    private String agencia = "0001";
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(unique = true)
    private String chavePix;

    @Enumerated(EnumType.STRING)
    private TipoConta tipo;

    @ManyToOne
    private Cliente cliente;


    @PrePersist
    private void gerarNumeroContaAutomaticamente() {
        if (this.numeroConta == null || this.numeroConta.isEmpty()) {
            this.numeroConta = gerarNumeroConta();
        }
    }


    private String gerarNumeroConta() {
        Random random = new Random();
        int parte1 = 10000 + random.nextInt(90000); 
        int parte2 = random.nextInt(10);            
        return parte1 + "-" + parte2;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
