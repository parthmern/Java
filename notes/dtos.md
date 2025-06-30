# DTOs

{% tabs %}
{% tab title="ReviewClass" %}
```javascript
public class Review extends BaseModel {

    @Column(nullable = false)
    private String content;

    private Double rating;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Booking booking; // we have defined a 1:1 relationship between booking and review
}
```
{% endtab %}

{% tab title="Controller" %}
```ruby
@PostMapping
    public ResponseEntity<Review> publishReview(@RequestBody Review request) {
        Review review = this.reviewService.publishReview(request);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }
```
{% endtab %}
{% endtabs %}

<figure><img src=".gitbook/assets/image (9).png" alt=""><figcaption></figcaption></figure>

* it is not able to identify by the Review class which has `Booking booking`  which is object ( that is not able to understand by spring)
* there is concept called as DTO ( data transfer obj )
* our apis strcutured in a way that needs booking object that doesnot understand booking as string
* for that we need intermediate level that called as DTOs

{% hint style="info" %}
client donot know the review object
{% endhint %}

### how to make

<figure><img src=".gitbook/assets/image (10).png" alt=""><figcaption></figcaption></figure>

* you can also do that
*

    <figure><img src=".gitbook/assets/image (11).png" alt=""><figcaption></figcaption></figure>
* i am doing manually

<figure><img src=".gitbook/assets/image (12).png" alt=""><figcaption></figcaption></figure>

{% tabs %}
{% tab title="CreateReviewDtoToReviewAdapter-interface" %}
```javascript
public interface CreateReviewDtoToReviewAdapter {
    public Review convertDto(CreateReviewDto createReviewDto);
}
```
{% endtab %}

{% tab title="CreateReviewDtoToReviewAdapterImpl" %}
```python

@Component
public class CreateReviewDtoToReviewAdapterImpl implements CreateReviewDtoToReviewAdapter {

    private BookingRepository bookingRepository;

    public CreateReviewDtoToReviewAdapterImpl(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Review convertDto(CreateReviewDto createReviewDto) {
        Optional<Booking> booking = bookingRepository.findById(createReviewDto.getBookingId());
        if(booking.isEmpty()){
            return null;
        }
        Review review = Review.builder().rating(createReviewDto.getRating()).booking(booking.get()).content(createReviewDto.getContent()).build();
        return review;
    }
};
```
{% endtab %}

{% tab title="ReviewController" %}
```ruby
@PostMapping
    public ResponseEntity<?> publishReview(@RequestBody CreateReviewDto request) {
        Review incomingReview = this.createReviewDtoToReviewAdapter.convertDto(request);
        if(incomingReview == null){
            return new ResponseEntity<>("Invalid arguments", HttpStatus.BAD_REQUEST);
        }
        Review review = this.reviewService.publishReview(incomingReview);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }
```
{% endtab %}
{% endtabs %}

{% hint style="info" %}
reviewcontroller ? -> wildcard

This controller method can return a response entity containing **any type** of object\
multiple possible body types
{% endhint %}

#### issue -> getting res obj as recursively ( 2000+ lines)

<figure><img src=".gitbook/assets/image (13).png" alt="" width="563"><figcaption></figcaption></figure>

* when we are fetching Booking and Drivers it should not recursively fetching this type of thigs
* inside Review , oneToOne mapping which is eager fetching method so make it as lazy
* solve it by adding `@JsonBackReference`  on top of @onetoone, @oneto many, @manytoon ....&#x20;
* OR you can add it on top of class with @JsonBackReference(
* read article about it\
  \

* another method by making _**response dto**_

{% tabs %}
{% tab title="ReviewDto" %}
```javascript
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ReviewDto {
    private Long id;
    private String content;
    private Double rating;
    private Long booking;
    private Date createdAt;
    private Date updatedAt;
}

```
{% endtab %}

{% tab title="postmethod-controller" %}
```java
 @PostMapping
    public ResponseEntity<?> publishReview(@RequestBody CreateReviewDto request) {
        Review incomingReview = this.createReviewDtoToReviewAdapter.convertDto(request);
        if(incomingReview == null){
            return new ResponseEntity<>("Invalid arguments", HttpStatus.BAD_REQUEST);
        }
        Review review = this.reviewService.publishReview(incomingReview);
        System.out.println(review);
        ReviewDto response = ReviewDto.builder().id(review.getId()).content(review.getContent()).booking(review.getBooking().getId()).rating(review.getRating()).createdAt(review.getCreatedAt()).updatedAt(review.getUpdatedAt()).build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
```
{% endtab %}
{% endtabs %}

<figure><img src=".gitbook/assets/image (14).png" alt="" width="563"><figcaption></figcaption></figure>

