package com.francieverton.digital_wallet.service;

import com.francieverton.digital_wallet.dto.CarteiraResponseDTO;
import com.francieverton.digital_wallet.dto.TransacaoResponseDTO;
import com.francieverton.digital_wallet.model.Carteira;
import com.francieverton.digital_wallet.model.TipoTransacao;
import com.francieverton.digital_wallet.model.Transacao;
import com.francieverton.digital_wallet.model.Usuario;
import com.francieverton.digital_wallet.repository.CarteiraRepository;
import com.francieverton.digital_wallet.repository.TransacaoRepository;
import com.francieverton.digital_wallet.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CarteiraService {

    private final CarteiraRepository carteiraRepository;
    private final TransacaoRepository transacaoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public void sacar (Long carteiraId, BigDecimal valor) {

        Carteira carteira = carteiraRepository.findById(carteiraId).orElseThrow(()
                -> new RuntimeException("Carteira não encontrada"));

        carteira.sacar(valor);

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

        carteira.depositar(valor);

        Transacao transacao = new Transacao();

        carteiraRepository.save(carteira);

        transacao.setTipo(TipoTransacao.DEPOSITO);
        transacao.setValor(valor);
        transacao.setCarteiraOrigem(carteira);
        transacaoRepository.save(transacao);
    }

    @Transactional
    public void transferir (Long origemId, Long destinoId, BigDecimal valor) {

        Carteira carteiraOrigem = carteiraRepository.findById(origemId).orElseThrow(()
                -> new RuntimeException("Carteira de Origem não encontrada."));

        Carteira carteiraDestino = carteiraRepository.findById(destinoId).orElseThrow(()
                -> new RuntimeException("Carteira de destino não encontrada."));

        if (carteiraOrigem.getId().equals(carteiraDestino.getId())) {
            throw new RuntimeException("Não pode fazer o depósito para a sua própria conta!");

        }

        carteiraOrigem.sacar(valor);
        carteiraDestino.depositar(valor);

        Transacao transacao = new Transacao();

        carteiraRepository.save(carteiraOrigem);
        carteiraRepository.save(carteiraDestino);

        transacao.setTipo(TipoTransacao.TRANSFERENCIA);
        transacao.setCarteiraOrigem(carteiraOrigem);
        transacao.setCarteiraDestino(carteiraDestino);
        transacao.setValor(valor);
        transacaoRepository.save(transacao);
    }

    public CarteiraResponseDTO consultarCarteira (Long carteiraId) {

        Carteira carteira = carteiraRepository.findById(carteiraId).orElseThrow(()
                -> new RuntimeException("Carteira não encontrada!"));

        return new CarteiraResponseDTO(carteira.getId(), carteira.getSaldo(), carteira.getMoeda());
    }

    public Page<TransacaoResponseDTO> consultarExtrato (Long carteiraId, Pageable pageable){

        Page<Transacao> transacaoPage = transacaoRepository.findByCarteiraOrigemIdOrCarteiraDestinoId(carteiraId, carteiraId, pageable);

        return transacaoPage.map(transacao -> new TransacaoResponseDTO(
                transacao.getId(),
                transacao.getTipo(),
                transacao.getValor(),
                transacao.getDataHora()
        ));
    }

    @Transactional
    public CarteiraResponseDTO criarCarteira(Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(()
                -> new RuntimeException("Usuário não encontrado!"));

        Carteira novaCarteira = new Carteira();
        novaCarteira.setSaldo(BigDecimal.ZERO);
        novaCarteira.setMoeda("BRL");
        novaCarteira.setUsuario(usuario);

        carteiraRepository.save(novaCarteira);

        return new CarteiraResponseDTO(novaCarteira.getId(), novaCarteira.getSaldo(), novaCarteira.getMoeda());
    }

}