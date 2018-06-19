/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teasoft.auth.repo;

import com.teasoft.auth.model.Role;
import com.teasoft.auth.model.UserRole;
import com.teasoft.auth.model.Users;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Theodore Elikem Attigah
 */
@Repository
public interface UserRoleRepo extends CrudRepository<UserRole, Long> {

    List<UserRole> findByUser(Users users);

    UserRole findByUserAndRole(Users user, Role role);

    @Override
    List<UserRole> findAll();

    @Override
    List<UserRole> findAll(Iterable<Long> itbls);
}
