/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teasoft.auth.repo;

import com.teasoft.auth.model.Role;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Theodore Elikem Attigah
 */
@Repository
public interface RoleRepo extends CrudRepository<Role, Long> {

    @Override
    List<Role> findAll();

    Role findByRoleName(String roleName);

    @Override
    List<Role> findAll(Iterable<Long> itbls);
}
