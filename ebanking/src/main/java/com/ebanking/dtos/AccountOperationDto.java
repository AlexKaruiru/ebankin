/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ebanking.dtos;

import com.ebanking.enums.OperationType;
import lombok.Data;
import java.util.Date;

@Data
public class AccountOperationDto {
    private BankAccountDto bankAccountDto;
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
}