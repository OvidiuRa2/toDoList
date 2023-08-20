CREATE TABLE users (
  username VARCHAR(50) NOT NULL,
  password VARCHAR(255) NOT NULL,
  enabled BOOLEAN NOT NULL,
  PRIMARY KEY (username)
);

INSERT INTO users
VALUES
('user', '$2a$10$nc34hRoT3H6KmpqBwm8Y0.2O9F4zorG/R5wKZ3Yw/nUJqeuFQUeGq', TRUE),
('admin', '$2a$10$nc34hRoT3H6KmpqBwm8Y0.2O9F4zorG/R5wKZ3Yw/nUJqeuFQUeGq', TRUE);

CREATE TABLE authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  PRIMARY KEY (username, authority),
  FOREIGN KEY (username) REFERENCES users (username)
);

INSERT INTO authorities
VALUES
('user', 'ROLE_USER'),
('admin', 'ROLE_ADMIN');
