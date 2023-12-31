/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ebanking.controllers;

import com.ebanking.dtos.CustomerDto;
import com.ebanking.models.Customer;
import com.ebanking.services.BankService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor 
@Slf4j
@CrossOrigin("*")
public class CustomerController {
    private BankService bankService;
    @GetMapping("/customer/{id}")
    public CustomerDto customers(@PathVariable(name = "id") long id){
        return bankService.getCustomer(id);
    }
    @GetMapping(path = "/customers")
    public List<CustomerDto> getcustomer(@RequestParam(name = "keyword", defaultValue = "") String keyword){
        return bankService.getCustomers(keyword);
    }
    
    @PostMapping(path = "/savecustomer")
    public Customer savecustomer(@RequestBody CustomerDto customerDto){
        return bankService.saveCustomer(customerDto);
    }
    @PutMapping(path = "/updatecustomer/{id}")
    public CustomerDto updatecustomer(@PathVariable(name = "id") Long customerId, @RequestBody CustomerDto customerDto){
        return bankService.updateCustomer(customerId, customerDto);
    }
    @DeleteMapping(path = "/deletecustomer/{id}")
    public void deletecustomer(@PathVariable(name = "id") Long customerId){
        bankService.deleteCustomer(customerId);
    }
}