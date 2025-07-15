package org.example.uberbookingservice.services;

import org.example.uberbookingservice.apis.LocationServiceApi;
import org.example.uberbookingservice.dtos.CreateBookingDto;
import org.example.uberbookingservice.dtos.CreateBookingResponseDto;
import org.example.uberbookingservice.dtos.DriverLocationDto;
import org.example.uberbookingservice.dtos.NearbyDriversRequestDto;
import org.example.uberbookingservice.repositories.BookingRepository;
import org.example.uberbookingservice.repositories.PassengerRepository;
import org.example.uberentityservice.models.Booking;
import org.example.uberentityservice.models.BookingStatus;
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

@Service
public class BookingServiceImpl implements BookingService{


    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;
    private final LocationServiceApi locationServiceApi;

    private static final String LOCATION_SERVICE_URL =  "http://localhost:6000/";

    public BookingServiceImpl(PassengerRepository passengerRepository, BookingRepository bookingRepository, LocationServiceApi locationServiceApi) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.restTemplate = new RestTemplate();
        this.locationServiceApi = locationServiceApi;
    }

    @Override
    public CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails){
        Optional<Passenger> passenger = passengerRepository.findById(bookingDetails.getPassengerId());
        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.ASSIGNING_DRIVER)
                .startLocation(bookingDetails.getStartLocation())
                .endLocation(bookingDetails.getEndLocation())
                .passenger(passenger.get())
                .build();
        Booking newBooking = bookingRepository.save(booking);

        NearbyDriversRequestDto reqBody = NearbyDriversRequestDto.builder()
                .latitude(bookingDetails.getStartLocation().getLatitude())
                .longitude(bookingDetails.getStartLocation().getLongitude())
                .build();

//        //make api call to get nearby
//        // here ResponseEntity<List<DriverLocationDto>> here java world it is List but when you return JSON from somewhere it is ARRAY[] only
//        ResponseEntity<DriverLocationDto[]> nearbyDrivers = restTemplate.postForEntity(LOCATION_SERVICE_URL+"/api/location/nearby/drivers", reqBody , DriverLocationDto[].class);
//        System.out.println("nearbyDrivers.getBody(): " + Arrays.toString(nearbyDrivers.getBody()));

        processNearbyDriversAsync(reqBody);



        CreateBookingResponseDto res = CreateBookingResponseDto.builder()
                .bookingId(newBooking.getId())
                .bookingStatus(newBooking.getBookingStatus().name())
//                .driver(Optional.of(newBooking.getDriver()))
                .build();
        return res;
    }

    private void processNearbyDriversAsync(NearbyDriversRequestDto requestDto) {
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
                }else {
                    System.out.println("Req failed No response"+ response.message());
                }
            }

            @Override
            public void onFailure(Call<DriverLocationDto[]> call, Throwable throwable) {

            }
        });
    }

}
