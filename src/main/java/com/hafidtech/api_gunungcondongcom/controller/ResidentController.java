package com.hafidtech.api_gunungcondongcom.controller;

import com.hafidtech.api_gunungcondongcom.exception.ResidentException;
import com.hafidtech.api_gunungcondongcom.model.resident.Resident;
import com.hafidtech.api_gunungcondongcom.repository.resident.ResidentRepository;
import com.hafidtech.api_gunungcondongcom.request.ResidentRequest;
import com.hafidtech.api_gunungcondongcom.response.PagedResponse;
import com.hafidtech.api_gunungcondongcom.response.ResidentResponse;
import com.hafidtech.api_gunungcondongcom.service.ResidentService;
import com.hafidtech.api_gunungcondongcom.utils.AppConstants;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resident")
public class ResidentController {

    @Autowired
    private ResidentRepository residentRepository;
    @Autowired
    private ResidentService residentService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<ResidentResponse> addResident(@Valid @RequestBody ResidentRequest resident) throws ResidentException {

        if (residentRepository.existsByNoIdentity(resident.getNoIdentity())) {
            throw new ResidentException("Resident with this identity number is exists");
        }
        Resident newResident = residentService.addResident(resident);

        ResidentResponse residentResponse = new ResidentResponse();
        residentResponse.setResident(newResident);
        residentResponse.setMessage("Success add data Resident");
        return new ResponseEntity<ResidentResponse>(residentResponse, HttpStatus.CREATED);
    }

//    @GetMapping
//    public ResponseEntity<PagedResponse<Resident>> getAllResident(
//            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
//            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) throws BadRequestException {
//        PagedResponse<Resident> response = residentService.getAllResidents(page, size);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<PagedResponse<Resident>> getAllPosts(
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) throws BadRequestException {
        PagedResponse<Resident> response = residentService.getAllResidents(page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
