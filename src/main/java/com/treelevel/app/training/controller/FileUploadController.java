package com.treelevel.app.training.controller;

import com.treelevel.app.training.storage.FolderName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.treelevel.app.training.storage.StorageFileNotFoundException;
import com.treelevel.app.training.storage.StorageService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Check IP
@CrossOrigin
@RequestMapping("/filesmanager")
@RestController
@Controller
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/{folder}")
    public ResponseEntity<List> listUploadedFiles(@PathVariable FolderName folder, Model model) throws IOException {

        return new ResponseEntity<List>(storageService.loadAll(folder).map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()), HttpStatus.OK);

    }

    @GetMapping("/files/{folder}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable FolderName folder, @PathVariable String filename) {

        Resource file = storageService.loadAsResource(folder, filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/{folder}")
    public ResponseEntity<String> handleFileUpload(@PathVariable FolderName folder, @RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(folder, file);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
