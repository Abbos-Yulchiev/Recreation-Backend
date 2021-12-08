package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Attachment;
import com.epam.recreation_module.model.Photo;
import com.epam.recreation_module.model.Recreation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Done
 */
@DataJpaTest
class PhotoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PhotoRepository photoRepository;

    @Test
    void findById() {
        Photo photo = new Photo("Photo", new Recreation(), new Attachment());
        Photo savedPhoto = entityManager.merge(photo);
        Optional<Photo> optionalPhoto = photoRepository.findById(savedPhoto.getId());
        if (optionalPhoto.isPresent()) {
            Photo photo2 = optionalPhoto.get();
            assertEquals("Photo", photo2.getName());
            assertEquals(new Recreation(), photo2.getRecreation());
            assertEquals(new Attachment(), photo2.getAttachment());
        }
    }
}