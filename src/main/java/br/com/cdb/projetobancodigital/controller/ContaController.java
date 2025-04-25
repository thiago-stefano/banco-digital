package br.com.cdb.projetobancodigital.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.cdb.projetobancodigital.dto.ComprovanteTransacaoDTO;
import br.com.cdb.projetobancodigital.dto.ComprovanteTransferenciaDTO;
import br.com.cdb.projetobancodigital.dto.ContaNumeroDTO;
import br.com.cdb.projetobancodigital.dto.ContaRequestDTO;
import br.com.cdb.projetobancodigital.dto.ContaResponseDTO;
import br.com.cdb.projetobancodigital.dto.DepositoDTO;
import br.com.cdb.projetobancodigital.dto.PixCadastroDTO;
import br.com.cdb.projetobancodigital.dto.PixDTO;
import br.com.cdb.projetobancodigital.dto.PixRequestDTO;
import br.com.cdb.projetobancodigital.dto.SaldoResponseDTO;
import br.com.cdb.projetobancodigital.dto.SaqueDTO;
import br.com.cdb.projetobancodigital.dto.TransacaoResponseDTO;
import br.com.cdb.projetobancodigital.dto.TransferenciaDTO;
import br.com.cdb.projetobancodigital.dto.TransferenciaInternaDTO;
import br.com.cdb.projetobancodigital.entity.Conta;
import br.com.cdb.projetobancodigital.enums.TipoConta;
import br.com.cdb.projetobancodigital.service.ContaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }


    @GetMapping("/{numeroConta}/extrato")
    public ResponseEntity<List<TransacaoResponseDTO>> consultarExtrato(@PathVariable String numeroConta) {
        return ResponseEntity.ok(contaService.obterExtrato(numeroConta));
    }


    @PostMapping
    public ResponseEntity<ContaResponseDTO> criarConta(@RequestBody @Valid ContaRequestDTO contaRequest) {
        ContaResponseDTO contaCriada = contaService.criarConta(contaRequest);
        return ResponseEntity.ok(contaCriada);
    }


    @GetMapping("/{numeroConta}")
    public ResponseEntity<ContaResponseDTO> buscarConta(@PathVariable String numeroConta) {
        return ResponseEntity.ok(contaService.buscarContaDTO(numeroConta));
    }


    @GetMapping("/{numeroConta}/saldo")
    public ResponseEntity<SaldoResponseDTO> consultarSaldo(@PathVariable String numeroConta) {
        BigDecimal saldo = contaService.consultarSaldo(numeroConta);
        return ResponseEntity.ok(new SaldoResponseDTO(numeroConta, saldo));
    }



    @PostMapping("/deposito")
    public ResponseEntity<ComprovanteTransacaoDTO> depositar(@RequestBody @Valid DepositoDTO dto) {
        return ResponseEntity.ok(contaService.depositar(dto.getNumeroConta(), dto.getValor()));
    }


    @PostMapping("/saque")
    public ResponseEntity<ComprovanteTransacaoDTO> sacar(@RequestBody @Valid SaqueDTO dto) {
        return ResponseEntity.ok(contaService.sacar(dto.getNumeroConta(), dto.getValor()));
    }


    @PostMapping("/transferencia")
    public ResponseEntity<ComprovanteTransferenciaDTO> transferir(@RequestBody @Valid TransferenciaDTO dto) {
        ComprovanteTransferenciaDTO comprovante = contaService.transferir(
                dto.getNumeroContaOrigem(),
                dto.getNumeroContaDestino(),
                dto.getValor()
        );
        return ResponseEntity.ok(comprovante);
    }


    @PostMapping("/pix")
    public ResponseEntity<ComprovanteTransacaoDTO> enviarPix(@RequestBody @Valid PixDTO dto) {
        ComprovanteTransacaoDTO comprovante = contaService.realizarPix(dto);
        return ResponseEntity.ok(comprovante);
    }


    @PutMapping("/{numeroConta}/manutencao")
    public ResponseEntity<Void> aplicarTaxaManutencao(
            @PathVariable String numeroConta,
            @RequestBody BigDecimal taxa) {

        contaService.aplicarManutencao(numeroConta, taxa);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{numeroConta}/rendimentos")
    public ResponseEntity<Void> aplicarRendimento(
            @PathVariable String numeroConta,
            @RequestBody BigDecimal taxa) {

        contaService.aplicarRendimento(numeroConta, taxa);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{numeroConta}/aplicar-taxa-mensal")
    public ResponseEntity<Void> aplicarTaxaMensal(@PathVariable String numeroConta) {
        contaService.aplicarTaxaMensal(numeroConta);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{numeroConta}/aplicar-rendimento-mensal")
    public ResponseEntity<Void> aplicarRendimentoMensal(@PathVariable String numeroConta) {
        contaService.aplicarRendimentoMensal(numeroConta);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/transferencia-entre-contas")
    public ResponseEntity<ComprovanteTransferenciaDTO> transferirEntreContasDoCliente(
        @RequestBody @Valid TransferenciaInternaDTO dto) {

        ComprovanteTransferenciaDTO comprovante = contaService.transferirEntreContasDoCliente(
            dto.getClienteId(), dto.getOrigem(), dto.getDestino(), dto.getValor());

        return ResponseEntity.ok(comprovante);
    }


    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ContaResponseDTO>> listarContasPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(contaService.listarContasPorCliente(clienteId));
    }

 
    @GetMapping
    public ResponseEntity<List<Conta>> listarContas() {
        return ResponseEntity.ok(contaService.listarContas());
    }
    
    @PutMapping("/pix")
    public ResponseEntity<String> cadastrarOuAtualizarChavePix(@RequestBody @Valid PixCadastroDTO dto) {
        contaService.cadastrarOuAtualizarChavePix(dto.getNumeroConta(), dto.getChavePix());
        return ResponseEntity.ok("Chave Pix cadastrada/atualizada com sucesso.");
    }
    
    @PostMapping("/simular-mes")
    public ResponseEntity<String> simularPassagemDeMes() {
        contaService.simularPassagemDeMes();
        return ResponseEntity.ok("Passagem de mês simulada com sucesso!");
    }
}