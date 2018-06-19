package com.teasoft.auth.controller;

import com.teasoft.auth.exceptions.MissingParameterException;
import com.teasoft.auth.model.Role;
import com.teasoft.auth.service.RoleService;
import com.teasoft.auth.util.Enums;
import com.teasoft.auth.util.JSONResponse;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
public class RoleController {

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/admin/auth/role", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONResponse saveRole(HttpServletRequest request, HttpServletResponse response, @RequestBody Object data) throws Exception {
        Map<String, String> dataHash = (HashMap<String, String>) data;
        Role role = new Role();
        if (dataHash.containsKey("role")) {
            role.setRoleName(dataHash.get("role"));
        } else {
            throw new MissingParameterException("Role");
        }

        role = roleService.save(role);
        return new JSONResponse(true, 1, role, Enums.JSONResponseMessage.SUCCESS.toString());
    }

    @RequestMapping(value = "/admin/auth/role", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONResponse updateRole(HttpServletRequest request, HttpServletResponse response, @RequestBody Object data) throws Exception {
        Map<String, String> dataHash = (HashMap<String, String>) data;
        Role role;
        Long roleId;
        if (dataHash.containsKey("roleId")) {
            roleId = Long.getLong(dataHash.get("roleId"));
        } else {
            throw new MissingParameterException("Role ID");
        }

        if (roleService.exists(roleId)) {
            role = roleService.findById(roleId).get();
        } else {
            return new JSONResponse(false, 0, null, Enums.JSONResponseMessage.RESOURCE_NOT_FOUND.toString());
        }

        if (dataHash.containsKey("role")) {
            role.setRoleName(dataHash.get("role"));
        }

        role = roleService.save(role);
        return new JSONResponse(true, 1, role, Enums.JSONResponseMessage.SUCCESS.toString());
    }

    @RequestMapping(value = "/admin/auth/role", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONResponse delete(HttpServletRequest request, HttpServletResponse response, @RequestBody Object data) throws Exception {
        Map<String, String> dataHash = (HashMap<String, String>) data;
        Role role;
        Long roleId;
        if (dataHash.containsKey("roleId")) {
            roleId = Long.getLong(dataHash.get("roleId"));
        } else {
            throw new MissingParameterException("Role ID");
        }

        roleService.deleteById(roleId);
        return new JSONResponse(true, 1, null, Enums.JSONResponseMessage.SUCCESS.toString());
    }

    @RequestMapping(value = "/admin/auth/role", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONResponse get(HttpServletRequest request, HttpServletResponse response, @RequestBody Object data) throws Exception {
        Map<String, String> dataHash = (HashMap<String, String>) data;
        Long roleId;
        if (dataHash.containsKey("roleId")) {
            roleId = Long.getLong(dataHash.get("roleId"));
        } else {
            throw new MissingParameterException("Role ID");
        }

        Optional<Role> role = roleService.findById(roleId);
        return new JSONResponse(true, 1, role, Enums.JSONResponseMessage.SUCCESS.toString());
    }

    @RequestMapping(value = "/admin/auth/role/all", method = RequestMethod.GET)
    @ResponseBody
    public JSONResponse getAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Role> role = roleService.findAll();
        return new JSONResponse(true, role.size(), role, Enums.JSONResponseMessage.SUCCESS.toString());
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
