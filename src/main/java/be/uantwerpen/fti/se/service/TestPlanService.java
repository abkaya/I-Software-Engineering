
package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.*;
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

    public Iterable<TestPlan> findByUserName(User user){
        if(user.isAdmin()){

            return testPlanRepository.findAll();
        }
        else{
            return testPlanRepository.findByUsers(user);
        }
    }

    public Iterable<TestPlan> findCompletedTestPlans(){
        List<TestPlan> testPlanList = new ArrayList<TestPlan>();
        for (TestPlan  testPlan : testPlanRepository.findAll()) {
            if(testPlan.isCompleted())
                testPlanList.add(testPlan);
        }
        return testPlanList;
    }

    public Iterable<Device> findDevicesByUser(User user){
        List<Device> devices = new ArrayList<Device>();
        for (TestPlan  testPlan : findByUserName(user)) {
            devices.add(testPlan.getDevice());
        }
        return devices;
    }

    public Iterable<TestTemplate> findTestTemplateByUser(User user){
        List<TestTemplate> testTemplates = new ArrayList<TestTemplate>();
        for (TestPlan  testPlan : findByUserName(user)) {
            testTemplates.add(testPlan.getTestTemplate());
        }
        return testTemplates;
    }

    public void saveSomeAttributes(TestPlan testPlan) {
        TestPlan tempTestPlan = testPlan.getId() == null ? null : findOne(testPlan.getId());
        if (tempTestPlan != null) {

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
        this.testPlanRepository.delete(id);
    }

    private TestPlan findOne(Long id) {
        return testPlanRepository.findOne(id);
    }
}
