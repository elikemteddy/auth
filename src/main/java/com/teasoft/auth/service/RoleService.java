/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teasoft.auth.service;

import com.teasoft.auth.model.Role;
import com.teasoft.auth.repo.RoleRepo;
import java.util.List;
import java.util.Optional;
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
        return roleRepo.saveAll(role);
    }

    public void delete(Role role) {
        roleRepo.delete(role);
    }

    public void deleteAll(Iterable<Role> role) {
        roleRepo.deleteAll(role);
    }

    public void deleteById(Long id) {
        roleRepo.deleteById(id);
    }

    public void deleteAll() {
        roleRepo.deleteAll();
    }

    public Boolean exists(Role role) {
        return roleRepo.existsById(role.getId());
    }

    public Boolean exists(Long roleId) {
        return roleRepo.existsById(roleId);
    }

    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    public Optional<Role> findById(Role role) {
        return roleRepo.findById(role.getId());
    }

    public Optional<Role> findById(Long roleId) {
        return roleRepo.findById(roleId);
    }

    public List<Role> findAll(Iterable<Long> roleIds) {
        return roleRepo.findAllById(roleIds);
    }

    public Long count() {
        return roleRepo.count();
    }
}
