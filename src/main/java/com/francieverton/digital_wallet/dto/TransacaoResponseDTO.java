package com.francieverton.digital_wallet.dto;

import com.francieverton.digital_wallet.model.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TransacaoResponseDTO {

    private Long id;
    private TipoTransacao tipo;
    private BigDecimal valor;
    private LocalDateTime dataHora;
}
