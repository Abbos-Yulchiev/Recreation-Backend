package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.ForbiddenException;
import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.model.Commentary;
import com.epam.recreation_module.model.DTO.CommentaryDTO;
import com.epam.recreation_module.model.Recreation;
import com.epam.recreation_module.model.User;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.CommentaryRepository;
import com.epam.recreation_module.repository.RecreationRepository;
import com.epam.recreation_module.service.CommentaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentaryServiceImpl implements CommentaryService {

    final CommentaryRepository commentaryRepository;
    final RecreationRepository recreationRepository;

    public CommentaryServiceImpl(CommentaryRepository commentaryRepository, RecreationRepository recreationRepository) {
        this.commentaryRepository = commentaryRepository;
        this.recreationRepository = recreationRepository;
    }

    @Override
    public ApiResponse addCommentary(CommentaryDTO commentaryDTO) throws NotFoundException {

        User user;
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Recreation recreation = recreationRepository.findById(commentaryDTO.getRecreationId())
                    .orElseThrow(() -> new NotFoundException(commentaryDTO.getRecreationId() + "  id not found!"));
            Commentary commentary = new Commentary();
            commentary.setCommentText(commentaryDTO.getCommentText());
            commentary.setRecreation(recreation);
            commentary.setCreatedBy(user);
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            commentary.setCreatedAt(timestamp);
            Commentary save = commentaryRepository.save(commentary);
            return new ApiResponse("Comment added. Comment Id: " + save.getId(), true);
        } catch (NullPointerException e) {
            return new ApiResponse("User not found!", false);
        }
    }

    @Override
    public ApiResponse editComment(Long commentId, CommentaryDTO commentaryDTO) {

        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Commentary commentary = commentaryRepository.findById(commentId)
                    .orElseThrow(() -> new NotFoundException("Comment not found!"));
            Recreation recreation = recreationRepository.findById(commentaryDTO.getRecreationId())
                    .orElseThrow(() -> new ForbiddenException("Invalid Recreation id given!"));
            commentary.setCommentText(commentaryDTO.getCommentText());
            commentary.setRecreation(recreation);
            commentary.setUpdatedBy(user);
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            commentary.setUpdatedAt(timestamp);
            Commentary save = commentaryRepository.save(commentary);
            return new ApiResponse("Comment edited. Id: " + save.getId(), true);
        } catch (NullPointerException e) {
            return new ApiResponse("User not found", false);
        }
    }

    @Override
    public ApiResponse editMyComment(Long commentId, CommentaryDTO commentaryDTO) {

        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Commentary commentary = commentaryRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment not found!"));
            Recreation recreation = recreationRepository.findById(commentaryDTO.getRecreationId())
                    .orElseThrow(() -> new ForbiddenException("Invalid Recreation id given!"));
            Long id = commentary.getCreatedBy().getId();

            if (user.getId().equals(id)) {
                return new ApiResponse("This is not your comment!", false);
            }
            commentary.setCommentText(commentaryDTO.getCommentText());
            commentary.setRecreation(recreation);
            commentary.setUpdatedBy(user);
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            commentary.setUpdatedAt(timestamp);
            commentaryRepository.save(commentary);
            return new ApiResponse("Comment edited!", true);
        } catch (NullPointerException e) {
            return new ApiResponse("User not found", false);
        }
    }

    @Override
    public ResponseEntity<?> getCommentaries(Long recreationId) {

        recreationRepository.findById(recreationId)
                .orElseThrow(() -> new NotFoundException("Recreation not found!"));
        List<Object> allCommentaries = commentaryRepository.allCommentaries(recreationId);
        return ResponseEntity.status(HttpStatus.OK).body(allCommentaries);
    }

    @Override
    public ApiResponse getCommentById(Long commentId) {
        Commentary commentary = commentaryRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found!"));
        return new ApiResponse("Ok!", true, commentary);
    }

    @Override
    public ApiResponse deleteComment(Long commentId) {

        try {
            Commentary deletedComment = commentaryRepository.findByIdAndDeletedIsFalse(commentId);
            if (deletedComment.isDeleted())
                return new ApiResponse("Comment Already deleted!", false);
            deletedComment.setDeleted(true);
            Commentary save = commentaryRepository.save(deletedComment);
            return new ApiResponse("Comment deleted! Id: " + save.getId(), true);
        }catch (NotFoundException e){
            return new ApiResponse("Error occurred", false);
        }
    }

    @Override
    public ApiResponse deleteMyComment(Long commentId) {

        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Commentary deletedComment = commentaryRepository.findByIdAndDeletedIsFalse(commentId);
            if (deletedComment.getCreatedBy().getId().equals(user.getId()))
                return new ApiResponse("This is not your comment you can not delete!", false);
            if (deletedComment.isDeleted())
                return new ApiResponse("Comment Already deleted!", false);
            deletedComment.setDeleted(true);
            Commentary save = commentaryRepository.save(deletedComment);
            return new ApiResponse("Comment deleted! Id: " + save.getId(), true);
        } catch (NullPointerException e) {
            return new ApiResponse("Invalid User!", false);
        }
    }
}
