package com.loteria360.service;

import com.loteria360.domain.dto.ClienteRequest;
import com.loteria360.domain.dto.ClienteResponse;
import com.loteria360.domain.model.Cliente;
import com.loteria360.mapper.ClienteMapper;
import com.loteria360.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteResponse criarCliente(ClienteRequest request) {
        log.info("Criando cliente com nome: {}", request.getNome());

        // Normalizar CPF (remover formatação se existir)
        String cpfNormalizado = null;
        if (request.getCpf() != null && !request.getCpf().isEmpty()) {
            cpfNormalizado = request.getCpf().replaceAll("\\D", ""); // Remove tudo que não é dígito
            if (cpfNormalizado.length() == 11) {
                // Formatar CPF para o padrão XXX.XXX.XXX-XX
                cpfNormalizado = String.format("%s.%s.%s-%s", 
                    cpfNormalizado.substring(0, 3),
                    cpfNormalizado.substring(3, 6),
                    cpfNormalizado.substring(6, 9),
                    cpfNormalizado.substring(9, 11));
            }
        }

        // Normalizar telefone (remover formatação se existir)
        String telefoneNormalizado = null;
        if (request.getTelefone() != null && !request.getTelefone().isEmpty()) {
            telefoneNormalizado = request.getTelefone().replaceAll("\\D", ""); // Remove tudo que não é dígito
        }

        // Verificar se CPF já existe (usando o CPF normalizado)
        if (cpfNormalizado != null) {
            if (clienteRepository.findByCpf(cpfNormalizado).isPresent()) {
                throw new IllegalArgumentException("CPF já está em uso");
            }
        }

        // Verificar se email já existe (se fornecido)
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (clienteRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email já está em uso");
            }
        }

        // Criar cliente com dados normalizados
        Cliente cliente = Cliente.builder()
                .id(UUID.randomUUID().toString())
                .nome(request.getNome())
                .cpf(cpfNormalizado)
                .telefone(telefoneNormalizado)
                .email(request.getEmail())
                .consentimentoLgpd(request.getConsentimentoLgpd())
                .build();

        Cliente clienteSalvo = clienteRepository.save(cliente);
        log.info("Cliente criado com sucesso: {}", clienteSalvo.getId());

        return clienteMapper.toResponse(clienteSalvo);
    }

    @Transactional(readOnly = true)
    public Page<ClienteResponse> listarClientes(Pageable pageable, String search) {
        log.info("Listando clientes");
        
        if (search != null && !search.trim().isEmpty()) {
            return clienteRepository.findByNomeContaining(search.trim(), pageable)
                    .map(clienteMapper::toResponse);
        }
        
        return clienteRepository.findAll(pageable)
                .map(clienteMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ClienteResponse buscarPorId(String id) {
        log.info("Buscando cliente por ID: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        return clienteMapper.toResponse(cliente);
    }

    public ClienteResponse atualizarCliente(String id, ClienteRequest request) {
        log.info("Atualizando cliente: {}", id);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        // Normalizar CPF (remover formatação se existir)
        String cpfNormalizado = null;
        if (request.getCpf() != null && !request.getCpf().isEmpty()) {
            cpfNormalizado = request.getCpf().replaceAll("\\D", ""); // Remove tudo que não é dígito
            if (cpfNormalizado.length() == 11) {
                // Formatar CPF para o padrão XXX.XXX.XXX-XX
                cpfNormalizado = String.format("%s.%s.%s-%s", 
                    cpfNormalizado.substring(0, 3),
                    cpfNormalizado.substring(3, 6),
                    cpfNormalizado.substring(6, 9),
                    cpfNormalizado.substring(9, 11));
            }
        }

        // Normalizar telefone (remover formatação se existir)
        String telefoneNormalizado = null;
        if (request.getTelefone() != null && !request.getTelefone().isEmpty()) {
            telefoneNormalizado = request.getTelefone().replaceAll("\\D", ""); // Remove tudo que não é dígito
        }

        // Verificar se CPF já está em uso por outro cliente
        if (cpfNormalizado != null) {
            // Buscar cliente existente com o mesmo CPF
            Optional<Cliente> clienteExistente = clienteRepository.findByCpf(cpfNormalizado);
            if (clienteExistente.isPresent() && !clienteExistente.get().getId().equals(cliente.getId())) {
                throw new IllegalArgumentException("CPF já está em uso por outro cliente");
            }
        }

        // Verificar se email já está em uso por outro cliente (apenas se o email mudou)
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (!request.getEmail().equals(cliente.getEmail())) {
                if (clienteRepository.findByEmail(request.getEmail()).isPresent()) {
                    throw new IllegalArgumentException("Email já está em uso por outro cliente");
                }
            }
        }

        // Atualizar os campos com dados normalizados
        cliente.setNome(request.getNome());
        cliente.setCpf(cpfNormalizado);
        cliente.setTelefone(telefoneNormalizado);
        cliente.setEmail(request.getEmail());
        cliente.setConsentimentoLgpd(request.getConsentimentoLgpd());

        Cliente clienteSalvo = clienteRepository.save(cliente);
        log.info("Cliente atualizado com sucesso: {}", clienteSalvo.getId());

        return clienteMapper.toResponse(clienteSalvo);
    }

    @Transactional(readOnly = true)
    public Page<ClienteResponse> buscarClientes(String query, Pageable pageable) {
        log.info("Buscando clientes com termo: {}", query);
        
        // Busca por nome (termo principal)
        return clienteRepository.findByNomeContaining(query.trim(), pageable)
                .map(clienteMapper::toResponse);
    }
}
