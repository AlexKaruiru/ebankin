/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ebanking.controllers;

import com.ebanking.dtos.AccountOperationDto;
import com.ebanking.dtos.HistoryDto;
import com.ebanking.services.BankService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class AccountOperationController {
    private BankService bankService;
    @GetMapping("/getallaccountoperation/{id}")
    public List<AccountOperationDto> getAllAccountOperation(@PathVariable(name = "id") String bankAccountId){
        return bankService.getAllAccountOperation(bankAccountId);
    }
    @GetMapping("/getaccountoperationInPage/{id}")
    public HistoryDto getAllAccountOperation(@PathVariable(name = "id") String bankAccountId,
          @RequestParam(name="page",defaultValue = "0") int page,
          @RequestParam(name = "size", defaultValue = "3") int size){
        return bankService.getAccountOperationInPage(bankAccountId,page,size);
    }

}