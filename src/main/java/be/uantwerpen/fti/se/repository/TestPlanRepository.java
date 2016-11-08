package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.TestPlan;
import be.uantwerpen.fti.se.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Willem on 24/10/2016.
 */

public interface TestPlanRepository extends CrudRepository<TestPlan, Long> {
    Iterable<TestPlan> findByUser(User user);
}
