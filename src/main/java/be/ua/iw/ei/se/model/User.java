package be.ua.iw.ei.se.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edwin on 6/10/2015.
 */
@Entity
public class User extends AbstractPersistable<Long>{
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    @ManyToMany
    @JoinTable(
            name="USER_ROLE",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = firstName;
        this.password = lastName;
        roles = new ArrayList<>();
    }
    public User(String firstName, String lastName, String userName, String password, List<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public User() {
        roles = new ArrayList<>();
        userName = "";
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userName.equals(user.userName);

    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }
}
