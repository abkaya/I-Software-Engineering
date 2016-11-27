package be.uantwerpen.fti.se.model;

import javax.persistence.Entity;

/**
 * Created by Kevin on 22/11/2016.
 */
@Entity
public class Survey extends MyAbstractPersistable<Long>{
    private String opinion;
    private String evaluateDifficulty;
  //  private int evaluateBER;

    public Survey(){
        opinion = "";
        evaluateDifficulty = "";
    //    evaluateBER = 0;
    }

    public Survey(String opinion, String evaluateDifficulty){//}, int evaluateBER, int evaluateDifficulty){
        this.opinion = opinion;
        this.evaluateDifficulty = evaluateDifficulty;
    //    this.evaluateBER = evaluateBER;
    }

    public String getOpinion(){
        return opinion;
    }

    public void setOpinion(String opinion){
        this.opinion = opinion;
    }

    public String getEvaluateDifficulty(){
        return evaluateDifficulty;
    }

    public void setEvaluateDifficulty(String evaluateDifficulty){
        this.evaluateDifficulty = evaluateDifficulty;
    }

/*    public int getEvaluateBER(){
        return evaluateBER;
    }

    public void setEvaluateBER(int evaluateBER){
        this.evaluateBER = evaluateBER;
    }*/
}
