package be.uantwerpen.fti.se.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

/**
 * Created by Kevin on 22/11/2016.
 */
@Entity
public class Survey extends MyAbstractPersistable<Long>{

    private String opinion;
    private String powerControl;
   // private Device device;
  //  private int evaluateBER;

    @ManyToOne
    @JoinTable(
            name="SURVEY_DEVICE",
            joinColumns={@JoinColumn(name="SURVEY_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="DEVICE_ID", referencedColumnName="ID")})
    private Device device;

    public Survey(){
        opinion = "";
        powerControl = "";
     //   device = new Device();
    //    evaluateBER = 0;
    }

    public Survey(String powerControl){
        opinion = "";
        this.powerControl = powerControl;
    //    device = new Device();
    }

    public Survey(String opinion, String powerControl){//}, int evaluateBER, int evaluateDifficulty){
        this.opinion = opinion;
        this.powerControl = powerControl;
    //    device = new Device();
    //    this.evaluateBER = evaluateBER;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
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

  /*  public void setDevice(Device device){
        this.device = device;
    }

    public Device getDevice(){
        return device;
    }*/

/*    public int getEvaluateBER(){
        return evaluateBER;
    }

    public void setEvaluateBER(int evaluateBER){
        this.evaluateBER = evaluateBER;
    }*/
}
