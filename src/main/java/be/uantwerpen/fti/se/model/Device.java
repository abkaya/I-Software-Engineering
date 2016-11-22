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
    private String imageHTMLPath;
    private String imagePath;
    private String filePath;



    public Device(){
        used = false;
        disabled = false;
        inUse = false;
    }

    public Device(String deviceName, String type, String version, String manufacturer, String driver, String imageRawName){
        this.deviceName = deviceName;
        this.type = type;
        this.version = version;
        this.manufacturer = manufacturer;
        this.driver = driver;
        used = false;
        inUse = false;
        disabled = false;

        // View image (Jan)
        imageName = deviceName + "_" + version;
        if(imageRawName.equals("x"))    {
            imageAvailable = false;
            imageHTMLPath = "devices_images/no_image.jpg";
        } else {
            imageAvailable = true;
            int loc = imageRawName.lastIndexOf('.');
            String extension = imageRawName.substring(loc + 1);
            imageHTMLPath = "devices_images/" + imageName + "." + extension;
        }

        // File & image upload (Dries)
        setFilePath(deviceName + "_" + version);
        setImagePath();
    }

    public String getFilePath() { return this.filePath; }

    public void setFilePath (String folder_id) {
        String foldername = "f_" + folder_id;
        String parent = Paths.get(".").toAbsolutePath().normalize().toString();
        this.filePath = parent + "\\src\\main\\resources\\static\\devices_files\\" + foldername;
        File destfile = new File(filePath);
        if(!destfile.exists()) {
            destfile.mkdir();
        }
    }

    public String getImagePath() { return this.imagePath; }

    public void setImagePath () {
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
        setFilePath(deviceName + "_" + version);
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

    public boolean getImageAvailable()  {
        return this.imageAvailable;
    }

    public void setImageAvailable(boolean imageAvailable)   {
        this.imageAvailable = imageAvailable;
    }

    public String getImageName()    {
        return imageName;
    }

    public void setImageName(String imageName)  {
        this.imageName = imageName;
    }

    public String getImageHTMLPath()    {
        return imageHTMLPath;
    }

    public void setImageHTMLPath(String imageHTMLPath)  {
        this.imageHTMLPath = imageHTMLPath;
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
