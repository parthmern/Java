alter table booking
    drop FOREIGN KEY FK_BOOKING_ON_REVIEW;

alter table booking_review
    add booking_id BIGINT NULL;

alter table driver
    add phone_number VARCHAR(255) NULL;

alter table booking_review
    add CONSTRAINT FK_BOOKING_REVIEW_ON_BOOKING FOREIGN KEY (booking_id) REFERENCES booking (id);

alter table booking
    drop COLUMN review_id;