package be.uantwerpen.fti.se.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 8/12/2016.
 */
@Entity
public class QuestionsSurvey extends MyAbstractPersistable<Long>{
    private String questions;

    @ManyToMany
    @JoinTable(
            name = "QUESTIONS_USER",
            joinColumns = {@JoinColumn(name = "QUESTIONS_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")})
    private List<User> users;

    public QuestionsSurvey(){
        questions = "";
        users = new ArrayList<>();
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    /*public void addOneQuestion(String question){
        this.questions.add(question);
    }*/

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addOnUser(User user){
        this.users.add(user);
    }
}
