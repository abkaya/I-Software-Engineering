package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.TestSequence;
import be.uantwerpen.fti.se.repository.TestSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Kevin on 21/10/2016.
 */
@Service
public class TestSequenceService {
    @Autowired
    private TestSequenceRepository testSequenceRepository;
    public Iterable<TestSequence> findAll(){ return this.testSequenceRepository.findAll();}

    public void add(final TestSequence testSequence) {this.testSequenceRepository.save(testSequence);}
    public void delete(Long id){this.testSequenceRepository.delete(id);}

    /*public TestSequence findByTestSequenceName(String name) {
        return testSequenceRepository.findByName(name);
    }*/

    public void saveSomeAttributes(TestSequence testSequence) {
        TestSequence tempTestSequence = testSequence.getId()==null?null:findOne(testSequence.getId());
        if (tempTestSequence != null){
            tempTestSequence.setDifficulty(testSequence.getDifficulty());
            tempTestSequence.setNumberOfTargets(testSequence.getNumberOfTargets());
            tempTestSequence.setRadiusBig(testSequence.getRadiusBig());
            tempTestSequence.setRadiusSmall(testSequence.getRadiusSmall());
            testSequenceRepository.save(tempTestSequence);
        }
        else{
            testSequenceRepository.save(testSequence);
        }
    }

    private TestSequence findOne(Long id) {
        return testSequenceRepository.findOne(id);
    }
}

