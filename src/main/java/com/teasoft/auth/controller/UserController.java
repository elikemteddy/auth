/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teasoft.auth.controller;


import com.teasoft.auth.exceptions.MissingParameterException;
import com.teasoft.auth.service.UserRoleService;
import com.teasoft.auth.service.UsersService;
import com.teasoft.auth.util.Enums;
import com.teasoft.auth.util.JSONResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Theodore Elikem Attigah
 */
@RestController
public class UserController {

    @Autowired
    UsersService userService;
    @Autowired
    UserRoleService userRoleService;

    @RequestMapping(value="/api/auth/user/roles", method=RequestMethod.GET)
    @ResponseBody
    public JSONResponse getRoles() {
        String[] roles = userService.getCurrentUsers().getRoles();
        return new JSONResponse(true, roles.length, roles, Enums.JSONResponseMessage.SUCCESS.toString());
    }
    
    @RequestMapping(value="resources/auth/user/count", method=RequestMethod.GET)
    @ResponseBody
    public JSONResponse getUserStatus() {
        Long count;
        boolean status = (count = userService.count()) > 0;
        return new JSONResponse(true, count, count, Enums.JSONResponseMessage.SUCCESS.toString());
    }
    
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public JSONResponse nullPointerException(NullPointerException e) {
        return new JSONResponse(false, 0, null, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JSONResponse exception(Exception e) {
        return new JSONResponse(false, 0, null, e.getMessage());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseBody
    public JSONResponse exception(EmptyResultDataAccessException e) {
        return new JSONResponse(false, 0, null, e.getMessage());
    }

    @ExceptionHandler(MissingParameterException.class)
    @ResponseBody
    public JSONResponse exception(MissingParameterException e) {
        return new JSONResponse(false, 0, null, e.getMessage());
    }
    
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public JSONResponse expiredJwtException(Exception e) {
        return new JSONResponse(false, 0, e.getMessage(), "ExpiredJwt");
    }
}
