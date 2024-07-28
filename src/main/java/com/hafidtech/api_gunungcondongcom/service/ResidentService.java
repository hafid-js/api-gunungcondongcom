package com.hafidtech.api_gunungcondongcom.service;

import com.hafidtech.api_gunungcondongcom.model.resident.Resident;
import com.hafidtech.api_gunungcondongcom.request.ResidentRequest;
import com.hafidtech.api_gunungcondongcom.response.PagedResponse;
import com.hafidtech.api_gunungcondongcom.response.ResidentResponse;
import org.apache.coyote.BadRequestException;

public interface ResidentService {

    Resident addResident(ResidentRequest resident);

    PagedResponse<Resident> getAllResidents(int page, int size) throws BadRequestException;
}
