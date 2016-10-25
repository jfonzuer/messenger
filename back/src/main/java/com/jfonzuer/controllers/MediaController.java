package com.jfonzuer.controllers;

import com.jfonzuer.entities.User;
import com.jfonzuer.service.UserService;
import com.jfonzuer.storage.StorageService;
import com.jfonzuer.validator.MediaValidator;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping(method = RequestMethod.POST)
    public void upload(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) {
        mediaValidator.validate(file);
        User user = userService.getUserFromToken(request);
        String filename = user.getId() + "_" + user.getUsername() + mediaValidator.getExtension(file.getContentType());
        storageService.store(file, filename);
    }
}
