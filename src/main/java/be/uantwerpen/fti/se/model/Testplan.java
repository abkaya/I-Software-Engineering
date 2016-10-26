package be.uantwerpen.fti.se.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Willem on 20/10/2016.
 */

@Entity
public class Testplan extends MyAbstractPersistable<Long>{
    private String name;
    private String startDate;
    private String endDate;
    private String description;

    @OneToMany
    @JoinTable(
            name="TESTPLAN_TEMPLATE",
            joinColumns={@JoinColumn(name="TESTPLAN_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="TEMPLATE_ID", referencedColumnName="ID")})
    private Template template;

    @ManyToMany
    @JoinTable(

            name="TESTPLAN_USER",
            joinColumns={@JoinColumn(name="TESTPLAN_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")})
    private List<User> users;

    @OneToMany
    @JoinTable(
            name="TESTPLAN_DEVICE",
            joinColumns={@JoinColumn(name="TESTPLAN_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="DEVICE_ID", referencedColumnName="ID")})
    private List<Device> devices;

    public Testplan(String name) {
        this.name = name;
        this.startDate = "";
        this.endDate = "";
        this.description = "";
        this.template = new Template();
        this.users = new ArrayList<>();
        this.devices = new ArrayList<>();
    }

    public Testplan(String name, String startDate, String endDate, String description, Template template, List<User> users, List<Device> devices) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.template = template;
        this.users = users;
        this.devices = devices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplates(Template template) {
        this.template = template;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
