
package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.*;
import be.uantwerpen.fti.se.repository.TestPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

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

    public void saveSomeAttributes(TestPlan testPlan) {
        TestPlan tempTestPlan = testPlan.getId() == null ? null : findOne(testPlan.getId());
        if (tempTestPlan != null) {

            tempTestPlan.setName(testPlan.getName());
            tempTestPlan.setStartDate(testPlan.getStartDate());
            tempTestPlan.setEndDate(testPlan.getEndDate());
            tempTestPlan.setDescription(testPlan.getDescription());

            testPlan.getTestTemplate().setEditable(false);
            tempTestPlan.setTestTemplate(testPlan.getTestTemplate());
            tempTestPlan.setUsers(testPlan.getUsers());

            for (Iterator<Device> iter = testPlan.getDevices().iterator(); iter.hasNext(); ) {
                Device device = iter.next();
                device.setIsInUse();
            }
            tempTestPlan.setDevices(testPlan.getDevices());


            testPlanRepository.save(tempTestPlan);
        } else {
            testPlan.getTestTemplate().setEditable(false);
            for (Iterator<Device> iter = testPlan.getDevices().iterator(); iter.hasNext(); ) {
                Device device = iter.next();
                device.setIsInUse();
            }
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
