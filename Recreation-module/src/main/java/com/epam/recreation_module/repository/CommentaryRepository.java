package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Commentary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaryRepository extends JpaRepository<Commentary, Long> {

    @Query(value = "SELECT *\n" +
            "FROM commentary\n" +
            "WHERE deleted = false AND recreation_id = ?1", nativeQuery = true)
    List<Commentary> allCommentaries(Long recreationId);

    Commentary findByIdAndDeletedIsFalse(Long id);
}
