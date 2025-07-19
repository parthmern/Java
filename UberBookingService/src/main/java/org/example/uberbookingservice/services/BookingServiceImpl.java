package org.example.uberbookingservice.services;

import org.example.uberbookingservice.apis.LocationServiceApi;
import org.example.uberbookingservice.apis.UberSocketApi;
import org.example.uberbookingservice.dtos.*;
import org.example.uberbookingservice.repositories.BookingRepository;
import org.example.uberbookingservice.repositories.DriverRepository;
import org.example.uberbookingservice.repositories.PassengerRepository;
import org.example.uberentityservice.models.Booking;
import org.example.uberentityservice.models.BookingStatus;
import org.example.uberentityservice.models.Driver;
import org.example.uberentityservice.models.Passenger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService{


    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;
    private final LocationServiceApi locationServiceApi;
    private final UberSocketApi uberSocketApi;

    private static final String LOCATION_SERVICE_URL =  "http://localhost:6000/";
    private final DriverRepository driverRepository;

    public BookingServiceImpl(PassengerRepository passengerRepository, BookingRepository bookingRepository, LocationServiceApi locationServiceApi, DriverRepository driverRepository, UberSocketApi uberSocketApi) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.restTemplate = new RestTemplate();
        this.locationServiceApi = locationServiceApi;
        this.driverRepository = driverRepository;
        this.uberSocketApi = uberSocketApi;
    }

    @Override
    public CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails){
        System.out.println("creating booking...");
        Optional<Passenger> passenger = passengerRepository.findById(bookingDetails.getPassengerId());
        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.ASSIGNING_DRIVER)
                .startLocation(bookingDetails.getStartLocation())
                .endLocation(bookingDetails.getEndLocation())
                .passenger(passenger.get())
                .build();
        Booking newBooking = bookingRepository.save(booking);

        System.out.println("booking created"+ " start X " + newBooking.getStartLocation().getLatitude() + " end X " + newBooking.getEndLocation().getLatitude());

        NearbyDriversRequestDto reqBody = NearbyDriversRequestDto.builder()
                .latitude(bookingDetails.getStartLocation().getLatitude())
                .longitude(bookingDetails.getStartLocation().getLongitude())
                .build();

//        //make api call to get nearby
//        // here ResponseEntity<List<DriverLocationDto>> here java world it is List but when you return JSON from somewhere it is ARRAY[] only
//        ResponseEntity<DriverLocationDto[]> nearbyDrivers = restTemplate.postForEntity(LOCATION_SERVICE_URL+"/api/location/nearby/drivers", reqBody , DriverLocationDto[].class);
//        System.out.println("nearbyDrivers.getBody(): " + Arrays.toString(nearbyDrivers.getBody()));


        processNearbyDriversAsync(reqBody, bookingDetails.getPassengerId(), newBooking);

        CreateBookingResponseDto res = CreateBookingResponseDto.builder()
                .bookingId(newBooking.getId())
                .bookingStatus(newBooking.getBookingStatus().name())
//                .driver(Optional.of(newBooking.getDriver()))
                .build();
        return res;
    }

    private void processNearbyDriversAsync(NearbyDriversRequestDto requestDto, Long passengerId, Booking newlyCreatedBooking) {
        System.out.println("Calling processNearbyDriversAsync - calling to locationServiceApi");

        Call<DriverLocationDto[]> call = locationServiceApi.getNearbyDrivers(requestDto);

        // working in different thread - asnchronus nature
        // async
        call.enqueue(new Callback<DriverLocationDto[]>() {
            @Override
            public void onResponse(Call<DriverLocationDto[]> call, Response<DriverLocationDto[]> response) {
                if(response.isSuccessful() & response.body() != null ) {
                    List<DriverLocationDto> driverLocations = Arrays.asList(response.body());

                    driverLocations.forEach(driverLocationDto -> {
                        System.out.println(driverLocationDto.getDriverId() + " " + driverLocationDto.getLatitude() + " " + driverLocationDto.getLongitude());
                    });
                    System.out.println("successful processNearbyDriversAsync");

                    List<Long> nearbyDriverIds = driverLocations.stream()
                            .map(driver -> Long.parseLong(driver.getDriverId()))
                            .collect(Collectors.toList());


                    raiseRideRequestAsync(RideRequestDto.builder().bookingId(newlyCreatedBooking.getId()).passengerId(passengerId).startLocation(newlyCreatedBooking.getStartLocation()).endLocation(newlyCreatedBooking.getEndLocation()).nearByDriverIds(nearbyDriverIds).build());

                }else {
                    System.out.println("Req failed No response"+ response.message());
                }
            }

            @Override
            public void onFailure(Call<DriverLocationDto[]> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto updateBookingRequestDto, Long bookingId) {
        Optional<Driver> driver = driverRepository.findById(updateBookingRequestDto.getDriverId().get());
        // TODO: check drivver is in DB and isnot associate to any ride present
        bookingRepository.updateBookingStatusAndDriverById(bookingId, BookingStatus.SCHEDULED, driver.get());
        // TODO: like make driver unavailable
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        return UpdateBookingResponseDto.builder()
                .bookingId(booking.get().getId())
                .status(booking.get().getBookingStatus())
                .driver(Optional.ofNullable(booking.get().getDriver()))
                .build();

    }

    private void raiseRideRequestAsync(RideRequestDto requestDto) {
        System.out.println("Calling raiseRideRequestAsync -- calling to uberSocketApi");
        Call<Boolean> call = uberSocketApi.raiseRideRequest(requestDto);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful() & response.body() != null ) {
                   Boolean result = response.body();
                   System.out.println("raiseRideRequestAsync success" + result);
                }else {
                    System.out.println("Req failed No response"+ response.message());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }


}
