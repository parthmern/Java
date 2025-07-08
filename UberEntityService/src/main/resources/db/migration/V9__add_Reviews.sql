alter table driver
    add active_city VARCHAR(255) NULL;

alter table driver
    add driver_approval_status VARCHAR(255) NULL;

alter table driver
    add home_id BIGINT NULL;

alter table driver
    add last_known_location_id BIGINT NULL;

alter table driver
    add rating DOUBLE NULL;

alter table driver
    add CONSTRAINT FK_DRIVER_ON_HOME FOREIGN KEY (home_id) REFERENCES exact_location (id);

alter table driver
    add CONSTRAINT FK_DRIVER_ON_LASTKNOWNLOCATION FOREIGN KEY (last_known_location_id) REFERENCES exact_location (id);

--alter table named_location
--    add CONSTRAINT FK_NAMEDLOCATION_ON_EXACTLOCATION FOREIGN KEY (exact_location_id) REFERENCES exact_location (id);