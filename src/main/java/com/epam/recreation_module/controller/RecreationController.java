package com.epam.recreation_module.controller;

import com.epam.recreation_module.model.DTO.RecreationDTO;
import com.epam.recreation_module.service.RecreationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recreation")
@CrossOrigin
public class RecreationController {

    final RecreationService recreationService;

    public RecreationController(RecreationService recreationService) {
        this.recreationService = recreationService;
    }


    @ApiOperation(value = "Get a Recreation by ID")
    @GetMapping(value = "/{recreationId}")
    public ResponseEntity<?> getRecreationById(@ApiParam("Recreation's ID") @PathVariable Long recreationId) {
        return recreationService.getRecreationById(recreationId);
    }

    @ApiOperation(value = "Get Recreations by category name")
    @GetMapping(value = "/byCategory")
    public ResponseEntity<?> getByCategory(@ApiParam(value = "Category name String") @RequestParam String category,
                                           @ApiParam(value = "Recreation page") @RequestParam(defaultValue = "0", required = false) Integer page,
                                           @ApiParam(value = "Page's size") @RequestParam(defaultValue = "20", required = false) Integer size) {
        return recreationService.getByCategory(category, page, size);
    }

    @ApiOperation(value = "Get Recreation by page")
    @GetMapping("/all")
    public ResponseEntity<?> getAll(@ApiParam("Event page") @RequestParam(defaultValue = "0", required = false) Integer page,
                                    @ApiParam(value = "Page's size") @RequestParam(defaultValue = "20", required = false) Integer size) {
        return recreationService.getAll(page, size);
    }

    @ApiOperation(value = "Get exist Recreations by page (for users)")
    @GetMapping("/allByExist")
    public ResponseEntity<?> getAllByExist(@ApiParam(value = "Event page", type = "Integer") @RequestParam(defaultValue = "0", required = false) Integer page,
                                           @ApiParam(value = "Page's size", type = "Integer") @RequestParam(defaultValue = "20", required = false) Integer size) {
        return recreationService.getAllByExist(page, size);
    }

    @ApiOperation(value = "This method creates a new Recreation")
    @PostMapping("/add")
    public ResponseEntity<?> createRecreation(@ApiParam(value = "Recreation with json", type = "Object") @RequestBody RecreationDTO recreationDTO) {
        return recreationService.createRecreation(recreationDTO);
    }

    @ApiOperation(value = "This method edits a Recreation by it's id")
    @PutMapping("/edit/{recreationId}")
    public ResponseEntity<?> editRecreation(@ApiParam(name = "Recreation ID", type = "Long", example = "1") @PathVariable Long recreationId,
                                            @ApiParam(value = "Recreation with json", type = "Object") @RequestBody RecreationDTO recreationDTO) {
        return recreationService.editRecreation(recreationId, recreationDTO);
    }

    @ApiOperation(value = "This method edits a Recreation's status by ID")
    @PutMapping("/editStatus/{recreationId}")
    public ResponseEntity<?> editRecreationStatus(@ApiParam(name = "Recreation ID", type = "Long", example = "1") @PathVariable Long recreationId,
                                                  @ApiParam(name = "Recreation ID", type = "String", example = "OPEN") @RequestBody String status) {
        return recreationService.editRecreationStatus(recreationId, status);
    }

    @ApiOperation(value = "This method deletes a Recreation by ID")
    @DeleteMapping("/delete/{recreationId}")
    public ResponseEntity<?> deleteRecreation(@ApiParam(name = "Recreation ID", type = "Long", example = "1") @PathVariable Long recreationId) {
        return recreationService.deleteRecreationById(recreationId);
    }
}
