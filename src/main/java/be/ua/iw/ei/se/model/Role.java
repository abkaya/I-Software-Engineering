package be.ua.iw.ei.se.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by Edwin on 6/10/2015.
 */
@Entity
public class Role extends MyAbstractPersistable<Long> {
    private String name;
    @ManyToMany
    @JoinTable(
            name="ROLE_PERM",
            joinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="PERM_ID", referencedColumnName="ID")})
    private List<Permission> permissions;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return name.equals(role.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
