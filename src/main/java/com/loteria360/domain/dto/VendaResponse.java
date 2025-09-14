package com.loteria360.domain.dto;

import com.loteria360.domain.model.StatusVenda;
import com.loteria360.domain.model.TipoVenda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendaResponse {
    
    private String id;
    private String turnoId;
    private UsuarioResponse vendedor;
    private TipoVenda tipo;
    private JogoResponse jogo;
    private BolaoResponse bolao;
    private Integer quantidadeApostas;
    private Integer cotasVendidas;
    private BigDecimal valorBruto;
    private BigDecimal desconto;
    private BigDecimal acrescimo;
    private BigDecimal valorLiquido;
    private StatusVenda status;
    private LocalDateTime criadoEm;
    private String motivoCancelamento;
    private List<PagamentoResponse> pagamentos;
    private ClienteResponse cliente;
    private String reciboUrl;
}
