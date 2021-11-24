package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, Long> {

    Optional<AttachmentContent> findByAttachmentId(Long attachment_id);

    @Query(value = "DELETE\n" +
            "FROM attachment_content\n" +
            "WHERE attachment_id = ?1\n" +
            "RETURNING  attachment_id", nativeQuery = true)
    Long deletingByAttachmentId(Long attachmentId);
}
