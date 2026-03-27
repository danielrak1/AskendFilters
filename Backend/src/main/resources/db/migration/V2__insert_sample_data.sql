INSERT INTO filters(name) VALUES ( 'Amount Filter');

INSERT INTO criteria_items(type, criteria_condition, criteria_value, filter_id)
VALUES ('AMOUNT', 'GREATER_THAN', '100',
        (SELECT id FROM filters WHERE name = 'Amount Filter'));

INSERT INTO filters(name) VALUES ( 'Title Filter');

INSERT INTO criteria_items(type, criteria_condition, criteria_value, filter_id)
VALUES ('TITLE', 'STARTS_WITH', 'HELLO',
        (SELECT id FROM filters WHERE name = 'Title Filter'));
