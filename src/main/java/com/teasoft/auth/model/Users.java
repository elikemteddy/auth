/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teasoft.auth.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.teasoft.auth.sec.Views;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author Theodore Elikem Attigah
 */
@Entity
@Table
public class Users implements Serializable {
    @JsonView(Views.TokenUser.class)
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @JsonView(Views.Public.class)
    @Column(unique=true)
    private String phone;
    @JsonView(Views.Public.class)
    @Column(unique=true)
    private String email;
    @JsonView(Views.Public.class)
    @Column(unique=true)
    private String username;
    @JsonIgnore
    private String password = "";
    @JsonView(Views.TokenUser.class)
    private Boolean enabled;
    @JsonView(Views.TokenUser.class)
    private Boolean accountNonExpired;
    @JsonView(Views.TokenUser.class)
    private Boolean credentialNonExpired;
    @JsonView(Views.TokenUser.class)
    private Boolean accountNonLocked;
    @JsonView(Views.Public.class)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateTimeCreated;
    @JsonView(Views.Public.class)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateTimeUpdated;
    @JsonView(Views.TokenUser.class)
    @OneToMany(mappedBy="user", fetch=FetchType.EAGER)
    private List<UserRole> roles;
    @Transient
    private List<String> userRoles;
    
    public Users() {
        
    }
    
    @PrePersist
    void createdAt() {
        this.dateTimeCreated = this.dateTimeUpdated = new Date();
    }
    
    @PreUpdate
    void updatedAt() {
        this.dateTimeUpdated = new Date();
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(Date dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Date getDateTimeUpdated() {
        return dateTimeUpdated;
    }

    public void setDateTimeUpdated(Date dateTimeUpdated) {
        this.dateTimeUpdated = dateTimeUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[] getRoles() {
        List<String> roles = new ArrayList<String>();
        for(UserRole role: this.roles) {
            roles.add(role.getRole().getRoleName());
        }
        return roles.toArray(new String[roles.size()]);
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
    
    public void setUserRoles(List<String> userRoles) {
        this.userRoles = new ArrayList<String>();
        for(String s : userRoles) {
            this.userRoles.add("ROLE_"+s);
        }
    }
    
    public String[] getUserRoles() {
        if(this.userRoles == null) {
            return getRoles();
        }
        return this.userRoles.toArray(new String[this.userRoles.size()]);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getCredentialNonExpired() {
        return credentialNonExpired;
    }

    public void setCredentialNonExpired(Boolean credentialNonExpired) {
        this.credentialNonExpired = credentialNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

}
