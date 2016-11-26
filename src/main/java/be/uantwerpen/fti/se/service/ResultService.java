package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.Result;
import be.uantwerpen.fti.se.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Quentin Van Ravels on 26-Nov-16.
 */
@Service
public class ResultService {
    @Autowired
    private ResultRepository resultRepository;

        public Iterable<Result> findAll() {return this.resultRepository.findAll();}
    public void add(final Result result){ this.resultRepository.save(result);}
    public void delete(Long id) {
        this.resultRepository.delete(id);
    }

    public void saveSomeAttributes(Result result) {
        Result tempResult = result.getId() == null ? null : resultRepository.findOne(result.getId());
        if (tempResult != null) {
            tempResult.setDifficulty(result.getDifficulty());
            tempResult.setMovementTime(result.getMovementTime());
            tempResult.setThroughtput(result.getThroughtput());
            tempResult.setErrorRate(result.getErrorRate());
            resultRepository.save(tempResult);
        } else {
            resultRepository.save(result);
        }
    }
}
