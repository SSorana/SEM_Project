package nl.tudelft.sem.template.authentication.entities;

import java.io.Serializable;

public class NetworkResponse implements Serializable {
    public static final long serialVersionUID = -5220691200495009753L;

    private String ipAddress;

    private String macAddress;

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

}

