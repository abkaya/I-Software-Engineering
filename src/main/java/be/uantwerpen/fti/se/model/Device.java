package be.uantwerpen.fti.se.model;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 20-10-2016.
 */

@Entity
public class Device extends MyAbstractPersistable<Long> {
    private String deviceName;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name="DEVICE_FILE",
            joinColumns={@JoinColumn(name="DEVICE_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="FILE_ID", referencedColumnName="ID")})
    private List<File> files;

    public Device() {

    }

    public Device(String name) {

        this.deviceName = name;
        files = new ArrayList<>();
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<File> getFiles(){return files;}

    public String getDeviceName(){return this.deviceName;}


}
