package com.loteria360.controller;

import com.loteria360.domain.dto.CriarJogoRequest;
import com.loteria360.domain.dto.JogoResponse;
import com.loteria360.domain.dto.AtualizarJogoRequest;
import com.loteria360.service.JogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jogos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Jogos", description = "Gerenciamento de jogos disponíveis")
@SecurityRequirement(name = "bearerAuth")
public class JogoController {

    private final JogoService jogoService;

    @PostMapping
    @Operation(summary = "Criar jogo", description = "Cria um novo jogo no sistema")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<JogoResponse> criarJogo(@Valid @RequestBody CriarJogoRequest request) {
        log.info("Criando jogo: {}", request.getNome());
        JogoResponse response = jogoService.criarJogo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar jogos", description = "Lista todos os jogos com paginação")
    public ResponseEntity<Page<JogoResponse>> listarJogos(
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Listando jogos");
        Page<JogoResponse> response = jogoService.listarJogos(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ativos/paginado")
    @Operation(summary = "Listar jogos ativos paginado", description = "Lista apenas jogos ativos com paginação")
    public ResponseEntity<Page<JogoResponse>> listarJogosAtivosPaginado(
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Listando jogos ativos paginado");
        Page<JogoResponse> response = jogoService.listarJogosAtivos(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar jogo por ID", description = "Retorna os dados de um jogo específico")
    public ResponseEntity<JogoResponse> buscarPorId(@PathVariable String id) {
        log.info("Buscando jogo por ID: {}", id);
        JogoResponse response = jogoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/nome/{nome}")
    @Operation(summary = "Buscar jogo por nome", description = "Retorna os dados de um jogo pelo nome")
    public ResponseEntity<JogoResponse> buscarPorNome(@PathVariable String nome) {
        log.info("Buscando jogo por nome: {}", nome);
        JogoResponse response = jogoService.buscarPorNome(nome);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar jogo", description = "Atualiza os dados de um jogo")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<JogoResponse> atualizarJogo(@PathVariable String id, @Valid @RequestBody AtualizarJogoRequest request) {
        log.info("Atualizando jogo: {}", id);
        JogoResponse response = jogoService.atualizarJogo(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/toggle-status")
    @Operation(summary = "Ativar/Desativar jogo", description = "Alterna o status ativo/inativo de um jogo")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<JogoResponse> ativarDesativarJogo(@PathVariable String id) {
        log.info("Alterando status do jogo: {}", id);
        JogoResponse response = jogoService.ativarDesativarJogo(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar jogos ativos", description = "Lista todos os jogos ativos sem paginação")
    public ResponseEntity<List<JogoResponse>> listarJogosAtivos() {
        log.info("Listando jogos ativos");
        List<JogoResponse> response = jogoService.listarTodosJogosAtivos();
        return ResponseEntity.ok(response);
    }
}
