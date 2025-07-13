# RestTemplate

```
 private final RestTemplate restTemplate;
 this.restTemplate = new RestTemplate();


//make api call to get nearby
// here ResponseEntity<List<DriverLocationDto>> here java world it is List but when you return JSON from somewhere it is ARRAY[] only
ResponseEntity<DriverLocationDto[]> nearbyDrivers = restTemplate.postForEntity(LOCATION_SERVICE_URL+"/api/location/nearby/drivers", reqBody , DriverLocationDto[].class);

System.out.println("nearbyDrivers.getBody(): " + Arrays.toString(nearbyDrivers.getBody()));
```

