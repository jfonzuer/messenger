package com.jfonzuer.controllers;

import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.security.JwtUser;
import com.jfonzuer.service.UserService;
import com.jfonzuer.storage.StorageService;
import com.jfonzuer.validator.MediaValidator;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

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

    @Value("${default.image}")
    private String defaultImage;

    @RequestMapping(method = RequestMethod.POST)
    public JwtUser upload(HttpServletRequest request, @RequestParam(value = "file") MultipartFile file) {

        mediaValidator.validate(file);
        User user = userService.getUserFromToken(request);

        // if user already have profile picture
        System.out.println("defaultImage = " + defaultImage);
        System.out.println("user.getProfilePicture().equals(defaultImage) = " + user.getProfilePicture().equals(defaultImage));

        if (!user.getProfilePicture().equals(defaultImage)) {
            storageService.delete(user.getProfilePicture());
        }

        String filename = user.getId() + "_" + user.getUsername() + mediaValidator.getExtension(file.getContentType());
        storageService.store(file, filename);
        user.setProfilePicture(filename);

        return UserMapper.toDto(userRepository.save(user));
    }
}
