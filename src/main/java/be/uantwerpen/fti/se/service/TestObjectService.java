package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.Result;
import be.uantwerpen.fti.se.model.TestObject;
import be.uantwerpen.fti.se.model.User;
import be.uantwerpen.fti.se.repository.ResultRepository;
import be.uantwerpen.fti.se.repository.TestObjectRepository;
import be.uantwerpen.fti.se.repository.TestPlanRepository;
import be.uantwerpen.fti.se.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Quentin Van Ravels on 24-Nov-16.
 */
@Service
public class TestObjectService {
    @Autowired
    private TestObjectRepository testObjectRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private TestPlanRepository testPlanRepository;

    public Iterable<TestObject> findAll() {return this.testObjectRepository.findAll();}
    public void add(final TestObject testObject){ this.testObjectRepository.save(testObject);}
    public void delete(Long id) {
        for(Result result : testObjectRepository.findOne(id).getResults()){
            resultRepository.delete(result.getId());
        }
        this.testObjectRepository.delete(id);
    }


    /**
     * Check for each testObject's testPlan, whether the device name is already in a list. If not, it'll be added to the list.
     * This method is used to pass the X-axis values to the graph javascript variable.
     * @return a list of deviceNames
     */
    public Iterable<String> getDeviceNames(){
       /*
        List<String> deviceNames = new ArrayList<String>();
        for(TestObject to : this.findAll()){
            if(!deviceNames.contains(to.getTestPlan().getDevice().getDeviceName()))
                deviceNames.add(to.getTestPlan().getDevice().getDeviceName());
        }
        return deviceNames;
        */
       return testObjectRepository.getDeviceNames();
    }

    /**
     * Method to get all errorRates, taking the Device name duplication in consideration and making sure the values end up in
     * the approriate index of the list.
     * @return a list of deviceNames
     */
    public List<List<Double>> getAllErrorRates(){
        List<String> deviceNames = new ArrayList<>();
        List<List<Double>> allErrorRates = new ArrayList<List<Double>>();

        for(TestObject to : this.findAll()){
            List<Double> currentErrorRates= new ArrayList<Double>(to.getResults().size());
            //how to get the list of errorRates
            //add these current results in a list.
            for(Result re : to.getResults()) {
                currentErrorRates.add(re.getErrorRate());
            }

            //don't simply add the list at a certain index, because it will move the double list one index further.
            //instead, go over every single Double within that list, at that index, then add each currentError Double to it.
            if(deviceNames.contains(to.getTestPlan().getDevice().getDeviceName())) {
                for(Double err : currentErrorRates){
                    allErrorRates.get(deviceNames.indexOf(to.getTestPlan().getDevice().getDeviceName())).add(err);
                }
            }

            //then iterate over these results. BUT here is the catch. You need to know where which device is and it could be in multiple places
            //So, first you go over whether or not the device is already in the list.
            //If it is, then deviceNames.indexOf(to.getTestPlan().getDevice().getDeviceName()), adding errorRates into the proper index.
            //If it is not, then you first add the device to the list, and then, again  deviceNames.indexOf(to.getTestPlan().getDevice().getDeviceName())
            //That is always the index of the list in which you add your results in the list within the list.
            if(!deviceNames.contains(to.getTestPlan().getDevice().getDeviceName()) && to.getTestPlan().getDevice().getDeviceName()!=null) {
                deviceNames.add(to.getTestPlan().getDevice().getDeviceName());
                allErrorRates.add(currentErrorRates);
            }
        }
        //Once all of the possible errorRates for each device is stored in the 2 dimensional list, we continue by
        //tranforming these values to another 2D list, one where each list(device bound) within the list will continue 5 values, being the ones required to draw a boxplot.
        return calcBoxPlotValues(allErrorRates);
        //return allErrorRates;
    }

    /**
     * calculate min, Q1, median. Q2, and max of the errorRates for every device
     * @param devicesErrorRates
     * @return
     */
    private List<List<Double>> calcBoxPlotValues(List<List<Double>> devicesErrorRates){
        List<Double> boxPlotValues= new ArrayList<Double>();
        List<List<Double>> allErrorRates = new ArrayList<List<Double>>();
        for(List<Double> devErr : devicesErrorRates){
            //sort list
            Collections.sort(devErr);
            boxPlotValues.add(Collections.min(devErr));         // 1st value : mim
            boxPlotValues.add(devErr.get(devErr.size()*1/4));   // 2nd value : Q1
            boxPlotValues.add(devErr.get(devErr.size()*2/4));   // 3rd value : mean
            boxPlotValues.add(devErr.get(devErr.size()*3/4));   // 4th value : Q3
            boxPlotValues.add(Collections.max(devErr));         // 5th value : max

            allErrorRates.add(boxPlotValues);
            boxPlotValues= new ArrayList<Double>();
        }
        return allErrorRates;
    }

    public void finishTest(TestObject to){
        List<Result> results = new ArrayList<>();
        Result tempResult;
        for(Long seq : to.getSequences()){
            double difficulty = Math.random()*10;
            double throughput = Math.random()*6 +4;
            double movementTime = Math.random()*1000 + 200;
            double errorRate = Math.random()*4;
            tempResult = new Result(difficulty, throughput, movementTime, errorRate);
            resultRepository.save(tempResult);
            results.add(tempResult);
        }
        to.getTestPlan().userFinished(to.getUser());
        testPlanRepository.save(to.getTestPlan());
        to.setResults(results);
        to.setComplete(true);
        testObjectRepository.save(to);
    }

    public Iterable<TestObject> findForUser(String name){
        /*
        List<TestObject> myTests = new ArrayList<>();
        for(TestObject it : this.findAll())
        {
            if(it.getUser().equals(name) && !it.isComplete())
            {
                myTests.add(it);
            }
        }

        return myTests;
        */

        return testObjectRepository.findForUser(name);
    }
}
