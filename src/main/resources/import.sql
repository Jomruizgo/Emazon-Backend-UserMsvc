--roles
INSERT INTO roles (role_name, role_description) VALUES ('ADMINISTRATOR', 'This is the administrator role.');
INSERT INTO roles (role_name, role_description) VALUES ('WAREHOUSE_ASSISTANT', 'This is the warehouse assistant role.');
INSERT INTO roles (role_name, role_description) VALUES ('CLIENT', 'This is the client role.');

--Users
INSERT INTO users (document_id, name, last_name, mobile_number, birthdate, email, password, is_active, created_at, role_id) VALUES (1001234567, 'Juan', 'Pérez', '3001234567', '1990-05-15', 'juan.perez@email.com', '$2y$10$gMPPMMtUrYxjoakPQCLnUeA/w/L.bFEcugcnUwimo5kCx7yhIZmeq', true, '2024-09-13 10:00:00', 1); -- Contraseña: password123
INSERT INTO users (document_id, name, last_name, mobile_number, birthdate, email, password, is_active, created_at, role_id) VALUES (1002345678, 'María', 'González', '3002345678', '1988-09-22', 'maria.gonzalez@email.com', '$2y$10$gMPPMMtUrYxjoakPQCLnUeA/w/L.bFEcugcnUwimo5kCx7yhIZmeq', true, '2024-09-13 11:00:00', 2); -- Contraseña: password123
INSERT INTO users (document_id, name, last_name, mobile_number, birthdate, email, password, is_active, created_at, role_id) VALUES (1003456789, 'Carlos', 'Rodríguez', '3003456789', '1995-03-10', 'carlos.rodriguez@email.com', '$2y$10$gMPPMMtUrYxjoakPQCLnUeA/w/L.bFEcugcnUwimo5kCx7yhIZmeq', true, '2024-09-13 12:00:00', 3); -- Contraseña: password123
INSERT INTO users (document_id, name, last_name, mobile_number, birthdate, email, password, is_active, created_at, role_id) VALUES (1004567890, 'Ana', 'Martínez', '3004567890', '1992-11-30', 'ana.martinez@email.com', '$2y$10$gMPPMMtUrYxjoakPQCLnUeA/w/L.bFEcugcnUwimo5kCx7yhIZmeq', true, '2024-09-13 13:00:00', 1); -- Contraseña: password123
INSERT INTO users (document_id, name, last_name, mobile_number, birthdate, email, password, is_active, created_at, role_id) VALUES (1005678901, 'Luis', 'López', '3005678901', '1987-07-05', 'luis.lopez@email.com', '$2y$10$gMPPMMtUrYxjoakPQCLnUeA/w/L.bFEcugcnUwimo5kCx7yhIZmeq', true, '2024-09-13 14:00:00', 2); -- Contraseña: password123

