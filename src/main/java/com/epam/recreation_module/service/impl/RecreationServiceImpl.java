package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.model.*;
import com.epam.recreation_module.model.DTO.RecreationDTO;
import com.epam.recreation_module.model.enums.RecreationCategory;
import com.epam.recreation_module.model.enums.RecreationStatus;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.*;
import com.epam.recreation_module.service.RecreationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RecreationServiceImpl implements RecreationService {

    final RecreationRepository recreationRepository;
    final RestTemplate restTemplate;
    final AddressRepository addressRepository;
    final BuildingRepository buildingRepository;
    final StreetRepository streetRepository;
    final DistrictRepository districtRepository;
    final ExternalConnections externalConnections;

    public RecreationServiceImpl(RecreationRepository recreationRepository, RestTemplate restTemplate,
                                 AddressRepository addressRepository, BuildingRepository buildingRepository,
                                 StreetRepository streetRepository, DistrictRepository districtRepository,
                                 ExternalConnections externalConnections) {
        this.recreationRepository = recreationRepository;
        this.restTemplate = restTemplate;
        this.addressRepository = addressRepository;
        this.buildingRepository = buildingRepository;
        this.streetRepository = streetRepository;
        this.districtRepository = districtRepository;
        this.externalConnections = externalConnections;
    }

    @Override
    public ResponseEntity<?> getRecreationById(Long recreationId) {

        try {
            Recreation recreation = recreationRepository.findByIdAndExistTrue(recreationId)
                    .orElseThrow(() -> new NotFoundException("Invalid recreation given!"));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(recreation);

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid recreation given! Recreation not found!");
        }
    }

    @Override
    public ResponseEntity<?> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Recreation> existTrue = recreationRepository.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(existTrue);
    }

    @Override
    public ResponseEntity<?> getByCategory(String category, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Recreation> existTrue = recreationRepository.findByCategory(category.toUpperCase(), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(existTrue);
    }

    @Override
    public ResponseEntity<?> getAllByExist(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Recreation> getAll = recreationRepository.findAllByExistTrue(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(getAll);
    }

    @Override
    public ResponseEntity<?> createRecreation(RecreationDTO recreationDTO) {

        List<Address> addresses = recreationDTO.getAddress();
        Address newAddress = addresses.get(0);
        boolean existsRecreation = recreationRepository.existsByNameAndAddress_Id(recreationDTO.getName(), newAddress.getId());
        if (existsRecreation)
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("This recreation already exist!", false));
        try {

            //Address saved and returned from method
            Address address = saveNewRecreationAddress(recreationDTO);
            Recreation recreation = new Recreation();
            return recreationSetter(recreation, recreationDTO, address, "saved");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Address not found! Invalid Address given!\n" +
                            "Address: " + "\n\t" + newAddress.getId() +
                            "\nHome number: " + newAddress.getHomeNumber() +
                            "\nHome code: " + newAddress.getHomeCode()
                    );
        }
    }

    @Override
    public ResponseEntity<?> editRecreation(Long recreationId, RecreationDTO recreationDTO) {

        List<Address> addresses = recreationDTO.getAddress();
        Address newAddress = addresses.get(0);

        try {
            Recreation recreation = recreationRepository.findById(recreationId)
                    .orElseThrow(() -> new NotFoundException("Recreation not found!"));
            Address address = addressRepository.findById(newAddress.getId())
                    .orElseThrow(() -> new NotFoundException("Address not found! Invalid Address given!"));
            return recreationSetter(recreation, recreationDTO, address, "edited!");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Address not found! Invalid Address given!\n" +
                            "Address: " + "\n\t" + newAddress.getId() +
                            "\nHome number: " + newAddress.getHomeNumber() +
                            "\nHome code: " + newAddress.getHomeCode()
                    );
        }
    }

    @Override
    public ResponseEntity<?> editRecreationStatus(Long recreationId, String status) {

        Recreation recreation = recreationRepository
                .findById(recreationId).orElseThrow(() -> new NotFoundException("Invalid Recreation ID!"));
        recreation.setRecreationStatus(RecreationStatus.valueOf(status));
        recreationRepository.save(recreation);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Status Edited", true));
    }

    @Override
    public ResponseEntity<?> deleteRecreationById(Long recreationId) {

        Recreation recreation = recreationRepository.findById(recreationId)
                .orElseThrow(() -> new NotFoundException("Invalid Recreation Id!", recreationId));
        recreation.setExist(false);
        recreationRepository.save(recreation);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse("Recreation deleted.", true));
    }

    private ResponseEntity<?> recreationSetter(Recreation recreation, RecreationDTO recreationDTO, Address address, String str) {

        List<String> openingTimes = recreationDTO.getOpeningTime();
        String openingTime = openingTimes.get(0);
        List<String> closingTimes = recreationDTO.getClosingTime();
        String closingTime = closingTimes.get(0);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        recreation.setName(recreationDTO.getName());
        if (recreationDTO.getRecreationStatus().equals(""))
            recreation.setRecreationCategory(RecreationCategory.valueOf("PARK"));
        else recreation.setRecreationCategory(RecreationCategory.valueOf(recreationDTO.getRecreationCategory()));
        if (recreationDTO.getRecreationStatus().equals(""))
            recreation.setRecreationStatus(RecreationStatus.valueOf("OPEN"));
        else recreation.setRecreationStatus(RecreationStatus.valueOf(recreationDTO.getRecreationStatus()));
        recreation.setAddress(address);
        recreation.setDescription(recreationDTO.getDescription());
        recreation.setAvailableSits(recreationDTO.getAvailableSits());
        recreation.setOpeningTime(LocalDateTime.parse(openingTime, format));
        recreation.setClosingTime(LocalDateTime.parse(closingTime, format));
        recreation.setPrice(recreationDTO.getPrice());

        ResponseEntity<?> responseEntity = externalConnections.creatingNewRecreationPlace(recreationDTO.getName(), address.getId(), address.getOwnerCardNumber());
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Recreation save = recreationRepository.save(recreation);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse("Recreation " + str + ". Recreation Id: " + save.getId(), true));
        } else return responseEntity;
    }

    public Address saveNewRecreationAddress(RecreationDTO recreationDTO) {

        List<Address> addresses = recreationDTO.getAddress();
        Address newAddress = addresses.get(0);

        /*Adding new Recreation place's address*/
        District district = new District();
        Street street = new Street();
        Building building = new Building();
        Address address = new Address();

        district.setId(newAddress.getBuilding().getStreet().getDistrict().getId());
        district.setName(newAddress.getBuilding().getStreet().getDistrict().getName());

        street.setDistrict(district);
        street.setName(newAddress.getBuilding().getStreet().getName());
        street.setId(newAddress.getBuilding().getStreet().getId());

        building.setStreet(street);
        building.setId(newAddress.getBuilding().getId());
        building.setBuildingType(newAddress.getBuilding().getBuildingType());
        building.setBuildingNumber(newAddress.getBuilding().getBuildingNumber());

        address.setBuilding(building);
        address.setId(newAddress.getId());
        address.setHomeCode(newAddress.getHomeCode());
        address.setHomeNumber(newAddress.getHomeNumber());

        districtRepository.save(district);
        streetRepository.save(street);
        buildingRepository.save(building);
        return addressRepository.save(address);
    }
}


