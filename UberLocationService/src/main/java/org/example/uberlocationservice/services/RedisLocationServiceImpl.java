package org.example.uberlocationservice.services;

import org.example.uberlocationservice.dtos.DriverLocationDto;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisLocationServiceImpl implements LocationService{

    private StringRedisTemplate stringRedisTemplate;

    private static final String DRIVER_GEO_OPS_KEY = "drivers";
    private static final Double SEARCH_RADIUS = 5.0;

    public RedisLocationServiceImpl(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Boolean saveDriverLocation(String driverId, Double latitude, Double longitude) {
        GeoOperations<String, String> getOps = stringRedisTemplate.opsForGeo();
        getOps.add(DRIVER_GEO_OPS_KEY,
                new RedisGeoCommands.GeoLocation<>(
                        driverId,
                        new Point(latitude, longitude)
                ));
        return true;
    }

    @Override
    public List<DriverLocationDto> getNearbyDrivers(Double latitude, Double longitude) {
        GeoOperations<String, String> getOps = stringRedisTemplate.opsForGeo();
        Distance radius = new Distance(SEARCH_RADIUS, Metrics.KILOMETERS);    // 5KM
        Circle within = new Circle(new Point(latitude, longitude), radius);
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = getOps.radius(DRIVER_GEO_OPS_KEY, within);
        List<DriverLocationDto> drivers = new ArrayList<>();
        for(GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
            Point point = getOps.position(DRIVER_GEO_OPS_KEY, result.getContent().getName()).get(0);
            DriverLocationDto driverLocationDto = DriverLocationDto.builder()
                    .driverId(result.getContent().getName())
                    .latitude(point.getX())
                    .longitude(point.getY())
                    .build();
            drivers.add(driverLocationDto);
        }
        return drivers;
    }
}
