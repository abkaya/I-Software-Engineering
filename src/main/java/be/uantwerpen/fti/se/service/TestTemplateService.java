package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.TestTemplate;
import be.uantwerpen.fti.se.repository.TestTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by abdil on 22/10/2016.
 */
@Service
public class TestTemplateService {
    @Autowired
    private TestTemplateRepository testTemplateRepository;
    public Iterable<TestTemplate> findAll(){ return this.testTemplateRepository.findAll();}

    public void add(final TestTemplate testTemplate) {this.testTemplateRepository.save(testTemplate);}
    public void delete(Long id){this.testTemplateRepository.delete(id);}

    public TestTemplate findByTestTemplateName(String name) {return testTemplateRepository.findByName(name);}
}