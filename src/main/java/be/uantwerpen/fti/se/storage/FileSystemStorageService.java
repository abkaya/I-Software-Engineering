package be.uantwerpen.fti.se.storage;

import be.uantwerpen.fti.se.model.Device;
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


@Service
public class FileSystemStorageService implements StorageService {

    private Path rootLocation;

    @Override
    public void store(MultipartFile file, Device device) {
        File dir = new File(rootLocation.toString());
        String files[] = dir.list();
        boolean exists = false;
        for(int i = 0; i < (files.length); i++) {
            if (files[i].toString().equals(file.getOriginalFilename().toString())){
                exists=true;
            }
        }
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            if(exists==false) {
                Files.copy(file.getInputStream(), rootLocation.resolve(file.getOriginalFilename()));
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public void storeImage(MultipartFile file, Device device) {
        Path Location = Paths.get(device.getImagesDefaultLocPath());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            String extension = "";
            int i = file.getOriginalFilename().lastIndexOf('.');
            if (i >= 0) {
                extension = file.getOriginalFilename().substring(i+1);
            }
            device.setImageExtension(extension);
            device.setImageFile(device.getImageId(), device.getImageExtension());
            device.setImageFullPath(device.getImageFile());
            Files.copy(file.getInputStream(), Location.resolve(device.getImageFile()));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Stream<Path> loadAll(Device device) {
        this.rootLocation = Paths.get(device.getFilesDirectoryPath());
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
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path path = load(filename);
            Resource resource = new UrlResource(path.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    //Only for images
    @Override
    public void deleteAll(Device device, MultipartFile file) {
        String location = Paths.get(device.getImagesDefaultLocPath()).toString();
        location = location + "\\" + device.getImageFile();
        Path path = Paths.get(location);
        FileSystemUtils.deleteRecursively(path.toFile());
    }

    @Override
    public void deleteFile(Device device, String filename) {
        String path = device.getFilesDirectoryPath() + "\\" + filename;
        File file = new File(path);
        FileSystemUtils.deleteRecursively(file);
    }

    @Override
    public void deleteDevice(Device device) {
        String path = device.getFilesDirectoryPath();
        File file = new File(path);
        FileSystemUtils.deleteRecursively(file);
    }

    @Override
    public void init(Device device) {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}