package be.uantwerpen.fti.se.model;

import javax.imageio.ImageIO;
import javax.persistence.Entity;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Admin on 20-10-2016.
 */

@Entity
public class Device extends MyAbstractPersistable<Long> {

    private BufferedImage devImg = null;

    public void addImage(String imageName){

        try {
            devImg = ImageIO.read(new File(imageName));
        } catch (IOException e) {
        }
    }

    public BufferedImage getImage(){return this.devImg;}

}
