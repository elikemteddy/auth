package com.teasoft.auth.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.teasoft.auth.exceptions.MissingParameterException;
import com.teasoft.auth.model.Role;
import com.teasoft.auth.model.UserRole;
import com.teasoft.auth.model.Users;
import com.teasoft.auth.sec.Views;
import com.teasoft.auth.service.RoleService;
import com.teasoft.auth.service.UserRoleService;
import com.teasoft.auth.service.UsersService;
import com.teasoft.auth.util.Enums;
import com.teasoft.auth.util.JSONResponse;
import com.teasoft.auth.util.JSONResponse2;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
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

    @Deprecated
    @RequestMapping(value = "/admin/auth/userrole/old", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONResponse save(HttpServletRequest request, HttpServletResponse response, @RequestBody Object data) throws Exception {
        Map<String, Object> dataHash = (HashMap<String, Object>) data;
        Long roleId, userId;
        if (dataHash.containsKey("roleId")) {
            roleId = ((Integer)dataHash.get("roleId")).longValue();
            
            if (!roleService.exists(roleId)) {
                return new JSONResponse(false, 0, null, Enums.JSONResponseMessage.RESOURCE_NOT_FOUND.toString());
            }
        } else {
            throw new MissingParameterException("Role ID");
        }

        if (dataHash.containsKey("userId")) {
            userId = ((Integer)dataHash.get("userId")).longValue();
            if (!userService.exists(userId)) {
                return new JSONResponse(false, 0, null, Enums.JSONResponseMessage.RESOURCE_NOT_FOUND.toString());
            }
        } else {
            throw new MissingParameterException("User ID");
        }

        Role role = roleService.findOne(roleId);
        Users user = userService.findOne(userId);
        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUser(user);
        userRole = userRoleService.save(userRole);

        return new JSONResponse(true, 1, userRole, Enums.JSONResponseMessage.SUCCESS.toString());
    }

    @RequestMapping(value = "/admin/auth/userrole", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONResponse saveUserRole(HttpServletRequest request, HttpServletResponse response, @RequestBody Object data) throws Exception {
        Map<String, Object> dataHash = (HashMap<String, Object>) data;
        Long userId;
        ArrayList roleId;
        if (dataHash.containsKey("userId")) {
            userId = ((Integer)dataHash.get("userId")).longValue();
            if (!userService.exists(userId)) {
                return new JSONResponse(false, 0, null, Enums.JSONResponseMessage.RESOURCE_NOT_FOUND.toString());
            }
        } else {
            throw new MissingParameterException("User ID");
        }
        Users user = userService.findOne(userId);
        ArrayList<UserRole> userRoles = new ArrayList<>();
        if (dataHash.containsKey("roleId")) {
            roleId = (ArrayList) dataHash.get("roleId");
            for (Object id : roleId) {
                if (!roleService.exists(((Integer)id).longValue())) {
                    return new JSONResponse(false, 0, null, Enums.JSONResponseMessage.RESOURCE_NOT_FOUND.toString());
                }
                Role role = roleService.findOne(((Integer)id).longValue());
                if(userRoleService.findByUserAndRole(user, role) != null) {
                    return new JSONResponse(false, 0, null, "User already assigned specified role");
                }
                UserRole userRole = new UserRole();
                userRole.setRole(role);
                userRole.setUser(user);
                userRoles.add(userRole);
            }

        } else {
            throw new MissingParameterException("Role ID");
        }

        return new JSONResponse(true, 1, userRoleService.save(userRoles), Enums.JSONResponseMessage.SUCCESS.toString());
    }

    @RequestMapping(value = "/admin/auth/userrole", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONResponse update(HttpServletRequest request, HttpServletResponse response, @RequestBody Object data) throws Exception {
        Map<String, Integer> dataHash = (HashMap<String, Integer>) data;
        Long roleId, userId, userRoleId;
        UserRole userRole;
        if (dataHash.containsKey("userRoleId")) {
            userRoleId = dataHash.get("userRoleId").longValue();
            if (!userRoleService.exists(userRoleId)) {
                return new JSONResponse(false, 0, null, Enums.JSONResponseMessage.RESOURCE_NOT_FOUND.toString());
            }
            userRole = userRoleService.findOne(userRoleId);
        } else {
            throw new MissingParameterException("UserRole ID");
        }

        if (dataHash.containsKey("roleId")) {
            roleId = dataHash.get("roleId").longValue();
            if (!roleService.exists(roleId)) {
                return new JSONResponse(false, 0, null, Enums.JSONResponseMessage.RESOURCE_NOT_FOUND.toString());
            }
            userRole.setRole(roleService.findOne(roleId));
        }

        if (dataHash.containsKey("userId")) {
            userId = dataHash.get("userId").longValue();
            if (!userService.exists(userId)) {
                return new JSONResponse(false, 0, null, Enums.JSONResponseMessage.RESOURCE_NOT_FOUND.toString());
            }
            userRole.setUser(userService.findOne(userId));
        }

        userRole = userRoleService.save(userRole);

        return new JSONResponse(true, 1, userRole, Enums.JSONResponseMessage.SUCCESS.toString());
    }

    @RequestMapping(value = "/admin/auth/userrole", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONResponse delete(HttpServletRequest request, HttpServletResponse response, @RequestBody Object data) throws Exception {
        Map<String, Integer> dataHash = (HashMap<String, Integer>) data;
        Long userRoleId;
        UserRole userRole;
        if (dataHash.containsKey("userRoleId")) {
            userRoleId = dataHash.get("userRoleId").longValue();
            if (!userRoleService.exists(userRoleId)) {
                return new JSONResponse(false, 0, null, Enums.JSONResponseMessage.RESOURCE_NOT_FOUND.toString());
            }
        } else {
            throw new MissingParameterException("UserRole ID");
        }

        userRoleService.delete(userRoleId);

        return new JSONResponse(true, 1, null, Enums.JSONResponseMessage.SUCCESS.toString());
    }

    @RequestMapping(value = "/admin/auth/userrole", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONResponse get(HttpServletRequest request, HttpServletResponse response, @RequestBody Object data) throws Exception {
        Map<String, Integer> dataHash = (HashMap<String, Integer>) data;
        Long userId;
        Users user;
        if (dataHash.containsKey("userId")) {
            userId = dataHash.get("userId").longValue();
            if (!userService.exists(userId)) {
                return new JSONResponse(false, 0, null, Enums.JSONResponseMessage.RESOURCE_NOT_FOUND.toString());
            }
            user = userService.findOne(userId);
        } else {
            throw new MissingParameterException("UserRole ID");
        }

        List<UserRole> userRoles = userRoleService.findByUser(user);

        return new JSONResponse(true, 1, userRoles, Enums.JSONResponseMessage.SUCCESS.toString());
    }

    /**
     * Returns a list of users of a particular role.
     *
     * @param role the role by which to return users
     * @return all users having a particular role.
     */
    @RequestMapping(value = "admin/auth/users", method = RequestMethod.GET)
    @ResponseBody
    public JSONResponse getByRole(@RequestParam("role") String role) {
        //Get the Role object from db
        Role roleObj = roleService.findByRoleName(role);
        //Find out if the role name provided exists
        if (roleObj == null) {
            //Return false if the role name doesn't exist
            return new JSONResponse(false, 0, null, "Invalid role name");
        }
        //Query the list of users using the specified role
        List<UserRole> users = userRoleService.findByRole(roleObj);
        //return true with the list of users
        return new JSONResponse(true, users.size(), users, Enums.JSONResponseMessage.SUCCESS.toString());
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
