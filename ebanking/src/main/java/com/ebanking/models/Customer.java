/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ebanking.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id;
    private String name;
    private String email;
    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE},mappedBy = "customer") 
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) 
    private List<BankAccount> bankAccounts;
}