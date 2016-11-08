package com.jfonzuer.dto.mapper;

import com.jfonzuer.dto.ImageDto;
import com.jfonzuer.entities.Image;

/**
 * Created by pgm on 07/11/16.
 */
public class ImageMapper {
    public static ImageDto toDto(Image image) {
        return image != null ? new ImageDto.ImageDtoBuilder()
                .setId(image.getId())
                .setOrder(image.getOrderNumber())
                .setUrl(image.getUrl())
                .createImageDto() : null;
    }
}
