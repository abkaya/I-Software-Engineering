package be.uantwerpen.fti.se.model;

import be.uantwerpen.fti.se.repository.TestSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

/**
 * Created by Quentin Van Ravels on 22-Nov-16.
 */
@Entity
public class TestObject extends MyAbstractPersistable<Long>{

    private long templateID;
    private String user;

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

    public TestObject(long templateID, String user){
        this.templateID = templateID;
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

    public void setResults(List<Result> results) {
        this.results = results;
    }


}
