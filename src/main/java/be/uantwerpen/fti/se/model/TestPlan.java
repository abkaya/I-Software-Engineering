
package be.uantwerpen.fti.se.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Willem on 20/10/2016.
 */

@Entity
public class TestPlan extends MyAbstractPersistable<Long>{
    private String name;
    private String description;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    private boolean completed = false;


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

    public List<String> getFinsihedUsers() {
        return finsihedUsers;
    }

    public void setFinsihedUsers(List<String> finsihedUsers) {
        this.finsihedUsers = finsihedUsers;
    }

    public void addFinsihedUsers(String finsihedUser) {
        this.finsihedUsers.add(finsihedUser);
    }


    @Column
    @ElementCollection
    private List<String> finsihedUsers;

    public void userFinished(String user){
        finsihedUsers.add(user);
        if(finsihedUsers.size() == users.size())
            this.completed = true;
    }


    @ManyToOne
    @JoinTable(
            name="TESTPLAN_DEVICE",
            joinColumns={@JoinColumn(name="TESTPLAN_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="DEVICE_ID", referencedColumnName="ID")})
    private Device device;



    public TestPlan() {}

    public TestPlan(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setDevice(Device device) {
        this.device = device;
    }

    public Boolean isFinished(User user){
        for (Iterator<String> iter = finsihedUsers.iterator(); iter.hasNext(); ) {
            String userF = iter.next();
            if(userF.equals(user.getUserName())){
                return true;
            }
        }
        return false;
    }
}
