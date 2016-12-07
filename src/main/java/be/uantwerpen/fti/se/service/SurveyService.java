package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.Survey;
import be.uantwerpen.fti.se.model.User;
import be.uantwerpen.fti.se.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Kevin on 24/11/2016.
 */

@Service
public class SurveyService {
    @Autowired
    private SurveyRepository surveyRepository;

    public Iterable<Survey> findAll(){
        return this.surveyRepository.findAll();
    }

    public Iterable<Survey> findByUserName(User user){
        if(user.isAdmin()){

            return surveyRepository.findAll();
        }
        else{
            return surveyRepository.findByUsers(user);
        }
    }

    public void add(final Survey survey) {
        this.surveyRepository.save(survey);
    }

    public void saveSomeAttributes(Survey survey) {
        Survey s1 = survey.getId()==null?null:findOne(survey.getId());
        if (s1 != null){
            s1.setOpinion(survey.getOpinion());
            s1.setPowerControl(survey.getPowerControl());
            s1.setDevice(survey.getDevice());
            s1.setUsers(survey.getUsers());
            surveyRepository.save(s1);
        }
        else{
            surveyRepository.save(survey);
        }
    }

    private Survey findOne(Long id) {
        return surveyRepository.findOne(id);
    }

}
