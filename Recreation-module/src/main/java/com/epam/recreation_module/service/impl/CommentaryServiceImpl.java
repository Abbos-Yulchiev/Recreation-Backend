package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.model.Commentary;
import com.epam.recreation_module.model.DTO.CommentaryDTO;
import com.epam.recreation_module.model.Recreation;
import com.epam.recreation_module.model.User;
import com.epam.recreation_module.repository.CommentaryRepository;
import com.epam.recreation_module.repository.RecreationRepository;
import com.epam.recreation_module.service.CommentaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    public ResponseEntity<?> getCommentaries(Long recreationId) {

        recreationRepository.findById(recreationId)
                .orElseThrow(() -> new NotFoundException("Recreation not found!"));
        List<Commentary> allCommentaries = commentaryRepository.allCommentaries(recreationId);
        return ResponseEntity.status(HttpStatus.OK).body(allCommentaries);
    }

    @Override
    public ResponseEntity<?> getCommentById(Long commentId) {
        Commentary commentary = commentaryRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment not found!"));
        return ResponseEntity.status(HttpStatus.OK).body(commentary);
    }

    @Override
    public ResponseEntity<?> addCommentary(CommentaryDTO commentaryDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Recreation recreation = recreationRepository.findById(commentaryDTO.getRecreationId())
                .orElseThrow(() -> new NotFoundException("Recreation not found with id: " + commentaryDTO.getRecreationId()));
        Commentary commentary = new Commentary();
        commentary.setCommentText(commentaryDTO.getCommentText());
        commentary.setWriter(user.getFirstName() + " " + user.getLastName());
        commentary.setWriterId(Long.parseLong(user.getCitizenId()));
        commentary.setRecreation(recreation);
        commentary.setWrittenTime(LocalDateTime.now());
        Commentary save = commentaryRepository.save(commentary);
        return ResponseEntity.status(HttpStatus.OK).body("Comment added. Comment Id: " + save.getId());
    }

    @Override
    public ResponseEntity<?> editComment(Long commentId, CommentaryDTO commentaryDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Commentary commentary = commentaryRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found with id: " + commentId));
        Recreation recreation = recreationRepository.findById(commentaryDTO.getRecreationId())
                .orElseThrow(() -> new NotFoundException("Recreation not found with id: " + commentaryDTO.getRecreationId()));
        commentary.setWriter(user.getFirstName() + " " + user.getLastName());
        commentary.setWriterId(Long.parseLong(user.getCitizenId()));
        commentary.setCommentText(commentaryDTO.getCommentText());
        commentary.setRecreation(recreation);
        commentary.setWrittenTime(LocalDateTime.now());
        Commentary save = commentaryRepository.save(commentary);
        return ResponseEntity.status(HttpStatus.OK).body("Comment edited. Id: " + save.getId());
    }

    @Override
    public ResponseEntity<?> editMyComment(Long commentId, CommentaryDTO commentaryDTO) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Commentary commentary = commentaryRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment not found!"));
            Recreation recreation = recreationRepository.findById(commentaryDTO.getRecreationId())
                    .orElseThrow(() -> new NotFoundException("Recreation not found with id: " + commentaryDTO.getRecreationId()));
            Long id = commentary.getId();

            if (user.getId().equals(id)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("This is not your comment!");
            }
            commentary.setCommentText(commentaryDTO.getCommentText());
            commentary.setRecreation(recreation);
            commentary.setWriter(user.getFirstName() + " " + user.getLastName());
            commentary.setWriterId(Long.parseLong(user.getCitizenId()));
            commentary.setWrittenTime(LocalDateTime.now());
            commentaryRepository.save(commentary);
            return ResponseEntity.status(HttpStatus.OK).body("Comment edited!");
        } catch (NullPointerException e) {
            throw new NullPointerException("Comment not found with id: " + commentId);
        }
    }

    @Override
    public ResponseEntity<?> deleteComment(Long commentId) {
        try {
            Commentary deletedComment = commentaryRepository.findByIdAndDeletedIsFalse(commentId);
            if (deletedComment.isDeleted())
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Comment Already deleted!");
            deletedComment.setDeleted(true);
            Commentary save = commentaryRepository.save(deletedComment);
            return ResponseEntity.status(HttpStatus.OK).body("Comment deleted! Id: " + save.getId());
        } catch (NullPointerException e) {
            throw new NullPointerException("Comment not found with id: " + commentId);
        }
    }

    @Override
    public ResponseEntity<?> deleteMyComment(Long commentId) {

        try {
            Commentary deletedComment = commentaryRepository.findByIdAndDeletedIsFalse(commentId);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (deletedComment.getWriterId().equals(user.getId()))
                return ResponseEntity.status(HttpStatus.CONFLICT).body("This is not your comment you can not delete!");
            if (deletedComment.isDeleted())
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Comment Already deleted!");
            deletedComment.setDeleted(true);
            Commentary save = commentaryRepository.save(deletedComment);
            return ResponseEntity.status(HttpStatus.OK).body("Comment deleted! Id: " + save.getId());
        } catch (NullPointerException e) {
            throw new NullPointerException("Comment not found with id: " + commentId);
        }
    }
}
