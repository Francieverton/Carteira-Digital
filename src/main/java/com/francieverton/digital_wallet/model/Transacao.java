package com.francieverton.digital_wallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacao")
@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carteira_origem_id", nullable = false)
    private Carteira carteiraOrigem;

    @ManyToOne
    @JoinColumn(name = "carteira_destino_id")
    private Carteira carteiraDestino;

    @Column(nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransacao tipo;

    @Column(nullable = false)
    private LocalDateTime dataHora = LocalDateTime.now();

    private String descricao;
}