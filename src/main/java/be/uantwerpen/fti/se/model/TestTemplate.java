package be.uantwerpen.fti.se.model;

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

    /**
     * A single TestTemplate object can be linked to multiple TestSequence objects, whereas a single
     * TestSequence object can only be linked to one TestTemplate object
     */
    @OneToMany
    @JoinTable(
            name = "TEMPLATE_SEQUENCE",
            joinColumns = {@JoinColumn(name = "TEMPLATE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "SEQUENCE_ID", referencedColumnName = "ID")})
    private List<TestSequence> testSequences;

    /**
     * Get number of targets
     *
     * @return numberOfTargets
     */
    public int getNumberOfTargets() {
        return numberOfTargets;
    }

    /**
     * Set number of targets
     *
     * @param numberOfTargets the number of targets for every sequence within this template
     */
    public void setNumberOfTargets(int numberOfTargets) {
        this.numberOfTargets = numberOfTargets;
    }

    /**
     * Get the first boundary target radius
     *
     * @return the first boundary target radius
     */
    public double getTargetRadius1() {
        return targetRadius1;
    }

    /**
     * Set the first boundary target radius
     *
     * @param targetRadius1 the first boundary target radius
     */
    public void setTargetRadius1(double targetRadius1) {
        this.targetRadius1 = targetRadius1;
    }

    /**
     * Get the second border of the target radius
     *
     * @return the second border of the target radius
     */
    public double getTargetRadius2() {
        return targetRadius2;
    }

    /**
     * Set the second border of the target radius
     *
     * @param targetRadius2 the second border of the target radius
     */
    public void setTargetRadius2(double targetRadius2) {
        this.targetRadius2 = targetRadius2;
    }

    /**
     * Get the first border of the circle radius
     *
     * @return double
     */
    public double getCircleRadius1() {
        return circleRadius1;
    }

    /**
     * Set the first border of the circle radius
     *
     * @param circleRadius1 the first border of the circle radius
     */
    public void setCircleRadius1(double circleRadius1) {
        this.circleRadius1 = circleRadius1;
    }

    /**
     * Get the second border of the circle radius
     *
     * @return the second border of the circle radius
     */
    public double getCircleRadius2() {
        return circleRadius2;
    }

    /**
     * Set the second border of the circle radius
     *
     * @param circleRadius2 the second border of the circle radius
     */
    public void setCircleRadius2(double circleRadius2) {
        this.circleRadius2 = circleRadius2;
    }

    /**
     * Get the TestSequence objects bound to this template
     *
     * @return a list of the test sequences within this template
     */
    public List<TestSequence> getTestSequences() {
        return testSequences;
    }

    /**
     * Set the TestSequence objects bound to this template
     *
     * @param testSequences a list of the test sequences bound to this template
     */
    public void setTestSequences(List<TestSequence> testSequences) {
        this.testSequences = testSequences;
    }

    /**
     * Get the name of this template
     *
     * @return name of this test template
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this template
     *
     * @param name name of this test template
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of this template
     *
     * @return Template description by admin
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of this template
     *
     * @param description Template description by admin
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the number of sequences bound to this template
     *
     * @return number of sequences bound to this template
     */
    public int getSeqCount() {
        return seqCount;
    }

    /**
     * Set the number of sequences which are bound to this template
     *
     * @param seqCount the number of sequences in this template object
     */
    public void setSeqCount(int seqCount) {
        this.seqCount = seqCount;
    }

    /**
     * Counts the number of sequences which are bound to this template, using the list of sequences
     */
    public void calcSeqCount() {
        this.seqCount = testSequences.size();
    }

    /**
     * Boolean request to check whether or not the template is editable
     *
     * @return Whether or not this template is editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Set whether or not his template is editable
     *
     * @param editable whether or not this template is editable
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * Class constructor specifying the name and description parameters
     *
     * @param name        The template's name
     * @param description Template description by admin
     */
    public TestTemplate(String name, String description) {
        this.name = name;
        this.description = description;
        this.seqCount = seqCount;
    }

    /**
     * Default class constructor
     */
    public TestTemplate() {
    };

    /**
     * Class constructor specifying parameters
     *
     * @param name            The template's name
     * @param description     Template description by admin
     * @param editable        Whether or not this template is editable
     * @param seqCount        The number of sequences bound to this template
     * @param numberOfTargets The number of targets each sequence has within this template
     * @param targetRadius1   The first boundary of the target radii of sequences within this template
     * @param targetRadius2   The last boundary of the target radii of sequences within this template
     * @param circleRadius1   The first boundary of the circle radii of sequences within this template
     * @param circleRadius2   The last boundary of the circle radii of sequences within this template
     */
    public TestTemplate(String name, String description, boolean editable, int seqCount, int numberOfTargets, double targetRadius1, double targetRadius2, double circleRadius1, double circleRadius2) {
        this.name = name;
        this.description = description;
        this.editable = editable;
        this.seqCount = seqCount;
        this.numberOfTargets = numberOfTargets;
        this.targetRadius1 = targetRadius1;
        this.targetRadius2 = targetRadius2;
        this.circleRadius1 = circleRadius1;
        this.circleRadius2 = circleRadius2;
    };

    /**
     * Clone method used to clone with all of its parameters except for the sequences, which can result in shared reference errors
     * @return TestTemplate clone object
     */
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

    /**
     * Overrides the default equals method.
     * The object will be cast to a TestTemplate object in order to compare the two
     * @param o object
     * @return whether or not this object is equals to the object it was compared to
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestTemplate template = (TestTemplate) o;

        return name.equals(template.name);
    }


    /**
     * Overrides the default hashcode method and returns the hashcode of the name attribute of this template object.
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
