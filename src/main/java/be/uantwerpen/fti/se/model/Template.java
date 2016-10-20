package be.uantwerpen.fti.se.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdil on 20/10/2016.
 */

@Entity
public class Template extends MyAbstractPersistable<Long>{
    private String templateName;
    private String templateDescription;
    private int numberOfTests;

    public List<Sequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<Sequence> sequences) {
        this.sequences = sequences;
    }

    @OneToMany
    @JoinTable(
            name="TEMPLATE_SEQUENCE",
            joinColumns={@JoinColumn(name="TEMPLATE_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="SEQUENCE_ID", referencedColumnName="ID")})
    private List<Sequence> sequences;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateDescription() {
        return templateDescription;
    }

    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    public int getNumberOfTests() {
        return numberOfTests;
    }

    public void setNumberOfTests(int numberOfTests) {
        this.numberOfTests = numberOfTests;
    }

    public Template(String templateName, String templateDescription, int numberOfTests ) {
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.numberOfTests = numberOfTests;
        sequences = new ArrayList<>();
    }
    public Template(String templateName, String templateDescription, int numberOfTests, List<Sequence> sequences) {
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.numberOfTests = numberOfTests;
        this.sequences = sequences;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        Template template = (Template) o;

        return templateName.equals(template.templateName);
    }

    @Override
    public int hashCode() {
        return templateName.hashCode();
    }
}
