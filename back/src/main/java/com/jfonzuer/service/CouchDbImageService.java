package com.jfonzuer.service;

import com.jfonzuer.dto.ImageDocument;
import com.jfonzuer.exception.ResourceNotFoundException;
import com.jfonzuer.exception.StorageException;
import org.ektorp.AttachmentInputStream;
import org.ektorp.CouchDbConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pgm on 19/03/17.
 */
@Service
public class CouchDbImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouchDbImageService.class);

    @Autowired
    private CouchDbConnector couchDbImageConnector;

    public void store(MultipartFile file, String uuid) {

        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
            AttachmentInputStream attachment = new AttachmentInputStream("image", file.getInputStream(), file.getContentType());
            couchDbImageConnector.createAttachment(uuid.toString(), attachment);
        } catch (IOException e) {
            LOGGER.error(e.getStackTrace().toString());
            throw new StorageException("Echec de l'enregistrement du fichier " + file.getOriginalFilename());
        }
    }

    public void delete(String uuid) {
        ImageDocument imageDocument = couchDbImageConnector.find(ImageDocument.class, uuid);
        if (imageDocument != null) {
            couchDbImageConnector.delete(imageDocument);

        } else {
            throw new ResourceNotFoundException("Le fichier n'existe pas");
        }
    }
}
