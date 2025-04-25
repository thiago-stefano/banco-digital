package br.com.cdb.projetobancodigital.controller;

import br.com.cdb.projetobancodigital.dto.ClienteResponseDTO;
import br.com.cdb.projetobancodigital.dto.VerificacaoEmailDTO;
import br.com.cdb.projetobancodigital.entity.Cliente;
import br.com.cdb.projetobancodigital.service.ClienteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criar(@RequestBody Cliente cliente) {
        ClienteResponseDTO dto = service.criarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscar(@PathVariable Long id) {
        ClienteResponseDTO cliente = service.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        ClienteResponseDTO dto = service.atualizar(id, cliente);
        LOGGER.info("Cliente atualizado: {}", dto.getId());
        return ResponseEntity.ok(dto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        LOGGER.info("Cliente deletado: {}", id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/verificar-email")
    public ResponseEntity<String> verificarEmail(@RequestParam String email, @RequestParam String codigo) {
        service.verificarCodigo(email, codigo);
        return ResponseEntity.ok("E-mail verificado com sucesso!");
    }
    
    @PostMapping("/confirmar-email")
    public ResponseEntity<String> confirmarEmail(@RequestBody VerificacaoEmailDTO dto) {
        service.confirmarEmail(dto);
        return ResponseEntity.ok("E-mail confirmado com sucesso!");
    }
}
