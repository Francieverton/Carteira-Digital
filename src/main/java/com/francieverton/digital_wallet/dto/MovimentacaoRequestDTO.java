package com.francieverton.digital_wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MovimentacaoRequestDTO {

    private BigDecimal valor;
    private Long carteiraDestinoId;
}
