package com.epam.recreation_module.service;

import com.epam.recreation_module.payload.ApiResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Component
public interface PhotoService {

    void getPhotoById(Long photoId, HttpServletResponse response);

    ApiResponse addPhoto(Long recreationId, MultipartFile request);

    ApiResponse editPhoto(Long photoId, Long recreationId, MultipartFile request);

    ApiResponse deletePhoto(Long photoId);
}
