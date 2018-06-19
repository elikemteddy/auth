/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teasoft.auth.service;

import com.teasoft.auth.sec.TokenUser;
import com.teasoft.auth.model.UserRole;
import com.teasoft.auth.model.Users;
import com.teasoft.auth.repo.UserRoleRepo;
import com.teasoft.auth.repo.UsersRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Theodore Elikem Attigah
 */
@Service
public class UsersService {

    @Autowired
    UsersRepo usersRepo;
    @Autowired
    UserRoleRepo userRoleRepo;
    
    public Users findByPhone(String phone) {
        return usersRepo.findByPhone(phone);
    }
    
    public Users findByEmail(String email) {
        return usersRepo.findByEmail(email);
    }
    
    public Users findByUsername(String username) {
        return usersRepo.findByUsername(username);
    }

    public String userRoles(Users user) {
        String roles = "";
        List<UserRole> userRoles = userRoleRepo.findByUser(user);
        for(UserRole userRole: userRoles) {
            roles += userRole.getRole().getRoleName()+",";
        }
        return roles;
    }
    
    public Users getCurrentUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((TokenUser) authentication.getPrincipal()).getUser();
    }

    public Iterable<Users> findAll() {
        return usersRepo.findAll();
    }

    public Users findOne(Long id) {
        return usersRepo.findOne(id);
    }

    public Users save(Users user) {
        return usersRepo.save(user);
    }

    public Iterable<Users> save(Iterable<Users> itrbl) {
        return usersRepo.save(itrbl);
    }

    public void delete(Users user) {
        usersRepo.delete(user);
    }

    public void delete(Long id) {
        usersRepo.delete(id);
    }

    public void deleteAll() {
        usersRepo.deleteAll();
    }

    public void deleteListOfUserss(Iterable<Users> users) {
        usersRepo.delete(users);
    }

    public long count() {
        return usersRepo.count();
    }
    
    public Boolean exists(Long userId) {
        return usersRepo.exists(userId);
    }

}
