package be.uantwerpen.fti.se.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 25-10-2016.
 */

@Entity
public class File extends MyAbstractPersistable<Long> {

    public String fileName;
    public String path;

    public File(){

    }

    public File(String name) {

        this.fileName = name;
    }

    public void setPath(String p){
        path = p;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

}
