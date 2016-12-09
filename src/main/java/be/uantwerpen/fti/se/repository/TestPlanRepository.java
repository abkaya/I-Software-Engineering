
package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Willem on 24/10/2016.
 */

public interface TestPlanRepository extends CrudRepository<TestPlan, Long> {
    Iterable<TestPlan> findByUsers(User user);

    @Query(value = "select tp from TestPlan tp where tp.completed=true")
    Iterable<TestPlan> findCompletedTestPlans();

    @Query(value = "select tp from TestPlan tp where tp.testTemplate=:tst")
    Iterable<TestPlan> findByTestTemplate(@Param("tst") TestTemplate testTemplate);

    @Query(value = "select tp from TestPlan tp where tp.device=:dev")
    Iterable<TestPlan> findByDevice(@Param("dev") Device device);

    TestPlan findOne(Long id);
}
