/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.teasoft.auth.repo;


import com.teasoft.auth.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Theodore Elikem Attigah
 */
@Repository
public interface UsersRepo extends CrudRepository<Users, Long> {
    Users findByUsername(String username);
    Users findByPhone(String phone);
    Users findByEmail(String email);
}
