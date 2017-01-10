package com.jfonzuer.storage;

import com.jfonzuer.entities.Conversation;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {


    void delete(String filename);

    void rename(String filename, String newFilename);
    void store(MultipartFile file, String path, String filename);
    void createDirectoriesAndStore(MultipartFile file, String folderPath, String filename);
    void createDirectory(String path);
    void createDirectories(String path);
    void recursiveDelete(String path);
    /*
    void init();
    Stream<Path> loadAll();
    Path load(String filename);
    Resource loadAsResource(String filename);
    void deleteAll();
    */
}
