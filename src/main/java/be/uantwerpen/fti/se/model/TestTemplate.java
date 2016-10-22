package be.uantwerpen.fti.se.model;

import org.aspectj.weaver.ast.Test;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdil on 20/10/2016.
 */
@Entity
public class TestTemplate extends MyAbstractPersistable<Long>{
    private String testTemplateName;
    private String testTemplateDescription;
    private int numberOfTestSequences;

    /*
    @OneToMany
    @JoinTable(
            name="TEMPLATE_SEQUENCE",
            joinColumns={@JoinColumn(name="TEMPLATE_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="SEQUENCE_ID", referencedColumnName="ID")})
    private List<TestSequence> testSequences;
    */

    public String getTestTemplateName() {
        return testTemplateName;
    }

    public void setTestTemplateName(String templateName) {
        this.testTemplateName = templateName;
    }

    public String getTemplateDescription() {
        return testTemplateDescription;
    }

    public void setTemplateDescription(String templateDescription) {
        this.testTemplateDescription = templateDescription;
    }

    public int getNumberOfTestSequences() {
        return numberOfTestSequences;
    }

    public void setNumberOfTestSequences(int numberOfTestSequences) {
        this.numberOfTestSequences = numberOfTestSequences;
    }

    public TestTemplate(String templateName, String templateDescription, int numberOfTestSequences) {
        this.testTemplateName = templateName;
        this.testTemplateDescription = templateDescription;
        this.numberOfTestSequences = numberOfTestSequences;
    }

    public TestTemplate(){};

    /*
    public TestTemplate(String templateName, String templateDescription, int numberOfTestSequences, List<TestSequence> testSequences) {
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.numberOfTestSequences = numberOfTestSequences;
        this.testSequences = testSequences;
    }*/


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        TestTemplate template = (TestTemplate) o;

        return testTemplateName.equals(template.testTemplateName);
    }

    @Override
    public int hashCode() {
        return testTemplateName.hashCode();
    }

}
