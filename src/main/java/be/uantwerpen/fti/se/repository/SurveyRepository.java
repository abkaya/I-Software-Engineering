package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.Survey;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Kevin on 24/11/2016.
 */
public interface SurveyRepository extends CrudRepository<Survey,Long> {
    Survey findOne(Long id);
}
