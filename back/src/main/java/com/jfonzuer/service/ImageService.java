package com.jfonzuer.service;

import com.jfonzuer.dto.ImageDto;
import com.jfonzuer.dto.OrderNumberDto;
import com.jfonzuer.dto.mapper.ImageMapper;
import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.Image;
import com.jfonzuer.entities.Message;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.ImageRepository;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.storage.StorageService;
import com.jfonzuer.validator.MediaValidator;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by pgm on 12/03/17.
 */
@Service
public class ImageService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    private final static Integer MAX_NUMBER = 3;

    @Value("${image.default.name}")
    private String defaultImage;

    private final ImageRepository imageRepository;
    private final MediaValidator mediaValidator;

    @Autowired
    private CouchDbImageService couchDbImageService;

    @Autowired
    public ImageService(ImageRepository imageRepository, MediaValidator mediaValidator) {
        this.imageRepository = imageRepository;
        this.mediaValidator = mediaValidator;
    }

    public ImageDto add(Integer order, User user, MultipartFile file) {

        mediaValidator.validate(file);
        validateOrder(order);

        List<Image> images = new ArrayList<>(user.getImages());

        // s'il s'agit de la première photo on delete la photo par defaut dans la db
        if (order == 1) {
            Image image = images.get(order - 1);
            imageRepository.delete(image);
        }
        String filename = order + "_" + user.getUsername() + mediaValidator.getExtension(file.getContentType());
        String uuid = UUID.randomUUID().toString();
        Image image = Image.Builder.anImage().withUrl(uuid).withUser(user).withOrderNumber(order).build();
        imageRepository.save(image);
        couchDbImageService.store(file, uuid);
        return ImageMapper.toDto(image);
    }


    public List<ImageDto> delete(Integer order, User user) {
        LOGGER.debug("delete order = {}",order);

        List<Image> images = new ArrayList<>(user.getImages());
        LOGGER.debug("images = {}", images);
        validateOrder(order);

        // suppression image profil en n'ayant pas d'autres photos
        if (order == 1 && images.size() == 1) {
            removeImage(images, order);
            Image image  = imageRepository.save(Image.Builder.anImage().withOrderNumber(1).withUrl(defaultImage).withUser(user).build());
            images = Arrays.asList(image);
        }
        // sinon on supprime soit la une (et on a d'autres photos) soit deux et trois
        else {
            removeImage(images, order);
            // s'il ne s'agit pas de la dernière image
            if (order != MAX_NUMBER) {
                reorderImages(images);
            }

        }
        return images.stream().map(ImageMapper::toDto).collect(Collectors.toList());
    }


    public List<ImageDto> setAsProfile(OrderNumberDto orderDto, User user) {
        Integer order = orderDto.getOrderNumber();

        // on peut uniquement set en profil les photos secondaires
        if (order < 2 || order > 3) {
            throw new IllegalArgumentException();
        }

        List<Image> images = new ArrayList<>(user.getImages());
        Image profile = images.get(0);
        Image otherImage = images.get(order - 1);
        // on intervertit les deux liens au niveau de la db
        String newProfileUrl = otherImage.getUrl();
        otherImage.setUrl(profile.getUrl());
        profile.setUrl(newProfileUrl);
        imageRepository.save(profile);
        imageRepository.save(otherImage);
        return images.stream().map(ImageMapper::toDto).collect(Collectors.toList());
    }

    public void saveImageInConversation(MultipartFile file, String uuid) {
        mediaValidator.validate(file);
        couchDbImageService.store(file, uuid);
    }

    /**
     * Permet de réordonner les images lorsqu'on a supprimé une image intermédiaire.
     * @param images
     */
    private void reorderImages(List<Image> images) {
        // on skip la photo de profil
        List<Image> secondaryImages = images.stream().skip(1L).collect(Collectors.toList());
        int index = 2;
        for (Image i : secondaryImages) {
            // on définit le nouveau fichier avec le nouveau nom
            i.setOrderNumber(index);
            imageRepository.save(i);
            images.set(index - 1, i);
            index++;
        }
    }

    private void removeImage(List<Image> images, Integer order) {
        Image image = images.get(order - 1);
        images.remove(order - 1);
        imageRepository.delete(image);
        couchDbImageService.delete(image.getUrl());
    }

    private void validateOrder(Integer order) {
        if (order < 1 || order > 3) {
            throw new IllegalArgumentException();
        }
    }
}
