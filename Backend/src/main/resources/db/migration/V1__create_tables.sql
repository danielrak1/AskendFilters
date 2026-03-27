CREATE TABLE filters
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE criteria_items
(
    id                 BIGSERIAL PRIMARY KEY,
    type               VARCHAR(50)  NOT NULL,
    criteria_condition VARCHAR(50)  NOT NULL,
    criteria_value     VARCHAR(255) NOT NULL,
    filter_id          BIGINT REFERENCES filters (id) ON DELETE CASCADE
)
