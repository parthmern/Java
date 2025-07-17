# Retrofit - integrating diff microservices

```
implementation('com.squareup.retrofit2:retrofit:2.4.0')
  implementation('com.squareup.retrofit2:converter-gson:2.4.0')
```

* make apis folder and inside that

```java
public interface LocationServiceApi {
    @POST("/api/location/nearby/drivers")
    Call<DriverLocationDto[]> getNearbyDrivers(@Body NearbyDriversRequestDto requestDto);
}
```



* now call retrofitconfig

{% tabs %}
{% tab title="RetrofitConfig" %}
```javascript
@Configuration
public class RetrofitConfig {

    @Bean
    public Retrofit retrofit(){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}

```
{% endtab %}

{% tab title="How does bean works here ? revision" %}
#### ⚙️ What is `@Bean` in Spring?

The `@Bean` annotation tells Spring that the method it annotates **produces a bean** — an object that is **managed by the Spring IoC (Inversion of Control) container**. When your application starts, Spring runs all methods annotated with `@Bean` and registers the returned objects as **singleton instances** in the application context.

<figure><img src=".gitbook/assets/image (43).png" alt="" width="375"><figcaption></figcaption></figure>

{% hint style="info" %}
> ❓ _"When I call `new Retrofit()` somewhere (or inject `Retrofit`), how does Spring know which `@Bean` to use — especially if I have multiple beans of the same type?"_
{% endhint %}

That’s a **key concept** in Spring: **Bean Resolution by Type + Name + Qualifier**.\
means spring identify it using the _<mark style="background-color:red;">**RETURN TYPE**</mark>_ of that bean function

<figure><img src=".gitbook/assets/image (45).png" alt="" width="375"><figcaption></figcaption></figure>

<figure><img src=".gitbook/assets/image (47).png" alt="" width="375"><figcaption></figcaption></figure>

* here for multiple beans there are ways like @Primary , @Qualifier("nameofbean") to handle multiple beans
{% endtab %}
{% endtabs %}

question\
if you have a lot of urls like you have 5 service \
so each seravice needs to know url for 4 service\
there is concept called as service discovery

<figure><img src=".gitbook/assets/54b63361-a105-4096-8a8b-c34d89086dac_2379x1514 (1).png" alt="" width="375"><figcaption></figcaption></figure>

_**Client-side service discovery**_**&#x20;allows services to find and communicate with each other without hard-coding the hostname and port**



### Eureka server

* **Eureka Client** is a Spring Boot application that registers itself with a Eureka Server.
* [https://medium.com/@danismaz.furkan/service-discovery-with-netflix-eureka-spring-cloud-f2ade76ffcdd](https://medium.com/@danismaz.furkan/service-discovery-with-netflix-eureka-spring-cloud-f2ade76ffcdd)

```
implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-server'
```

```
@SpringBootApplication
@EnableEurekaServer
public class UberServiceDiscoveryApplication {
    public static void main(String[] args) {
        SpringApplication.run(UberServiceDiscoveryApplication.class, args);
    }
}

```

application.yml file

```
spring:
  application:
    name: "UberServiceDiscovery"

server:
  port: 8761

eureka:
  instance:
    hostname: my-registry
  client:
    register-with-eureka: false
    fetch-registry: false
#    serviceUrl:
#      defaultZone:    http://${eureka.instance.hostname}:${server.port}/eureka/
```

start server vsit - [http://localhost:8761/](http://localhost:8761/)

<figure><img src=".gitbook/assets/image (41).png" alt=""><figcaption></figcaption></figure>



### how to register other service

```
   implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:4.1.1'
```

```
@SpringBootApplication
@EnableDiscoveryClient
public class UberLocationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UberLocationServiceApplication.class, args);
    }

}
```

```
spring:
  application:
    name: "UberLocationService"
  cloud:
    compatibility-verifier:
      enabled: false

server:
  port: 6000
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/ # URL of the Eureka Server
  instance:
    prefer-ip-address: true
```

after visiting [http://localhost:8761/](http://localhost:8761/) you got registerd thing as above img

<figure><img src=".gitbook/assets/image (40).png" alt=""><figcaption></figcaption></figure>

codes

{% tabs %}
{% tab title="LocationServiceApi" %}
```javascript
public interface LocationServiceApi {
    @POST("/api/location/nearby/drivers")
    Call<DriverLocationDto[]> getNearbyDrivers(@Body NearbyDriversRequestDto requestDto);
}

```
{% endtab %}

{% tab title="RetrofitConfig" %}
```python
@Configuration
public class RetrofitConfig {

    @Autowired
    private EurekaClient eurekaClient;

    public String getServiceUrl(String serviceName) {
        return eurekaClient.getNextServerFromEureka(serviceName, false).getHomePageUrl();
    }

    @Bean
    public LocationServiceApi getLocationServiceApi() {
        return new Retrofit.Builder()
                .baseUrl(getServiceUrl("UBERLOCATIONSERVICE"))
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build()
                .create(LocationServiceApi.class);
    }
}
```
{% endtab %}

{% tab title="BookingServiceImpl" %}
```ruby
private final LocationServiceApi locationServiceApi;

public BookingServiceImpl(PassengerRepository passengerRepository, BookingRepository bookingRepository, LocationServiceApi locationServiceApi) {
        // ....
        this.locationServiceApi = locationServiceApi;
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
```
{% endtab %}

{% tab title="explain" %}
* first locationServiceApi dependency injection invoked
* so @bean which is returning LocationServiceApi called which is `public LocationServiceApi getLocationServiceApi() {}`&#x20;
* so we got the retrofit obj&#x20;
* Call\<DriverLocationDto\[]> call = locationServiceApi.getNearbyDrivers(requestDto);
  * serialize requestDto
  * URL = `http://<location-service-url>/api/location/nearby/drivers`
  * Method = POST
  * Body = JSON version of `requestDto`&#x20;
  * in end you have prepared HTTP request" object ( which is not executed )
* call.enqueue -> used for async execution
* call.execute -> sync call
{% endtab %}
{% endtabs %}

