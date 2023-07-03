package com.valuequest.controller.monitoring.model;

public class UpdateDeviceStatusModel {
    private String username;
	private String deviceId;
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
