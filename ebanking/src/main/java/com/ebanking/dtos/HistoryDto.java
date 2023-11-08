/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ebanking.dtos;

import lombok.Data;

import java.util.List;

@Data
public class HistoryDto {
    private List<AccountOperationDto> accountOperationDtoList;
    private int currentPage;
    private int sizePage;
    private int totalPages;

}