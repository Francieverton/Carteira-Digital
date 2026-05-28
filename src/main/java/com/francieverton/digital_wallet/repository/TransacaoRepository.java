package com.francieverton.digital_wallet.repository;

import com.francieverton.digital_wallet.model.Transacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    Page<Transacao> findByCarteiraOrigemIdOrCarteiraDestinoId(Long origemId, Long destinoId, Pageable pageable);
}