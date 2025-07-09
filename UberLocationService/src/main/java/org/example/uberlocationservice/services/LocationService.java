package org.example.uberlocationservice.services;

import org.example.uberlocationservice.dtos.DriverLocationDto;

import java.util.List;

public interface LocationService {
    Boolean saveDriverLocation(String driverId, Double latitude, Double longitude);

    List<DriverLocationDto> getNearbyDrivers(Double latitude, Double longitude);
}
