create TABLE booking
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    created_at     datetime              NOT NULL,
    updated_at     datetime              NOT NULL,
    review_id      BIGINT                NULL,
    booking_status VARCHAR(255)          NULL,
    start_time     datetime              NULL,
    end_time       datetime              NULL,
    total_distance BIGINT                NULL,
    driver_id      BIGINT                NULL,
    passenger_id   BIGINT                NULL,
    CONSTRAINT pk_booking PRIMARY KEY (id)
);

create TABLE booking_review
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime              NOT NULL,
    updated_at datetime              NOT NULL,
    content    VARCHAR(255)          NOT NULL,
    rating     DOUBLE                NULL,
    CONSTRAINT pk_booking_review PRIMARY KEY (id)
);

create TABLE driver
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    created_at     datetime              NOT NULL,
    updated_at     datetime              NOT NULL,
    name           VARCHAR(255)          NULL,
    license_number VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_driver PRIMARY KEY (id)
);

create TABLE passenger
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime              NOT NULL,
    updated_at datetime              NOT NULL,
    name       VARCHAR(255)          NULL,
    CONSTRAINT pk_passenger PRIMARY KEY (id)
);

create TABLE passenger_review
(
    id                        BIGINT       NOT NULL,
    passengeer_review_content VARCHAR(255) NULL,
    passenger_rating          VARCHAR(255) NULL,
    CONSTRAINT pk_passengerreview PRIMARY KEY (id)
);

alter table driver
    add CONSTRAINT uc_driver_licensenumber UNIQUE (license_number);

alter table booking
    add CONSTRAINT FK_BOOKING_ON_DRIVER FOREIGN KEY (driver_id) REFERENCES driver (id);

alter table booking
    add CONSTRAINT FK_BOOKING_ON_PASSENGER FOREIGN KEY (passenger_id) REFERENCES passenger (id);

alter table booking
    add CONSTRAINT FK_BOOKING_ON_REVIEW FOREIGN KEY (review_id) REFERENCES booking_review (id);

alter table passenger_review
    add CONSTRAINT FK_PASSENGERREVIEW_ON_ID FOREIGN KEY (id) REFERENCES booking_review (id);