package com.francieverton.digital_wallet.controller;

import com.francieverton.digital_wallet.dto.CarteiraResponseDTO;
import com.francieverton.digital_wallet.dto.MovimentacaoRequestDTO;
import com.francieverton.digital_wallet.dto.TransacaoResponseDTO;
import com.francieverton.digital_wallet.service.CarteiraService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/{id}/extrato")
    @ResponseStatus(HttpStatus.OK)
    public Page<TransacaoResponseDTO> extrato (@PathVariable Long id, @PageableDefault(size = 10) Pageable pageable){

        return carteiraService.consultarExtrato(id, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarteiraResponseDTO criarCarteira() {
        return carteiraService.criarCarteira();
    }
}
