# inheritance | table rel

## inheritance concept in spring data JPA

### 1. mapped super class

<figure><img src=".gitbook/assets/image (14) (1).png" alt=""><figcaption></figcaption></figure>

* This means `BaseModel` fields will be **inherited into Review** and its subclasses.
* `BaseModel` **does not become its own table** &#x20;

`@MappedSuperclass`   this is annotation for Parentclass/abstract class here

{% tabs %}
{% tab title="Base.java" %}
<pre class="language-java"><code class="lang-java">@Getter 
@Setter
@EntityListeners(AuditingEntityListener.class)  // to solve error of null date with createdAt,updatedAt
<a data-footnote-ref href="#user-content-fn-1">@MappedSuperclass</a>
public abstract class BaseModel {

    @Id     // this annotation makes PK of table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY means auto_increment
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP) // format of date obj to be stored ex. TIME, DATE, TIMESTAMP
    @CreatedDate    // handle it only when object creation
    protected Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate   // only handle it for object update
    protected Date updatedAt;

}

</code></pre>
{% endtab %}

{% tab title="Child.java" %}
<pre class="language-python"><code class="lang-python">

<strong>@Getter // lombok - adding all getters func
</strong>@Setter // lombok - adding all setter func
@Builder   // lombok - builder pattern
@NoArgsConstructor  // lombok - default constructor without args ( needed to builder pattern/lombok
@AllArgsConstructor // lombok - all arg const. ( needed to builder pattern/lombok

@Entity    // database table JPA
@Table(name = "bookingreview") // optional else class_name = table_name
public class Review extends BaseModel {

    @Column(nullable = false)
    private String content;

    private Double rating;

    @Override
    public String toString(){
        return "Review: " + this.content + " " + this.rating + " " + this.createdAt;
    }

}
// new review(content, rating, createdAt, updatedAt)
</code></pre>
{% endtab %}
{% endtabs %}

this thing giving me error like&#x20;

> Caused by: org.hibernate.AnnotationException: Entity 'com.example.uberreviewservice.models.Review' has no identifier (every '@Entity' class must declare or inherit at least one '@Id' or '@EmbeddedId' property)

* here your mysql do not know about this inheritance concepts
* so hwo to solve
* <mark style="background-color:blue;">add</mark> <mark style="background-color:blue;"></mark><mark style="background-color:blue;">`@MappedSuperclass`</mark>  <mark style="background-color:blue;"></mark><mark style="background-color:blue;">inside the</mark> <mark style="background-color:blue;"></mark>_<mark style="background-color:blue;">BaseClass.java</mark>_&#x20;
  * waht does this annotation means?
  * no table for the parent class.
  * one table for each child class having its own attributes and parent class attributes

<table><thead><tr><th width="82.66668701171875">id</th><th width="120.66668701171875">createdAt</th><th width="94">updatedAt</th><th width="102.33331298828125">content</th><th>rating</th></tr></thead><tbody><tr><td></td><td></td><td></td><td></td><td></td></tr></tbody></table>

***

***

## another types ( inheritance )&#x20;

### 2. single table

<figure><img src=".gitbook/assets/image (15) (1).png" alt=""><figcaption></figcaption></figure>

* ```
  @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
  ```

{% tabs %}
{% tab title="BaseModel" %}
```java
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)  // to solve error of null date with createdAt,updatedAt
@MappedSuperclass
public abstract class BaseModel {

    @Id     // this annotation makes PK of table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY means auto_increment
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP) // format of date obj to be stored ex. TIME, DATE, TIMESTAMP
    @CreatedDate    // handle it only when object creation
    protected Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate   // only handle it for object update
    protected Date updatedAt;

}

```
{% endtab %}

{% tab title="Review" %}
<pre class="language-python"><code class="lang-python">

<strong>@Getter // lombok - adding all getters func
</strong>@Setter // lombok - adding all setter func
@Builder   // lombok - builder pattern
@NoArgsConstructor  // lombok - default constructor without args ( needed to builder pattern/lombok
@AllArgsConstructor // lombok - all arg const. ( needed to builder pattern/lombok

@Entity
@Table(name = "bookingreview") // optional else class_name = table_name

<a data-footnote-ref href="#user-content-fn-2">@Inheritance(strategy = InheritanceType.SINGLE_TABLE)</a>

public class Review extends BaseModel {

    @Column(nullable = false)
    private String content;

    private Double rating;

    @Override
    public String toString(){
        return "Review: " + this.content + " " + this.rating + " " + this.createdAt;
    }

}
</code></pre>
{% endtab %}

{% tab title="DriverReview" %}
```ruby

@Entity
@Getter
@Setter

public class DriverReview extends Review {
    private String driverReviewContent;
}
```
{% endtab %}

{% tab title="PassengerReview" %}
```
@Entity
@Getter
@Setter
public class PassengerReview extends Review {
    private String passengeerReviewContent;

}
```
{% endtab %}
{% endtabs %}

<figure><img src=".gitbook/assets/image (16).png" alt=""><figcaption></figcaption></figure>

