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
    private ArrayList<String> questions;

    @ManyToMany
    @JoinTable(
            name = "SURVEY_USER",
            joinColumns = {@JoinColumn(name = "SURVEY_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")})
    private List<User> users;

    public QuestionsSurvey(){
        questions = new ArrayList<String>();
        users = new ArrayList<>();
    }

    public ArrayList<String> getQuestion() {
        return questions;
    }

    public void setQuestion(ArrayList<String> questions) {
        this.questions = questions;
    }

    public void addOneQuestion(String question){
        this.questions.add(question);
    }

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
