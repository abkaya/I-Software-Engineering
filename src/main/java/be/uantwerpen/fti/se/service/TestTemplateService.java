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

    public Iterable<TestTemplate> findAll() {
        for (TestTemplate tt : this.testTemplateRepository.findAll()) {
            tt.setSeqCount();
        }
        return this.testTemplateRepository.findAll();
    }

    public void add(final TestTemplate testTemplate) {
        this.testTemplateRepository.save(testTemplate);
    }

    public void delete(Long id) {
        this.testTemplateRepository.delete(id);
    }

    public TestTemplate findByTestTemplateName(String name) {
        return testTemplateRepository.findByName(name);
    }

    public void saveSomeAttributes(TestTemplate testTemplate) {
        TestTemplate tempTestTemplate = testTemplate.getId() == null ? null : findOne(testTemplate.getId());
        if (tempTestTemplate != null) {
            tempTestTemplate.setDescription(testTemplate.getDescription());
            tempTestTemplate.setTestSequences(testTemplate.getTestSequences());
            tempTestTemplate.setTestSequences(generateTestSequences(testTemplate.getId(), testTemplate.getSeqCount(), testTemplate.getNumberOfTargets(), testTemplate.getTargetRadius1(), testTemplate.getTargetRadius2(), testTemplate.getCircleRadius1(), testTemplate.getCircleRadius2()));
            tempTestTemplate.setEditable(testTemplate.isEditable());
            tempTestTemplate.setName(testTemplate.getName());
            tempTestTemplate.setSeqCount(testTemplate.getSeqCount());
            tempTestTemplate.setNumberOfTargets(testTemplate.getNumberOfTargets());
            tempTestTemplate.setTargetRadius1(testTemplate.getTargetRadius1());
            tempTestTemplate.setTargetRadius2(testTemplate.getTargetRadius2());
            tempTestTemplate.setCircleRadius1(testTemplate.getCircleRadius1());
            tempTestTemplate.setCircleRadius2(testTemplate.getCircleRadius2());
            testTemplateRepository.save(tempTestTemplate);
        } else {
            testTemplateRepository.save(testTemplate);
        }
    }

    public void copy(Long id) {
        TestTemplate clone = testTemplateRepository.findOne(id).clone();
        testTemplateRepository.save(clone);
        //the clone now has its own unique id
        clone.setName(clone.getName()+"_Copy_"+clone.getId());
        clone.setTestSequences(generateTestSequences(clone.getId(), clone.getSeqCount(), clone.getNumberOfTargets(), clone.getTargetRadius1(), clone.getTargetRadius2(), clone.getCircleRadius1(), clone.getCircleRadius2()));
        testTemplateRepository.save(clone);
    }

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
}