package be.uantwerpen.fti.se.model;

import org.hibernate.hql.internal.ast.InvalidPathException;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import be.uantwerpen.fti.se.model.TestSequence;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;

import java.security.InvalidParameterException;

/**
 * Created by Kevin on 5/11/2016.
 */
public class TestSequenceTest {
    private TestSequence testSequence;

    @Before
    public void setupTestSequence(){
        testSequence = mock(TestSequence.class);
    }

    @Test
    public void testRadiusSmall() throws InvalidValue {
        testSequence = new TestSequence(20,21);
        assertEquals(3,testSequence.determineRadiusSmall());
        testSequence.setDifficulty(1);
        assertEquals(30,testSequence.determineRadiusSmall());
    }

    @Test
    public void difficultyTest(){
        testSequence = new TestSequence(1,15,6,250);
        assertEquals(9,testSequence.CalculateDifficulty());
    }

    @Test
    public void difficultyTest2(){
        testSequence = new TestSequence(1,15,6,260);
        assertEquals(10,testSequence.CalculateDifficulty());
    }

    @Test
    public void difficultyTest3(){
        testSequence = new TestSequence(1,15,3,250);
        assertEquals(10,testSequence.CalculateDifficulty());
    }

    @Test
    public void difficultyTest4(){
        testSequence = new TestSequence(1,15,3,260);
        assertEquals(10,testSequence.CalculateDifficulty());
    }

    @Test
    public void difficultyTest5(){
        testSequence = new TestSequence(1,25,6,250);
        assertEquals(10,testSequence.CalculateDifficulty());
    }

    @Test
    public void difficultyTest6(){
        testSequence = new TestSequence(1,15,30,250);
        assertEquals(1,testSequence.CalculateDifficulty());
    }

    @Test
    public void difficultyTest7(){
        testSequence = new TestSequence(1,15,27,250);
        assertEquals(2,testSequence.CalculateDifficulty());
    }

    @Test
    public void difficultyTest8(){
        testSequence = new TestSequence(1,15,30,100);
        assertEquals(1,testSequence.CalculateDifficulty());
    }

    @Test
    public void MaxErrorRateTest(){
        testSequence = new TestSequence(1,15);
        testSequence.CalculateMaxErrorRate();
        assertEquals(0.5,testSequence.getmaxErrorRate(),0.0001);
    }

    @Test
    public void MaxErrorRateTest1(){
        testSequence = new TestSequence(9,15);
        testSequence.CalculateMaxErrorRate();
        assertEquals(0.1,testSequence.getmaxErrorRate(),0.0001);
    }
}
