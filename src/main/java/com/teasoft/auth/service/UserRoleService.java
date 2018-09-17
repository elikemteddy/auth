
package com.teasoft.auth.service;

import com.teasoft.auth.model.Role;
import com.teasoft.auth.model.UserRole;
import com.teasoft.auth.model.Users;
import com.teasoft.auth.repo.UserRoleRepo;
import java.util.List;
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
    
    public List<UserRole> findByRole(Role role) {
        return userRoleRepo.findByRole(role);
    }

    public UserRole save(UserRole userRole) {
        return userRoleRepo.save(userRole);
    }

    public Iterable<UserRole> save(Iterable<UserRole> userRole) {
        return userRoleRepo.save(userRole);
    }

    public void delete(UserRole userRole) {
        userRoleRepo.delete(userRole);
    }

    public void delete(Iterable<UserRole> userRole) {
        userRoleRepo.delete(userRole);
    }

    public void delete(Long id) {
        userRoleRepo.delete(id);
    }

    public void deleteAll() {
        userRoleRepo.deleteAll();
    }

    public Boolean exists(UserRole userRole) {
        return userRoleRepo.exists(userRole.getId());
    }

    public Boolean exists(Long roleId) {
        return userRoleRepo.exists(roleId);
    }

    public List<UserRole> findAll() {
        return userRoleRepo.findAll();
    }

    public UserRole findOne(UserRole userRole) {
        return userRoleRepo.findOne(userRole.getId());
    }

    public UserRole findOne(Long roleId) {
        return userRoleRepo.findOne(roleId);
    }

    public List<UserRole> findAll(Iterable<Long> roleIds) {
        return userRoleRepo.findAll(roleIds);
    }

    public Long count() {
        return userRoleRepo.count();
    }
}
