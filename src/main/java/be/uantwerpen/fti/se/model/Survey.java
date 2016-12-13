package be.uantwerpen.fti.se.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 22/11/2016.
 */
@Entity
public class Survey extends MyAbstractPersistable<Long> {

    private String opinion;
    private String powerControl;
    private ArrayList<String> tester;

    @ManyToOne
    @JoinTable(
            name = "SURVEY_DEVICE",
            joinColumns = {@JoinColumn(name = "SURVEY_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "DEVICE_ID", referencedColumnName = "ID")})
    private Device device;

    public Survey() {
        opinion = "";
        powerControl = "";
        tester = new ArrayList<>();
    }

    public Survey(String powerControl) {
        opinion = "";
        this.powerControl = powerControl;
        tester = new ArrayList<>();
    }

    public Survey(String opinion, String powerControl) {
        this.opinion = opinion;
        this.powerControl = powerControl;
        tester = new ArrayList<>();
    }

    public ArrayList<String> getTester(){
        return tester;
    }

    public void setTester(ArrayList<String> tester){
        this.tester = tester;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getPowerControl() {
        return powerControl;
    }

    public void setPowerControl(String powerControl) {
        this.powerControl = powerControl;
    }
}
