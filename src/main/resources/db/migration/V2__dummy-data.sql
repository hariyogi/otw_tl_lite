-- V2__Insert_Seed_Data.sql

-- Insert Users (Password: password123)
INSERT INTO users (id, name, email, password, role)
VALUES ('u-admin-001', 'Super Admin', 'admin@otwtl.com', '$2a$10$8.uXF3.Sqr.m7F7.jX.Auef8.h8e8.jX.Auef8.h8e8.jX.Aue',
        'ADMIN'),
       ('u-driver-001', 'Budi Driver', 'budi@otwtl.com', '$2a$10$8.uXF3.Sqr.m7F7.jX.Auef8.h8e8.jX.Auef8.h8e8.jX.Aue',
        'DRIVER'),
       ('u-user-001', 'Bimbim User', 'bimbim@example.com', '$2a$10$8.uXF3.Sqr.m7F7.jX.Auef8.h8e8.jX.Auef8.h8e8.jX.Aue',
        'USER');

-- Insert Sample Locations
INSERT INTO locations (id, name, address, is_active)
VALUES ('loc-001', 'Stasiun Sudirman', 'Jl. Jenderal Sudirman No.1, Jakarta Pusat', TRUE),
       ('loc-002', 'Bandara Soekarno-Hatta', 'Tangerang, Banten', TRUE),
       ('loc-003', 'Mall Grand Indonesia', 'Jl. M.H. Thamrin No.1, Jakarta Pusat', TRUE);

-- Insert 1 Sample Order (Optional)
INSERT INTO orders (id, user_id, location_id, status)
VALUES ('ord-001', 'u-user-001', 'loc-001', 'PENDING');