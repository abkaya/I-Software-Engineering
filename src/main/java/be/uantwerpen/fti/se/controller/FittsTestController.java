package be.uantwerpen.fti.se.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Jan on 30/11/2016.
 */
@Controller
public class FittsTestController {

    @RequestMapping(value="/fittstest", method = RequestMethod.GET)
    public String showFittsTest(final ModelMap model) {
        return "fitts-test";
    }
}
