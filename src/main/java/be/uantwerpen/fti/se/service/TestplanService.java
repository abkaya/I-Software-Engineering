package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.Testplan;
import be.uantwerpen.fti.se.model.User;
import be.uantwerpen.fti.se.repository.TestplanRepository;
import be.uantwerpen.fti.se.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Willem on 24/10/2016.
 */

@Service
public class TestplanService {
    @Autowired
    private TestplanRepository testplanRepository;

    public Iterable<Testplan> findByUserName(User user){
        return testplanRepository.findByUser(user);

    }

    public void save(final Testplan testplan) {
        this.testplanRepository.save(testplan);
    }

    public void delete(Long id) {
        this.testplanRepository.delete(id);
    }

}
