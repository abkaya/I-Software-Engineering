package be.uantwerpen.fti.se.model;

import javax.persistence.Entity;

/**
 * Created by Quentin Van Ravels on 24-Nov-16.
 */
@Entity
public class Result extends MyAbstractPersistable<Long> {

    private double difficulty;
    private double throughput;
    private double movementTime;
    private Double errorRate;

    public Result() {
        difficulty = 0;
        throughput = 0;
        movementTime = 0;
        //errorRate in percentages
        errorRate = 0.0;
    }

    public Result(double difficulty, double throughput, double movementTime, double errorRate){
        this.difficulty = difficulty;
        this.throughput = throughput;
        this.movementTime = movementTime;
        this.errorRate = errorRate;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public double getThroughput() {
        return throughput;
    }

    public void setThroughput(double throughput) {
        this.throughput = throughput;
    }

    public double getMovementTime() {
        return movementTime;
    }

    public void setMovementTime(double movementTime) {
        this.movementTime = movementTime;
    }

    public double getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(double errorRate) {
        this.errorRate = errorRate;
    }
}
