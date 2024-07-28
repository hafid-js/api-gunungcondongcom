package com.hafidtech.api_gunungcondongcom.service.impl;

import com.hafidtech.api_gunungcondongcom.exception.AppException;
import com.hafidtech.api_gunungcondongcom.exception.ResidentException;
import com.hafidtech.api_gunungcondongcom.exception.ResourceNotFoundException;
import com.hafidtech.api_gunungcondongcom.model.resident.*;
import com.hafidtech.api_gunungcondongcom.model.user.role.Role;
import com.hafidtech.api_gunungcondongcom.model.user.role.RoleName;
import com.hafidtech.api_gunungcondongcom.repository.resident.*;
import com.hafidtech.api_gunungcondongcom.request.ResidentRequest;
import com.hafidtech.api_gunungcondongcom.response.PagedResponse;
import com.hafidtech.api_gunungcondongcom.response.ResidentResponse;
import com.hafidtech.api_gunungcondongcom.service.ResidentService;
import com.hafidtech.api_gunungcondongcom.utils.AppConstants;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ResidentServiceImpl implements ResidentService {

    @Autowired
    private HemletRepository hemletRepository;
    @Autowired
    private RwRepository rwRepository;
    @Autowired
    private RtRepository rtRepository;
    @Autowired
    private ReligionRepository religionRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private GenderRepository genderRepository;
    @Autowired
    private BloodRepository bloodRepository;
    @Autowired
    private ResidentRepository residentRepository;

    @Override
    public Resident addResident(ResidentRequest request) {

        Gender gender = genderRepository.findById(request.getGenderId()).orElseThrow(() -> new ResourceNotFoundException(AppConstants.GENDER, AppConstants.ID, request.getGenderId()));
        Hemlet hemlet = hemletRepository.findById(request.getHemletId()).orElseThrow(() -> new ResourceNotFoundException(AppConstants.HEMLET, AppConstants.ID, request.getHemletId()));
        RW rw = rwRepository.findById(request.getRwId()).orElseThrow(() -> new ResourceNotFoundException(AppConstants.RW, AppConstants.ID, request.getRwId()));
        RT rt = rtRepository.findById(request.getRtId()).orElseThrow(() -> new ResourceNotFoundException(AppConstants.RT, AppConstants.ID, request.getRtId()));
        Religion religion = religionRepository.findById(request.getReligionId()).orElseThrow(() -> new ResourceNotFoundException(AppConstants.RELIGION, AppConstants.ID, request.getReligionId()));
        Job job = jobRepository.findById(request.getJobId()).orElseThrow(() -> new ResourceNotFoundException(AppConstants.JOB, AppConstants.ID, request.getJobId()));
        Education education = educationRepository.findById(request.getEducationId()).orElseThrow(() -> new ResourceNotFoundException(AppConstants.EDUCATION, AppConstants.ID, request.getEducationId()));
        Blood blood = bloodRepository.findById(request.getBloodId()).orElseThrow(() -> new ResourceNotFoundException(AppConstants.BLOOD, AppConstants.ID, request.getBloodId()));

        Resident resident = new Resident();

        resident.setName(request.getName());
        resident.setNoIdentity(request.getNoIdentity());
        resident.setPlaceOfBirth(request.getPlaceOfBirth());
        resident.setDateOfBirth(request.getDateOfBirth());
        resident.setGender(gender);
        resident.setHemlet(hemlet);
        resident.setRw(rw);
        resident.setRt(rt);
        resident.setHouseNumber(request.getHouseNumber());
        resident.setReligion(religion);
        resident.setJob(job);
        resident.setEducation(education);
        resident.setBlood(blood);

        return residentRepository.save(resident);
    }


    @Override
    public PagedResponse<Resident> getAllResidents(int page, int size) throws BadRequestException {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, AppConstants.CREATED_AT);
        Page<Resident> residents = residentRepository.findAll(pageable);

        List<Resident> content = residents.getNumberOfElements() == 0 ? Collections.emptyList() : residents.getContent();

        return new PagedResponse<>(content, residents.getNumber(), residents.getSize(), residents.getTotalElements(), residents.getTotalPages(), residents.isLast());

    }


    private void validatePageNumberAndSize(int page, int size) throws BadRequestException {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }
        if (size < 0) {
            throw new BadRequestException("Size number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
}
