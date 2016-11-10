
package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.Role;
import be.uantwerpen.fti.se.model.TestPlan;
import be.uantwerpen.fti.se.model.User;
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

    public void save(final TestPlan testplan) {
        this.testPlanRepository.save(testplan);
    }

    public void delete(Long id) {
        this.testPlanRepository.delete(id);
    }

}
