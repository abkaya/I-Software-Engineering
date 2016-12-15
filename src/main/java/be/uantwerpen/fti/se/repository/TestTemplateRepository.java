package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.Device;
import be.uantwerpen.fti.se.model.TestPlan;
import be.uantwerpen.fti.se.model.TestTemplate;
import be.uantwerpen.fti.se.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by abdil on 22/10/2016.
 */
public interface TestTemplateRepository extends CrudRepository<TestTemplate, Long> {
    TestTemplate findByName(String name);
    TestTemplate findOne(Long id);

    @Query(value = "select tp.testTemplate from TestPlan tp left join tp.users u where u=:usr")
    Iterable<TestTemplate> findTestTemplateByUser(@Param("usr") User user);
}
