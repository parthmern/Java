alter table booking
    drop FOREIGN KEY FK_BOOKING_ON_REVIEW;

alter table booking_review
    add booking_id BIGINT NULL;

alter table booking_review
    modify booking_id BIGINT not NULL;

alter table booking_review
    add CONSTRAINT uc_booking_review_booking UNIQUE (booking_id);

alter table booking_review
    add CONSTRAINT FK_BOOKING_REVIEW_ON_BOOKING FOREIGN KEY (booking_id) REFERENCES booking (id);

alter table booking
    drop COLUMN review_id;