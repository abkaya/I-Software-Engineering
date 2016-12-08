package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.QuestionsSurvey;
import be.uantwerpen.fti.se.model.User;
import be.uantwerpen.fti.se.repository.QuestionsSurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Kevin on 8/12/2016.
 */
@Service
public class QuestionsSurveyService {

    @Autowired
    private QuestionsSurveyRepository questionsSurveyRepository;

    public Iterable<QuestionsSurvey> findAll(){
        return this.questionsSurveyRepository.findAll();
    }

    public Iterable<QuestionsSurvey> findByUserName(User user){
        if(user.isAdmin()){

            return questionsSurveyRepository.findAll();
        }
        else{
            return questionsSurveyRepository.findByUsers(user);
        }
    }

    public void add(final QuestionsSurvey survey) {
        this.questionsSurveyRepository.save(survey);
    }

    public void saveSomeAttributes(QuestionsSurvey survey) {
        QuestionsSurvey s1 = survey.getId()==null?null:findOne(survey.getId());
        if (s1 != null){
            s1.setUsers(survey.getUsers());
            s1.setQuestions(survey.getQuestions());
            questionsSurveyRepository.save(s1);
        }
        else{
            questionsSurveyRepository.save(survey);
        }
    }

    private QuestionsSurvey findOne(Long id) {
        return questionsSurveyRepository.findOne(id);
    }
}
