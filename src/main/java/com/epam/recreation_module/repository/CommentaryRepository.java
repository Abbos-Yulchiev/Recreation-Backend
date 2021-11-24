package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Commentary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaryRepository extends JpaRepository<Commentary, Long> {

    @Query(value = "SELECT c.comment_text, u.first_name, u.last_name, c.created_at\n" +
            "FROM commentary c\n" +
            "        FULL  JOIN users u on c.created_by_id = u.id\n" +
            "WHERE c.deleted = false\n" +
            "  AND recreation_id = ?1", nativeQuery = true)
    List<Object> allCommentaries(Long recreationId);

    Commentary findByIdAndDeletedIsFalse(Long id);
}
