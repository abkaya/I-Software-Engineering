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

    private String imagesDefaultLocPath;    // Example: parent + ...\templates.devices_images
    private String filesDefaultLocPath;     // Example: parent + ...\devices_files
    private String imageId;                 // deviceName_deviceVersion
    private String imageExtension;          // jpg, png, ...
    private String imageFile;               // deviceName_deviceVersion.jpg (or png, ...)
    private String imageFullPath;           // ...\devices_images\deviceName_deviceVersion.jpg (or png, ...)
    private String filesDirectoryPath;      // ...\devices_files\f_deviceName_deviceVersion

    private boolean used;
    private boolean inUse;
    private boolean disabled;

    public Device(){
        String parent = Paths.get(".").toAbsolutePath().normalize().toString();
        this.imagesDefaultLocPath = parent + "\\src\\main\\resources\\devices_images";
        this.filesDefaultLocPath = parent + "\\src\\main\\resources\\devices_files";

        used = false;
        disabled = false;
        inUse = false;
    }

    public Device(String deviceName, String type, String version, String manufacturer, String driver)  {
        this.deviceName = deviceName;
        this.type = type;
        this.version = version;
        this.manufacturer = manufacturer;
        this.driver = driver;
        String parent = Paths.get(".").toAbsolutePath().normalize().toString();
        this.imagesDefaultLocPath = parent + "\\src\\main\\resources\\devices_images";
        this.filesDefaultLocPath = parent + "\\src\\main\\resources\\devices_files";

        used = false;
        inUse = false;
        disabled = false;

        setImageId(deviceName, version);
        setNOImageFullPath();
        setFilesDirectoryPath(getImageId());
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

    public String getVersion() {return version;}

    public void setVersion(String version) {
        this.version = version;
        setImageId(this.deviceName, this.version);
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

    public String getImagesDefaultLocPath() {
        return imagesDefaultLocPath;
    }

    public String getFilesDefaultLocPath()  {
        return imagesDefaultLocPath;
    }

    public String getImageId()  {
        return imageId;
    }

    public void setImageId(String deviceName, String deviceVersion) {
        this.imageId = deviceName + "_" + deviceVersion;
    }

    public String getImageExtension()   {
        return imageExtension;
    }

    public void setImageExtension(String imageExtension)    {
        this.imageExtension = imageExtension;
    }

    public String getImageFile()    {
        return imageFile;
    }

    public void setImageFile(String imageId, String imageExtension) {
        this.imageFile = imageId + "." + imageExtension;
    }

    public String getImageFullPath()    {
        return imageFullPath;
    }

    public void setImageFullPath(String imageFile)  {
        this.imageFullPath = imagesDefaultLocPath + "\\" + imageFile;
    }

    public void setNOImageFullPath()    {
        this.imageFullPath = "images/no_image.jpg";
    }

    public String getFilesDirectoryPath()   {
        return filesDirectoryPath;
    }

    public void setFilesDirectoryPath(String imageId)   {
        String folder_name = "f_" + imageId;
        this.filesDirectoryPath = filesDefaultLocPath + "\\" + folder_name;
        File destination_folder = new File(filesDirectoryPath);
        if(!destination_folder.exists()) {
            destination_folder.mkdir();
        }
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
