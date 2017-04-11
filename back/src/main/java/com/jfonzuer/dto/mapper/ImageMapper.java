package com.jfonzuer.dto.mapper;

import com.jfonzuer.dto.ImageDto;
import com.jfonzuer.entities.Image;

/**
 * Created by pgm on 07/11/16.
 */
public class ImageMapper {
    public static ImageDto toDto(Image image) {
        return image != null ? ImageDto.Builder.anImageDto()
                .withId(image.getId())
                .withOrderNumber(image.getOrderNumber())
                .withUrl(image.getUrl())
                .build() : null;
    }

    public static Image fromDto(ImageDto dto) {
        return dto != null ? Image.Builder.anImage()
                .withId(dto.getId())
                .withOrderNumber(dto.getOrderNumber())
                .withUrl(dto.getUrl())
                .build() : null;
    }
}
