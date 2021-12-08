package com.epam.recreation_module.service;

import com.epam.recreation_module.model.DTO.RecreationDTO;
import org.springframework.http.ResponseEntity;

public interface RecreationService {

    ResponseEntity<?> getRecreationById(Long recreationId);

    ResponseEntity<?> getAll(Integer page, Integer size);

    ResponseEntity<?> getAllByExist(Integer page, Integer size);

    ResponseEntity<?> getByCategory(String category, Integer page, Integer size);

    ResponseEntity<?> createRecreation(RecreationDTO recreationDTO);

    ResponseEntity<?> editRecreation(Long RecreationId, RecreationDTO recreationDTO);

    ResponseEntity<?> editRecreationStatus(Long recreationId, String status);

    ResponseEntity<?> deleteRecreationById(Long recreationId);
}
