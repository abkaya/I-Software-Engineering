
package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.*;
import be.uantwerpen.fti.se.repository.TestObjectRepository;
import be.uantwerpen.fti.se.repository.TestPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Willem on 24/10/2016.
 */

@Service
public class TestPlanService {
    @Autowired
    private TestPlanRepository testPlanRepository;
    @Autowired
    private TestObjectRepository testObjectRepository;
    @Autowired
    private TestObjectService testObjectService;

    public Iterable<TestPlan> findByUsers(User user){
        if(user.isAdmin()){

            return testPlanRepository.findAll();
        }
        else{
            return testPlanRepository.findByUsers(user);
        }
    }

    public Iterable<TestPlan> findCompletedTestPlans(){
        return testPlanRepository.findCompletedTestPlans();
    }

    Iterable<TestPlan> findByTestTemplate(TestTemplate testTemplate){
        return testPlanRepository.findByTestTemplate(testTemplate);
    }

    Iterable<TestPlan> findByDevice(Device device){
        return testPlanRepository.findByDevice(device);
    }

    public void saveSomeAttributes(TestPlan testPlan) {
        TestPlan tempTestPlan = testPlan.getId() == null ? null : findOne(testPlan.getId());
        if (tempTestPlan != null) {


            if(((List<TestPlan>)findByTestTemplate(tempTestPlan.getTestTemplate())).size() == 1){
                tempTestPlan.getTestTemplate().setEditable(true);
            }

            if(!(((List<TestPlan>)findByDevice(tempTestPlan.getDevice())).size() == 1 )){
                tempTestPlan.getDevice().setIsNotInUse();
            }

            tempTestPlan.setName(testPlan.getName());
            tempTestPlan.setDescription(testPlan.getDescription());
            tempTestPlan.setTestTemplate(testPlan.getTestTemplate());
            tempTestPlan.setUsers(testPlan.getUsers());
            tempTestPlan.setDevice(testPlan.getDevice());

            if(testPlan.getTestTemplate() != null)
                testPlan.getTestTemplate().setEditable(false);
            if(testPlan.getDevice() != null){
                testPlan.getDevice().setIsInUse();
            }
            testPlanRepository.save(tempTestPlan);
        } else {
            if(testPlan.getTestTemplate() != null)
                testPlan.getTestTemplate().setEditable(false);
            if(testPlan.getDevice() != null)
                testPlan.getTestTemplate().setEditable(false);
            testPlanRepository.save(testPlan);
        }
    }

    public void delete(Long id) {
        for(TestObject to : testObjectRepository.findAll())
        {
            if(to.getTestPlan().equals(findOne(id))){
                testObjectService.delete(to.getId());
            }
        }

        this.testPlanRepository.delete(id);
    }

    private TestPlan findOne(Long id) {
        return testPlanRepository.findOne(id);
    }

    public void createTestObject(TestPlan testplan){
        for(User user : testplan.getUsers()){
            testObjectRepository.save(new TestObject(testplan.getName(), user.getUserName(), testplan ));
        }
    }
}
