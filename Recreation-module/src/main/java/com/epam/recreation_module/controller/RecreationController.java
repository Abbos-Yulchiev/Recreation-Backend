package com.epam.recreation_module.controller;

import com.epam.recreation_module.model.DTO.RecreationDTO;
import com.epam.recreation_module.service.RecreationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class {@code CommentaryController} is an endpoint of the API which used for Recreations
 * Annotated by {@link RestController} with no parameters to provide answer in application/json
 * Annotated by {@link RequestMapping} with parameter value =  "/recreation"
 * Annotated by {@link CrossOrigin} with no parameters to <em>give permission to connection</em>
 * So that {@code RecreationController} is accessed by sending requests to /recreation
 *
 * @author Yulchiev Abbos
 * @since 1.0
 */
@RestController
@RequestMapping("/recreation")
@CrossOrigin
public class RecreationController {

    final RecreationService recreationService;

    public RecreationController(RecreationService recreationService) {
        this.recreationService = recreationService;
    }


    /**
     * Get a Recreation by Recreation's ID method
     * Annotated by {@link ApiOperation} with parameters:
     * value =  "Get a Recreation by ID"
     * Annotated by {@link GetMapping} with parameter value = /{recreationId}
     *
     * @param recreationId Recreation ID for get Recreation
     * @return <em>HTTP</em> response with a Recreation
     */
    @ApiOperation(value = "Get a Recreation by ID")
    @GetMapping(value = "/{recreationId}")
    public ResponseEntity<?> getRecreationById(@ApiParam("Recreation's ID") @PathVariable Long recreationId) {
        return recreationService.getRecreationById(recreationId);
    }

    /**
     * Get by  Category method
     * Annotated by {@link ApiOperation} with parameters:
     * value =  "Get Recreations by category name"
     * Annotated by {@link GetMapping} with parameter value = /byCategory
     *
     * @param category Recreation's category name
     * @param page     Recreation's page
     * @param size     Recreation page's size
     * @return <em>HTTP</em> response with Recreation page by category
     */
    @ApiOperation(value = "Get Recreations by category name")
    @GetMapping(value = "/byCategory")
    public ResponseEntity<?> getByCategory(@ApiParam(value = "Category name String") @RequestParam String category,
                                           @ApiParam(value = "Recreation page") @RequestParam(defaultValue = "0", required = false) Integer page,
                                           @ApiParam(value = "Page's size") @RequestParam(defaultValue = "20", required = false) Integer size) {
        return recreationService.getByCategory(category, page, size);
    }

    /**
     * Get all Recreation by page method
     * Annotated by {@link ApiOperation} with parameters:
     * value =  "Get Recreation by page"
     * Annotated by {@link GetMapping} with parameter value = /all
     *
     * @param page Recreation's page
     * @param size Recreation page's size
     * @return <em>HTTP</em> response with Recreation page
     */
    @ApiOperation(value = "Get Recreation by page")
    @GetMapping("/all")
    public ResponseEntity<?> getAll(@ApiParam("Event page")
                                    @RequestParam(defaultValue = "0", required = false) Integer page,
                                    @ApiParam(value = "Page's size")
                                    @RequestParam(defaultValue = "20", required = false) Integer size) {
        return recreationService.getAll(page, size);
    }

    /**
     * Get all exist Recreation by page method
     * Annotated by {@link ApiOperation} with parameters:
     * value =  "Get exist Recreations by page (for users)"
     * Annotated by {@link GetMapping} with parameter value = /allByExist
     *
     * @param page Recreation's page
     * @param size Recreation page's size
     * @return <em>HTTP</em> response with Recreation page
     */
    @ApiOperation(value = "Get exist Recreations by page (for users)")
    @GetMapping("/allByExist")
    public ResponseEntity<?> getAllByExist(@ApiParam(value = "Event page", type = "Integer")
                                           @RequestParam(defaultValue = "0", required = false) Integer page,
                                           @ApiParam(value = "Page's size", type = "Integer")
                                           @RequestParam(defaultValue = "20", required = false) Integer size) {
        return recreationService.getAllByExist(page, size);
    }

    /**
     * Create new Recreation method
     * Annotated by {@link ApiOperation} with parameters:
     * value =  "This method creates a new Recreation"
     * Annotated by {@link PostMapping} with parameter value = /add
     *
     * @param recreationDTO Object for create new Recreation
     * @return <em>HTTP</em> response with message
     */
    @ApiOperation(value = "This method creates a new Recreation")
    @PostMapping("/add")
    public ResponseEntity<?> createRecreation(@ApiParam(value = "Recreation with json", type = "Object")
                                              @RequestBody RecreationDTO recreationDTO) {
        return recreationService.createRecreation(recreationDTO);
    }

    /**
     * Edit a Recreation by Recreation's ID method
     * Annotated by {@link ApiOperation} with parameters:
     * value =  "This method edits a Recreation by its id"
     * Annotated by {@link PutMapping} with parameter value = /edit/{recreationId}
     *
     * @param recreationId  Recreation ID
     * @param recreationDTO Object of edit Recreation
     * @return - <em>HTTP</em> response with message
     */
    @ApiOperation(value = "This method edits a Recreation by its id")
    @PutMapping("/edit/{recreationId}")
    public ResponseEntity<?> editRecreation(@ApiParam(name = "Recreation ID", type = "Long", example = "1") @PathVariable Long recreationId,
                                            @ApiParam(value = "Recreation with json", type = "Object") @RequestBody RecreationDTO recreationDTO) {
        return recreationService.editRecreation(recreationId, recreationDTO);
    }

    /**
     * Edit a Recreation's Status by Recreation's ID method
     * Annotated by {@link ApiOperation} with parameters:
     * value =  "This method edits a Recreation's status by ID"
     * Annotated by {@link PutMapping} with parameter value = /editStatus/{recreationId}
     *
     * @param recreationId Recreation ID
     * @param status       Object of edit Recreation
     * @return - <em>HTTP</em> response with message
     */
    @ApiOperation(value = "This method edits a Recreation's status by ID")
    @PutMapping("/editStatus/{recreationId}")
    public ResponseEntity<?> editRecreationStatus(@ApiParam(name = "Recreation ID", type = "Long", example = "1") @PathVariable Long recreationId,
                                                  @ApiParam(name = "Recreation ID", type = "String", example = "OPEN") @RequestBody String status) {
        return recreationService.editRecreationStatus(recreationId, status);
    }

    /**
     * Delete a Recreation's by Recreation's ID method
     * Annotated by {@link ApiOperation} with parameters:
     * value =  "This method deletes a Recreation by ID"
     * Annotated by {@link PutMapping} with parameter value = /delete/{recreationId}
     *
     * @param recreationId Recreation ID
     * @return <em>HTTP</em> response with message
     */
    @ApiOperation(value = "This method deletes a Recreation by ID")
    @DeleteMapping("/delete/{recreationId}")
    public ResponseEntity<?> deleteRecreation(@ApiParam(name = "Recreation ID", type = "Long", example = "1") @PathVariable Long recreationId) {
        return recreationService.deleteRecreationById(recreationId);
    }
}
