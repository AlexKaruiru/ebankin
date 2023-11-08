/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ebanking.models;

import  com.ebanking.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity 
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) 
@DiscriminatorColumn(name = "TYPE", length = 4,discriminatorType = DiscriminatorType.STRING) 
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class BankAccount {
    @Id
    private String id;
    private double balance; 
    private Date createdAt;
    @Enumerated(EnumType.STRING) 
    private AccountStatus status;
    @ManyToOne 
    private Customer customer;
    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "bankAccount", fetch = FetchType.LAZY)
    private List<AccountOperation> accountOperationList;
}