package be.uantwerpen.fti.se.model;

import javax.persistence.Entity;
import java.io.File;
import java.nio.file.Paths;

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
    private boolean imageAvailable;
    private String imageName;
    private String imageExtension;
    private String imagePath;
    private String filePath;



    public Device(){
        used = false;
        disabled = false;
        inUse = false;
    }

    public Device(String deviceName, String type, String version, String manufacturer, String driver, boolean imageAvailable){
        this.deviceName = deviceName;
        this.type = type;
        this.version = version;
        this.manufacturer = manufacturer;
        this.driver = driver;
        used = false;
        inUse = false;
        disabled = false;
        // Added by Jan for image
        this.imageAvailable = imageAvailable;
        imageName = "no_image";
        if(imageAvailable)  {
            imageExtension = "jpg"; // Search extension!!!
            imageName = deviceName + "_" + version + "." + imageExtension;
            imagePath = "devices_images/" + imageName;
        } else {
            imagePath = "devices_images/no_image.jpg";
        }
        setFilePath(deviceName);
        setImagePath(deviceName);

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
        String parent = Paths.get(".").toAbsolutePath().normalize().toString();
        this.imagePath = parent+"\\src\\main\\resources\\static\\devices_images";
        File destfile = new File(imagePath);
        if(!destfile.exists()) {
            destfile.mkdir();
        }
    }

    public String getDeviceName() {
        return deviceName;
    }

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

    public String getImageName()    {
        return imageName;
    }

    public void setImageName(String imageName)  {
        this.imageName = imageName;
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
