package com.ebanking.objects;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class UserObject {
    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String password;

    private BigDecimal account;

}
