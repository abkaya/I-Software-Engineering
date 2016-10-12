package be.uantwerpen.fti.se.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;

/**
 * Created by Edwin on 28/10/2015.
 */
public class MyAbstractPersistable<T extends Serializable> extends AbstractPersistable<T>{
    @Override
    public void setId(T id) {
        super.setId(id);
    }
}
