package com.treelevel.app.training.storage;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(FolderName folder, MultipartFile file);

    Stream<Path> loadAll(FolderName folder);

    Path load(String path);

    Resource loadAsResource(FolderName folder, String filename);

    void deleteAll();

}
