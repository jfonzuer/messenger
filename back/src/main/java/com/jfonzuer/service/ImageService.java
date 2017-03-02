package com.jfonzuer.service;

import com.jfonzuer.dto.ImageDto;
import com.jfonzuer.dto.OrderNumberDto;
import com.jfonzuer.dto.mapper.ImageMapper;
import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.Image;
import com.jfonzuer.entities.Message;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.ImageRepository;
import com.jfonzuer.storage.StorageService;
import com.jfonzuer.validator.MediaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pgm on 12/03/17.
 */
@Service
public class ImageService {

    private final static Integer MAX_NUMBER = 3;

    @Value("${upload.images.directory}")
    private String imagesLocation;

    @Value("${image.default.name}")
    private String defaultImage;

    @Value("${upload.conversation.directory}")
    private String conversationLocation;

    private final ImageRepository imageRepository;
    private final StorageService storageService;
    private final MediaValidator mediaValidator;

    @Autowired
    public ImageService(ImageRepository imageRepository, StorageService storageService, MediaValidator mediaValidator) {
        this.imageRepository = imageRepository;
        this.storageService = storageService;
        this.mediaValidator = mediaValidator;
    }

    public ImageDto add(Integer order, User user, MultipartFile file) {

        mediaValidator.validate(file);
        validateOrder(order);

        List<Image> images = new ArrayList<>(user.getImages());
        System.out.println("images = " + images);

        // s'il s'agit de la première photo on delete la photo par defaut dans la db
        if (order == 1) {
            Image image = images.get(order - 1);
            imageRepository.delete(image);
            // TODO test if theses lines are need or not
            /*
            // suppression de l'image
            if (!image.getUrl().equals(defaultImage)) {
                storageService.delete(image.getUrl());
            }
            */
        }
        String filename = order + "_" + user.getUsername() + mediaValidator.getExtension(file.getContentType());
        Image image = Image.Builder.anImage().withUrl(filename).withUser(user).withOrderNumber(order).build();
        imageRepository.save(image);
        storageService.store(file, imagesLocation, filename);
        return ImageMapper.toDto(image);
    }


    public List<ImageDto> delete(Integer order, User user) {
        System.out.println("delete order = " + order);
        // TODO pouvoir supprimer la photo numéro 1 => remettre image par défaut
        // on peut uniquement supprimer les photos secondaires

        List<Image> images = new ArrayList<>(user.getImages());
        System.out.println("images = " + images);
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
        String prefix = "tmp_";

        // deplacement profile vers un fichier temporaire
        storageService.rename(profile.getUrl(), prefix + profile.getUrl());

        // deplacement other image en tant que photo de profil
        String newProfileName = 1 + otherImage.getUrl().substring(1, otherImage.getUrl().length());
        storageService.rename(otherImage.getUrl(), newProfileName);

        // deplacement de tmp vers son nouveau nom
        String newOtherImageName = order + profile.getUrl().substring(1, profile.getUrl().length());
        storageService.rename(prefix + profile.getUrl(), newOtherImageName);

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

    public String saveImageInConversation(Conversation conversation, Message message, MultipartFile file) {
        String filename = conversation.getId() + "_" + message.getId() + mediaValidator.getExtension(file.getContentType());
        String location = conversationLocation + conversation.getId() + "/";
        String url = location + filename;
        storageService.createDirectoriesAndStore(file, location, filename);
        return url;
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
            String newFilename = index + i.getUrl().substring(1, i.getUrl().length());
            storageService.rename(i.getUrl(), newFilename);
            i.setUrl(newFilename);
            i.setOrderNumber(2);
            imageRepository.save(i);
            images.set(index - 1, i);
            index++;
        }
    }

    private void removeImage(List<Image> images, Integer order) {
        Image image = images.get(order - 1);
        images.remove(order - 1);
        imageRepository.delete(image);
        storageService.delete(image.getUrl());
    }

    private void validateOrder(Integer order) {
        if (order < 1 || order > 3) {
            throw new IllegalArgumentException();
        }
    }
}
