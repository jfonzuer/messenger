package com.jfonzuer.runner;

import com.jfonzuer.dto.ImageDocument;
import org.ektorp.AttachmentInputStream;
import org.ektorp.CouchDbConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * Created by pgm on 15/04/17.
 */

@Component
public class CouchdbCLR implements CommandLineRunner {

    @Value("${image.default.name}")
    private String defaultImage;

    @Autowired
    private CouchDbConnector couchDbImageConnector;

    @Override
    public void run(String... strings) throws Exception {
        couchDbImageConnector.createDatabaseIfNotExists();

        // si l'image par défaut n'existe pas déjà on la crée
        if (couchDbImageConnector.find(ImageDocument.class, "profile.png") == null) {
            InputStream resource = new ClassPathResource("profile.png").getInputStream();
            String contentType = "image/png";
            AttachmentInputStream attachment = new AttachmentInputStream("image", resource, "image/png");
            couchDbImageConnector.createAttachment(defaultImage, attachment);
        }
    }
}
