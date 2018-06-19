
package com.teasoft.auth.service;

import com.teasoft.auth.model.Role;
import com.teasoft.auth.model.UserRole;
import com.teasoft.auth.model.Users;
import com.teasoft.auth.repo.UserRoleRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 *
 * @author Theodore Elikem Attigah
 */
@Service
public class UserRoleService {

    @Autowired
    UserRoleRepo userRoleRepo;

    public List<UserRole> findByUser(Users user) {
        return userRoleRepo.findByUser(user);
    }

    public UserRole findByUserAndRole(Users user, Role role) {
        return userRoleRepo.findByUserAndRole(user, role);
    }

    public UserRole save(UserRole userRole) {
        return userRoleRepo.save(userRole);
    }

    public Iterable<UserRole> saveAll(Iterable<UserRole> userRole) {
        return userRoleRepo.saveAll(userRole);
    }

    public void delete(UserRole userRole) {
        userRoleRepo.delete(userRole);
    }

    public void deleteAll(Iterable<UserRole> userRole) {
        userRoleRepo.deleteAll(userRole);
    }

    public void deleteById(Long id) {
        userRoleRepo.deleteById(id);
    }

    public void deleteAll() {
        userRoleRepo.deleteAll();
    }

    public Boolean exists(UserRole userRole) {
        return userRoleRepo.existsById(userRole.getId());
    }

    public Boolean exists(Long roleId) {
        return userRoleRepo.existsById(roleId);
    }

    public List<UserRole> findAll() {
        return userRoleRepo.findAll();
    }

    public Optional<UserRole> findOne(UserRole userRole) {
        return userRoleRepo.findById(userRole.getId());
    }

    public Optional<UserRole> findOne(Long roleId) {
        return userRoleRepo.findById(roleId);
    }

    public List<UserRole> findAll(Iterable<Long> roleIds) {
        return userRoleRepo.findAllById(roleIds);
    }

    public Long count() {
        return userRoleRepo.count();
    }
}
