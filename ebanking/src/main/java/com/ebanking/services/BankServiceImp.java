/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ebanking.services;

import com.ebanking.dtos.AccountOperationDto;
import com.ebanking.dtos.BankAccountDto;
import com.ebanking.dtos.CustomerDto;
import com.ebanking.dtos.HistoryDto;
import com.ebanking.enums.OperationType;
import com.ebanking.exceptions.BalanceNotSufficientException;
import com.ebanking.exceptions.BankAccountException;
import com.ebanking.exceptions.CustomerNotFoundException;
import com.ebanking.models.AccountOperation;
import com.ebanking.models.BankAccount;
import com.ebanking.models.Credit;
import com.ebanking.models.CurrentAccount;
import com.ebanking.models.Customer;
import com.ebanking.models.Debit;
import com.ebanking.models.SavingAccount;
import com.ebanking.models.Transfer;
import com.ebanking.repositories.AccountOperationRepository;
import com.ebanking.repositories.BankAccountRepository;
import com.ebanking.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor 
@Slf4j 

public class BankServiceImp implements BankService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankMapperImpl bankAccountMapper;
    @Override
    public Customer saveCustomer(CustomerDto customerDto) {
        log.info("Your Customer is saved");
        Customer customer = bankAccountMapper.fromCustomerDto(customerDto);
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)
    throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null){
            throw new CustomerNotFoundException("Customer not found");
        }
        SavingAccount bankAccount = new SavingAccount();
        bankAccount.setInterestRate(interestRate);
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCustomer(customer);
        SavingAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        return savedBankAccount;
    }

    @Override
    public List<CustomerDto> getCustomers(String keyword) {
        List<CustomerDto> listOfCustomersDto = new ArrayList<>();
        customerRepository.findByNameContains(keyword).forEach(customerEntity -> {
            listOfCustomersDto.add(bankAccountMapper.fromCustomer(customerEntity));
        });
        return listOfCustomersDto;
    }

    @Override
    public CustomerDto getCustomer(Long id) {
        CustomerDto customerDto = new CustomerDto();
        customerDto = bankAccountMapper.fromCustomer(customerRepository.findById(id).orElse(null));
        return customerDto;
    }

    @Override
    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)
            throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null){
            throw new CustomerNotFoundException("Customer not found");
        }
        CurrentAccount bankAccount = new CurrentAccount();
        bankAccount.setOverDraft(overDraft);
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCustomer(customer);
        CurrentAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        return savedBankAccount;
    }
    @Override
    public List<CustomerDto> listCustomersDto() {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        customerRepository.findAll().forEach(customer->{
            customerDtoList.add(bankAccountMapper.fromCustomer(customer));
        });
        return customerDtoList;
    }
    @Override
    public BankAccountDto getBankAccount(String accountId) throws BankAccountException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount == null){
            throw new BankAccountException("Bank Account not found");
        } else {
            if (bankAccount instanceof CurrentAccount){
                return bankAccountMapper.fromCurrentAccount((CurrentAccount) bankAccount);
            } else {
                return bankAccountMapper.fromSavingAccount((SavingAccount) bankAccount);
            }
        }
    }

    @Override
    public void debit(Debit debit) throws BankAccountException, BalanceNotSufficientException {
        BankAccount bankAccount = bankAccountRepository.findById(debit.getId()).orElseThrow(()->new BankAccountException("Bank Account not found"));
        if (bankAccount.getBalance() < debit.getAmount()){
            throw new BalanceNotSufficientException("Balance Not Sufficient");
        }
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationDate(new Date());
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setAmount(debit.getAmount());
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() - debit.getAmount());
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(Credit credit) throws BankAccountException {
        BankAccount bankAccount = bankAccountRepository.findById(credit.getId()).orElseThrow(()->new BankAccountException("Bank Account not found"));
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationDate(new Date());
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setAmount(credit.getAmount());
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() + credit.getAmount());
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(Transfer transfer) throws BankAccountException, BalanceNotSufficientException {
        Debit debit = new Debit(transfer.getId(), transfer.getAmount(), "debit");
        debit(debit);
        Credit credit = new Credit(transfer.getDestinationId(), transfer.getAmount(), "credit");
        credit(credit);
    }

    @Override
    public List<BankAccount> listBankAccount(){
        return bankAccountRepository.findAll();
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id).orElseThrow(()->
                new CustomerNotFoundException("Customer not found"));
        customerDto.setId(id);
        customer = bankAccountMapper.fromCustomerDto(customerDto);
        customerRepository.save(customer);
        return bankAccountMapper.fromCustomer(customer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<BankAccountDto> getAllbankaccounts() {
        List<BankAccountDto> bankAccountDtos =  new ArrayList<>();
        listBankAccount().forEach(account->{
            bankAccountDtos.add(getBankAccount(account.getId()));
        });
        return bankAccountDtos;
    }

    @Override
    public void deleteBankAccount(String bankAccountd) {
        bankAccountRepository.deleteById(bankAccountd);
    }

    @Override
    public List<AccountOperationDto> getAllAccountOperation(String bankAccountId) {
        List<AccountOperationDto> accountOperationDtoList = new ArrayList<>();
        List<AccountOperation> listOfAccountOperation = accountOperationRepository.findByBankAccountId(bankAccountId);
        listOfAccountOperation.forEach(accountOperation -> {
            accountOperationDtoList.add(bankAccountMapper.fromAccountOperation(accountOperation));
        });
        return accountOperationDtoList;
    }

    @Override
    public HistoryDto getAccountOperationInPage(String bankAccountId, int page, int size) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId).orElseThrow(()->new BankAccountException("Bank Account not found"));
        Page<AccountOperation> listOfAccountOperation = accountOperationRepository.findByBankAccountId(bankAccountId, PageRequest.of(page,size));
        HistoryDto historyDto = new HistoryDto();
        historyDto.setAccountOperationDtoList(listOfAccountOperation.getContent().stream().map(accountOperation ->
            bankAccountMapper.fromAccountOperation(accountOperation)).collect(Collectors.toList()));
        historyDto.setSizePage(size);
        historyDto.setCurrentPage(page);
        historyDto.setTotalPages(listOfAccountOperation.getTotalPages());
        return historyDto;
    }
    @Override
    public List<BankAccountDto> getAllbankaccountsOfUser(Long id) {
        List<BankAccountDto> bankAccountDtos = new ArrayList<>();
        customerRepository.findById(id).get().getBankAccounts().forEach(bankAccount -> {
            if (bankAccount instanceof CurrentAccount){
                bankAccountDtos.add(bankAccountMapper.fromCurrentAccount((CurrentAccount) bankAccount));
            } else {
                bankAccountDtos.add(bankAccountMapper.fromSavingAccount((SavingAccount) bankAccount));
            }
        });
        return bankAccountDtos;
    }
}