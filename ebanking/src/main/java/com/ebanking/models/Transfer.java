/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ebanking.models;

import lombok.Data;

@Data
public class Transfer {
    private String id;
    private double amount;
    private String description;
    private String destinationId;
}