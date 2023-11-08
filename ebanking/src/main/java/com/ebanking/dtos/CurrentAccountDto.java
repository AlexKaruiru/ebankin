/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ebanking.dtos;

import lombok.Data;

@Data
public class CurrentAccountDto extends BankAccountDto {
    private double overDraft;
}