package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.Survey;
import be.uantwerpen.fti.se.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Kevin on 24/11/2016.
 */
public interface SurveyRepository extends CrudRepository<Survey,Long> {
    Iterable<Survey> findByUsers(User user);
    Survey findOne(Long id);
}
