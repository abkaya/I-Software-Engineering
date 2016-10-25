package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.Testplan;
import be.uantwerpen.fti.se.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Willem on 24/10/2016.
 */

public interface TestplanRepository extends CrudRepository<Testplan, Long> {
    Iterable<Testplan> findByUser(User user);
}
