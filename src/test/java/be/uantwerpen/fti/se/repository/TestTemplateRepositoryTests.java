package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.TestTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;
/**
 * Created by Kevin on 12/11/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestTemplateRepositoryTests {
    @Autowired
    private TestTemplateRepository testTemplateRepository;

    @Test
    public void TestRepositoryTemplate(){
        TestTemplate testTemplate = new TestTemplate();
        testTemplate.setName("template1");

        assertNull(testTemplate.getId());
        testTemplateRepository.save(testTemplate);
        assertNotNull(testTemplate.getId());

        TestTemplate fetchTemplate = testTemplateRepository.findOne(testTemplate.getId());
        assertNotNull(fetchTemplate);
        assertEquals(testTemplate.getId(), fetchTemplate.getId());
        assertEquals(testTemplate.getName(), fetchTemplate.getName());

        fetchTemplate.setName("NewName");
        testTemplateRepository.save(fetchTemplate);
        TestTemplate fetchUpdatedTemplate = testTemplateRepository.findOne(fetchTemplate.getId());
        assertEquals(fetchTemplate.getName(), fetchUpdatedTemplate.getName());
        long templateCount = testTemplateRepository.count();
        assertEquals(templateCount,9);

        Iterable<TestTemplate> testTemplates = testTemplateRepository.findAll();
        int count = 0;
        for(TestTemplate t : testTemplates)
        {
            count++;
        }
        assertEquals(count,9);
    }
}
