package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.Device;
import be.uantwerpen.fti.se.repository.DeviceRepository;
import be.uantwerpen.fti.se.storage.StorageFileNotFoundException;
import be.uantwerpen.fti.se.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/devices/{id}/files")
    public String listUploadedFiles(Model model, @PathVariable Long id) throws IOException {
        Device device =deviceRepository.findOne(id);
        model.addAttribute("device",device);
        model.addAttribute("files", storageService
                .loadAll(device)
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));
        model.addAttribute("filenames", storageService.loadAll(device).collect(Collectors.toList()));
        return "load-file";
    }

    @GetMapping("/devices/{id}/files/{filename}/delete")
    public String deleteFile(@PathVariable Long id, @PathVariable String filename) {
        Device dev = deviceRepository.findOne(id);
        storageService.deleteFile(dev, filename);
        return "redirect:/devices/{id}/files";
    }


    @GetMapping("/devices/{id}/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping("/devices/{id}/files")
    public String handleFileUpload(@Valid Device device, @PathVariable Long id, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (!file.isEmpty()) {
            storageService.store(file, device);
        } else {
            redirectAttributes.addFlashAttribute("message", "There was no file selected");
        }
        return "redirect:/devices/{id}/files";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}