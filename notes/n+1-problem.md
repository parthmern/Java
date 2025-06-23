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



