package be.uantwerpen.fti.se.formatter;

import be.uantwerpen.fti.se.model.File;
import be.uantwerpen.fti.se.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by Admin on 26-10-2016.
 */
@Component
public class FileFormatter implements Formatter<File> {

    @Autowired
    private FileRepository fileRepository;

    public File parse(final String text, final Locale locale) throws ParseException {
        if (text != null && !text.isEmpty())
            return fileRepository.findOne(new Long(text));
        else return null;
    }

    public String print(final File object, final Locale locale) {
        return (object != null ? object.getId().toString() : "");
    }
}


