ALTER TABLE orders DROP FOREIGN KEY fk_orders_location;
ALTER TABLE orders ADD CONSTRAINT fk_orders_location FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE CASCADE ON UPDATE RESTRICT;
