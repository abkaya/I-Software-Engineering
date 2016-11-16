package be.uantwerpen.fti.se.model;

import be.uantwerpen.fti.se.repository.TestSequenceRepository;
import org.aspectj.weaver.ast.Test;

import javax.persistence.*;
import java.util.List;

/**
 * Created by abdil on 20/10/2016.
 */

@Entity
public class TestTemplate extends MyAbstractPersistable<Long> {
    private String name;
    private String description;
    private int seqCount = 0;
    private boolean editable = true;
    private int numberOfTargets;
    private double targetRadius1;
    private double targetRadius2;
    private double circleRadius1;
    private double circleRadius2;

    @OneToMany
    @JoinTable(
            name = "TEMPLATE_SEQUENCE",
            joinColumns = {@JoinColumn(name = "TEMPLATE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "SEQUENCE_ID", referencedColumnName = "ID")})
    private List<TestSequence> testSequences;

    public int getNumberOfTargets() {
        return numberOfTargets;
    }

    public void setNumberOfTargets(int numberOfTargets) {
        this.numberOfTargets = numberOfTargets;
    }

    public double getTargetRadius1() {
        return targetRadius1;
    }

    public void setTargetRadius1(double targetRadius1) {
        this.targetRadius1 = targetRadius1;
    }

    public void setSeqCount(int seqCount) {
        this.seqCount = seqCount;
    }

    public double getTargetRadius2() {
        return targetRadius2;
    }

    public void setTargetRadius2(double targetRadius2) {
        this.targetRadius2 = targetRadius2;
    }

    public double getCircleRadius1() {
        return circleRadius1;
    }

    public void setCircleRadius1(double circleRadius1) {
        this.circleRadius1 = circleRadius1;
    }

    public double getCircleRadius2() {
        return circleRadius2;
    }

    public void setCircleRadius2(double circleRadius2) {
        this.circleRadius2 = circleRadius2;
    }

    public List<TestSequence> getTestSequences() {
        return testSequences;
    }

    public void setTestSequences(List<TestSequence> testSequences) {
        this.testSequences = testSequences;
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

    public int getSeqCount() {
        return seqCount;
    }

    public void setSeqCount() {
        this.seqCount = testSequences.size();
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * Class constructor specifying parameters
     *
     * @param name          The template's name
     * @param description   Template description by admin
     */
    public TestTemplate(String name, String description) {
        this.name = name;
        this.description = description;
        this.seqCount = seqCount;
    }

    public TestTemplate(){};

    public TestTemplate clone() {
        TestTemplate obj = new TestTemplate();
        obj.setName(this.name);
        obj.setDescription(this.description);
        obj.setSeqCount(this.seqCount);
        obj.setNumberOfTargets(this.numberOfTargets);
        obj.setTargetRadius1(this.targetRadius1);
        obj.setTargetRadius2(this.targetRadius2);
        obj.setCircleRadius1(this.circleRadius1);
        obj.setCircleRadius2(this.circleRadius2);
        obj.setEditable(true);
        //Copying sequences will result in a shared reference error. To prevent this, share the attributes to generate the same
        //sequences with a unique id instead
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestTemplate template = (TestTemplate) o;

        return name.equals(template.name);
    }


    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
