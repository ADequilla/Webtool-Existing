/**
 * 
 */
package com.valuequest.controller.administration.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResetCredentialModel {
    
    private Long id;
    private String login;
    private String givenName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private String status;
    private String checkStatus;
    private String[] institution;
    private String[] branch;
    private String[] unit;
    private String[] center;
    private Boolean islogin;
    
    @JsonProperty("roles")
    private List<StateModel> states;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getInstitution() {
        return institution;
    }

    public void setInstitution(String[] institution) {
        this.institution = institution;
    }

    public String[] getBranch() {
        return branch;
    }

    public void setBranch(String[] branch) {
        this.branch = branch;
    }

    public String[] getUnit() {
        return unit;
    }

    public void setUnit(String[] unit) {
        this.unit = unit;
    }

    public String[] getCenter() {
        return center;
    }

    public void setCenter(String[] center) {
        this.center = center;
    }

    public List<StateModel> getStates() {
        return states;
    }

    public void setStates(List<StateModel> states) {
        this.states = states;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Boolean getLoginStat() {
        return islogin;
    }

    public void setLoginStat(Boolean islogin) {
        this.islogin = islogin;
    }
}
