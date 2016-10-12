package be.uantwerpen.fti.se.formatter;

import be.uantwerpen.fti.se.model.Permission;
import be.uantwerpen.fti.se.repository.PermissionRepository;
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
 * Created by Thomas on 02/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PermissionFormatterTests
{
    @Autowired
    private PermissionFormatter permissionFormatter;

    @Autowired
    private PermissionRepository permissionRepository;

    private Permission permission;
    private Locale locale;

    @Before
    public void init()
    {
        permission = new Permission("formatterTest");

        permissionRepository.save(permission);

        locale = new Locale("default");
    }

    @Test
    public void parserTest() throws ParseException
    {
        Permission parserPermission = permissionFormatter.parse(permission.getId().toString(), locale);

        assertTrue(parserPermission.equals(permission));
    }

    @Test
    public void toStringTest()
    {
        String parsedString = permissionFormatter.print(permission, locale);

        assertTrue(parsedString.equals(permission.getId().toString()));
    }
}
