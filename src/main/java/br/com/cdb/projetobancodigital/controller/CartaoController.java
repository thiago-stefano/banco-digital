package br.com.cdb.projetobancodigital.controller;

import br.com.cdb.projetobancodigital.dto.*;
import br.com.cdb.projetobancodigital.entity.Transacao;
import br.com.cdb.projetobancodigital.enums.TipoCartao;
import br.com.cdb.projetobancodigital.service.CartaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final CartaoService cartaoService;

    public CartaoController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }


    @PostMapping
    public ResponseEntity<CartaoResponseDTO> emitirCartao(@Valid @RequestBody CartaoRequestDTO dto) {
        return ResponseEntity.ok(cartaoService.emitirCartao(dto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CartaoResponseDTO> buscarCartao(@PathVariable Long id) {
        return ResponseEntity.ok(cartaoService.buscarCartao(id));
    }


    @PostMapping("/compra")
    public ResponseEntity<String> realizarCompra(@RequestBody PagamentoCartaoDTO dto) {
        if (dto.getTipo() == TipoCartao.CREDITO || dto.getTipo() == TipoCartao.DEBITO) {
            cartaoService.realizarPagamento(dto);
        } else {
            return ResponseEntity.badRequest().body("Tipo de cartão inválido.");
        }

        return ResponseEntity.ok("Compra realizada com sucesso.");
    }


    @PutMapping("/limite")
    public ResponseEntity<String> alterarLimite(@Valid @RequestBody AlterarLimiteDTO dto) {
        cartaoService.alterarLimite(dto);
        return ResponseEntity.ok("Limite alterado com sucesso.");
    }


    @PutMapping("/status")
    public ResponseEntity<String> alterarStatus(@Valid @RequestBody AlterarStatusDTO dto) {
        cartaoService.alterarStatus(dto);
        return ResponseEntity.ok("Status do cartão atualizado.");
    }


    @PutMapping("/senha")
    public ResponseEntity<String> alterarSenha(@Valid @RequestBody AlterarSenhaDTO dto) {
        cartaoService.alterarSenha(dto);
        return ResponseEntity.ok("Senha alterada com sucesso.");
    }


    @GetMapping("/{id}/fatura")
    public ResponseEntity<List<Transacao>> consultarFatura(@PathVariable Long id) {
        return ResponseEntity.ok(cartaoService.consultarFatura(id));
    }


    @PostMapping("/fatura/pagamento")
    public ResponseEntity<String> pagarFatura(@Valid @RequestBody PagamentoFaturaDTO dto) {
        cartaoService.pagarFatura(dto.getId(), dto);
        return ResponseEntity.ok("Fatura paga com sucesso.");
    }


    @PutMapping("/limite-diario")
    public ResponseEntity<String> alterarLimiteDiario(@Valid @RequestBody AlterarLimiteDiarioDTO dto) {
        cartaoService.alterarLimiteDiario(dto.getId(), dto.getNovoLimite());
        return ResponseEntity.ok("Limite diário alterado.");
    }

}
