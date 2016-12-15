package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.TestSequence;
import be.uantwerpen.fti.se.model.TestTemplate;
import be.uantwerpen.fti.se.repository.TestSequenceRepository;
import be.uantwerpen.fti.se.repository.TestTemplateRepository;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdil on 22/10/2016.
 */
@Service
public class TestTemplateService {
    @Autowired
    private TestTemplateRepository testTemplateRepository;

    @Autowired
    TestSequenceRepository testSequenceRepository;

    /**
     * Implements the default repository findAll method. It no longer differs from the default method.
     * @return Iterable<TestTemplate>
     */
    public Iterable<TestTemplate> findAll() {
        return this.testTemplateRepository.findAll();
    }

    /**
     * Implements the default repository save method.
     * @param testTemplate
     */
    public void add(final TestTemplate testTemplate) {
        this.testTemplateRepository.save(testTemplate);
    }

    /**
     * Implements the default repository delete (by id) method
     * @param id
     */
    public void delete(Long id) {
        this.testTemplateRepository.delete(id);
    }

    /**
     * Implements the default repository findByName method
     * @param name
     * @return TestTemplate
     */
    public TestTemplate findByName(String name) {
        return testTemplateRepository.findByName(name);
    }

    /**
     * Service method to save certain attributes of either existing templates or  entirely new ones
     * @param testTemplate
     */
    public void saveSomeAttributes(TestTemplate testTemplate) {
        TestTemplate tempTestTemplate = testTemplate.getId() == null ? null : findOne(testTemplate.getId());
        //if the template is existent
        if (tempTestTemplate != null) {
            tempTestTemplate.setDescription(testTemplate.getDescription());
            tempTestTemplate.setTestSequences(null);
            tempTestTemplate.setTestSequences(generateTestSequences(testTemplate.getId(), testTemplate.getSeqCount(), testTemplate.getNumberOfTargets(), testTemplate.getTargetRadius1(), testTemplate.getTargetRadius2(), testTemplate.getCircleRadius1(), testTemplate.getCircleRadius2()));
            //tempTestTemplate.setEditable(testTemplate.isEditable());
            tempTestTemplate.setName(testTemplate.getName());
            tempTestTemplate.setSeqCount(testTemplate.getSeqCount());
            tempTestTemplate.setNumberOfTargets(testTemplate.getNumberOfTargets());
            tempTestTemplate.setTargetRadius1(testTemplate.getTargetRadius1());
            tempTestTemplate.setTargetRadius2(testTemplate.getTargetRadius2());
            tempTestTemplate.setCircleRadius1(testTemplate.getCircleRadius1());
            tempTestTemplate.setCircleRadius2(testTemplate.getCircleRadius2());
            testTemplateRepository.save(tempTestTemplate);
        }
        //if it is a new template, first save it to acquire a template id, then generate the sequences
        else {
            testTemplateRepository.save(testTemplate);
            testTemplate.setTestSequences(generateTestSequences(testTemplate.getId(), testTemplate.getSeqCount(), testTemplate.getNumberOfTargets(), testTemplate.getTargetRadius1(), testTemplate.getTargetRadius2(), testTemplate.getCircleRadius1(), testTemplate.getCircleRadius2()));
            testTemplateRepository.save(testTemplate);
        }
    }

    /**
     * Copies an existing TestTemplate object, whilst preserving the uniqueness of its id as well as the sequences it contains
     * @param id
     */
    public void copy(Long id) {
        TestTemplate clone = testTemplateRepository.findOne(id).clone();
        testTemplateRepository.save(clone);
        //the clone now has its own unique id
        clone.setName(clone.getName()+"_Copy_"+clone.getId());
        clone.setTestSequences(generateTestSequences(clone.getId(), clone.getSeqCount(), clone.getNumberOfTargets(), clone.getTargetRadius1(), clone.getTargetRadius2(), clone.getCircleRadius1(), clone.getCircleRadius2()));
        testTemplateRepository.save(clone);
    }

    /**
     * Service method used to generate TestSequence sequences for a TestTemplate object, linked using the TestTemplate object's unique id.
     * @param templateID
     * @param seqCount
     * @param numberOfTargets
     * @param targetRadius1
     * @param targetRadius2
     * @param circleRadius1
     * @param circleRadius2
     * @return
     */
    private List<TestSequence> generateTestSequences(Long templateID, int seqCount, int numberOfTargets, double targetRadius1, double targetRadius2, double circleRadius1, double circleRadius2) {
        List<TestSequence> testSequences = new ArrayList<TestSequence>();
        TestSequence ts = null;
        double targetRadiusDiff = (targetRadius2 - targetRadius1) / (seqCount - 1);
        double circleRadiusDiff = (circleRadius2 - circleRadius1) / (seqCount - 1);

        deleteLinkedSequences(templateID);

        for (int i = 0; i < seqCount; i++) {
            ts = new TestSequence(templateID, numberOfTargets, (i) * targetRadiusDiff + targetRadius1, (i) * circleRadiusDiff + circleRadius1);
            testSequences.add(ts);
        }
        for (TestSequence seq : testSequences) {
            testSequenceRepository.save(seq);
        }
        testSequences = new ArrayList<TestSequence>();

        for (TestSequence seq : testSequenceRepository.findAll()) {
            if (templateID.equals(seq.getTemplateID()))
                testSequences.add(seq);
        }
        return testSequences;
    }

    /**
     * This will delete all TestSequence sequences linked to a certain TestTemplate object.
     * Generally used when either renewing the sequences of a TestTemplate object, or when the TestTemplate object itself needs to be deleted.
     * @param templateID
     */
    public void deleteLinkedSequences(Long templateID) {
        testTemplateRepository.findOne(templateID).setTestSequences(null);
        for (TestSequence s : testSequenceRepository.findAll()) {
            if (s.getTemplateID().equals(templateID))
                testSequenceRepository.delete(s.getId());
        }
    }

    private TestTemplate findOne(Long id) {
        return testTemplateRepository.findOne(id);
    }

    private long size() {
        return testTemplateRepository.count();
    }
}
