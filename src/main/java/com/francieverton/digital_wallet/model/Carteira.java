package com.francieverton.digital_wallet.model;

import com.francieverton.digital_wallet.exception.SaldoInsuficienteException;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "carteira")
@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(nullable = false)
    private String moeda = "BRL";

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    public void sacar (BigDecimal valor){

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero!");
        }

        if (valor.compareTo(this.saldo) > 0){
            throw new SaldoInsuficienteException("Saldo Insuficiente!");
        }
        this.saldo = saldo.subtract(valor);
    }

    public void depositar (BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("O valor do depósito deve ser mais que zero!");
        }
        this.saldo = saldo.add(valor);
    }
}
