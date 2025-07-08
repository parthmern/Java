alter table booking
    add end_location_id BIGINT NULL;

alter table booking
    add start_location_id BIGINT NULL;

alter table booking
    add CONSTRAINT FK_BOOKING_ON_ENDLOCATION FOREIGN KEY (end_location_id) REFERENCES exact_location (id);

alter table booking
    add CONSTRAINT FK_BOOKING_ON_STARTLOCATION FOREIGN KEY (start_location_id) REFERENCES exact_location (id);