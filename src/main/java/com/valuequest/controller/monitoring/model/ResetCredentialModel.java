package com.valuequest.controller.monitoring.model;

public class ResetCredentialModel {
    private String username;
	private String password;
	private String toEncrypt;
    private String mobile;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUsername() {
		return username;
	}
    public void setUsername(String username) {
		this.username = username;
	}
    public String getPassword() {
		return password;
	}
    public void setPassword(String password) {
		this.password = password;
	}

	public String gettoEncrypt() {
		return toEncrypt;
	}

	public void toEncrypt(String rawPassword) {
		this.toEncrypt = rawPassword;
	}

	public String getusername() {
		return username;
	}
    public void username(String uname) {
		this.username = uname;
	}
    public String getpassword() {
		return password;
	}
    public void password(String pass) {
		this.password = pass;
	}

    
}
