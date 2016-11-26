package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.TestObject;
import be.uantwerpen.fti.se.repository.TestObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Quentin Van Ravels on 24-Nov-16.
 */
@Service
public class TestObjectService {
    @Autowired
    private TestObjectRepository testObjectRepository;

    public Iterable<TestObject> findAll() {return this.testObjectRepository.findAll();}
    public void add(final TestObject testObject){ this.testObjectRepository.save(testObject);}
    public void delete(Long id) {
        this.testObjectRepository.delete(id);
    }
}
