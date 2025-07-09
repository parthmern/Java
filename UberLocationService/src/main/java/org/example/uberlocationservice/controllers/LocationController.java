package org.example.uberlocationservice.controllers;

import org.example.uberlocationservice.dtos.DriverLocationDto;
import org.example.uberlocationservice.dtos.NearbyDriversRequestDto;
import org.example.uberlocationservice.dtos.SaveDriverLocationDto;
import org.example.uberlocationservice.services.LocationService;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/location")
public class LocationController {

    private LocationService locationService;

    public LocationController(LocationService locationService){
        this.locationService = locationService;
    }

    @PostMapping("/drivers")
    public ResponseEntity<Boolean> saveDriverLocation(@RequestBody SaveDriverLocationDto saveDriverLocationDto){
        try {
            Boolean res = locationService.saveDriverLocation(saveDriverLocationDto.getDriverId(), saveDriverLocationDto.getLatitude(), saveDriverLocationDto.getLongitude());
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/nearby/drivers")
    public ResponseEntity<List<DriverLocationDto>> getNearbyDrivers(@RequestBody NearbyDriversRequestDto nearbyDriversRequestDto){
        try{
            List<DriverLocationDto> drivers = locationService.getNearbyDrivers(
                    nearbyDriversRequestDto.getLatitude(),
                    nearbyDriversRequestDto.getLongitude()
            );
            return new ResponseEntity<>(drivers, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
