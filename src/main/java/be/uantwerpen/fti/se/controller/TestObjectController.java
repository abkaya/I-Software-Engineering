package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.repository.TestObjectRepository;
import be.uantwerpen.fti.se.service.TestObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Quentin Van Ravels on 26-Nov-16.
 */
public class TestObjectController {

    @Autowired
    private TestObjectRepository testObjectRepository;
    @Autowired
    private TestObjectService testObjectService;

    @RequestMapping(value = "/testobjects", method = RequestMethod.GET)
    public String showTestObject(final ModelMap model){

        return "user-testobject";
    }

}
