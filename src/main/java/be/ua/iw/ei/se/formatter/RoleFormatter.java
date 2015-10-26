package be.ua.iw.ei.se.formatter;

import be.ua.iw.ei.se.model.Role;
import be.ua.iw.ei.se.model.User;
import be.ua.iw.ei.se.repository.RoleRepository;
import be.ua.iw.ei.se.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Edwin on 19/10/2015.
 */
@Component
public class RoleFormatter implements Formatter<Role> {

    @Autowired
    private RoleRepository roleRepository;

    public Role parse(final String text, final Locale locale) throws ParseException {
        if (text != null && !text.isEmpty())
            return roleRepository.findOne(new Long(text));
        else return null;
    }

    public String print(final Role object, final Locale locale) {
        return (object != null ? object.getId().toString() : "");
    }
}

