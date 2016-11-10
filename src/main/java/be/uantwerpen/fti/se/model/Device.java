package be.uantwerpen.fti.se.model;


import javafx.scene.image.Image;
import sun.awt.image.BufferedImageDevice;

import javax.persistence.Entity;
import java.io.File;

/**
 * Created by Quentin Van Ravels and Jan Huijghebaert on 20-Oct-16.
 */
@Entity
public class Device extends MyAbstractPersistable<Long> {


    private String deviceName;
    private String type;
    private String deviceClass;
    private String manufacturer;
    private String driver;
    private String path;
    private boolean used;
    private boolean disabled;

    public Device(){
        used = false;
        disabled = false;
    }

    public Device(String deviceName, String type, String deviceClass, String manufacturer, String driver, File imageName, File f){
        this.deviceName = deviceName;
        this.type = type;
        this.deviceClass = deviceClass;
        this.manufacturer = manufacturer;
        this.driver = driver;
        String foldername = "files_"+deviceName;
        this.path = "C:\\Users\\Admin\\IdeaProjects\\repos\\src\\main\\resources\\static\\devices_files\\"+foldername;
        File destfile = new File(path);
        if(!destfile.exists()) {
            destfile.mkdir();
        }
        used = false;
        disabled = false;
    }

    public String getPath() { return this.path; }

    public void setPath (String p) { this.path = p; }

    public String getDeviceName() {return deviceName;}

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
