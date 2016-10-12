package be.uantwerpen.fti.se.formatter;

import be.uantwerpen.fti.se.model.Role;
import be.uantwerpen.fti.se.repository.RoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.util.Locale;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Thomas on 09/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RoleFormatterTests
{
    @Autowired
    private RoleFormatter roleFormatter;

    @Autowired
    private RoleRepository roleRepository;

    private Role role;
    private Locale locale;

    @Before
    public void init()
    {
        role = new Role("formatterTest");

        roleRepository.save(role);

        locale = new Locale("default");
    }

    @Test
    public void parserTest() throws ParseException
    {
        Role parserRole = roleFormatter.parse(role.getId().toString(), locale);

        assertTrue(parserRole.equals(role));
    }

    @Test
    public void toStringTest()
    {
        String parsedString = roleFormatter.print(role, locale);

        assertTrue(parsedString.equals(role.getId().toString()));
    }
}
