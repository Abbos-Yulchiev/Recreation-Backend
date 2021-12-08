package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.model.*;
import com.epam.recreation_module.model.enums.RecreationCategory;
import com.epam.recreation_module.model.enums.RecreationStatus;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.AttachmentContentRepository;
import com.epam.recreation_module.repository.AttachmentRepository;
import com.epam.recreation_module.repository.PhotoRepository;
import com.epam.recreation_module.repository.RecreationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class PhotoServiceImplTest {

    @InjectMocks
    private PhotoServiceImpl photoService;
    @Mock
    private PhotoRepository photoRepository;
    @Mock
    private AttachmentRepository attachmentRepository;
    @Mock
    private AttachmentContentRepository attachmentContentRepository;
    @Mock
    private RecreationRepository recreationRepository;

    private Photo photo;
    private Attachment attachment;
    private AttachmentContent attachmentContent;
    private Recreation recreation;

    @BeforeEach
    void setUp() {
        District district = new District(1, 123, "Name");
        Street street = new Street(1, 1, "Brooklyn", district);
        Building building = new Building(1, 1, street, 1, "Type");
        Address address = new Address(1L, 1L, 1, "12A", building, "12345678");
        recreation = new Recreation(
                "Main park", "Description", 100,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                RecreationCategory.PARK, RecreationStatus.OPEN,
                address, true, 10.0
        );
        recreation.setId(1L);
        attachment = new Attachment("name", 1L, UUID.randomUUID(), "text/plain");
        photo = new Photo("photo", recreation, attachment);
        photo.setId(1L);
        attachmentContent = new AttachmentContent("ABS".getBytes(StandardCharsets.UTF_8), attachment);
        recreationRepository = mock(RecreationRepository.class);
    }

    @Test
    void getPhotoById() {
        Optional<Photo> optionalPhoto = Optional.of(photo);
        Optional<Attachment> attachmentOptional = Optional.of(attachment);
        Optional<AttachmentContent> attachmentContentOptional = Optional.of(attachmentContent);
        given(this.photoRepository.findById(any())).willReturn(optionalPhoto);
        given(this.attachmentRepository.findById(any())).willReturn(attachmentOptional);
        given(this.attachmentContentRepository.findById(any())).willReturn(attachmentContentOptional);
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        this.photoService.getPhotoById(1L, mockHttpServletResponse);
        assertEquals(2, mockHttpServletResponse.getHeaderNames().size());
    }
    //TODO
//    @Test
//    void addPhoto() {
//
//        Recreation recreation = mock(Recreation.class);
//        recreation.setId(1L);
//        when(recreationRepository.findById(any())).thenReturn(Optional.of(recreation));
//        when(recreationRepository.save(any())).thenReturn(recreation);
//        given(this.photoRepository.save(any(Photo.class))).willReturn(photo);
//        given(this.attachmentRepository.save(any())).willReturn(attachment);
//        given(this.attachmentContentRepository.save(any())).willReturn(attachmentContent);
//        ApiResponse apiResponse = new ApiResponse("File(s) saved!", true);
//        MockMultipartFile mockMultipartFile = new MockMultipartFile("File", "File", "text.txt", "ABC".getBytes(StandardCharsets.UTF_8));
//        assertTrue(apiResponse.isSuccess());
//        Assertions.assertSame(apiResponse, photoService.addPhoto(1L, mockMultipartFile));
//        Assertions.assertSame("File(s) saved!", apiResponse.getMessage());
//    }

    @Test
    void deletePhoto() {
        Optional<Photo> optionalPhoto = Optional.of(photo);
        doNothing().when(this.photoRepository).deleteById(any());
        given(this.photoRepository.findById(any())).willReturn(optionalPhoto);
        Optional<Attachment> optionalAttachment = Optional.of(attachment);
        doNothing().when(this.attachmentRepository).deleteById(any());
        doNothing().when(this.attachmentContentRepository).deletingByAttachmentId(any());
        Optional<AttachmentContent> optionalAttachmentContent = Optional.of(attachmentContent);
        ApiResponse deletePhoto = this.photoService.deletePhoto(1L);
        assertTrue(deletePhoto.isSuccess());
        assertEquals("Photo deleted. Id: " + photo.getId(), (deletePhoto).getMessage());
    }
}