package com.epam.recreation_module.controller;

import com.epam.recreation_module.model.DTO.CommentaryDTO;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.service.CommentaryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Class {@code CommentaryController} is an endpoint of the API which used for Commentaries
 * Annotated by {@link RestController} with no parameters to provide answer in application/json
 * Annotated by {@link RequestMapping} with parameter value =  "/commentary"
 * Annotated by {@link CrossOrigin} with no parameters to <em>give permission to connection</em>
 * So that {@code CommentaryController} is accessed by sending requests to /commentary
 *
 * @author Yulchiev Abbos
 * @since 1.0
 */
@RestController
@RequestMapping("/commentary")
@CrossOrigin
public class CommentaryController {

    final CommentaryService commentaryService;

    public CommentaryController(CommentaryService commentaryService) {
        this.commentaryService = commentaryService;
    }


    /**
     * Get a recreation's all commentaries' method
     * Annotated by {@link ApiOperation} with parameters:
     * value = "Get all commentaries of the Recreation"
     * Annotated by {@link GetMapping} with parameter value = /all
     *
     * @param recreationId - Recreation ID which wanted to get commentaries
     * @return HTTP response with commentaries
     */
    @ApiOperation(value = "Get all commentaries of the Recreation")
    @GetMapping("/all")
    public ResponseEntity<?> getCommentaries(
            @ApiParam(name = "recreationId", value = "Enter the Recreation's id to get it's commentaries", required = true)
            @RequestParam Long recreationId) {
        return commentaryService.getCommentaries(recreationId);
    }

    /**
     * Get a recreation's all commentaries' method
     * Annotated by {@link ApiOperation} with parameters:
     * value = "Get a commentary by ID"
     * Annotated by {@link GetMapping} with parameter value = /{commentId}
     *
     * @param commentId Commentary ID which commentaries wanted to get
     * @return HTTP response with commentary
     */
    @ApiOperation(value = "Get a commentary by ID")
    @GetMapping("/getById/{commentId}")
    public ResponseEntity<?> getCommentById(@ApiParam("Commentary's ID") @PathVariable Long commentId) {
        return commentaryService.getCommentById(commentId);
    }

    /**
     * Add a new commentary to recreation place
     * Annotated by {@link ApiOperation} with parameters:
     * value = "CommentaryDTO object"
     * Annotated by {@link PostMapping} with parameter value = /add
     *
     * @param commentaryDTO object to write a new commentary
     * @return <em>HTTP</em> response with commentary ID
     */
    @ApiOperation(value = "Creating new commentary")
    @PostMapping("/add")
    public ResponseEntity<?> addCommentary(@ApiParam("CommentaryDTO object")
                                           @RequestBody CommentaryDTO commentaryDTO) {
        return commentaryService.addCommentary(commentaryDTO);
    }

    /**
     * Edit commentary by ID method
     * Annotated by {@link ApiOperation} with parameters:
     * value = "Edit commentary by id (can do admins)"
     * Annotated by {@link PutMapping} with parameter value = /edit/{commentId}
     *
     * @param commentId     Commentary ID which  wanted to edit
     * @param commentaryDTO Object to edit a commentary
     * @return <em>HTTP</em> response with success or failed message
     */
    @ApiOperation(value = "Edit commentary by id (can do admins)")
    @PutMapping("/edit/{commentId}")
    public ResponseEntity<?> editCommentary(@ApiParam("Commentary id") @PathVariable Long commentId,
                                            @ApiParam("CommentaryDTO object") @RequestBody CommentaryDTO commentaryDTO) {
        return commentaryService.editComment(commentId, commentaryDTO);
    }


    /**
     * Edit commentary by ID method
     * Annotated by {@link ApiOperation} with parameters:
     * value = "Edit commentary by id (can do admins and comment owner)"
     * Annotated by {@link PutMapping} with parameter value = /editMy/{commentId}
     *
     * @param commentId     Commentary ID which  wanted to edit
     * @param commentaryDTO Object to edit a commentary
     * @return  <em>HTTP</em> response with success or failed message
     */
    @ApiOperation(value = "Edit commentary by id (can do admins and comment owner)")
    @PutMapping("/editMy/{commentId}")
    public ResponseEntity<?> editMyCommentary(@ApiParam("Commentary id") @PathVariable Long commentId,
                                              @ApiParam("CommentaryDTO object") @RequestBody CommentaryDTO commentaryDTO) {
        return commentaryService.editMyComment(commentId, commentaryDTO);
    }

    /**
     * Delete commentary by ID method (for Admins)
     * Annotated by {@link ApiOperation} with parameters:
     * value = "Delete commentary by id (can do admins)"
     * Annotated by {@link DeleteMapping} with parameter value = /delete/{commentId}
     *
     * @param commentId Commentary ID which  wanted to delete
     * @return <em>HTTP</em> response with success or failed message
     */
    @ApiOperation(value = "Delete commentary by id (can do admins)")
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteCommentary(@ApiParam("Commentary id") @PathVariable Long commentId) {
        return commentaryService.deleteComment(commentId);
    }

    /**
     * Delete commentary by ID method (for commentary' writer)
     * Annotated by {@link ApiOperation} with parameters:  value = "Commentary id"
     * Annotated by {@link DeleteMapping} with parameter value = /delete/{commentId}
     *
     * @param commentId  Commentary ID which  wanted to delete
     * @return <em>HTTP</em> response with success or failed message
     */
    @ApiOperation(value = "Delete commentary by id (can do admins and comment owner)")
    @DeleteMapping("/deleteMy/{commentId}")
    public ResponseEntity<?> deleteMyCommentary(@ApiParam("Commentary id") @PathVariable Long commentId) {
        return commentaryService.deleteMyComment(commentId);
    }
}
