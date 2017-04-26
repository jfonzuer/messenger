package com.jfonzuer.storage;

import com.jfonzuer.entities.Conversation;
import com.jfonzuer.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;

@Service
public class FileSystemStorageService implements StorageService {

    @Value("${upload.directory}")
    private String rootLocation;

    @Override
    public void recursiveDelete(String path) {
        Path rootPath = Paths.get(rootLocation + path);
        if (Files.exists(rootPath)) {
            // return all files/directories below rootPath including
            try {
                Files.walk(rootPath)
                        // sort the list in reverse order, so the directory itself comes after the including subdirectories and files
                        .sorted(Comparator.reverseOrder())
                        // .map - path the Path to File
                        .map(Path::toFile)
                        //.peek(System.out::println)
                        // forEach - calls an every File object the .delete() method
                        .forEach(File::delete);
            } catch (IOException e) {
                throw new StorageException("Failed to delete file/folder recursively " + path, e);
            }
        }
    }

    @Override
    public void createDirectory(String path) {
        try {
            Files.createDirectory(Paths.get(rootLocation + path));
        } catch (IOException e) {
            throw new StorageException("Failed to create directory " + path, e);
        }
    }

    @Override
    public void createDirectories(String path) {
        try {
            Files.createDirectories(Paths.get(rootLocation + path));
        } catch (IOException e) {
            throw new StorageException("Failed to create directory " + path, e);
        }
    }

    @Override
    public void store(MultipartFile file, String path, String filename) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), Paths.get(rootLocation + path).resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public void createDirectoriesAndStore(MultipartFile file, String folderPath, String filename) {
        createDirectories(folderPath);
        store(file, folderPath, filename);
    }

    @Override
    public void delete(String filename) {
        try {
            Files.delete(Paths.get(rootLocation).resolve(filename));
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageException("Failed to delete file " + filename, e);
        }
    }

    @Override
    public void rename(String filename, String newFilename) {
        try {
            Files.move(Paths.get(rootLocation).resolve(filename), Paths.get(rootLocation).resolve(newFilename));
        } catch (IOException e) {
            throw new StorageException("Failed to rename file " + filename, e);
        }
    }

    /*
    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
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
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
    */
}
