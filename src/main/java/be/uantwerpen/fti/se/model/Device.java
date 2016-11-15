package be.uantwerpen.fti.se.model;


import javafx.scene.image.Image;
import org.springframework.web.multipart.MultipartFile;
import sun.awt.image.BufferedImageDevice;

import javax.persistence.Entity;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    private String filePath;
    private String imagePath;
    //private String imageName;
    //private File image;
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
        /*
        String foldername = "files_"+deviceName;
        this.path = "C:\\Users\\Admin\\IdeaProjects\\repos\\src\\main\\resources\\static\\devices_files\\"+foldername;
        File destfile = new File(path);
        if(!destfile.exists()) {
            destfile.mkdir();
        }
        */
        setFilePath(deviceName);
        setImagePath(deviceName);
        used = false;
        disabled = false;
    }

    public String getFilePath() { return this.filePath; }

    public void setFilePath (String dev) {
        String foldername = "files_"+dev;
        String parent = Paths.get(".").toAbsolutePath().normalize().toString();
        this.filePath = parent+"\\src\\main\\resources\\static\\devices_files\\"+foldername;
        File destfile = new File(filePath);
        if(!destfile.exists()) {
            destfile.mkdir();
        }
    }

    public String getImagePath() { return this.imagePath; }

    public void setImagePath (String dev) {
        String foldername = "files_"+dev;
        String parent = Paths.get(".").toAbsolutePath().normalize().toString();
        this.imagePath = parent+"\\src\\main\\resources\\static\\devices_images\\"+foldername;
        File destfile = new File(imagePath);
        if(!destfile.exists()) {
            destfile.mkdir();
        }
    }

    /*
    public String getImageName(){return this.image.toString();}

    //public void setImageName(String name){this.imageName = name;}

    public File getImage(){return this.image;}

    public void setImage(File im){this.image = im;}
    */

    public String getDeviceName() {return this.deviceName;}

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        setFilePath(deviceName);
        setImagePath(deviceName);
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
