package be.uantwerpen.fti.se.model;

import be.uantwerpen.fti.se.repository.TestSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Quentin Van Ravels on 22-Nov-16.
 */
@Entity
public class TestObject extends MyAbstractPersistable<Long>{

    private String name;
    private long templateID;
    private String user;

    @OneToOne
    private TestPlan testPlan;

    @ElementCollection
    @CollectionTable(name="sequences", joinColumns=@JoinColumn(name="sequence_id"))
    @Column(name="sequences")
    private List<Long> sequences;

    @OneToMany
    @JoinTable(
            name="TESTOBJECT_RESULTS",
            joinColumns={@JoinColumn(name="TESTOBJECT_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="RESULT_ID", referencedColumnName="ID")})
    private List<Result> results;


    public TestObject(){

    }

    public TestObject(String name, String user, TestPlan testPlan){
        this.name = name;
        this.templateID = testPlan.getTestTemplate().getId();
        this.user = user;
        this.testPlan = testPlan;
        List<Long> seqID = new ArrayList<>();
        for(TestSequence seq : testPlan.getTestTemplate().getTestSequences()){
            seqID.add(seq.getId());
        }
        setSequences(seqID);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public List<Long> getSequences() {
        return sequences;
    }

    public void setSequences(List<Long> sequences) {
        this.sequences = sequences;
        Collections.shuffle(sequences);
    }

    public List<Result> getResults() {
        return results;
    }

    public TestPlan getTestPlan() {
        return testPlan;
    }

    public void setTestPlan(TestPlan testPlan) {
        this.testPlan = testPlan;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public long getTemplateID() {
        return templateID;
    }

    public void setTemplateID(long templateID) {
        this.templateID = templateID;
    }
}
