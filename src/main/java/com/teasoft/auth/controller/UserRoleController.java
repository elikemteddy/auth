package com.teasoft.auth.controller;

import com.teasoft.auth.exceptions.MissingParameterException;
import com.teasoft.auth.model.Role;
import com.teasoft.auth.model.UserRole;
import com.teasoft.auth.model.Users;
import com.teasoft.auth.service.RoleService;
import com.teasoft.auth.service.UserRoleService;
import com.teasoft.auth.service.UsersService;
import com.teasoft.auth.util.Enums;
import com.teasoft.auth.util.JSONResponse;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Theodore Elikem Attigah
 */
@RestController
public class UserRoleController {

    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RoleService roleService;
    @Autowired
    UsersService userService;

    @RequestMapping(value = "/admin/auth/userrole", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONResponse save(HttpServletRequest request, HttpServletResponse response, @RequestBody Object data) throws Exception {
        Map<String, String> dataHash = (HashMap<String, String>) data;
        Long roleId, userId;
        if (dataHash.containsKey("roleId")) {
            roleId = Long.getLong(dataHash.get("roleId"));
            if (!roleService.exists(roleId)) {
                return new JSONResponse(false, 0, null, Enums.JSONResponseMessage.RESOURCE_NOT_FOUND.toString());
            }
        } else {
            throw new MissingParameterException("Role ID");
        }

        if (dataHash.containsKey("userId")) {
            userId = Long.getLong(dataHash.get("userId"));
            if (!userService.exists(userId)) {
                return new JSONResponse(false, 0, null, Enums.JSONResponseMessage.RESOURCE_NOT_FOUND.toString());
            }
        } else {
            throw new MissingParameterException("User ID");
        }

        Role role = roleService.findById(roleId).get();
        Users user = userService.findOne(userId).get();
        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUser(user);
        userRole = userRoleService.save(userRole);

        return new JSONResponse(true, 1, userRole, Enums.JSONResponseMessage.SUCCESS.toString());
    }

    @RequestMapping(value = "/admin/auth/userrole", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONResponse update(HttpServletRequest request, HttpServletResponse response, @RequestBody Object data) throws Exception {
        Map<String, String> dataHash = (HashMap<String, String>) data;
        Long roleId, userId, userRoleId;
        UserRole userRole;
        if (dataHash.containsKey("userRoleId")) {
            userRoleId = Long.getLong(dataHash.get("userRoleId"));
            if (!userRoleService.exists(userRoleId)) {
                return new JSONResponse(false, 0, null, Enums.JSONResponseMessage.RESOURCE_NOT_FOUND.toString());
            }
            userRole = userRoleService.findOne(userRoleId).get();
        } else {
            throw new MissingParameterException("UserRole ID");
        }

        if (dataHash.containsKey("roleId")) {
            roleId = Long.getLong(dataHash.get("roleId"));
            if (!roleService.exists(roleId)) {
                return new JSONResponse(false, 0, null, Enums.JSONResponseMessage.RESOURCE_NOT_FOUND.toString());
            }
            userRole.setRole(roleService.findById(roleId).get());
        }

        if (dataHash.containsKey("userId")) {
            userId = Long.getLong(dataHash.get("userId"));
            if (!userService.exists(userId)) {
                return new JSONResponse(false, 0, null, Enums.JSONResponseMessage.RESOURCE_NOT_FOUND.toString());
            }
            userRole.setUser(userService.findOne(userId).get());
        }

        userRole = userRoleService.save(userRole);

        return new JSONResponse(true, 1, userRole, Enums.JSONResponseMessage.SUCCESS.toString());
    }

    @RequestMapping(value = "/admin/auth/userrole", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONResponse delete(HttpServletRequest request, HttpServletResponse response, @RequestBody Object data) throws Exception {
        Map<String, String> dataHash = (HashMap<String, String>) data;
        Long userRoleId;
        UserRole userRole;
        if (dataHash.containsKey("userRoleId")) {
            userRoleId = Long.getLong(dataHash.get("userRoleId"));
            if (!userRoleService.exists(userRoleId)) {
                return new JSONResponse(false, 0, null, Enums.JSONResponseMessage.RESOURCE_NOT_FOUND.toString());
            }
        } else {
            throw new MissingParameterException("UserRole ID");
        }

        userRoleService.deleteById(userRoleId);

        return new JSONResponse(true, 1, null, Enums.JSONResponseMessage.SUCCESS.toString());
    }

    @RequestMapping(value = "/admin/auth/userrole", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONResponse get(HttpServletRequest request, HttpServletResponse response, @RequestBody Object data) throws Exception {
        Map<String, String> dataHash = (HashMap<String, String>) data;
        Long userId;
        Users user;
        if (dataHash.containsKey("userId")) {
            userId = Long.getLong(dataHash.get("userId"));
            if (!userService.exists(userId)) {
                return new JSONResponse(false, 0, null, Enums.JSONResponseMessage.RESOURCE_NOT_FOUND.toString());
            }
            user = userService.findOne(userId).get();
        } else {
            throw new MissingParameterException("UserRole ID");
        }

        List<UserRole> userRoles = userRoleService.findByUser(user);

        return new JSONResponse(true, 1, userRoles, Enums.JSONResponseMessage.SUCCESS.toString());
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
