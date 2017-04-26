package com.jfonzuer.validator;

import com.jfonzuer.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

/**
 * Created by pgm on 25/10/16.
 */
@Component
public class MediaValidator {

    private final static Logger LOGGER = LoggerFactory.getLogger(MediaValidator.class);

    private Long maxSize = 2048*1000L;
    //public Long maxSize = 100*1000L;

    private final HashMap<String, String> mimeTypes = new HashMap<>();

    public MediaValidator() {
        mimeTypes.put("image/jpeg", ".jpeg");
        mimeTypes.put("image/jpg", ".jpg");
        mimeTypes.put("image/png", ".png");
    }

    public boolean isTypeSupported(String mimeType) {
        return mimeTypes.containsKey(mimeType);
    }

    public String getExtension(String mimeType) {
        return mimeTypes.get(mimeType);
    }

    public void validate(MultipartFile file) {
        if (!isTypeSupported(file.getContentType())) {
            throw new IllegalArgumentException("Type not supported");
        }
        LOGGER.debug("file.getSize() = {}", file.getSize());
        LOGGER.debug("maxSize() = {}", file.getSize());
        if (file.getSize() > maxSize) {
            throw new IllegalArgumentException("File is too big");
        }
    }
}
