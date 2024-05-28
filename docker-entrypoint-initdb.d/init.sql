-- Create a Role Table
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create a User Table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL
);

-- Create a table to link users and roles
CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);

-- Create a Carriers Table
CREATE TABLE carriers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20)
);

-- Create a Route Table
CREATE TABLE routes (
    id SERIAL PRIMARY KEY,
    departure_point VARCHAR(255) NOT NULL,
    destination_point VARCHAR(255) NOT NULL,
    carrier_id INT NOT NULL,
    duration_minutes INT NOT NULL,
    FOREIGN KEY (carrier_id) REFERENCES carriers(id)
);

-- Create a Ticket Table
CREATE TABLE tickets (
    id SERIAL PRIMARY KEY,
    route_id INT NOT NULL,
    travel_date TIMESTAMP NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (route_id) REFERENCES routes(id)
);