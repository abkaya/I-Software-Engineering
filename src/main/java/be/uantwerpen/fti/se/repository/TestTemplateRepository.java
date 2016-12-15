package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.TestTemplate;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by abdil on 22/10/2016.
 */
public interface TestTemplateRepository extends CrudRepository<TestTemplate, Long> {
    TestTemplate findByName(String name);
    TestTemplate findOne(Long id);
}
