package br.com.cdb.projetobancodigital.service;

import br.com.cdb.projetobancodigital.dto.ClienteResponseDTO;
import br.com.cdb.projetobancodigital.dto.EnderecoResponse;
import br.com.cdb.projetobancodigital.dto.VerificacaoEmailDTO;
import br.com.cdb.projetobancodigital.entity.Cliente;
import br.com.cdb.projetobancodigital.entity.Endereco;
import br.com.cdb.projetobancodigital.exception.CepInvalidoException;
import br.com.cdb.projetobancodigital.exception.ClienteNaoEncontradoException;
import br.com.cdb.projetobancodigital.exception.CpfJaCadastradoException;
import br.com.cdb.projetobancodigital.exception.EntidadeNaoEncontradaException;
import br.com.cdb.projetobancodigital.exception.RegraNegocioException;
import br.com.cdb.projetobancodigital.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final EmailService emailService;

    public ClienteService(ClienteRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    public ClienteResponseDTO criarCliente(Cliente cliente) {

        repository.findByCpf(cliente.getCpf()).ifPresent(c -> {
            throw new CpfJaCadastradoException(cliente.getCpf());
        });

        Endereco enderecoCompleto = buscarEnderecoPorCep(cliente.getEndereco().getCep());
        enderecoCompleto.setNumero(cliente.getEndereco().getNumero());
        enderecoCompleto.setComplemento(cliente.getEndereco().getComplemento());
        cliente.setEndereco(enderecoCompleto);

        String codigoVerificacao = String.format("%06d", new Random().nextInt(1000000));
        cliente.setCodigoVerificacao(codigoVerificacao);
        cliente.setEmailVerificado(false);

        Cliente clienteSalvo = repository.save(cliente);

        emailService.enviarCodigoVerificacao(clienteSalvo.getEmail(), codigoVerificacao);

        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(clienteSalvo.getId());
        dto.setNome(clienteSalvo.getNome());
        dto.setCpf(clienteSalvo.getCpf());
        dto.setEmail(clienteSalvo.getEmail());
        dto.setTelefone(clienteSalvo.getTelefone());
        dto.setDataNascimento(clienteSalvo.getDataNascimento());
        dto.setEndereco(clienteSalvo.getEndereco());
        dto.setEmailVerificado(clienteSalvo.isEmailVerificado());

        return dto;
    }


    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));
        
        return converterParaDTO(cliente);
    }

    public List<ClienteResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    public ClienteResponseDTO converterParaDTO(Cliente cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setCpf(cliente.getCpf());
        dto.setEmail(cliente.getEmail());
        dto.setTelefone(cliente.getTelefone());
        dto.setDataNascimento(cliente.getDataNascimento());
        dto.setEndereco(cliente.getEndereco());
        dto.setEmailVerificado(cliente.isEmailVerificado());
        return dto;
    }
    
    public Cliente buscarEntidadePorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ClienteNaoEncontradoException(id));
    }

    public ClienteResponseDTO atualizar(Long id, Cliente novoCliente) {
        Cliente cliente = buscarEntidadePorId(id);
        cliente.setNome(novoCliente.getNome());
        cliente.setCpf(novoCliente.getCpf());
        cliente.setEmail(novoCliente.getEmail());
        cliente.setTelefone(novoCliente.getTelefone());

        Endereco novoEndereco = buscarEnderecoPorCep(novoCliente.getEndereco().getCep());
        novoEndereco.setNumero(novoCliente.getEndereco().getNumero());
        novoEndereco.setComplemento(novoCliente.getEndereco().getComplemento());
        cliente.setEndereco(novoEndereco);

        Cliente atualizado = repository.save(cliente);
        return converterParaDTO(atualizado);
    }

    public void deletar(Long id) {
        Cliente cliente = repository.findById(id)
            .orElseThrow(() -> new ClienteNaoEncontradoException(id));
        repository.delete(cliente);
    }

    public Endereco buscarEnderecoPorCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<EnderecoResponse> response = restTemplate.getForEntity(url, EnderecoResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && response.getBody().getCep() != null) {
                EnderecoResponse er = response.getBody();
                Endereco endereco = new Endereco();
                endereco.setCep(er.getCep());
                endereco.setRua(er.getLogradouro());
                endereco.setBairro(er.getBairro());
                endereco.setCidade(er.getLocalidade());
                endereco.setEstado(er.getUf());
                return endereco;
            } else {
                throw new CepInvalidoException(cep);
            }
        } catch (HttpClientErrorException e) {
            throw new CepInvalidoException(cep);
        }
    }
    public void verificarCodigo(String email, String codigo) {
        Cliente cliente = repository.findByEmail(email)
            .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado", "404"));

        if (!codigo.equals(cliente.getCodigoVerificacao())) {
            throw new RegraNegocioException("Código de verificação inválido.");
        }

        cliente.setEmailVerificado(true);
        cliente.setCodigoVerificacao(null);
        repository.save(cliente);
    }
    
    public void confirmarEmail(VerificacaoEmailDTO dto) {
        Cliente cliente = repository.findByEmail(dto.getEmail())
            .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente com esse e-mail não encontrado", "404"));

        if (cliente.isEmailVerificado()) {
            throw new RegraNegocioException("E-mail já foi verificado.");
        }

        if (!cliente.getCodigoVerificacao().equals(dto.getCodigo())) {
            throw new RegraNegocioException("Código de verificação inválido.");
        }

        cliente.setEmailVerificado(true);
        cliente.setCodigoVerificacao(null);
        repository.save(cliente);
    }

}

