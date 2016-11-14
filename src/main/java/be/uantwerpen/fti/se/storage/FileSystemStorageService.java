package be.uantwerpen.fti.se.storage;

import be.uantwerpen.fti.se.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

@Service
public class FileSystemStorageService implements StorageService {

    private Path rootLocation;

    /*
    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }
    */

    @Override
    public void store(MultipartFile file, Device device) {

        //Path rootLocation = Paths.get(device.getPath());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Stream<Path> loadAll(Device device) {

        System.out.println(device.getPath());
        this.rootLocation = Paths.get(device.getPath());
        try {
            return Files.walk(rootLocation, 1)
                    .filter(path -> !path.equals(rootLocation))
                    .map(path -> rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        //Path rootLocation = Paths.get(device.getPath());
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll(Device device) {
        //Path rootLocation = Paths.get(device.getPath());
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void deleteFile(Device device, String filename) {
        String foldername = "files_"+device.getDeviceName();
        String path = "C:\\Users\\Admin\\IdeaProjects\\repos\\src\\main\\resources\\static\\devices_files\\"+foldername+"\\"+filename;
        File file = new File(path);
        FileSystemUtils.deleteRecursively(file);
    }

    @Override
    public void init(Device device) {
        //Path rootLocation = Paths.get(device.getPath());
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}