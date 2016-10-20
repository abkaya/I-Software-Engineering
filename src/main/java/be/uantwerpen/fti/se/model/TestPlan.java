package be.uantwerpen.fti.se.model;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by Willem on 20/10/2016.
 */
public class TestPlan extends MyAbstractPersistable<Long>{
    private String name;
    private String startDate;
    private String endDate;
    private String description;

    @OneToMany
    @JoinTable(
            name="TESTPLAN_TEMPLATE",
            joinColumns={@JoinColumn(name="TESTPLAN_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="TEMPLATE_ID", referencedColumnName="ID")})
    private List<Template> templates;

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
}
