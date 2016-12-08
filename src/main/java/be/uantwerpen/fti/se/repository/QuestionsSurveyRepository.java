package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.QuestionsSurvey;
import be.uantwerpen.fti.se.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Kevin on 8/12/2016.
 */
public interface QuestionsSurveyRepository extends CrudRepository<QuestionsSurvey, Long> {
    Iterable<QuestionsSurvey> findByUsers(User user);
    QuestionsSurvey findOne(Long id);
}
