package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Attachment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Done
 */
@DataJpaTest
public class AttachmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AttachmentRepository attachmentRepository;

    private Attachment savedAttachment;

    @BeforeEach
    void setUp() {
        Attachment attachment = new Attachment("File", 200L, UUID.randomUUID(), "text/plain");
        savedAttachment = entityManager.merge(attachment);
    }

    @AfterEach
    void tearDown() {
        attachmentRepository.deleteAll();
    }

    @Test
    void findById() {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(savedAttachment.getId());
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            assertEquals("File", attachment.getFileOriginalName());
            assertEquals("text/plain", attachment.getContentType());
            assertEquals(200L, attachment.getSize());
        }
    }
}
