package be.uantwerpen.fti.se.model;

import javax.persistence.Entity;

/**
 * Created by Quentin Van Ravels on 24-Nov-16.
 */
@Entity
public class Result extends MyAbstractPersistable<Long> {

    private double difficulty;
    private double throughtput;
    private double movementTime;

    Result() {
        difficulty = 0;
        throughtput = 0;
        movementTime = 0;
    }

    Result(int difficulty, int throughput, int movementTime){
        this.difficulty = difficulty;
        this.throughtput = throughput;
        this.movementTime = movementTime;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public double getThroughtput() {
        return throughtput;
    }

    public void setThroughtput(double throughtput) {
        this.throughtput = throughtput;
    }

    public double getMovementTime() {
        return movementTime;
    }

    public void setMovementTime(double movementTime) {
        this.movementTime = movementTime;
    }
}
