package br.com.cdb.projetobancodigital.controller;

import br.com.cdb.projetobancodigital.entity.PixKey;
import br.com.cdb.projetobancodigital.enums.TipoChavePix;
import br.com.cdb.projetobancodigital.service.PixKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pix")
public class PixKeyController {

    private final PixKeyService pixKeyService;

    public PixKeyController(PixKeyService pixKeyService) {
        this.pixKeyService = pixKeyService;
    }

    @PostMapping("/criar/{clienteId}")
    public ResponseEntity<PixKey> criarChavePix(@PathVariable Long clienteId, @RequestParam TipoChavePix tipo) {
        return ResponseEntity.ok(pixKeyService.criarChavePix(clienteId, tipo));
    }

    @PutMapping("/atualizar/{chaveId}")
    public ResponseEntity<PixKey> atualizarChavePix(@PathVariable Long chaveId, @RequestParam TipoChavePix tipo) {
        return ResponseEntity.ok(pixKeyService.atualizarChavePix(chaveId, tipo));
    }
}
