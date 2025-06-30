---
description: famous interview probolem
---

# N+1 problem

> we know api which looks like this\
> GET -> /api/v1/drivers/bookings\
> Req-Body : \
> `{`
>
> `driver_ids : [ 2,3 4,5 ]`
>
> `}`
>
> res: we should able to fetch booking of each and every driver

driver details may be specific req for other apis

so lazy loading is be the good option here&#x20;

{% hint style="info" %}
1\) List\<Driver<> drivers findAllByIds( List\<Number> ids );

* select \* from Driver where id in \[ 1, 2 ,3]\


2\) for( driver : drivers )\
{\
&#x20;  driver.getBooking();\
}
{% endhint %}

* when we have 1 driver it is ok
* but when u have n drivers you are calling n times to DB\
  Time complexity in sql kind of\
  this problme called as **`N+1`  problem**



```java
List<Long> driverIds = new ArrayList<>(Arrays.asList(1L,2L,3L,4L,5L));
List<Driver> drivers = driverRepository.findAllByIdIn(driverIds);  

        for(Driver driver : drivers){
            List<Booking> bookings = driver.getBookings();
            bookings.forEach(booking -> System.out.println(
                    booking.getId()
            ));
        }
```

* issue n+1 queries here

How to optimize it ? ( TC )

{% tabs %}
{% tab title="DriverRepo" %}
```javascript
  List<Driver> findAllByIdIn(List<Long> driverIds);
```
{% endtab %}

{% tab title="BookingRepo" %}
```java
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByDriverIn(List<Driver> drivers);
}
```
{% endtab %}

{% tab title="Service" %}
```python
List<Long> driverIds = new ArrayList<>(Arrays.asList(1L,2L,3L,4L,5L));
        List<Driver> drivers = driverRepository.findAllByIdIn(driverIds);   // select d1_0.id,d1_0.created_at,d1_0.license_number,d1_0.name,d1_0.updated_at from driver d1_0 where d1_0.id in (?,?)

        List<Booking> bookings = bookingRepository.findAllByDriverIn(drivers);
```
{% endtab %}
{% endtabs %}

<figure><img src=".gitbook/assets/image (3) (1) (1) (1).png" alt=""><figcaption></figcaption></figure>

* still we are not getting in proper way so how to fix this

{% hint style="info" %}
- add @Transactional annotaiton on top of the class inside which we are fetching this \
  \- this annotation means whatever query are goign to execute inside the method are going to be part of one complete tranasaction
{% endhint %}

<figure><img src=".gitbook/assets/image (2) (1) (1) (1) (1).png" alt=""><figcaption></figcaption></figure>

* within 2 query only we are able to fetch all data

#### another way proper using FetchMode.SUBSELECT

* make sure to set fetchtype = "lazy" for each and every relationship
* then use fetchmode.subselect&#x20;
*

    <pre><code><strong>@OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    </strong>@Fetch(value = FetchMode.SUBSELECT)
    private List&#x3C;Booking> bookings = new ArrayList&#x3C;>();
    </code></pre>
* then make sure to use @Transactional annotaion of top of the method in which you are calling it
*

    <figure><img src=".gitbook/assets/image (3) (1) (1) (1) (1).png" alt=""><figcaption></figcaption></figure>

