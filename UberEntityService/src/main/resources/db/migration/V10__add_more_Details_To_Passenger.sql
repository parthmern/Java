alter table passenger
    add active_booking_id BIGINT NULL;

alter table passenger
    add home_id BIGINT NULL;

alter table passenger
    add last_known_location_id BIGINT NULL;

alter table passenger
    add rating DOUBLE NULL;

alter table driver
    add is_available BIT(1) NULL;

alter table driver
    modify is_available BIT(1) NOT NULL;

alter table passenger
    add CONSTRAINT FK_PASSENGER_ON_ACTIVEBOOKING FOREIGN KEY (active_booking_id) REFERENCES booking (id);

alter table passenger
    add CONSTRAINT FK_PASSENGER_ON_HOME FOREIGN KEY (home_id) REFERENCES exact_location (id);

alter table passenger
    add CONSTRAINT FK_PASSENGER_ON_LASTKNOWNLOCATION FOREIGN KEY (last_known_location_id) REFERENCES exact_location (id);