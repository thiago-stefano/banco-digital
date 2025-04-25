package br.com.cdb.projetobancodigital.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.cdb.projetobancodigital.dto.ComprovanteTransacaoDTO;
import br.com.cdb.projetobancodigital.dto.ComprovanteTransferenciaDTO;
import br.com.cdb.projetobancodigital.dto.ContaRequestDTO;
import br.com.cdb.projetobancodigital.dto.ContaResponseDTO;
import br.com.cdb.projetobancodigital.dto.PixDTO;
import br.com.cdb.projetobancodigital.dto.TransacaoResponseDTO;
import br.com.cdb.projetobancodigital.entity.Cliente;
import br.com.cdb.projetobancodigital.entity.Conta;
import br.com.cdb.projetobancodigital.entity.Transacao;
import br.com.cdb.projetobancodigital.enums.TipoConta;
import br.com.cdb.projetobancodigital.enums.TipoTransacao;
import br.com.cdb.projetobancodigital.repository.ClienteRepository;
import br.com.cdb.projetobancodigital.repository.ContaRepository;
import br.com.cdb.projetobancodigital.validation.ContaValidator;
import br.com.cdb.projetobancodigital.validation.PixValidator;

@Service
public class ContaService {

	private final TransacaoService transacaoService;
    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;

