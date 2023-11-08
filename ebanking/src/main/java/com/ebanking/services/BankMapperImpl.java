/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ebanking.services;


import com.ebanking.dtos.AccountOperationDto;
import com.ebanking.dtos.CurrentAccountDto;
import com.ebanking.dtos.CustomerDto;
import com.ebanking.dtos.SavingAccountDto;
import com.ebanking.models.AccountOperation;
import com.ebanking.models.CurrentAccount;
import com.ebanking.models.Customer;
import com.ebanking.models.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankMapperImpl {
    public Customer fromCustomerDto(CustomerDto customerDto){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        return customer;
    }
    public CustomerDto fromCustomer(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer, customerDto);
        return customerDto;
    }
    public CurrentAccount fromCurrentAccountDto(CurrentAccountDto currentAccountDto){
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDto, currentAccount);
        currentAccount.setCustomer(fromCustomerDto(currentAccountDto.getCustomerDto()));
        return currentAccount;
    }
    public CurrentAccountDto fromCurrentAccount(CurrentAccount currentAccount){
        CurrentAccountDto currentAccountDto = new CurrentAccountDto();
        BeanUtils.copyProperties(currentAccount, currentAccountDto);
        currentAccountDto.setCustomerDto(fromCustomer(currentAccount.getCustomer()));
        return currentAccountDto;
    }
    public SavingAccount fromSavingAccountDto(SavingAccountDto savingAccountDto){
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDto, savingAccount);
        savingAccount.setCustomer(fromCustomerDto(savingAccountDto.getCustomerDto()));
        return savingAccount;
    }
    public SavingAccountDto fromSavingAccount(SavingAccount savingAccount){
        SavingAccountDto savingAccountDto = new SavingAccountDto();
        BeanUtils.copyProperties(savingAccount, savingAccountDto);
        savingAccountDto.setCustomerDto(fromCustomer(savingAccount.getCustomer()));
        return savingAccountDto;
    }
    public AccountOperationDto fromAccountOperation(AccountOperation accountOperation){
    AccountOperationDto accountOperationDto = new AccountOperationDto();
    BeanUtils.copyProperties(accountOperation, accountOperationDto);
    if (accountOperation.getBankAccount() instanceof SavingAccount){
        accountOperationDto.setBankAccountDto(fromSavingAccount((SavingAccount) accountOperation.getBankAccount()));
    } else {
        accountOperationDto.setBankAccountDto(fromCurrentAccount((CurrentAccount) accountOperation.getBankAccount()));
    }
    return accountOperationDto;
}

    public AccountOperation fromAccountOperationDto(AccountOperationDto accountOperationDto){
        AccountOperation accountOperation  = new AccountOperation();
        BeanUtils.copyProperties(accountOperationDto, accountOperation);
        return accountOperation;
    }
}