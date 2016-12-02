package be.uantwerpen.fti.se.model;

import javax.persistence.Entity;

/**
 * Created by Kevin on 22/11/2016.
 */
@Entity
public class Survey extends MyAbstractPersistable<Long>{

    private String opinion;
    private String powerControl;
  //  private int evaluateBER;

    public Survey(){
        opinion = "";
        powerControl = "";
    //    evaluateBER = 0;
    }

    public Survey(String powerControl){
        opinion = "";
        this.powerControl = powerControl;
    }

    public Survey(String opinion, String powerControl){//}, int evaluateBER, int evaluateDifficulty){
        this.opinion = opinion;
        this.powerControl = powerControl;
    //    this.evaluateBER = evaluateBER;
    }

    public String getOpinion(){
        return opinion;
    }

    public void setOpinion(String opinion){
        this.opinion = opinion;
    }

    public String getPowerControl(){
        return powerControl;
    }

    public void setPowerControl(String powerControl){
        this.powerControl = powerControl;
    }

/*    public int getEvaluateBER(){
        return evaluateBER;
    }

    public void setEvaluateBER(int evaluateBER){
        this.evaluateBER = evaluateBER;
    }*/
}
