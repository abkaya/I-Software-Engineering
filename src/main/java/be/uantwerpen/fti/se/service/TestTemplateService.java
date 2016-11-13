package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.TestTemplate;
import be.uantwerpen.fti.se.repository.TestTemplateRepository;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by abdil on 22/10/2016.
 */
@Service
public class TestTemplateService {
    @Autowired
    private TestTemplateRepository testTemplateRepository;

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
            //html page still needs to support the editing of multiple attributes
            //tempTestTemplate.setTemplateDescription(testTemplate.getTemplateDescription());
            tempTestTemplate.setDescription(testTemplate.getDescription());
            tempTestTemplate.generateTestSequences(testTemplate.getSeqCount(),testTemplate.getNumberOfTargets(),testTemplate.getTargetRadius1(),testTemplate.getTargetRadius2(),testTemplate.getCircleRadius1(),testTemplate.getCircleRadius2());
            tempTestTemplate.setTestSequences(testTemplate.getTestSequences());
            tempTestTemplate.setEditable(testTemplate.isEditable());
            tempTestTemplate.setName(testTemplate.getName());
            tempTestTemplate.setSeqCount();
            testTemplateRepository.save(tempTestTemplate);
        } else {
            testTemplateRepository.save(testTemplate);
        }
    }

    private TestTemplate findOne(Long id) {
        return testTemplateRepository.findOne(id);
    }
}