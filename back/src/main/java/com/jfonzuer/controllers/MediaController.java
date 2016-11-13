package com.jfonzuer.controllers;

import com.jfonzuer.dto.CustomErrorType;
import com.jfonzuer.dto.ImageDto;
import com.jfonzuer.dto.OrderNumberDto;
import com.jfonzuer.dto.mapper.ImageMapper;
import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.entities.Image;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.ImageRepository;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.security.JwtUser;
import com.jfonzuer.service.UserService;
import com.jfonzuer.storage.StorageService;
import com.jfonzuer.validator.MediaValidator;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by pgm on 25/10/16.
 */

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "medias")
public class MediaController {

    @Autowired
    private MediaValidator mediaValidator;

    @Autowired
    private StorageService storageService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    private String defaultImage;

    private final static Integer MAX_NUMBER = 3;

    @RequestMapping(method = RequestMethod.POST)
    public ImageDto upload(HttpServletRequest request, @RequestParam(value = "file") MultipartFile file, @RequestParam(value = "order") Integer order) {

        System.out.println("order = " + order);
        
        mediaValidator.validate(file);
        User user = userService.getUserFromToken(request);

        if (order < 1 || order > 3) {
            throw new IllegalArgumentException();
        }

        List<Image> images = new ArrayList<>(user.getImages());
        System.out.println("images = " + images);

        // s'il s'agit de la première photo on delete la photo par defaut dans la db
        if (order == 1) {
            Image image = images.get(order - 1);
            imageRepository.delete(image);
            /*
            // suppression de l'image
            if (!image.getUrl().equals(defaultImage)) {
                storageService.delete(image.getUrl());
            }
            */
        }

        String filename = order + "_" + user.getUsername() + mediaValidator.getExtension(file.getContentType());
        Image image = new Image.ImageBuilder().setUrl(filename).setUser(user).setOrderNumber(order).createImage();
        imageRepository.save(image);
        storageService.store(file, filename);
        return ImageMapper.toDto(image);
    }

    @RequestMapping(value = "/{order}" , method = RequestMethod.DELETE)
    public List<ImageDto> delete(HttpServletRequest request, @PathVariable Integer order) {

        System.out.println("delete order = " + order);
        User user = userService.getUserFromToken(request);

        // on peut uniquement supprimer les photos secondaires
        if (order < 2 || order > 3) {
            throw new IllegalArgumentException();
        }
        List<Image> images = new ArrayList<>(user.getImages());
        System.out.println("images = " + images);
        Image image = images.get(order - 1);
        images.remove(order - 1);
        imageRepository.delete(image);
        storageService.delete(image.getUrl());

        System.out.println("maxNumber = " + MAX_NUMBER);
        // s'il ne s'agit pas de la dernière image
        if (order != MAX_NUMBER) {
            // on skip la photo de profil
            List<Image> secondaryImages = images.stream().skip(1L).collect(Collectors.toList());
            int index = 2;
            for (Image i : secondaryImages) {
                // on définit le nouveau fichier avec le nouveau nom
                String newFilename = index + i.getUrl().substring(1, i.getUrl().length());
                storageService.move(i.getUrl(), newFilename);
                i.setUrl(newFilename);
                i.setOrderNumber(2);
                imageRepository.save(i);
                images.set(index - 1, i);
                order++;
            }
        }
        return images.stream().map(ImageMapper::toDto).collect(Collectors.toList());
    }


    @RequestMapping(method = RequestMethod.PUT)
    public List<ImageDto> setAsProfile(HttpServletRequest request, @RequestBody OrderNumberDto orderDto) {

        Integer order = orderDto.getOrderNumber();

        // on peut uniquement set en profil les photos secondaires
        if (order < 2 || order > 3) {
            throw new IllegalArgumentException();
        }

        User user = userService.getUserFromToken(request);
        List<Image> images = new ArrayList<>(user.getImages());

        Image profile = images.get(0);
        Image otherImage = images.get(order - 1);
        String prefix = "tmp_";

        // deplacement profile vers un fichier temporaire
        storageService.move(profile.getUrl(), prefix + profile.getUrl());

        // deplacement other image en tant que photo de profil
        String newProfileName = 1 + otherImage.getUrl().substring(1, otherImage.getUrl().length());
        storageService.move(otherImage.getUrl(), newProfileName);

        // deplacement de tmp vers son nouveau nom
        String newOtherImageName = order + profile.getUrl().substring(1, profile.getUrl().length());
        storageService.move(prefix + profile.getUrl(), newOtherImageName);

        // on intervertit les deux liens au niveau de la db
        profile.setUrl(newProfileName);
        otherImage.setUrl(newOtherImageName);
        imageRepository.save(profile);
        imageRepository.save(otherImage);

        // on met à jour la liste
        images.set(0, profile);
        images.set(order - 1, otherImage);

        return images.stream().map(ImageMapper::toDto).collect(Collectors.toList());
    }
}
