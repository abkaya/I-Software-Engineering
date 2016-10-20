package be.uantwerpen.fti.se.model;

import javax.persistence.Entity;

/**
 * Created by Quentin Van Ravels on 20-Oct-16.
 */
@Entity
public class Device extends MyAbstractPersistable<Long> {

    private String deviceName;
    private String type;
    private String deviceClass;
    private String manufacturer;
    private String driver;

    public Device(){

    }

    public Device(String deviceName, String type, String deviceClass, String manufacturer, String driver){
        this.deviceName = deviceName;
        this.type = type;
        this.deviceClass = deviceClass;
        this.manufacturer = manufacturer;
        this.driver = driver;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeviceClass() {
        return deviceClass;
    }

    public void setDeviceClass(String deviceClass) {
        this.deviceClass = deviceClass;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

        return deviceName.equals(device.deviceName);

    }

    @Override
    public int hashCode() {
        return deviceName.hashCode();
    }

}
