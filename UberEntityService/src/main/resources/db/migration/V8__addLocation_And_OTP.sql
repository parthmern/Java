create TABLE exact_location
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime              NOT NULL,
    updated_at datetime              NOT NULL,
    latitude   DOUBLE                NULL,
    longitude  DOUBLE                NULL,
    CONSTRAINT pk_exactlocation PRIMARY KEY (id)
);

create TABLE named_location
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    created_at        datetime              NOT NULL,
    updated_at        datetime              NOT NULL,
    exact_location_id BIGINT                NULL,
    name              VARCHAR(255)          NULL,
    zipcode           VARCHAR(255)          NULL,
    city              VARCHAR(255)          NULL,
    state             VARCHAR(255)          NULL,
    country           VARCHAR(255)          NULL,
    CONSTRAINT pk_namedlocation PRIMARY KEY (id)
);

create TABLE otp
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    created_at     datetime              NOT NULL,
    updated_at     datetime              NOT NULL,
    code           VARCHAR(255)          NULL,
    sent_to_number VARCHAR(255)          NULL,
    CONSTRAINT pk_otp PRIMARY KEY (id)
);

alter table named_location
    add CONSTRAINT FK_NAMEDLOCATION_ON_EXACTLOCATION FOREIGN KEY (exact_location_id) REFERENCES exact_location (id);
