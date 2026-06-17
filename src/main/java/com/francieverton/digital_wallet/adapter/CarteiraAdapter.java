package com.francieverton.digital_wallet.adapter;

import com.francieverton.digital_wallet.model.Carteira;
import com.francieverton.digital_wallet.port.CarteiraPort;
import com.francieverton.digital_wallet.repository.CarteiraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CarteiraAdapter implements CarteiraPort {

    private final CarteiraRepository carteiraRepository;

    @Override
    public Optional<Carteira> buscarId(Long id) {
        return carteiraRepository.findById(id);
    }

    @Override
    public Carteira salvar(Carteira carteira) {
        return carteiraRepository.save(carteira);
    }
}
