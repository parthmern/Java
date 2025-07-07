alter table booking_review
    drop FOREIGN KEY FK_BOOKING_REVIEW_ON_BOOKING;

alter table passenger_review
    drop FOREIGN KEY FK_PASSENGERREVIEW_ON_ID;

create TABLE car_model
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    created_at   datetime              NOT NULL,
    updated_at   datetime              NOT NULL,
    plate_number VARCHAR(255)          NULL,
    brand        VARCHAR(255)          NULL,
    model        VARCHAR(255)          NULL,
    car_type     VARCHAR(255)          NULL,
    driver_id    BIGINT                NULL,
    color_id     BIGINT                NULL,
    CONSTRAINT pk_carmodel PRIMARY KEY (id)
);

create TABLE color
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime              NOT NULL,
    updated_at datetime              NOT NULL,
    color      VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_color PRIMARY KEY (id)
);

alter table color
    add CONSTRAINT uc_color_color UNIQUE (color);

alter table car_model
    add CONSTRAINT FK_CARMODEL_ON_COLOR FOREIGN KEY (color_id) REFERENCES color (id);

alter table car_model
    add CONSTRAINT FK_CARMODEL_ON_DRIVER FOREIGN KEY (driver_id) REFERENCES driver (id);

drop table booking_review;

drop table passenger_review;