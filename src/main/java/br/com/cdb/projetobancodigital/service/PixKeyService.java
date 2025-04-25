package br.com.cdb.projetobancodigital.service;

import br.com.cdb.projetobancodigital.entity.Cliente;
import br.com.cdb.projetobancodigital.entity.PixKey;
import br.com.cdb.projetobancodigital.enums.TipoChavePix;
import br.com.cdb.projetobancodigital.repository.ClienteRepository;
import br.com.cdb.projetobancodigital.repository.PixKeyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PixKeyService {

    private final PixKeyRepository pixKeyRepository;
    private final ClienteRepository clienteRepository;

    public PixKeyService(PixKeyRepository pixKeyRepository, ClienteRepository clienteRepository) {
        this.pixKeyRepository = pixKeyRepository;
        this.clienteRepository = clienteRepository;
    }

    public PixKey criarChavePix(Long clienteId, TipoChavePix tipo) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        String valorChave = switch (tipo) {
            case CPF -> cliente.getCpf();
            case EMAIL -> cliente.getEmail();
            case TELEFONE -> cliente.getTelefone();
            case ALEATORIA -> UUID.randomUUID().toString();
        };

        PixKey pixKey = new PixKey(tipo, valorChave, cliente);
        return pixKeyRepository.save(pixKey);
    }

    public PixKey atualizarChavePix(Long chaveId, TipoChavePix novoTipo) {
        PixKey chave = pixKeyRepository.findById(chaveId)
                .orElseThrow(() -> new RuntimeException("Chave Pix não encontrada"));

        Cliente cliente = chave.getCliente();
        String novoValor = switch (novoTipo) {
            case CPF -> cliente.getCpf();
            case EMAIL -> cliente.getEmail();
            case TELEFONE -> cliente.getTelefone();
            case ALEATORIA -> UUID.randomUUID().toString();
        };

        chave.setTipo(novoTipo);
        chave.setValor(novoValor);
        return pixKeyRepository.save(chave);
    }
}
