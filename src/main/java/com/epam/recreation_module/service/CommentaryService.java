package com.epam.recreation_module.service;

import com.epam.recreation_module.model.Commentary;
import com.epam.recreation_module.model.DTO.CommentaryDTO;
import com.epam.recreation_module.payload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommentaryService {

    ApiResponse addCommentary(CommentaryDTO commentaryDTO);

    ApiResponse editComment(Long commentId, CommentaryDTO commentaryDTO);

    ApiResponse editMyComment(Long commentId, CommentaryDTO commentaryDTO);

    ResponseEntity<?> getCommentaries(Long recreationId);

    ApiResponse getCommentById(Long commentId);

    ApiResponse deleteComment(Long commentId);

    ApiResponse deleteMyComment(Long commentId);
}
