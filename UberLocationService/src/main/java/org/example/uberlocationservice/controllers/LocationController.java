package org.example.uberlocationservice.controllers;

import org.example.uberlocationservice.dtos.NearbyDriversRequestDto;
import org.example.uberlocationservice.dtos.SaveDriverLocationDto;
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

    private StringRedisTemplate stringRedisTemplate;

    private static final String DRIVER_GEO_OPS_KEY = "drivers";
    private static final Double SEARCH_RADIUS = 5.0;

    public LocationController(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @PostMapping("/drivers")
    public ResponseEntity<Boolean> saveDriverLocation(@RequestBody SaveDriverLocationDto saveDriverLocationDto){
        try {
            GeoOperations<String, String> getOps = stringRedisTemplate.opsForGeo();
            getOps.add(DRIVER_GEO_OPS_KEY,
                    new RedisGeoCommands.GeoLocation<>(
                            saveDriverLocationDto.getDriverId(),
                            new Point(saveDriverLocationDto.getLatitude(), saveDriverLocationDto.getLongitude())
                    ));
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/nearby/drivers")
    public ResponseEntity<List<String>> getNearbyDrivers(@RequestBody NearbyDriversRequestDto nearbyDriversRequestDto){
        try{
            GeoOperations<String, String> getOps = stringRedisTemplate.opsForGeo();
            Distance radius = new Distance(SEARCH_RADIUS, Metrics.KILOMETERS);    // 5KM
            Circle within = new Circle(new Point(nearbyDriversRequestDto.getLatitude(), nearbyDriversRequestDto.getLongitude()), radius);
            GeoResults<RedisGeoCommands.GeoLocation<String>> results = getOps.radius(DRIVER_GEO_OPS_KEY, within);
            List<String> drivers = new ArrayList<>();
            for(GeoResult<RedisGeoCommands.GeoLocation<String>> result : results){
                drivers.add(result.getContent().getName());
            }
            return new ResponseEntity<>(drivers, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
