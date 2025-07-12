# Geospatial Data

* there are multiple solutions to save this like _Redis geospatial indexes, mongodb geospatial indexes and also postgres has ssolution for that_
* how this internally works - using algo like QUAD TREE



### how to choose Db - sql or nosql

* whenever you have transactional data liek booking, payment — RDBMS
* easy to use — RDBMS
* good lanagauage sql easy for fast development liek development productivity — RDBMS
* many ORMS support — RDBMS
* ( you cannot scale SQL — wrong statement 100% )



* logs, location, keyValue pair, neo4j graphdb — NOSQL



### UberLocationService

<figure><img src=".gitbook/assets/image (1) (1).png" alt=""><figcaption></figcaption></figure>

<img src=".gitbook/assets/file.excalidraw (3) (1).svg" alt="" class="gitbook-drawing">

### integrating redis

* jedis is javaclient for that
*

    ```
    implementation "redis.clients:jedis:5.1.2"
    ```



* config and controller

```java
@Configuration
public class RedisConfig {

private static final String REDIS_HOST = "redis-11644.c62.us-east-1-4.ec2.redns.redis-cloud.com";
private static final int REDIS_PORT = 11644;
private static final String REDIS_PASSWORD = "YqEuTNiwLlJZZPiEutAKwATnYSifEZt3";

@Bean
public RedisConnectionFactory redisConnectionFactory() {
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
    jedisConnectionFactory.setHostName(REDIS_HOST);
    jedisConnectionFactory.setPort(REDIS_PORT);
    jedisConnectionFactory.setPassword(REDIS_PASSWORD);
    return jedisConnectionFactory;
}

@Bean
public RedisTemplate<String, String> redisTemplate() {
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
    return redisTemplate;
}
}
```

```
// .js
```

— location controller. java

{% tabs %}
{% tab title="JavaScript" %}
```javascript

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
;
```
{% endtab %}

{% tab title="Python" %}
```python
message = "hello world"
print(message)
```
{% endtab %}

{% tab title="Ruby" %}
```ruby
message = "hello world"
puts message
```
{% endtab %}
{% endtabs %}
