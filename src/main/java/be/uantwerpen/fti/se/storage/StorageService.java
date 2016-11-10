package be.uantwerpen.fti.se.storage;

import be.uantwerpen.fti.se.model.Device;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init(Device device);

    void store(MultipartFile file, Device device);

    Stream<Path> loadAll(Device device);

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll(Device device);

}