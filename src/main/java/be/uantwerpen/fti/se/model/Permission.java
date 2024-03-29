package be.uantwerpen.fti.se.model;

import javax.persistence.Entity;

/**
 * Created by Edwin on 6/10/2015.
 */
@Entity
public class Permission  extends MyAbstractPersistable<Long> {
    private String name;

    public Permission() {
    }

    public Permission(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
