package br.com.cdb.projetobancodigital.service;

import br.com.cdb.projetobancodigital.dto.*;
import br.com.cdb.projetobancodigital.entity.Cartao;
import br.com.cdb.projetobancodigital.entity.Conta;
import br.com.cdb.projetobancodigital.entity.Transacao;
import br.com.cdb.projetobancodigital.enums.TipoCartao;
import br.com.cdb.projetobancodigital.enums.TipoTransacao;
import br.com.cdb.projetobancodigital.exception.EntidadeNaoEncontradaException;
import br.com.cdb.projetobancodigital.exception.RegraNegocioException;
import br.com.cdb.projetobancodigital.repository.CartaoRepository;
import br.com.cdb.projetobancodigital.repository.ContaRepository;
import br.com.cdb.projetobancodigital.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CartaoService {

    private final CartaoRepository cartaoRepository;
    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    @Autowired
    public CartaoService(CartaoRepository cartaoRepository, ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
        this.cartaoRepository = cartaoRepository;
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    // ===================== MÉTODOS AUXILIARES =========================

    private Cartao buscarOuFalhar(Long id) {
        return cartaoRepository.findById(id).orElseThrow(() ->
            new EntidadeNaoEncontradaException("Cartão com ID " + id + " não encontrado", "404"));
    }

    private String gerarNumeroCartaoUnico() {
        String numero;
        do {
            numero = new Random().ints(16, 0, 10)
                .mapToObj(String::valueOf)
                .reduce("", String::concat);
        } while (cartaoRepository.existsByNumero(numero));
        return numero;
    }

    private CartaoResponseDTO toResponseDTO(Cartao cartao) {
        CartaoResponseDTO dto = new CartaoResponseDTO();
        dto.setId(cartao.getId());
        dto.setNumero(cartao.getNumero());
        dto.setNomeTitular(cartao.getNomeTitular());
        dto.setTipo(cartao.getTipo());
        dto.setValidade(cartao.getValidade());
        dto.setAtivo(cartao.isAtivo());
        dto.setLimite(cartao.getLimite());
        dto.setLimiteDiario(cartao.getLimiteDiario());
        return dto;
    }

    // ===================== FUNCIONALIDADES =========================

    public CartaoResponseDTO emitirCartao(CartaoRequestDTO dto) {
        Conta conta = contaRepository.findByNumeroConta(dto.getNumeroConta())
            .orElseThrow(() -> new EntidadeNaoEncontradaException("Conta não encontrada", "404"));

        Cartao cartao = new Cartao();
        cartao.setConta(conta);
        cartao.setNumero(gerarNumeroCartaoUnico(dto.getTipo()));
        cartao.setTipo(dto.getTipo());
        cartao.setNomeTitular(dto.getNomeTitular());
        cartao.setSenha(dto.getSenha());
        cartao.setAtivo(true);
        cartao.setValidade(LocalDate.now().plusYears(5));

        if (dto.getTipo() == TipoCartao.CREDITO) {
            cartao.setLimite(dto.getLimite() != null ? dto.getLimite() : BigDecimal.valueOf(1000));
            cartao.setFatura(BigDecimal.ZERO);
        } else {
            cartao.setLimiteDiario(dto.getLimiteDiario() != null ? dto.getLimiteDiario() : BigDecimal.valueOf(500));
        }

        cartaoRepository.save(cartao);
        return toResponseDTO(cartao);
    }
    
    private String gerarNumeroCartaoUnico(TipoCartao tipoCartao) {
        String prefixo;


        if (tipoCartao == TipoCartao.CREDITO) {
            prefixo = "5454";
        } else {
            prefixo = "4024";
        }

        StringBuilder numero = new StringBuilder(prefixo);
        for (int i = 0; i < 12; i++) {
            numero.append(new Random().nextInt(10));
        }

        String numeroFinal = numero.toString();


        while (cartaoRepository.existsByNumero(numeroFinal)) {
            numeroFinal = gerarNumeroCartaoUnico(tipoCartao);
        }

        return numeroFinal;
    }


    public CartaoResponseDTO buscarCartao(Long id) {
        return toResponseDTO(buscarOuFalhar(id));
    }

    public void alterarSenha(AlterarSenhaDTO dto) {
        Cartao cartao = buscarOuFalhar(dto.getId());

        if (!cartao.getSenha().equals(dto.getSenhaAtual())) {
            throw new RegraNegocioException("Senha atual incorreta.");
        }

        cartao.setSenha(dto.getNovaSenha());
        cartaoRepository.save(cartao);
    }

    public void alterarLimite(AlterarLimiteDTO dto) {
        Cartao cartao = buscarOuFalhar(dto.getId());

        if (cartao.getTipo() != TipoCartao.CREDITO) {
            throw new RegraNegocioException("Apenas cartões de crédito possuem limite.");
        }

        cartao.setLimite(dto.getNovoLimite());
        cartaoRepository.save(cartao);
    }

    public void alterarLimiteDiario(Long id, BigDecimal novoLimite) {
        Cartao cartao = buscarOuFalhar(id);

        if (cartao.getTipo() != TipoCartao.DEBITO) {
            throw new RegraNegocioException("Apenas cartões de débito possuem limite diário.");
        }

        cartao.setLimiteDiario(novoLimite);
        cartaoRepository.save(cartao);
    }

    public void alterarStatus(AlterarStatusDTO dto) {
        Cartao cartao = buscarOuFalhar(dto.getId());
        cartao.setAtivo(dto.isAtivo());
        cartaoRepository.save(cartao);
    }

    public void realizarPagamento(PagamentoCartaoDTO dto) {
        Cartao cartao = buscarOuFalhar(dto.getId());

        if (!cartao.isAtivo()) throw new RegraNegocioException("Cartão inativo.");
        if (!cartao.getSenha().equals(dto.getSenha())) throw new RegraNegocioException("Senha incorreta.");

        BigDecimal valor = dto.getValor();
        Conta conta = cartao.getConta();

        if (cartao.getTipo() == TipoCartao.DEBITO) {
            if (conta.getSaldo().compareTo(valor) < 0) {
                throw new RegraNegocioException("Saldo insuficiente.");
            }
            if (cartao.getLimiteDiario().compareTo(valor) < 0) {
                throw new RegraNegocioException("Limite diário excedido.");
            }
            conta.setSaldo(conta.getSaldo().subtract(valor));
            cartao.setLimiteDiario(cartao.getLimiteDiario().subtract(valor));
        } else if (cartao.getTipo() == TipoCartao.CREDITO) {
            if (cartao.getLimite().compareTo(valor) < 0) {
                throw new RegraNegocioException("Limite do cartão excedido.");
            }
            cartao.setLimite(cartao.getLimite().subtract(valor));
            cartao.setFatura(cartao.getFatura().add(valor));
        }

        Transacao transacao = new Transacao();
        transacao.setNumeroConta(conta != null ? conta.getNumeroConta() : "CREDITO-SEM-CONTA");
        transacao.setValor(valor);
        transacao.setTipo(cartao.getTipo() == TipoCartao.DEBITO ? TipoTransacao.PAGAMENTO_DEBITO : TipoTransacao.PAGAMENTO_CREDITO);
        transacao.setDataHora(LocalDateTime.now());
        transacao.setDescricao(dto.getDescricao() != null ? dto.getDescricao() : "Pagamento com cartão");
        transacao.setNumeroCartao(cartao.getNumero());

        transacaoRepository.save(transacao);
        cartaoRepository.save(cartao);
        contaRepository.save(conta);
    }

    public List<Transacao> consultarFatura(Long idCartao) {
        Cartao cartao = buscarOuFalhar(idCartao);

        if (cartao.getTipo() != TipoCartao.CREDITO) {
            throw new RegraNegocioException("Somente cartões de crédito possuem fatura.");
        }

        return transacaoRepository.findByNumeroCartaoAndTipoAndQuitadaFalse(
            cartao.getNumero(),
            TipoTransacao.PAGAMENTO_CREDITO
        );
    }

    public void pagarFatura(Long idCartao, PagamentoFaturaDTO dto) {
        Cartao cartao = buscarOuFalhar(idCartao);

        if (cartao.getTipo() != TipoCartao.CREDITO) {
            throw new RegraNegocioException("Somente cartões de crédito possuem fatura.");
        }

        BigDecimal valor = dto.getValor();
        Conta conta = cartao.getConta();

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("Valor inválido.");
        }

        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new RegraNegocioException("Saldo insuficiente para pagamento da fatura.");
        }

        if (valor.compareTo(cartao.getFatura()) > 0) {
            throw new RegraNegocioException("Valor excede o total da fatura.");
        }


        conta.setSaldo(conta.getSaldo().subtract(valor));
        cartao.setLimite(cartao.getLimite().add(valor));

  
        BigDecimal novaFatura = cartao.getFatura().subtract(valor);
        cartao.setFatura(novaFatura.compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : novaFatura);

 
        List<Transacao> transacoesPendentes = transacaoRepository.findByNumeroCartaoAndTipoAndQuitadaFalse(
            cartao.getNumero(), TipoTransacao.PAGAMENTO_CREDITO
        );

        BigDecimal restante = valor;
        for (Transacao t : transacoesPendentes) {
            if (restante.compareTo(BigDecimal.ZERO) <= 0) break;

            if (restante.compareTo(t.getValor()) >= 0) {
                t.setQuitada(true);
                restante = restante.subtract(t.getValor());
            }
        }


        Transacao transacao = new Transacao();
        transacao.setNumeroConta(conta.getNumeroConta());
        transacao.setValor(valor);
        transacao.setTipo(TipoTransacao.PAGAMENTO_FATURA);
        transacao.setDataHora(LocalDateTime.now());
        transacao.setDescricao("Pagamento de fatura do cartão");
        transacao.setNumeroCartao(cartao.getNumero());
        transacao.setQuitada(true);


        transacaoRepository.save(transacao);
        transacaoRepository.saveAll(transacoesPendentes);
        cartaoRepository.save(cartao);
        contaRepository.save(conta);
    }


    public List<Cartao> listarCartoesPorConta(String numeroConta) {
        Conta conta = contaRepository.findByNumeroConta(numeroConta)
            .orElseThrow(() -> new EntidadeNaoEncontradaException("Conta não encontrada","404"));
        return cartaoRepository.findByConta(conta);
    }
}
