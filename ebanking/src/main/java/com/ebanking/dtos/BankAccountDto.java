/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ebanking.dtos;

import com.ebanking.models.AccountOperation;
import com.ebanking.models.Customer;
import com.ebanking.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
public class BankAccountDto {
    private CustomerDto customerDto;
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
}
