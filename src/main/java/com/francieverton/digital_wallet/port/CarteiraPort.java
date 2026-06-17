package com.francieverton.digital_wallet.port;

import com.francieverton.digital_wallet.model.Carteira;

import java.util.Optional;

public interface CarteiraPort {

    Optional<Carteira> buscarId(Long id);

    Carteira salvar (Carteira carteira);
}
