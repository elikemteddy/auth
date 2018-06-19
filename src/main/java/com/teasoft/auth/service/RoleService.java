/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teasoft.auth.service;

import com.teasoft.auth.model.Role;
import com.teasoft.auth.repo.RoleRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author Theodore Elikem Attigah
 */
@Service
public class RoleService {

    @Autowired
    RoleRepo roleRepo;

    public Role findByRoleName(String roleName) {
        return roleRepo.findByRoleName(roleName);
    }

    public Role save(Role role) {
        return roleRepo.save(role);
    }

    public Iterable<Role> save(Iterable<Role> role) {
        return roleRepo.save(role);
    }

    public void delete(Role role) {
        roleRepo.delete(role);
    }

    public void delete(Iterable<Role> role) {
        roleRepo.delete(role);
    }

    public void delete(Long id) {
        roleRepo.delete(id);
    }

    public void deleteAll() {
        roleRepo.deleteAll();
    }

    public Boolean exists(Role role) {
        return roleRepo.exists(role.getId());
    }

    public Boolean exists(Long roleId) {
        return roleRepo.exists(roleId);
    }

    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    public Role find(Role role) {
        return roleRepo.findOne(role.getId());
    }

    public Role findOne(Long roleId) {
        return roleRepo.findOne(roleId);
    }

    public List<Role> findAll(Iterable<Long> roleIds) {
        return roleRepo.findAll(roleIds);
    }

    public Long count() {
        return roleRepo.count();
    }
}
