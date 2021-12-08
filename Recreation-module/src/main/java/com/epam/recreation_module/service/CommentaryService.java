package com.epam.recreation_module.service;

import com.epam.recreation_module.model.DTO.CommentaryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface CommentaryService {

    ResponseEntity<?> addCommentary(CommentaryDTO commentaryDTO);

    ResponseEntity<?> editComment(Long commentId, CommentaryDTO commentaryDTO);

    ResponseEntity<?>editMyComment(Long commentId, CommentaryDTO commentaryDTO);

    ResponseEntity<?> getCommentaries(Long recreationId);

    ResponseEntity<?> getCommentById(Long commentId);

    ResponseEntity<?> deleteComment(Long commentId);

    ResponseEntity<?> deleteMyComment(Long commentId);
}
