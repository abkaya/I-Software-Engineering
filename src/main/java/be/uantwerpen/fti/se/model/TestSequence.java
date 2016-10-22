package be.uantwerpen.fti.se.model;

import javax.persistence.Entity;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Kevin on 20/10/2016.
 */
@Entity
public class TestSequence extends MyAbstractPersistable<Long>{
    private int difficulty;
    private int numberOfTargets;
    private int radiusSmall;
    private int radiusBig;
    ArrayList<ArrayList<Integer>> sequences;

    //When you use the default constructor, you get the easiest difficulty
    public TestSequence() {
        this.difficulty = 1;
        this.numberOfTargets = 11;
        this.radiusSmall = 50;
        this.radiusBig = 250;
        this.sequences = new ArrayList<ArrayList<Integer>>();
    }

    /**
     * Constructor with parameters difficulty and numberOfTargets
     *
     * @param difficulty:      choice the difficulty you want (1-10)
     * @param numberOfTargets: choice the number of targets you want (11-25)
     */
    public TestSequence(int difficulty, int numberOfTargets) {
        this.difficulty = difficulty;
        this.numberOfTargets = numberOfTargets;
        this.radiusSmall = 50;
        this.radiusBig = 250;
        this.sequences = new ArrayList<ArrayList<Integer>>();
    }

    /**
     * Calculate the radius of the targets
     *
     * @return the radius of the targets
     */
    public int determineRadiusSmall() {
        int initialradius = 55;
        if (difficulty > 10) {
            radiusSmall = 0;
            return 5;
        } else {
            radiusSmall = initialradius - (difficulty * 5);
            return initialradius - (difficulty * 5);
        }
    }

    /**
     * Calculate the radius of the global circle
     *
     * @return the radius of the circle
     */
    public int determineRadiusBig() {
        int initialradius = 270;
        if (difficulty > 10) {
            radiusBig = 600;
            return 0;
        } else {
            radiusBig = initialradius + (difficulty * 30);
            return initialradius + (difficulty * 30);
        }
    }

    public ArrayList<ArrayList<Integer>> getSequences(){
        return sequences;
    }

    public void setSequences(ArrayList<ArrayList<Integer>> sequences){
        this.sequences = sequences;
    }

    /**
     * Get the number of targets
     *
     * @return the number of targets
     */
    public int getNumberOfTargets() {
        return numberOfTargets;
    }

    /**
     * Get the radius of the targets
     *
     * @return the radius
     */
    public int getRadiusSmall() {
        return radiusSmall;
    }

    /**
     * Get the radius of the big circle
     *
     * @return the radius
     */
    public int getRadiusBig() {
        return radiusBig;
    }

    /**
     * Get the difficulty of the test
     *
     * @return the difficulty
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * set the number of targets
     *
     * @param number: new number of targets
     */
    public void setNumberOfTargets(int number) {
        numberOfTargets = number;
    }

    /**
     * set the radius of the targets
     *
     * @param small: the new small radius
     */
    public void setRadiusSmall(int small) {
        radiusSmall = small;
    }

    /**
     * set the radius of the big circle
     *
     * @param big: the new big radius
     */
    public void setRadiusBig(int big) {
        radiusBig = big;
    }

    /**
     * Gst the difficulty of the test
     *
     * @param diff: the new difficulty
     */
    public void setDifficulty(int diff) {
        difficulty = diff;
    }

    /**
     * http://java-demos.blogspot.be/2012/11/get-screen-center-point-in-java.html
     *
     * @return the middlepoint of the screen
     */
    public Point getMiddlePoint() {
        Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        return p;
    }

    /**
     * Create a sequence of tests
     *
     * @return sequence of tests
     */
    public ArrayList<ArrayList<Integer>> CreateSequence() {
        sequences = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.add(getDifficulty());
        test.add(numberOfTargets);
        test.add(determineRadiusSmall());
        test.add(determineRadiusBig());
        sequences.add(test);
        test.add(getDifficulty());
        test.add(numberOfTargets);
        test.add(determineRadiusSmall());
        test.add(determineRadiusBig());
        sequences.add(test);
        return sequences;
    }
}
