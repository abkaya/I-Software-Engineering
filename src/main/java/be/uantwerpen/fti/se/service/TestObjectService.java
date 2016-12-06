package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.Result;
import be.uantwerpen.fti.se.model.TestObject;
import be.uantwerpen.fti.se.model.User;
import be.uantwerpen.fti.se.repository.ResultRepository;
import be.uantwerpen.fti.se.repository.TestObjectRepository;
import be.uantwerpen.fti.se.repository.TestPlanRepository;
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
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private TestPlanRepository testPlanRepository;

    public Iterable<TestObject> findAll() {return this.testObjectRepository.findAll();}
    public void add(final TestObject testObject){ this.testObjectRepository.save(testObject);}
    public void delete(Long id) {
        this.testObjectRepository.delete(id);
    }


    /**
     * Check for each testObject's testPlan, whether the device name is already in a list. If not, it'll be added to the list.
     * This method is used to pass the X-axis values to the graph javascript variable.
     * @return a list of deviceNames
     */
    public List<String> getDeviceNames(){
        List<String> deviceNames = new ArrayList<String>();
        for(TestObject to : this.findAll()){
            if(!deviceNames.contains(to.getTestPlan().getDevice().getDeviceName()))
                deviceNames.add(to.getTestPlan().getDevice().getDeviceName());
        }
        return deviceNames;
    }

    public Iterable<TestObject> findForUser(String name){
        List<TestObject> myTests = new ArrayList<>();
        for(TestObject it : this.findAll())
        {
            if(it.getUser().equals(name) && !it.isComplete())
            {
                myTests.add(it);
            }
        }

        return myTests;
    }

    public void finishTest(TestObject to){
        List<Result> results = new ArrayList<>();
        Result tempResult;
        for(Long seq : to.getSequences()){
            double difficulty = Math.random()*10;
            double throughput = Math.random()*6 +4;
            double movementTime = Math.random()*1000 + 200;
            double errorRate = Math.random()*4;
            tempResult = new Result(difficulty, throughput, movementTime, errorRate);
            resultRepository.save(tempResult);
            results.add(tempResult);
        }
        to.getTestPlan().addFinsihedUsers(to.getUser());
        testPlanRepository.save(to.getTestPlan());
        to.setResults(results);
        to.setComplete(true);
        testObjectRepository.save(to);
    }
}
