package com.jfonzuer.controllers;

import com.jfonzuer.dto.ImageDto;
import com.jfonzuer.dto.OrderNumberDto;
import com.jfonzuer.entities.User;
import com.jfonzuer.service.ImageService;
import com.jfonzuer.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by pgm on 25/10/16.
 */

@RestController
@RequestMapping(value = "medias")
public class MediaController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;


    /**
     * endpoint permettant l'ajout d'image
     * @param request
     * @param file
     * @param order
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ImageDto upload(HttpServletRequest request, @RequestParam(value = "file") MultipartFile file, @RequestParam(value = "order") Integer order) {
        User user = userService.getUserFromToken(request);
        return imageService.add(order, user, file);
    }

    /**
     * endpoint permettant de supprimer une image
     * @param request
     * @param order
     * @return
     */
    @RequestMapping(value = "/{order}" , method = RequestMethod.DELETE)
    public List<ImageDto> delete(HttpServletRequest request, @PathVariable Integer order) {
        User user = userService.getUserFromToken(request);
        return imageService.delete(order, user);
    }

    /**
     * endpoint permettant de set une photo en tant que photo de profil
     * @param request
     * @param orderDto
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<ImageDto> setAsProfile(HttpServletRequest request, @RequestBody OrderNumberDto orderDto) {
        User user = userService.getUserFromToken(request);
        return imageService.setAsProfile(orderDto, user);
    }
}
