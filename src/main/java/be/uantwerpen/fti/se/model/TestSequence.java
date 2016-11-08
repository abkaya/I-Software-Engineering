package be.uantwerpen.fti.se.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.awt.*;
import java.util.*;

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

    @ManyToMany(mappedBy="testSequences")
    private java.util.List<TestTemplate> testTemplates;

    //When you use the default constructor, you get the easiest difficulty
    public TestSequence() {
        this.difficulty = 1;
        this.numberOfTargets = 11;
        this.radiusSmall = 33;
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
        this.radiusSmall = 33;
        this.radiusBig = 250;
        this.sequences = new ArrayList<ArrayList<Integer>>();
    }

    /**
     * Constructor with parameters difficulty, numberOfTargets, radiusSmall and radiusBig
     *
     * @param difficulty:      choice the difficulty you want (1-10)
     * @param numberOfTargets: choice the number of targets you want (11-25)
     * @param radiusSmall:     choice the radius of the small circles
     * @param radiusBig:       choice the radius of the big circle
     */
    public TestSequence(int difficulty, int numberOfTargets, int radiusSmall, int radiusBig){
        this.difficulty = difficulty;
        this.numberOfTargets = numberOfTargets;
        this.radiusSmall = radiusSmall;
        this.radiusBig = radiusBig;
        this.sequences = new ArrayList<ArrayList<Integer>>();
    }

    /**
     * Calculate the radius of the targets
     *
     * @return the radius of the targets
     */
    public int determineRadiusSmall() {
        int initialradius = 36;
        if (difficulty > 10) {
            radiusSmall = 3;
            return 3;
        } else {
            radiusSmall = initialradius - (difficulty * 3);
            return initialradius - (difficulty * 3);
        }
    }

    /**
     * Calculate the radius of the global circle
     *
     * @return the radius of the circle
     */
    public int determineRadiusBig() {
        int initialradius = 135;
        if (difficulty > 10) {
            radiusBig = 300;
            return 0;
        } else {
            radiusBig = initialradius + (difficulty * 15);
            return initialradius + (difficulty * 15);
        }
    }

    /**
     * Get the list of sequences
     *
     * @return the test sequences
     */
    public ArrayList<ArrayList<Integer>> getSequences(){
        return sequences;
    }

    /**
     * set the test sequences
     *
     * @param sequences: the new sequences
     */
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
     * Create a sequence
     *
     * @return sequence
     */
    public ArrayList<ArrayList<Integer>> CreateSequence() {
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.add(getDifficulty());
        test.add(numberOfTargets);
        test.add(determineRadiusSmall());
        test.add(determineRadiusBig());
        sequences.add(test);
        return sequences;
    }

    /**
     * Add a sequence to the list of test sequences
     *
     * @parameter sequence: the sequence we want to add
     */
    public void AddSequence(ArrayList<Integer> sequence){
        this.sequences.add(sequence);
    }

    /**
     * Delete a sequence
     *
     * @param index: gives the place of the sequence we want to remove
     */
    public void RemoveSequence(int index){
        sequences.remove(index);
    }
}