* only **one giant table** created and which has driver\_review\_content and passenger\_review\_content added
* why REVIEW is parent class ? the `@Entity` annotated class with `@Inheritance` is treated as the **parent in the entity hierarchy**, **even if it extends a superclass** like `BaseModel`.

{% hint style="info" %}
issue\
in Child tables if i have to use\
&#x20;\
&#xNAN;_@Column(nullable = false)_\
_private String driverReviewContent;_\
\
_so everytime it expect to paass this attribute "null"_\
_and multiple null in actual table_\
\
**good thing**

with single query you are able to get all details
{% endhint %}

### 3. table per class

* everything same as mapped super class \
  except parent also has dedicated table
* child will have all properties of parents

```
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
```

{% hint style="info" %}
after chainging this getting error "Caused by: jakarta.persistence.PersistenceException: \[PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.MappingException: Cannot use identity column key generation with mapping for: com.example.uberreviewservice.models.DriverReview"\
\
it says now we cannot use auto-incremtn type @id so we need to change it to `@GeneratedValue(strategy = GenerationType.TABLE)`&#x20;

\
means separate table used for autoincrement of id behaviour
{% endhint %}

<figure><img src=".gitbook/assets/image (17).png" alt=""><figcaption></figcaption></figure>

here 3 total tables are created ( hibernet\_seq for auto inc. PK)\


<figure><img src=".gitbook/assets/image (18).png" alt=""><figcaption></figcaption></figure>

* issue : multiple same things redundancy in DB

### 4. joined table

```
@Inheritance(strategy = InheritanceType.JOINED)
```

<figure><img src=".gitbook/assets/image (19).png" alt=""><figcaption></figcaption></figure>

here id for driver\_review table @id is acting as PK/FK \
bcz Review has id which is PK and for drier\_review that is going to be FK

<figure><img src=".gitbook/assets/image (20).png" alt=""><figcaption></figcaption></figure>

here how to give PK/FK name&#x20;

```
@PrimaryKeyJoinColumn(name = "driver_review_id")
```

_it is in child class_



## Composition

* has-a relationship



### enum storing

{% tabs %}
{% tab title="class" %}
```javascript
 @Enumerated(value = EnumType.STRING)
    private BookingStatus bookingStatus;
```
{% endtab %}

{% tab title="enum" %}
```python
package com.example.uberreviewservice.models;

public enum BookingStatus {
    SCHEDULED,
    CANELLED,
    CAB_ARRIVED,
    ASSIGNING_DRIVER,
    IN_RIDE,
    COMPLETED
}

```
{% endtab %}
{% endtabs %}

* @Enumerated(value = EnumType.STRING) here enum is going to store as "string"&#x20;
* EnumType.ORDINAL means going to store as 0,1,2 ... like mapping with enum table&#x20;

### one to one

* driver has a reiew - one to one rel

{% tabs %}
{% tab title="Booking" %}
```javascript
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Booking extends BaseModel {

    @OneToOne
    private Review driverReview;

    @Enumerated(value = EnumType.STRING)
    private BookingStatus bookingStatus;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date endTime;

    private Long totalDistance;


}
```
{% endtab %}
{% endtabs %}

```
@OneToOne
private Review driverReview;
```

<figure><img src=".gitbook/assets/image (21).png" alt=""><figcaption></figcaption></figure>

* here booking can exist without review\
  booking is going to be depend on booking\_Review ( relationships)
* so first you need to create Booking\
  then create booking\_review

```java
Review r = Review.builder().content("new good").rating(4.0).build();
Booking b = Booking.builder().review(r).endTime(new Date()).build();
reviewRepository.save(r);
bookingRepository.save(b);
```

( you cannot create "b" first )

#### cascading

```
@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
```

* what persist means

```
@OneToOne(cascade = CascadeType.PERSIST)
@JoinColumn(name = "review_id")
private Review review;

```

<figure><img src=".gitbook/assets/image (22).png" alt=""><figcaption></figcaption></figure>

* how does delete works ( same as mysql delete cascades )

### one to many

* a driver has many reviews
* a review belong to driver

1 : n\
driver : review\
here review side has FK ( driver\_id )&#x20;

<figure><img src=".gitbook/assets/image (23).png" alt=""><figcaption></figcaption></figure>

{% tabs %}
{% tab title="Booking" %}
```javascript
public class Booking extends BaseModel {


// n : 1 -> booking : driver ( a booking has one driver )
    @ManyToOne
    private Driver driver;

}
```
{% endtab %}

{% tab title="Driver" %}
```python
public class Driver extends BaseModel {

    private String name;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    // 1 : n -> driver : booking   (a driver has many booking)
    @OneToMany(mappedBy = "driver")
    private List<Booking> bookings = new ArrayList<>();
}

```
{% endtab %}
{% endtabs %}

{% hint style="info" %}
`mappedby`  this property you have to create while establishing relatonship
{% endhint %}

<figure><img src=".gitbook/assets/image (24).png" alt=""><figcaption></figcaption></figure>

[^1]: this is

[^2]: single table strategy

