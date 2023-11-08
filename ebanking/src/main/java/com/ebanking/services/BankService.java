/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ebanking.services;

import com.ebanking.dtos.AccountOperationDto;
import com.ebanking.dtos.BankAccountDto;
import com.ebanking.dtos.HistoryDto;
import com.ebanking.models.CurrentAccount;
import com.ebanking.dtos.CustomerDto;
import com.ebanking.models.Customer;
import com.ebanking.exceptions.BalanceNotSufficientException;
import com.ebanking.exceptions.BankAccountException;
import com.ebanking.exceptions.CustomerNotFoundException;
import com.ebanking.models.BankAccount;
import com.ebanking.models.Credit;
import com.ebanking.models.Debit;
import com.ebanking.models.SavingAccount;
import com.ebanking.models.Transfer;

import java.util.List;

public interface BankService {
    Customer saveCustomer(CustomerDto customer);
    CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    List<CustomerDto> getCustomers(String keyword);
    CustomerDto getCustomer(Long id);
    List<Customer> listCustomers();
    List<CustomerDto> listCustomersDto();
    BankAccountDto getBankAccount(String accountId) throws BankAccountException;
    void debit(Debit debit) throws BalanceNotSufficientException;
    void credit(Credit credit) throws BalanceNotSufficientException;
    void transfer(Transfer transfer) throws BalanceNotSufficientException;
    List<BankAccount> listBankAccount();
    CustomerDto updateCustomer(Long id, CustomerDto customerDto) throws CustomerNotFoundException;
    void deleteCustomer(Long customerId);
    List<BankAccountDto> getAllbankaccounts();
    void deleteBankAccount(String bankAccountd);
    List<AccountOperationDto> getAllAccountOperation(String bankAccountId);
    HistoryDto getAccountOperationInPage(String bankAccountId, int page, int size);
    List<BankAccountDto> getAllbankaccountsOfUser(Long id);
}