    public ContaService(ContaRepository contaRepository, ClienteRepository clienteRepository, TransacaoService transacaoService) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
        this.transacaoService = transacaoService;
    }
    
    public List<TransacaoResponseDTO> obterExtrato(String numeroConta) {
        if (!contaRepository.existsByNumeroConta(numeroConta)) {
            throw new RuntimeException("Conta não encontrada");
        }

        List<Transacao> transacoes = transacaoService.buscarPorNumeroConta(numeroConta);

        return transacoes.stream()
                .map(transacao -> new TransacaoResponseDTO(
                        transacao.getTipo(),
                        transacao.getValor(),
                        transacao.getDataHora(),
                        transacao.getDescricao(),
                        transacao.getNumeroConta()
                )).collect(Collectors.toList());
    }


    public ContaResponseDTO criarConta(ContaRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        ContaValidator.validarTipoConta(dto.getTipoConta());
        ContaValidator.validarSaldoInicial(dto.getSaldoInicial());

        Conta conta = new Conta();
        conta.setCliente(cliente);
        conta.setTipo(dto.getTipoConta());
        conta.setSaldo(dto.getSaldoInicial() != null ? dto.getSaldoInicial() : BigDecimal.ZERO);
        contaRepository.save(conta);

        return toDTO(conta);
    }

    public Conta buscarContaPorNumero(String numeroConta) {
        System.out.println("Buscando conta com número: " + numeroConta);
        return contaRepository.findByNumeroConta(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

    public BigDecimal consultarSaldo(String numeroConta) {
        Conta conta = contaRepository.findByNumeroConta(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        return conta.getSaldo();
    }

    public ComprovanteTransacaoDTO depositar(String numeroConta, BigDecimal valor) {
        ContaValidator.validarValorPositivo(valor, "depósito");

        Conta conta = buscarContaPorNumero(numeroConta);
        conta.setSaldo(conta.getSaldo().add(valor));
        contaRepository.save(conta);

        transacaoService.registrarTransacao(conta.getNumeroConta(), valor, TipoTransacao.DEPOSITO, "Depósito realizado");
        
        return new ComprovanteTransacaoDTO(
                conta.getNumeroConta(),
                valor,
                "DEPÓSITO",
                LocalDateTime.now(),
                "Depósito realizado com sucesso"
            );
    }

    public ComprovanteTransacaoDTO sacar(String numeroConta, BigDecimal valor) {
        ContaValidator.validarValorPositivo(valor, "saque");

        Conta conta = buscarContaPorNumero(numeroConta);
        ContaValidator.validarSaldoSuficiente(conta.getSaldo(), valor);

        conta.setSaldo(conta.getSaldo().subtract(valor));
        contaRepository.save(conta);

        transacaoService.registrarTransacao(conta.getNumeroConta(), valor, TipoTransacao.SAQUE, "Saque realizado");
        
        return new ComprovanteTransacaoDTO(
                numeroConta,
                valor,
                "SAQUE",
                LocalDateTime.now(),
                "Saque realizado com sucesso"
            );
    }

    public ComprovanteTransferenciaDTO transferir(String numeroContaOrigem, String numeroContaDestino, BigDecimal valor) {
        ContaValidator.validarValorPositivo(valor, "transferência");
        ContaValidator.validarContasDiferentes(numeroContaOrigem, numeroContaDestino);

        Conta origem = buscarContaPorNumero(numeroContaOrigem);
        Conta destino = buscarContaPorNumero(numeroContaDestino);

        ContaValidator.validarSaldoSuficiente(origem.getSaldo(), valor);

        origem.setSaldo(origem.getSaldo().subtract(valor));
        destino.setSaldo(destino.getSaldo().add(valor));

        contaRepository.save(origem);
        contaRepository.save(destino);

        transacaoService.registrarTransacao(origem.getNumeroConta(), valor, TipoTransacao.TRANSFERENCIA_SAIDA, "Transferência para conta " + destino.getNumeroConta());
        transacaoService.registrarTransacao(destino.getNumeroConta(), valor, TipoTransacao.TRANSFERENCIA_ENTRADA, "Transferência recebida da conta " + origem.getNumeroConta());

        return new ComprovanteTransferenciaDTO(
                "Transferência realizada com sucesso!",
                origem.getCliente().getId(),
                origem.getTipo(),
                destino.getTipo(),
                valor,
                origem.getNumeroConta(),
                destino.getNumeroConta()
        );
    }


    public void aplicarManutencao(String numeroConta, BigDecimal taxa) {
        ContaValidator.validarValorPositivo(taxa, "taxa de manutenção");

        Conta conta = buscarContaPorNumero(numeroConta);
        ContaValidator.validarSaldoSuficiente(conta.getSaldo(), taxa);

        conta.setSaldo(conta.getSaldo().subtract(taxa));
        contaRepository.save(conta);
    }

    public void aplicarRendimento(String numeroConta, BigDecimal taxa) {
        ContaValidator.validarValorPositivo(taxa, "rendimento");

        Conta conta = buscarContaPorNumero(numeroConta);
        BigDecimal rendimento = conta.getSaldo().multiply(taxa);
        conta.setSaldo(conta.getSaldo().add(rendimento));
        contaRepository.save(conta);
    }

    public ComprovanteTransacaoDTO realizarPix(PixDTO dto) {
        PixValidator.validarOuLancarExcecao(dto.getChavePixDestino());
        ContaValidator.validarValorPositivo(dto.getValor(), "Pix");

        Conta origem = buscarContaPorNumero(dto.getNumeroContaOrigem());
        Conta destino = contaRepository.findByChavePix(dto.getChavePixDestino())
            .orElseThrow(() -> new RuntimeException("Chave Pix de destino não encontrada"));

        ContaValidator.validarSaldoSuficiente(origem.getSaldo(), dto.getValor());

        origem.setSaldo(origem.getSaldo().subtract(dto.getValor()));
        destino.setSaldo(destino.getSaldo().add(dto.getValor()));

        contaRepository.save(origem);
        contaRepository.save(destino);


        transacaoService.registrarTransacao(origem.getNumeroConta(), dto.getValor(), TipoTransacao.PIX_ENVIO, "Pix enviado para " + dto.getChavePixDestino());


        transacaoService.registrarTransacao(destino.getNumeroConta(), dto.getValor(), TipoTransacao.PIX_RECEBIMENTO, "Pix recebido de " + origem.getNumeroConta());

        return new ComprovanteTransacaoDTO(
            origem.getNumeroConta(),
            dto.getValor(),
            "PIX",
            LocalDateTime.now(),
            "Pix enviado com sucesso para a chave " + dto.getChavePixDestino()
        );
    }


    public void aplicarTaxaMensal(String numeroConta) {
        Conta conta = buscarContaPorNumero(numeroConta);

        if (conta.getTipo() != TipoConta.CORRENTE) {
            throw new RuntimeException("Taxa mensal só se aplica a contas correntes.");
        }

        BigDecimal taxa = BigDecimal.valueOf(10.00);
        ContaValidator.validarSaldoSuficiente(conta.getSaldo(), taxa);

        conta.setSaldo(conta.getSaldo().subtract(taxa));
        contaRepository.save(conta);
    }

    public void aplicarRendimentoMensal(String numeroConta) {
        Conta conta = buscarContaPorNumero(numeroConta);

        if (conta.getTipo() != TipoConta.POUPANCA) {
            throw new RuntimeException("Rendimento só se aplica a contas poupança.");
        }

        BigDecimal rendimento = conta.getSaldo().multiply(BigDecimal.valueOf(0.005)); // 0.5%
        conta.setSaldo(conta.getSaldo().add(rendimento));
        contaRepository.save(conta);
    }

    public ComprovanteTransferenciaDTO transferirEntreContasDoCliente(Long clienteId, TipoConta origem, TipoConta destino, BigDecimal valor) {
        ContaValidator.validarTransferenciaEntreTipos(origem, destino);
        ContaValidator.validarValorPositivo(valor, "transferência entre contas");

        Conta contaOrigem = contaRepository.findByClienteIdAndTipo(clienteId, origem)
                .orElseThrow(() -> new RuntimeException("Conta de origem não encontrada"));

        Conta contaDestino = contaRepository.findByClienteIdAndTipo(clienteId, destino)
                .orElseThrow(() -> new RuntimeException("Conta de destino não encontrada"));

        BigDecimal valorTotal = valor;

        if (origem == TipoConta.CORRENTE && destino == TipoConta.POUPANCA) {
            valorTotal = valor.add(BigDecimal.valueOf(2.00));
        }

        ContaValidator.validarSaldoSuficiente(contaOrigem.getSaldo(), valorTotal);

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valorTotal));
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        transacaoService.registrarTransacao(contaOrigem.getNumeroConta(), valorTotal, TipoTransacao.TRANSFERENCIA_ENTRE_CONTAS, "Transferência para conta " + destino.name());
        transacaoService.registrarTransacao(contaDestino.getNumeroConta(), valor, TipoTransacao.TRANSFERENCIA_ENTRE_CONTAS, "Transferência recebida da conta " + origem.name());

        return new ComprovanteTransferenciaDTO(
                "Transferência realizada com sucesso",
                clienteId,
                origem,
                destino,
                valor,
                contaOrigem.getNumeroConta(),
                contaDestino.getNumeroConta()
        );
    }


    public ContaResponseDTO buscarContaDTO(String numeroConta) {
        Conta conta = buscarContaPorNumero(numeroConta);
        return toDTO(conta);
    }

    private ContaResponseDTO toDTO(Conta conta) {
        ContaResponseDTO dto = new ContaResponseDTO();
        dto.setId(conta.getId());
        dto.setClienteId(conta.getCliente().getId());
        dto.setTipoConta(conta.getTipo());
        dto.setSaldo(conta.getSaldo());
        dto.setNumeroConta(conta.getNumeroConta());
        return dto;
    }
    
    public List<ContaResponseDTO> listarContasPorCliente(Long clienteId) {
        return contaRepository.findAllByClienteId(clienteId)
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public List<Conta> listarContas() {
        return contaRepository.findAll();
    }
    
    public void cadastrarOuAtualizarChavePix(String numeroConta, String chavePix) {
        PixValidator.validarOuLancarExcecao(chavePix);

        Conta conta = buscarContaPorNumero(numeroConta);


        Optional<Conta> existente = contaRepository.findByChavePix(chavePix);
        if (existente.isPresent() && !existente.get().getNumeroConta().equals(numeroConta)) {
            throw new RuntimeException("Essa chave Pix já está associada a outra conta.");
        }

        conta.setChavePix(chavePix);
        contaRepository.save(conta);
    }
    
    public void simularPassagemDeMes() {
        List<Conta> contas = contaRepository.findAll();

        for (Conta conta : contas) {
            if (conta.getTipo() == TipoConta.CORRENTE) {
                BigDecimal taxa = new BigDecimal("12.00");
                conta.setSaldo(conta.getSaldo().subtract(taxa));

                transacaoService.registrarTransacao(
                    conta.getNumeroConta(),
                    taxa.negate(),
                    TipoTransacao.TAXA_MANUTENCAO,
                    "Taxa de manutenção mensal"
                );

            } else if (conta.getTipo() == TipoConta.POUPANCA) {
                BigDecimal rendimento = conta.getSaldo().multiply(new BigDecimal("0.005")); // 0,5%
                conta.setSaldo(conta.getSaldo().add(rendimento));

                transacaoService.registrarTransacao(
                    conta.getNumeroConta(),
                    rendimento,
                    TipoTransacao.RENDIMENTO,
                    "Rendimento mensal aplicado"
                );
            }

            contaRepository.save(conta);
        }
    }

}

