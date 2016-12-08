package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.Result;
import be.uantwerpen.fti.se.model.TestSequence;
import be.uantwerpen.fti.se.repository.ResultRepository;
import be.uantwerpen.fti.se.repository.TestObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Quentin Van Ravels on 08-Dec-16.
 */
@Controller
public class FittsController {

    @Autowired
    private TestObjectRepository testObjectRepository;
    @Autowired
    private ResultRepository resultRepository;


}

