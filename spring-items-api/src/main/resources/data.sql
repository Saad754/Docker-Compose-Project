-- Seed data: 3 sample items inserted on every startup.
-- INSERT IGNORE prevents duplicate-key errors on subsequent restarts
-- when spring.jpa.hibernate.ddl-auto=update (table already exists).

INSERT IGNORE INTO items (id, name) VALUES (1, 'Laptop');
INSERT IGNORE INTO items (id, name) VALUES (2, 'Wireless Mouse');
INSERT IGNORE INTO items (id, name) VALUES (3, 'Mechanical Keyboard');
