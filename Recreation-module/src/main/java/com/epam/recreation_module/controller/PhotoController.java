package com.epam.recreation_module.controller;

import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.service.impl.PhotoServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


/**
 * Class {@code CommentaryController} is an endpoint of the API which used for Photos
 * Annotated by {@link RestController} with no parameters to provide answer in application/json
 * Annotated by {@link RequestMapping} with parameter value =  "/photo"
 * Annotated by {@link CrossOrigin} with no parameters to <em>give permission to connection</em>
 * So that {@code PhotoController} is accessed by sending requests to /photo
 *
 * @author Yulchiev Abbos
 * @since 1.0
 */
@RestController
@RequestMapping("/photo")
@CrossOrigin
public class PhotoController {

    final PhotoServiceImpl photoService;

    public PhotoController(PhotoServiceImpl photoService) {
        this.photoService = photoService;
    }

    /**
     * Getting a photo by Recreation ID method
     * Annotated by {@link ApiOperation} with parameters: value = "Get a photo by recreation ID"
     * Annotated by {@link GetMapping} with parameter value = /get/{recreationId}
     *
     * @param recreationId - Recreation ID which wanted to see photos
     * @param response     - return the photo's content
     */
    @ApiOperation(value = "Get a photo by recreation ID")
    @GetMapping("/get/{recreationId}")
    public void getPhotoById(@ApiParam("Recreation id") @PathVariable Long recreationId,
                             HttpServletResponse response) {
        photoService.getPhotoById(recreationId, response);
    }

    /**
     * Add new photo to the Recreation by Recreation ID method
     * Annotated by {@link ApiOperation} with parameters: value = "Add a photo to recreation by recreation ID"
     * Annotated by {@link PostMapping} with parameter value = /add/{recreationId}
     *
     * @param recreationId - Recreation ID which will be added photo
     * @return <em>HTTP</em> response with message
     */
    @ApiOperation(value = "Add a photo to recreation by recreation ID")
    @PostMapping("/add/{recreationId}")
    public ResponseEntity<?> addPhoto(@ApiParam(name = "Recreation id", type = "Long", value = "ID of photo", example = "1")
                                      @PathVariable Long recreationId,
                                      @ApiParam(type = "file") @RequestParam(name = "file") MultipartFile file) {
        ApiResponse response = photoService.addPhoto(recreationId, file);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    /**
     * Edit a photo by Photo ID method
     * Annotated by {@link ApiOperation} with parameters: value = "Edit Photo by photo ID"
     * Annotated by {@link PutMapping} with parameter value = /edit/{photoId}
     *
     * @param photoId      - photo ID for edit Photo
     * @param recreationId - Recreation ID which Recreation's photo should be edited
     * @param file         - Photo content
     * @return <em>Http</em> response with message
     */
    @ApiOperation(value = "Edit Photo by photo ID")
    @PutMapping("/edit/{photoId}")
    public ResponseEntity<?> editPhoto(@ApiParam(name = "Photo id", type = "Long", value = "ID of photo", example = "1") @PathVariable Long photoId,
                                       @ApiParam("Recreation id") @RequestParam Long recreationId,
                                       @ApiParam(name = "file id", type = "file") @RequestParam("file") MultipartFile file) {
        ApiResponse response = photoService.editPhoto(photoId, recreationId, file);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    /**
     * Delete a photo by Photo ID method
     * Annotated by {@link ApiOperation} with parameters: value = "Delete Photo by photo ID"
     * Annotated by {@link DeleteMapping} with parameter value = /delete/{photoId}
     *
     * @param photoId - Photo ID for delete Photo
     * @return <em>HTTP</em> response with message
     */
    @ApiOperation(value = "Delete Photo by photo ID")
    @DeleteMapping("/delete/{photoId}")
    public ResponseEntity<?> deletePhoto(@ApiParam(name = "Photo id", type = "Long",
            value = "ID of photo", example = "1")
                                         @PathVariable Long photoId) {
        ApiResponse response = photoService.deletePhoto(photoId);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
}