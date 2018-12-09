/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teasoft.auth.util;

import com.fasterxml.jackson.annotation.JsonView;
import com.teasoft.auth.sec.Views;

/**
 *
 * @author TOSHIBA
 */
public class JSONResponse2 {
    @JsonView(Views.TokenUser.class)
    private boolean status;
    @JsonView(Views.TokenUser.class)
    private long count;
    @JsonView(Views.TokenUser.class)
    private Object result;
    @JsonView(Views.TokenUser.class)
    private String message;
    
    public JSONResponse2() {}
    
    public JSONResponse2(boolean status, long count, Object result, String message) {
        this.status = status;
        this.count = count;
        this.result = result;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
