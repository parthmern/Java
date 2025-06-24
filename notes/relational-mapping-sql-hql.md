# Relational mapping, sql, hql

* in many to many rel, there is _through table ( connecting\_table )_&#x20;
* like doctor | patient
* one doctor has many patient | one patient has many doctos
* through table \[ doc\_id, pat\_id ] here det of both id makes single row unique

### many to many

{% tabs %}
{% tab title="Course" %}
```python
public class Course extends BaseModel{

    private String name;

    private String rollNo;

    @ManyToMany
    private List<Student> students = new ArrayList<>();
}

```
{% endtab %}

{% tab title="Student" %}
```javascript

public class Student extends BaseModel {

    private String name;

    @ManyToMany
    @JoinTable(
            name = "course_students",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();
}

```
{% endtab %}
{% endtabs %}

### jpa query

{% embed url="https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html" %}

{% tabs %}
{% tab title="Service" %}
```java
  Optional<Driver> driver = driverRepository.findByIdAndLicenseNumber(1L, "DJid3003");

        if(driver.isPresent()){
            System.out.println(driver.get().getName());
            List<Booking> bookings = bookingRepository.findAllByDriverId(1L);
            for (Booking booking: bookings){
                System.out.println(booking.getBookingStatus());
            }
        }
```
{% endtab %}

{% tab title="BookingRepository" %}
```java
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByDriverId(Long driverId);
}
```
{% endtab %}

{% tab title="DriverRepository" %}
```java
public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByIdAndLicenseNumber(long id, String license_number);
}

```
{% endtab %}
{% endtabs %}

<figure><img src=".gitbook/assets/image (25).png" alt=""><figcaption></figcaption></figure>

it is doing left join by it self&#x20;

{% hint style="info" %}
main thing is to write function name in repository level is important

i attached the notes above and intellij also suggest but in my machine it is not working
{% endhint %}

* here when we are fetching the driver&#x20;
* it is array which has only ids

<figure><img src=".gitbook/assets/image (26).png" alt=""><figcaption></figcaption></figure>

* it is giving me only driver details
* ( in word of mongoose it is like not populating the ids )
* here <mark style="background-color:red;">fetch mode is LAZY</mark>&#x20;
* like Driver details has&#x20;

### fetch mode "eager"

```java
// Drivermodel
@OneToMany(mappedBy = "driver", fetch = FetchType.EAGER)
    private List<Booking> bookings = new ArrayList<>();
    
```

```
Optional<Driver> driver = driverRepository.findById(1L);
```

here trying to fetch all details

<figure><img src=".gitbook/assets/image (27).png" alt=""><figcaption></figcaption></figure>

{% hint style="info" %}
oneToOne rel -> default fetch mode is eager

one to many -> lazy

many to  many -> lazy

many to one -> eager
{% endhint %}

to enable lazy loading + eager in spjpa  (Spring Boot - Transaction Management Using @Transactional Annotation)

```
spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true
```

```java
Optional<Driver> driver = driverRepository.findById(1L);

if(driver.isPresent()){
    
    List<Booking> bookings = driver.get().getBookings();
    for (Booking booking: bookings){
        System.out.println(booking.getBookingStatus());
    }
}
```

here you have driver which is by lazyloading

from driver you can get bookings with eager loading

### Query

* when you have too many tables like 30-40 in this scenaroio it is too hard to work with this kind of srping data query&#x20;
* so we have to option RAW SQL QUERY and Hibernate Query Language

### RAW query

* optional of driver

```java
 @Query(nativeQuery = true, value = "SELECT * FROM driver WHERE id= :id AND license_number = :license" )
    Optional<Driver> rawFindByIdAndLicenseNumber(Long id, String license);
```

<figure><img src=".gitbook/assets/image (28).png" alt=""><figcaption></figcaption></figure>

issue : here sql query issue error cannot detect at compile time so it can give error on runntime

### HQL&#x20;

-can give you compile time error

```
@Query("SELECT d FROM Driver d WHERE d.id = :id AND d.licenseNumber = :license")
    Optional<Driver> hqaFindByIdAndLicenseNumber(Long id, String license);
```

```java
    @Query("SELECT d FROM Driver d WHERE d.id = :id AND d.licenseNumber = :ln")
    Optional<Driver> hqaFindByIdAndLicenseNumber(@Param("id") Long id, @Param("ln") String ln);
```

<figure><img src=".gitbook/assets/image (29).png" alt=""><figcaption></figcaption></figure>

* here while receving the Driver we need all details mean whole table that we need to fetch
* but what if we want to fetch only some collums not the whole table then how ?

{% tabs %}
{% tab title="JavaScript" %}
```javascript
public class DriverBasicInfo {
    private String name;
    private String licenseNumber;

    public DriverBasicInfo(String name, String licenseNumber) {
        this.name = name;
        this.licenseNumber = licenseNumber;
    }

    // Getters (and setters if needed)
}


```
{% endtab %}

{% tab title="Python" %}
```java
@Query("SELECT new com.example.dto.DriverBasicInfo(d.name, d.licenseNumber) FROM Driver d WHERE d.id = :id")
Optional<DriverBasicInfo> findDriverBasicInfoById(@Param("id") Long id);
```
{% endtab %}
{% endtabs %}

{% hint style="info" %}
**Note:** You must use the full-qualified class name in `SELECT new ...` and the DTO **must have a matching constructor**.

and this DriverBasicInfo alsoo known as DTO&#x20;
{% endhint %}

