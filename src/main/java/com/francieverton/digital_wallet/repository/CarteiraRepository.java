package com.francieverton.digital_wallet.repository;

import com.francieverton.digital_wallet.model.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

}