package com.example.tripdivine.util;

import com.example.tripdivine.dto.LocationMasterGenericDTO;

import java.util.ArrayList;

public class LocationMasterService {

    public static LocationMasterGenericDTO getLocationMasterByTitle(ArrayList<LocationMasterGenericDTO> locationMasterGenericDTOS,
                                                                    String title) {
        LocationMasterGenericDTO masterGenericDTO = null;

        for (LocationMasterGenericDTO dto : locationMasterGenericDTOS) {
            if (title.equals(dto.getTitle_gu())) {
                return dto;
            }
        }
        return masterGenericDTO;
    }
}
