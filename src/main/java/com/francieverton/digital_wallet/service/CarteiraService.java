package com.francieverton.digital_wallet.service;

import com.francieverton.digital_wallet.model.Carteira;
import com.francieverton.digital_wallet.model.TipoTransacao;
import com.francieverton.digital_wallet.model.Transacao;
import com.francieverton.digital_wallet.repository.CarteiraRepository;
import com.francieverton.digital_wallet.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CarteiraService {

    private final CarteiraRepository carteiraRepository;
    private final TransacaoRepository transacaoRepository;

    @Transactional
    public void sacar (Long carteiraId, BigDecimal valor) {

        Carteira carteira = carteiraRepository.findById(carteiraId).orElseThrow(()
                -> new RuntimeException("Carteira não encontrada"));

        if (carteira.getSaldo().compareTo(valor) < 0){
            throw new RuntimeException("Saldo insuficiente!");
        }
        else {
            carteira.setSaldo(carteira.getSaldo().subtract(valor));
        }

        Transacao transacao = new Transacao();

        carteiraRepository.save(carteira);

        transacao.setTipo(TipoTransacao.SAQUE);
        transacao.setValor(valor);
        transacao.setCarteiraOrigem(carteira);
        transacaoRepository.save(transacao);
    }

    @Transactional
    public void deposito (Long carteiraId, BigDecimal valor) {

        Carteira carteira = carteiraRepository.findById(carteiraId).orElseThrow(()
        -> new RuntimeException("Carteira não encontrada!"));

        if (valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("O valor do depósito deve ser mais que zero!");
        }
        else {
            carteira.setSaldo(carteira.getSaldo().add(valor));
        }

        Transacao transacao = new Transacao();

        carteiraRepository.save(carteira);

        transacao.setTipo(TipoTransacao.DEPOSITO);
        transacao.setValor(valor);
        transacao.setCarteiraOrigem(carteira);
        transacaoRepository.save(transacao);
    }
}
