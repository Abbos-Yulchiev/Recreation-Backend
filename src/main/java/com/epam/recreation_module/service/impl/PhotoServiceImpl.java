package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.exception.RestException;
import com.epam.recreation_module.model.Attachment;
import com.epam.recreation_module.model.AttachmentContent;
import com.epam.recreation_module.model.Photo;
import com.epam.recreation_module.model.Recreation;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.AttachmentContentRepository;
import com.epam.recreation_module.repository.AttachmentRepository;
import com.epam.recreation_module.repository.PhotoRepository;
import com.epam.recreation_module.repository.RecreationRepository;
import com.epam.recreation_module.service.PhotoService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Service
public class PhotoServiceImpl implements PhotoService {

    final PhotoRepository photoRepository;
    final AttachmentRepository attachmentRepository;
    final AttachmentContentRepository attachmentContentRepository;
    final RecreationRepository recreationRepository;

    public PhotoServiceImpl(PhotoRepository photoRepository, AttachmentRepository attachmentRepository,
                            AttachmentContentRepository attachmentContentRepository, RecreationRepository recreationRepository) {
        this.photoRepository = photoRepository;
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
        this.recreationRepository = recreationRepository;
    }

    @SneakyThrows
    @Override
    public void getPhotoById(Long placeId, HttpServletResponse response) {

        try {
            Long id1 = photoRepository.findPhotosByRecreationId(placeId);
            Photo photo = photoRepository.findById(id1).orElseThrow(() -> new NotFoundException("Photo not found!"));
            Long attachmentId = photo.getAttachment().getId();
            Attachment attachment = attachmentRepository.findById(attachmentId).orElseThrow(() ->
                    new NotFoundException("Attachment not found!"));
            Long id = attachment.getAttachmentContent().getId();
            AttachmentContent attachmentContent = attachmentContentRepository.findById(id).orElseThrow(() ->
                    new NotFoundException("Attachment content not found!"));
            response.setHeader("Content-Disposition", "filename: \"" + attachment.getFileOriginalName()
                    + "\"\tname: \"" + photo.getName());
            response.setContentType(attachment.getContentType());
            FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());
        }catch (RestException e){
            return;
        }
    }

    @Override
    public ApiResponse addPhoto(Long recreationId, MultipartFile[] files) {

        if (files != null && files.length != 0) {
            try {
                for (MultipartFile file : files) {
                    Recreation recreation = recreationRepository.findById(recreationId)
                            .orElseThrow(() -> new NotFoundException("Invalid Recreation given!"));

                    Photo photo = new Photo();
                    photo.setName(file.getOriginalFilename());
                    photo.setRecreation(recreation);
                    String originalFilename = file.getOriginalFilename();
                    Long size = file.getSize();
                    String contentType = file.getContentType();

                    Attachment attachment = new Attachment();
                    attachment.setFileOriginalName(originalFilename);
                    attachment.setSize(size);
                    attachment.setContentType(contentType);
                    attachment.setName(originalFilename);
                    /*Save Attachment to photo*/
                    photo.setAttachment(attachment);
                    Attachment savedAttachment = attachmentRepository.save(attachment);
                    AttachmentContent attachmentContent = new AttachmentContent();
                    attachmentContent.setBytes(file.getBytes());
                    attachmentContent.setAttachment(savedAttachment);
                    attachmentContentRepository.save(attachmentContent);
                    photoRepository.save(photo);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ApiResponse("File(s) saved!", true);
    }

    @Override
    public ApiResponse editPhoto(Long photoId, Long recreationId, MultipartFile file) {

        Optional<Photo> optionalPhoto = photoRepository.findById(photoId);
        if (optionalPhoto.isPresent()) {
            try {
                Recreation recreation = recreationRepository.findById(recreationId)
                        .orElseThrow(() -> new NotFoundException("Invalid recreation given!"));
                Photo photo = optionalPhoto.get();
                photo.setName(file.getOriginalFilename());
                photo.setRecreation(recreation);
                String originalFilename = file.getOriginalFilename();
                Long size = file.getSize();
                String contentType = file.getContentType();
                Long attachmentId = photo.getAttachment().getId();
                Attachment attachment = attachmentRepository.findById(attachmentId)
                        .orElseThrow(() -> new NotFoundException("Invalid attachment!"));
                attachment.setFileOriginalName(originalFilename);
                attachment.setSize(size);
                attachment.setContentType(contentType);
                attachment.setName(originalFilename);
                /*set Attachment to photo*/
                photo.setAttachment(attachment);
                Attachment savedAttachment = attachmentRepository.save(attachment);

                Long attachmentContentId = attachment.getAttachmentContent().getId();
                AttachmentContent attachmentContent = attachmentContentRepository.findById(attachmentContentId)
                        .orElseThrow(() -> new NotFoundException("Invalid AttachmentContent!"));
                attachmentContent.setBytes(file.getBytes());
                attachmentContent.setAttachment(savedAttachment);
                attachmentContentRepository.save(attachmentContent);
                photoRepository.save(photo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ApiResponse("File(s) saved!", true);
    }

    @Override
    public ApiResponse deletePhoto(Long photoId) {

        //Photo id came from recreation
        Long id = photoRepository.findPhotosByRecreationId(photoId);
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new NotFoundException("Invalid Photo Id"));
        Long attachmentId = photo.getAttachment().getId();
        attachmentContentRepository.deletingByAttachmentId(attachmentId);
        photoRepository.deleteById(id);
        attachmentRepository.deleteById(attachmentId);
        return new ApiResponse("Photo deleted. Id: " + photoId, true);
    }
}
