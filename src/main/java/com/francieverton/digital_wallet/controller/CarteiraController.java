package com.francieverton.digital_wallet.controller;

import com.francieverton.digital_wallet.dto.CarteiraResponseDTO;
import com.francieverton.digital_wallet.dto.MovimentacaoRequestDTO;
import com.francieverton.digital_wallet.service.CarteiraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/carteiras")
@RequiredArgsConstructor
public class CarteiraController {

    private final CarteiraService carteiraService;

    @PostMapping("/{id}/deposito")
    @ResponseStatus(HttpStatus.CREATED)
    public void depositar (@PathVariable Long id, @RequestBody MovimentacaoRequestDTO dto){

        carteiraService.deposito(id, dto.getValor());
    }

    @PostMapping("/{id}/saque")
    @ResponseStatus(HttpStatus.CREATED)
    public void sacar (@PathVariable Long id, @RequestBody MovimentacaoRequestDTO dto){

        carteiraService.sacar(id, dto.getValor());
    }

    @PostMapping("/{id}/transferencia")
    @ResponseStatus(HttpStatus.CREATED)
    public void transferir (@PathVariable Long id, @RequestBody MovimentacaoRequestDTO dto){

        carteiraService.transferir(id, dto.getCarteiraDestinoId(), dto.getValor());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarteiraResponseDTO buscarCarteira (@PathVariable Long id){

        return carteiraService.consultarCarteira(id);
    }
}
