
package be.uantwerpen.fti.se.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Willem on 20/10/2016.
 */

@Entity
public class TestPlan extends MyAbstractPersistable<Long>{
    private String name;
    private String startDate;
    private String endDate;
    private String description;

    @ManyToOne
    @JoinTable(
            name="TESTPLAN_TESTTEMPLATE",
            joinColumns={@JoinColumn(name="TESTPLAN_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="TESTTEMPLATE_ID", referencedColumnName="ID")})
    private TestTemplate testTemplate;

    public TestTemplate getTestTemplate() {
        return testTemplate;
    }

    public void setTestTemplate(TestTemplate testTemplate) {
        this.testTemplate = testTemplate;
    }

    @ManyToMany
    @JoinTable(

            name="TESTPLAN_USER",
            joinColumns={@JoinColumn(name="TESTPLAN_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")})
    private List<User> users;

    @ManyToOne
    @JoinTable(
            name="TESTPLAN_DEVICE",
            joinColumns={@JoinColumn(name="TESTPLAN_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="DEVICE_ID", referencedColumnName="ID")})
    private Device device;

    public TestPlan() {}

    public TestPlan(String name, String startDate, String endDate, String description) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
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

    public List<User> getUsers() {
        return users;
    }

    public int getAmountUsers() {
        return users.size();
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevices(Device device) {
        this.device = device;
    }
}
