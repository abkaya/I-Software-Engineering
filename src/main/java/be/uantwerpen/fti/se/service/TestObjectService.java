package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.TestObject;
import be.uantwerpen.fti.se.model.User;
import be.uantwerpen.fti.se.repository.TestObjectRepository;
import be.uantwerpen.fti.se.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quentin Van Ravels on 24-Nov-16.
 */
@Service
public class TestObjectService {
    @Autowired
    private TestObjectRepository testObjectRepository;
    @Autowired
    private UserRepository userRepository;

    public Iterable<TestObject> findAll() {return this.testObjectRepository.findAll();}
    public void add(final TestObject testObject){ this.testObjectRepository.save(testObject);}
    public void delete(Long id) {
        this.testObjectRepository.delete(id);
    }

    public Iterable<TestObject> findForUser(String name){
        List<TestObject> myTests = new ArrayList<>();
        for(TestObject it : this.findAll())
        {
            if(it.getUser().equals(name))
            {
                myTests.add(it);
            }
        }

        return myTests;
    }
}
