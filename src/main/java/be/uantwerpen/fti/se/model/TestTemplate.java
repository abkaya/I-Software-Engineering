package be.uantwerpen.fti.se.model;

import javax.persistence.*;

/**
 * Created by abdil on 20/10/2016.
 */


@Entity
public class TestTemplate extends MyAbstractPersistable<Long>{
    private String name;
    private String description;
    private int seqCount;

    /*
    @OneToMany
    @JoinTable(
            name="TEMPLATE_SEQUENCE",
            joinColumns={@JoinColumn(name="TEMPLATE_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="SEQUENCE_ID", referencedColumnName="ID")})
    private List<TestSequence> testSequences;
    */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplateDescription() {
        return description;
    }

    public void setTemplateDescription(String description) {
        this.description = description;
    }

    public int getSeqCount() {
        return seqCount;
    }

    public void setSeqCount(int seqCount) {
        this.seqCount = seqCount;
    }

    /**
     * Class constructor specifying parameters
     *
     * @param name          The template's name
     * @param description   Template description by admin
     * @param seqCount      Number of tests in this template
     */
    public TestTemplate(String name, String description, int seqCount) {
        this.name = name;
        this.description = description;
        this.seqCount = seqCount;
    }

    public TestTemplate(){};

    /*
    public TestTemplate(String templateName, String templateDescription, int seqCount, List<TestSequence> testSequences) {
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.seqCount = seqCount;
        this.testSequences = testSequences;
    }*/


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        TestTemplate template = (TestTemplate) o;

        return name.equals(template.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
