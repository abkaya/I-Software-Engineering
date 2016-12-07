package be.uantwerpen.fti.se.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Kevin on 22/11/2016.
 */
@Entity
public class Survey extends MyAbstractPersistable<Long> {

    private String opinion;
    private String powerControl;
    private String question;

    @ManyToMany
    @JoinTable(
            name = "SURVEY_USER",
            joinColumns = {@JoinColumn(name = "SURVEY_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")})
    private List<User> users;

    @ManyToOne
    @JoinTable(
            name = "SURVEY_DEVICE",
            joinColumns = {@JoinColumn(name = "SURVEY_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "DEVICE_ID", referencedColumnName = "ID")})
    private Device device;

    public Survey() {
        opinion = "";
        powerControl = "";
    }

    public Survey(String powerControl) {
        opinion = "";
        this.powerControl = powerControl;
    }

    public Survey(String opinion, String powerControl) {
        this.opinion = opinion;
        this.powerControl = powerControl;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
