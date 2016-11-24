package be.uantwerpen.fti.se.model;

import javax.persistence.Entity;

/**
 * Created by Kevin on 22/11/2016.
 */
@Entity
public class Survey extends MyAbstractPersistable<Long>{
    private String opinion;
    private int evaluateDifficulty;
    private int evaluateBER;

    public Survey(){
        opinion = "";
        evaluateDifficulty = 0;
        evaluateBER = 0;
    }

    public String getOpinion(){
        return opinion;
    }

    public void setOpinion(String opinion){
        this.opinion = opinion;
    }

    public int getEvaluateDifficulty(){
        return evaluateDifficulty;
    }

    public void setEvaluateDifficulty(int evaluateDifficulty){
        this.evaluateDifficulty = evaluateDifficulty;
    }

    public int getEvaluateBER(){
        return evaluateBER;
    }

    public void setEvaluateBER(int evaluateBER){
        this.evaluateBER = evaluateBER;
    }
}
