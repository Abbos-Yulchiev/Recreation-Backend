package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Attachment;
import com.epam.recreation_module.model.AttachmentContent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Done
 */
@DataJpaTest
public class AttachmentContentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AttachmentContentRepository attachmentContentRepository;

    private Attachment savedAttachment;
    AttachmentContent attachmentContent;

    @BeforeEach
    void setUp() {
        Attachment attachment = new Attachment("File", 200L, UUID.randomUUID(), "text/plain");
        savedAttachment = entityManager.merge(attachment);
        attachmentContent = new AttachmentContent("12345".getBytes(StandardCharsets.UTF_8), savedAttachment);
        entityManager.merge(attachmentContent);
    }

    @AfterEach
    void tearDown() {
        attachmentContentRepository.deleteAll();
    }

    @Test
    void findByAttachment_Id() {
        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findById(savedAttachment.getId());
        if (optionalAttachmentContent.isPresent()) {
            AttachmentContent attachmentContent = optionalAttachmentContent.get();
            assertEquals("File", attachmentContent.getAttachment().getFileOriginalName());
            assertEquals("text/plain", attachmentContent.getAttachment().getContentType());
            assertEquals(200L, attachmentContent.getAttachment().getSize());
            assertNotNull(attachmentContent.getBytes());
        }
    }
}