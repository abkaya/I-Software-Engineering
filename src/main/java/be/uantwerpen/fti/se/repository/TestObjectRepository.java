package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.TestObject;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Quentin Van Ravels on 24-Nov-16.
 */
public interface TestObjectRepository extends CrudRepository<TestObject, Long> {

}