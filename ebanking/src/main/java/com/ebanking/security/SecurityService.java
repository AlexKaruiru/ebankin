/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ebanking.security;

import com.ebanking.security.AppRole.AppRole;
import com.ebanking.security.AppUser.AppUser;

import java.util.List;

public interface SecurityService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username, String roelName);
    AppUser loadUserByUsername(String username);
    List<AppUser> listUsers();

}
