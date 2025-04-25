package br.com.cdb.projetobancodigital.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.cdb.projetobancodigital.entity.Conta;
import br.com.cdb.projetobancodigital.entity.Transacao;
import br.com.cdb.projetobancodigital.enums.TipoTransacao;
import br.com.cdb.projetobancodigital.repository.TransacaoRepository;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public void registrarTransacao(String numeroConta, BigDecimal valor, TipoTransacao tipo, String descricao) {
        Transacao transacao = new Transacao();
        transacao.setNumeroConta(numeroConta);
        transacao.setValor(valor);
        transacao.setTipo(tipo);
        transacao.setDescricao(descricao);
        transacao.setDataHora(LocalDateTime.now());

        transacaoRepository.save(transacao);
    }


    
    public List<Transacao> buscarPorNumeroConta(String numeroConta) {
        return transacaoRepository.findByNumeroConta(numeroConta);
    }

    public void salvar(Transacao transacao) {
        transacaoRepository.save(transacao);
    }
    
    public BigDecimal totalComprasDebitoHoje(String numeroCartao) {
        LocalDate hoje = LocalDate.now();
        return transacaoRepository.findAll().stream()
            .filter(t -> t.getTipo().equals("DÉBITO"))
            .filter(t -> t.getNumeroCartao() != null && t.getNumeroCartao().equals(numeroCartao))
            .filter(t -> t.getDataHora().toLocalDate().isEqual(hoje))
            .map(Transacao::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public void registrarTransacaoDebito(Conta conta, BigDecimal valor, String numeroCartao) {
        Transacao transacao = new Transacao();
        transacao.setNumeroConta(conta.getNumeroConta());
        transacao.setValor(valor);
        transacao.setTipo(TipoTransacao.PAGAMENTO_DEBITO);
        transacao.setDescricao("Compra com cartão de débito");
        transacao.setDataHora(LocalDateTime.now());
        transacao.setNumeroConta(conta.getNumeroConta());
        transacao.setNumeroCartao(numeroCartao);
        transacaoRepository.save(transacao);
    }
}

