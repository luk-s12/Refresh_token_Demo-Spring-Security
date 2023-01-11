-- ROLES ---

INSERT INTO roles(name)VALUES ('ROLE_USER');
INSERT INTO roles(name)VALUES ('ROLE_ADMIN');

-- USUARIOS ---

INSERT INTO users(  create_date, email, enabled, first_name, last_name, password, username) 
VALUES ('2022-12-22T22:45:15.677034072Z' , 'admin@gmail.com', true, 'tu_nombre', 'tu_apellido', '$2a$10$W2bJRQeAUMwtw06Y4Oz3ouZGohIvd.y3rxJV6m5mTbRoRUeDQ9rki', 'admin');

INSERT INTO users(  create_date, email, enabled, first_name, last_name, password, username) 
VALUES ('2022-12-22T22:45:15.677034072Z' , 'user@gmail.com', true, 'tu_nombre', 'tu_apellido', '$2a$10$igD4P3UFaqwT6We.brjRuu6dvK/njsvtb9ZkPdzLh1JkxPUurcOIS', 'user');

-- USER ROLES ---

INSERT INTO users_roles(user_id, rol_name)VALUES (1, 'ROLE_USER');
INSERT INTO users_roles(user_id, rol_name)VALUES (1, 'ROLE_ADMIN');

INSERT INTO users_roles(user_id, rol_name)VALUES (2, 'ROLE_USER');
