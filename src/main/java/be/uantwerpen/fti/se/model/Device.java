package be.uantwerpen.fti.se.model;

import javax.persistence.Entity;

/**
 * Created by Quentin Van Ravels and Jan Huijghebaert on 20-Oct-16.
 */
@Entity
public class Device extends MyAbstractPersistable<Long> {


    private String deviceName;
    private String type;
    private String version;
    private String manufacturer;
    private String driver;
    private boolean used;
    private boolean inUse;
    private boolean disabled;

    public Device(){
        used = false;
        disabled = false;
    }

    public Device(String deviceName, String type, String version, String manufacturer, String driver){
        this.deviceName = deviceName;
        this.type = type;
        this.version = version;
        this.manufacturer = manufacturer;
        this.driver = driver;
        used = false;
        inUse = false;
        disabled = false;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public boolean isUsed() {
        return used;
    }

    public void setIsUsed() {
        this.used = true;
    }

    public void setIsUnused()   {
        this.used = false;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled()   {
        disabled = true;
    }

    public void setEnabled()    {
        disabled = false;
    }

    public boolean isInUse() {return inUse;}

    public void setIsInUse() {this.inUse = true; this.setIsUsed();}

    public void setIsNotInUse() {this.inUse = false;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

        return deviceName.equals(device.deviceName);

    }

    @Override
    public int hashCode() {
        if(deviceName != null) {
            return deviceName.hashCode();
        }

        return 0;
    }
}
