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
    private double distanceTargets;
    private float maxErrorRate;         //Between 0 and 1
    ArrayList<ArrayList<Integer>> sequences;

    @ManyToMany(mappedBy="testSequences")
    private java.util.List<TestTemplate> testTemplates;

    //When you use the default constructor, you get the easiest difficulty
    public TestSequence() {
        this.difficulty = 1;
        this.numberOfTargets = 11;
        this.radiusSmall = 33;
        this.radiusBig = 250;
        this.distanceTargets = 360/numberOfTargets;
        this.sequences = new ArrayList<ArrayList<Integer>>();
        this.maxErrorRate = 1;
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
        this.distanceTargets = 360/numberOfTargets;
        this.sequences = new ArrayList<ArrayList<Integer>>();
        this.maxErrorRate = (float) (0.55 - (difficulty*0.05));
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
        this.distanceTargets = 360/numberOfTargets;
        this.sequences = new ArrayList<ArrayList<Integer>>();
        this.maxErrorRate = (float) (0.55 - (difficulty*0.05));
    }

    /**
     * Constructor with parameters numberOfTargets, radiusSmall and radiusBig
     *
     * @param numberOfTargets: choice the number of targets you want (11-25)
     * @param radiusSmall:     choice the radius of the small circles
     * @param radiusBig:       choice the radius of the big circle
     */
    public TestSequence(int numberOfTargets, int radiusSmall, int radiusBig){
        this.numberOfTargets = numberOfTargets;
        this.radiusSmall = radiusSmall;
        this.radiusBig = radiusBig;
        this.distanceTargets = 360/numberOfTargets;
        this.sequences = new ArrayList<ArrayList<Integer>>();
        this.maxErrorRate = (float) (0.55 - (difficulty*0.05));
    }

    /**
     * Constructor with parameters distanceTargets, radiusSmall and radiusBig
     *
     * @param distanceTargets: choice the distance between the targets you want
     * @param radiusSmall:     choice the radius of the small circles
     * @param radiusBig:       choice the radius of the big circle
     */
    public TestSequence(double distanceTargets, int radiusSmall, int radiusBig){
        this.distanceTargets = distanceTargets;
        this.radiusSmall = radiusSmall;
        this.radiusBig = radiusBig;
        this.numberOfTargets = (int) (360/distanceTargets);
        this.sequences = new ArrayList<ArrayList<Integer>>();
        this.maxErrorRate = (float) (0.55 - (difficulty*0.05));
    }

    /**
     * Calculate the max error rate
     */
    public void CalculateMaxErrorRate(){
        this.maxErrorRate = (float) (0.55 - (difficulty*0.05));
    }

    /**
     * Calculate the number of targets
     */
    public void CalculateNumberOfTargets(){
        this.numberOfTargets = (int) (360/distanceTargets);
    }

    /**
     * Calculate the number of targets
     */
    public void CalculateDistanceTargets(){
        this.numberOfTargets = 360/numberOfTargets;
    }

    /**
     * Calculate the difficulty  with parameters radiusSmall, radiusBig and numberOfTargets
     */
    public int CalculateDifficulty(){
        int dif = 0;
        if(radiusSmall < 3){
            radiusSmall = 3;
            difficulty = 10;
        }
        else if (radiusSmall > 30){
            difficulty = 1;
        }
        else{
            int mod = radiusSmall % 3;
            if(mod == 0){
                dif = 0;
            }
            else {
                dif = 3 - mod;
            }
            int calculation = (radiusSmall + dif) / 3;
            difficulty = 11-calculation;
        }
        if((numberOfTargets > 20) && (difficulty < 10)){
            difficulty++;
        }
        if((radiusBig > 250)&& (difficulty < 10)){
            difficulty++;
        }
        if((radiusBig < 200)&& (difficulty > 1)){
            difficulty--;
        }
        return difficulty;
    }

    /**
     * Calculate the radius of the targets
     *
     * @return the radius of the targets
     */
    public int determineRadiusSmall() {
        int initialradius = 33;
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
     * Get the maximum error rate
     *
     * @return the maximum error rate
     */
    public float getmaxErrorRate(){
        return maxErrorRate;
    }

    /**
     * set the maximum error rate
     *
     * @param maxErrorRate: the new maxErrorRate
     */
    public void setMaxErrorRate(float maxErrorRate)
    {
        this.maxErrorRate = maxErrorRate;
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
