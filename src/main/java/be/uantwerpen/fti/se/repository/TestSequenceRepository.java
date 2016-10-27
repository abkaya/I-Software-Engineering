package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.TestSequence;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Kevin on 22/10/2016.
 */
public interface TestSequenceRepository extends CrudRepository<TestSequence,Long> {
    //TestSequence findByName(String name);
    TestSequence findOne(Long id);
}
