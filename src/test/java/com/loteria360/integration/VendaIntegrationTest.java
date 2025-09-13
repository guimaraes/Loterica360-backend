package com.loteria360.integration;

import com.loteria360.domain.dto.PagamentoRequest;
import com.loteria360.domain.dto.VendaRequest;
import com.loteria360.domain.model.*;
import com.loteria360.domain.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@Testcontainers
@ActiveProfiles("test")
@Transactional
class VendaIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("loteria360_test")
            .withUsername("loteria")
            .withPassword("loteria");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private BolaoRepository bolaoRepository;

    @Autowired
    private TurnoRepository turnoRepository;

    private Usuario usuario;
    private Jogo jogo;
    private Bolao bolao;
    private Turno turno;

    @BeforeEach
    void setUp() {
        // Create test user
        usuario = Usuario.builder()
                .nome("Teste Vendedor")
                .email("vendedor@test.com")
                .senhaHash("$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi")
                .papel(Usuario.Papel.VENDEDOR)
                .ativo(true)
                .build();
        usuario = usuarioRepository.save(usuario);

        // Create test game
        jogo = Jogo.builder()
                .nome("Teste Jogo")
                .codigo("TEST")
                .precoBase(new BigDecimal("5.00"))
                .ativo(true)
                .build();
        jogo = jogoRepository.save(jogo);

        // Create test bolao
        bolao = Bolao.builder()
                .jogo(jogo)
                .concurso("2024-001")
                .descricao("Bolão Teste")
                .cotasTotais(100)
                .cotasVendidas(0)
                .valorCota(new BigDecimal("2.00"))
                .dataSorteio(LocalDateTime.now().plusDays(7))
                .status(Bolao.Status.ABERTO)
                .build();
        bolao = bolaoRepository.save(bolao);

        // Create test turno
        turno = Turno.builder()
                .usuario(usuario)
                .caixaId("CAIXA-001")
                .abertoEm(LocalDateTime.now())
                .valorInicial(BigDecimal.ZERO)
                .status(Turno.Status.ABERTO)
                .build();
        turno = turnoRepository.save(turno);
    }

    @Test
    @WithMockUser(username = "vendedor@test.com", roles = "VENDEDOR")
    void shouldCreateVendaJogo() throws Exception {
        VendaRequest request = new VendaRequest(
                jogo.getId(),
                null,
                2,
                null,
                null,
                List.of(new PagamentoRequest(
                        Pagamento.Metodo.DINHEIRO,
                        new BigDecimal("10.00"),
                        null,
                        null,
                        null
                ))
        );

        mockMvc.perform(post("/vendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "jogoId": "%s",
                                    "quantidade": 2,
                                    "pagamentos": [
                                        {
                                            "metodo": "DINHEIRO",
                                            "valor": 10.00
                                        }
                                    ]
                                }
                                """.formatted(jogo.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.tipo").value("JOGO"))
                .andExpect(jsonPath("$.quantidadeApostas").value(2))
                .andExpect(jsonPath("$.valorLiquido").value(10.00));
    }

    @Test
    @WithMockUser(username = "vendedor@test.com", roles = "VENDEDOR")
    void shouldCreateVendaBolao() throws Exception {
        mockMvc.perform(post("/vendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "bolaoId": "%s",
                                    "cotas": 3,
                                    "pagamentos": [
                                        {
                                            "metodo": "PIX",
                                            "valor": 4.00
                                        },
                                        {
                                            "metodo": "DINHEIRO",
                                            "valor": 2.00
                                        }
                                    ]
                                }
                                """.formatted(bolao.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.tipo").value("BOLAO"))
                .andExpect(jsonPath("$.cotasVendidas").value(3))
                .andExpect(jsonPath("$.valorLiquido").value(6.00));
    }
}
